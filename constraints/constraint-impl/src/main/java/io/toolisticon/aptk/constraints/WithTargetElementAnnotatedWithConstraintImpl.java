package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpiService(value = AnnotationConstraintSpi.class)
public class WithTargetElementAnnotatedWithConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return WithTargetElementAnnotatedWith.class;
    }


    @DeclareCompilerMessage(code = "TARGETMUSTBEANNOTATEDWITH_001", enumValueName = "TARGETMUSTBEANNOTATEDWITH_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Wasn't able to find TypeElement ${1}", processorClass = BasicConstraints.class)
    @DeclareCompilerMessage(code = "TARGETMUSTBEANNOTATEDWITH_002", enumValueName = "TARGETMUSTBEANNOTATEDWITH_ERROR_ELEMENT_IS_NOT_ANNOTATED_WITH", message = "'${0}' Constraint violated: Target Element '${1}' is not annotated with to '${2}'", processorClass = BasicConstraints.class)

    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        WithTargetElementAnnotatedWithWrapper constraintWrapper = WithTargetElementAnnotatedWithWrapper.wrap(annotatedElement, constraintAnnotationMirror);


        ElementWrapper elementToCheck = null;
        switch (constraintWrapper.targetElement()) {

            case ANNOTATED_ELEMENT: {
                elementToCheck = ElementWrapper.wrap(annotatedElement);
                break;
            }
            case TOP_LEVEL_TYPE_ELEMENT: {
                elementToCheck = ElementWrapper.wrap(annotatedElement);
                List<ElementWrapper<Element>> enclosingElements = elementToCheck.getAllEnclosingElements(true);
                List<ElementWrapper<?>> result = enclosingElements
                        .stream()
                        .filter(ElementWrapper::isTypeElement)
                        .filter(e -> !TypeElementWrapper.toTypeElement(e).isNested())
                        .collect(Collectors.toList());

                if (result.size() == 1) {
                    elementToCheck = result.get(0);
                } else {
                    wrappedElement.compilerMessage(annotationMirrorToCheck).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEANNOTATEDWITH_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), constraintWrapper.targetElement());
                }

                break;
            }
            case PARENT_TYPE_ELEMENT: {
                elementToCheck = ElementWrapper.wrap(annotatedElement);

                while (elementToCheck != null && !elementToCheck.isTypeElement()) {
                    Optional<ElementWrapper<Element>> tmpEnclosingElement = elementToCheck.getEnclosingElement();
                    elementToCheck = tmpEnclosingElement.isPresent() ? tmpEnclosingElement.get() : null;
                }

                if (elementToCheck == null) {
                    wrappedElement.compilerMessage(annotationMirrorToCheck).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEANNOTATEDWITH_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), constraintWrapper.targetElement());
                }

                break;
            }

        }

        if (elementToCheck != null) {
            if (!elementToCheck.hasAnnotation(constraintWrapper.valueAsFqn())) {
                wrappedElement.compilerMessage(annotationMirrorToCheck).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEANNOTATEDWITH_ERROR_ELEMENT_IS_NOT_ANNOTATED_WITH, UtilityFunctions.getSimpleName(constraintAnnotationMirror), elementToCheck.getSimpleName(), constraintWrapper.valueAsFqn());
                return false;
            }
        }

        return true;
    }
}