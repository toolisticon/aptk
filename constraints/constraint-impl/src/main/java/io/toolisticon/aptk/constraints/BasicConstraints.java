package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;
import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

@DeclareCompilerMessageCodePrefix("BASIC_CONSTRAINTS")
@DeclareCompilerMessage(code = "BASE_002", enumValueName = "BASE_ERROR_MUST_BE_PLACE_ON_ANNOTATION_TYPE", message = "Constraint must be placed on annotation type", processorClass = BasicConstraints.class)
@DeclareCompilerMessage(code = "BASE_003", enumValueName = "BASE_ERROR_MUST_BE_PLACE_ON_ANNOTATION_ATTRIBUTE", message = "Constraint must be placed on annotation attribute", processorClass = BasicConstraints.class)
@DeclareCompilerMessage(code = "BASE_004", enumValueName = "BASE_WARNING_CONSTRAINT_SPI_IMPLEMENTATION_NOT_FOUND", message = "Couldn't find and apply annotation constraint implementation for constraint ${0} in annotation ${1}.", processorClass = BasicConstraints.class)
public class BasicConstraints {

    private static BasicConstraints INSTANCE = null;


    private final Map<String, AnnotationConstraintSpi> annotationConstraintSpiMap;
    private final Map<String, AnnotationConstraints> onAnnotationConstraintsLUT = new HashMap<>();

    private final Map<String, ManualConstraintSpi> manualConstraintsLUT;



    public BasicConstraints(Map<String, AnnotationConstraintSpi> annotationConstraintSpiMap, final Map<String, ManualConstraintSpi> manualConstraintSpiMap) {
        this.annotationConstraintSpiMap = annotationConstraintSpiMap;
        this.manualConstraintsLUT = manualConstraintSpiMap;
    }

    public boolean checkForConstraints(ElementWrapper<?> elementWrapper) {

        boolean result = true;

        // must determine constraints of all annotations of passed Element if not already done
        for (AnnotationMirrorWrapper annotationMirror : elementWrapper.getAnnotationMirrors()) {
            String annotationFQN = annotationMirror.asTypeMirror().getQualifiedName();

            // Check for manual constraints
            ManualConstraintSpi manualConstraintSpi = manualConstraintsLUT.get(annotationFQN);
            if (manualConstraintSpi != null) {
                manualConstraintSpi.checkManualConstraints(elementWrapper.unwrap(),elementWrapper.getAnnotationMirror(annotationFQN).get().unwrap());
            }




            AnnotationConstraints annotationConstraints = onAnnotationConstraintsLUT.get(annotationFQN);

            if (annotationConstraints == null) {

                // annotation hasn't been processed before so determine constraints
                annotationConstraints = determineConstraints(annotationMirror.getAnnotationTypeAsWrappedTypeElement());
                onAnnotationConstraintsLUT.put(annotationFQN, annotationConstraints);

            }

            // now we need to check the constraint
            // on type
            for (AnnotationMirrorWrapper constraintAnnotation : annotationConstraints.getConstraintsOnAnnotationType()) {

                AnnotationConstraintSpi annotationConstraintSpi = annotationConstraintSpiMap.get(constraintAnnotation.getAnnotationType().toString());

                if (annotationConstraintSpi != null) {
                    result = result & annotationConstraintSpi.checkConstraints(elementWrapper.unwrap(), annotationMirror.unwrap(), constraintAnnotation.unwrap(), null);
                } else {
                    elementWrapper.compilerMessage(annotationMirror.unwrap()).asWarning().write(BasicConstraintsCompilerMessages.BASE_WARNING_CONSTRAINT_SPI_IMPLEMENTATION_NOT_FOUND, constraintAnnotation.getAnnotationType().toString(), constraintAnnotation.asTypeMirror().getQualifiedName());
                }
            }

            // on annotation attribute
            for (AnnotationAttributeConstraints annotationAttributeConstraints : annotationConstraints.getConstraintsOnAnnotationAttributes()) {

                // check all constraints on attribute
                for (AnnotationMirrorWrapper constraintAnnotation : annotationAttributeConstraints.getConstraints()) {

                    AnnotationConstraintSpi annotationConstraintSpi = annotationConstraintSpiMap.get(((TypeElement) constraintAnnotation.getAnnotationType().asElement()).getQualifiedName().toString());

                    if (annotationConstraintSpi != null) {
                        result = result & annotationConstraintSpi.checkConstraints(elementWrapper.unwrap(), annotationMirror.unwrap(), constraintAnnotation.unwrap(), annotationAttributeConstraints.getAttributeName());
                    } else {
                        elementWrapper.compilerMessage(annotationMirror.unwrap()).asWarning().write(BasicConstraintsCompilerMessages.BASE_WARNING_CONSTRAINT_SPI_IMPLEMENTATION_NOT_FOUND, constraintAnnotation.getAnnotationType().toString(), constraintAnnotation.asTypeMirror().getQualifiedName());
                    }


                }


            }


        }


        return result;

    }


    private AnnotationConstraints determineConstraints(TypeElementWrapper annotationTypeElement) {

        // This will be done in two phases
        // PHASE 1 : check for constraints on annotation  type
        AnnotationConstraints annotationConstraints = new AnnotationConstraints(annotationTypeElement);
        for (AnnotationMirrorWrapper annotation : annotationTypeElement.getAnnotationMirrors()) {

            if (hasConstraintAnnotationOnTypeElement(annotation)) {
                annotationConstraints.addConstraintOnType(annotation);
            }

        }


        // PHASE 2 : check for constraints on Annotation attributes
        List<ExecutableElement> executableElements = ElementUtils.CastElement.castElementList(ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(annotationTypeElement.unwrap(), ElementKind.METHOD), ExecutableElement.class);

        for (ExecutableElement executableElement : executableElements) {
            String name = executableElement.getSimpleName().toString();

            List<AnnotationMirrorWrapper> detectedConstraints = new ArrayList<>();

            for (AnnotationMirror annotationMirror : executableElement.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().asElement().getAnnotation(Constraint.class) != null) {
                    detectedConstraints.add(AnnotationMirrorWrapper.wrap(annotationMirror));

                }
            }


            if (detectedConstraints.size() > 0) {
                // detected constraint
                annotationConstraints.addConstraintOnType(new AnnotationAttributeConstraints(name, detectedConstraints));
            }

        }


        return annotationConstraints;
    }

    /**
     * Check if annotation is annotated with Constraint annotation.
     *
     * @param annotationMirror the annotation to check
     * @return true if annotation has a constraint
     */
    protected boolean hasConstraintAnnotationOnTypeElement(AnnotationMirrorWrapper annotationMirror) {

        return AnnotationUtils.getAnnotationMirror(annotationMirror.getAnnotationType().asElement(), Constraint.class) != null;

    }


    static BasicConstraints getInstance() {

        Map<String, AnnotationConstraintSpi> annotationConstraintSpiMap = new HashMap<String, AnnotationConstraintSpi>();

        ServiceLoader<AnnotationConstraintSpi> services = ServiceLoader.load(AnnotationConstraintSpi.class, BasicConstraints.class.getClassLoader());

        for (AnnotationConstraintSpi annotationConstraintSpi : services) {
            annotationConstraintSpiMap.put(annotationConstraintSpi.getSupportedAnnotation().getCanonicalName(), annotationConstraintSpi);
        }


        Map<String, ManualConstraintSpi> manualConstraintSpiMap =  new HashMap<>();
        ServiceLoader<ManualConstraintSpi> manualConstraintServices = ServiceLoader.load(ManualConstraintSpi.class, BasicConstraints.class.getClassLoader());

        for (ManualConstraintSpi manualConstraintSpi : manualConstraintServices) {
            manualConstraintSpiMap.put(manualConstraintSpi.getSupportedAnnotation().getCanonicalName(), manualConstraintSpi);
        }
        return new BasicConstraints(annotationConstraintSpiMap, manualConstraintSpiMap);
    }

    public static boolean checkConstraints(Element element) {

        return checkConstraints(ElementWrapper.wrap(element));

    }

    public static boolean checkConstraints(ElementWrapper<?> element) {

        if (INSTANCE == null) {
            INSTANCE = getInstance();
        }

        return INSTANCE.checkForConstraints(element);

    }
}
