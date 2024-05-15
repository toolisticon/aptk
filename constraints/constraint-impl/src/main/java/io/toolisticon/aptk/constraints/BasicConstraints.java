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
@DeclareCompilerMessage(code = "BASE_005", enumValueName = "BASE_WARNING_INVALID_USAGE_OF_CONSTRAINT", message = "Constraint annotation ${} isn't used correctly and will be ignmored!", processorClass = BasicConstraints.class)
public class BasicConstraints {

    private static BasicConstraints INSTANCE = null;


    private final Map<String, List<AnnotationConstraintSpi>> annotationConstraintSpiMap;
    private final Map<String, AnnotationConstraints> onAnnotationConstraintsLUT = new HashMap<>();

    private final Map<String, List<ManualConstraintSpi>> manualConstraintsLUT;



    public BasicConstraints(Map<String, List<AnnotationConstraintSpi>> annotationConstraintSpiMap, final Map<String, List<ManualConstraintSpi>> manualConstraintSpiMap) {
        this.annotationConstraintSpiMap = annotationConstraintSpiMap;
        this.manualConstraintsLUT = manualConstraintSpiMap;
    }

    public boolean checkForConstraints(ElementWrapper<?> elementWrapper) {

        boolean result = true;

        // must determine constraints of all annotations of passed Element if not already done
        for (AnnotationMirrorWrapper annotationMirror : elementWrapper.getAnnotationMirrors()) {
            String annotationFQN = annotationMirror.asTypeMirror().getQualifiedName();

            // Check for manual constraints
            List<ManualConstraintSpi> manualConstraintSpis = manualConstraintsLUT.get(annotationFQN);
            if (manualConstraintSpis != null) {
                for (ManualConstraintSpi manualConstraintSpi : manualConstraintSpis) {
                    manualConstraintSpi.checkManualConstraints(elementWrapper.unwrap(), elementWrapper.getAnnotationMirror(annotationFQN).get().unwrap());
                }
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

                List<AnnotationConstraintSpi> annotationConstraintSpis = annotationConstraintSpiMap.get(constraintAnnotation.getAnnotationType().toString());

                if (annotationConstraintSpis != null) {
                    for (AnnotationConstraintSpi annotationConstraintSpi : annotationConstraintSpis) {
                        result = result & annotationConstraintSpi.checkConstraints(elementWrapper.unwrap(), annotationMirror.unwrap(), constraintAnnotation.unwrap(), null);
                    }
                } else {
                    elementWrapper.compilerMessage(annotationMirror.unwrap()).asWarning().write(BasicConstraintsCompilerMessages.BASE_WARNING_CONSTRAINT_SPI_IMPLEMENTATION_NOT_FOUND, constraintAnnotation.getAnnotationType().toString(), constraintAnnotation.asTypeMirror().getQualifiedName());
                }
            }

            // on annotation attribute
            for (AnnotationAttributeConstraints annotationAttributeConstraints : annotationConstraints.getConstraintsOnAnnotationAttributes()) {

                // check all constraints on attribute
                for (AnnotationMirrorWrapper constraintAnnotation : annotationAttributeConstraints.getConstraints()) {

                    List<AnnotationConstraintSpi> annotationConstraintSpis = annotationConstraintSpiMap.get(((TypeElement) constraintAnnotation.getAnnotationType().asElement()).getQualifiedName().toString());

                    if (annotationConstraintSpis != null) {
                        for (AnnotationConstraintSpi annotationConstraintSpi : annotationConstraintSpis) {
                            result = result & annotationConstraintSpi.checkConstraints(elementWrapper.unwrap(), annotationMirror.unwrap(), constraintAnnotation.unwrap(), annotationAttributeConstraints.getAttributeName());
                        }
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
                annotationConstraints.addConstraintOnAttribute(annotation);
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
                annotationConstraints.addConstraintOnAttribute(new AnnotationAttributeConstraints(name, detectedConstraints));
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

        // There might be multiple implementations for one single constraint
        // a good example for this could be JPA constraints based on database type

        Map<String, List<AnnotationConstraintSpi>> annotationConstraintSpiMap = new HashMap<>();
        ServiceLoader<AnnotationConstraintSpi> services = ServiceLoader.load(AnnotationConstraintSpi.class, BasicConstraints.class.getClassLoader());

        for (AnnotationConstraintSpi annotationConstraintSpi : services) {
            annotationConstraintSpiMap.computeIfAbsent(annotationConstraintSpi.getSupportedAnnotation().getCanonicalName(), e -> new ArrayList<>()).add(annotationConstraintSpi);
        }

        Map<String, List<ManualConstraintSpi>> manualConstraintSpiMap =  new HashMap<>();
        ServiceLoader<ManualConstraintSpi> manualConstraintServices = ServiceLoader.load(ManualConstraintSpi.class, BasicConstraints.class.getClassLoader());

        for (ManualConstraintSpi manualConstraintSpi : manualConstraintServices) {
            manualConstraintSpiMap.computeIfAbsent(manualConstraintSpi.getSupportedAnnotation().getCanonicalName(), e -> new ArrayList<>()).add(manualConstraintSpi);
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
