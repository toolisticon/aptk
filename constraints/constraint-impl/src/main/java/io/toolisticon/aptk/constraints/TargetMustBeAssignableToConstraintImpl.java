package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
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
public class TargetMustBeAssignableToConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return TargetMustBeAssignableTo.class;
    }


    @DeclareCompilerMessage(code = "TARGETMUSTBEASSIGNABLE_001", enumValueName = "TARGETMUSTBEASSIGNABLE_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Wasn't able to find TypeElement ${1}", processorClass = BasicConstraints.class)
    @DeclareCompilerMessage(code = "TARGETMUSTBEASSIGNABLE_002", enumValueName = "TARGETMUSTBEASSIGNABLE_ERROR_ELEMENT_IS_NOT_ASSIGNABLE", message = "'${0}' Constraint violated: Target Element '${1}' is not assignable to '${2}'", processorClass = BasicConstraints.class)

    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        TargetMustBeAssignableToWrapper constraintWrapper = TargetMustBeAssignableToWrapper.wrap(annotatedElement, constraintAnnotationMirror);
        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);


        ElementWrapper elementToCheck = null;
        switch (constraintWrapper.targetElement()) {

            case ANNOTATED_ELEMENT: {
                elementToCheck = ElementWrapper.wrap(annotatedElement);
                if (!elementToCheck.isTypeElement()) {
                    // must trigger error
                    wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), constraintWrapper.targetElement());
                }
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
                    wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), constraintWrapper.targetElement());
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
                    wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), constraintWrapper.targetElement());
                }

                break;
            }

        }

        if (elementToCheck != null) {
            if (!TypeElementWrapper.toTypeElement(elementToCheck).asType().isAssignableTo(attribute.get().getClassValue())) {
                wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.TARGETMUSTBEASSIGNABLE_ERROR_ELEMENT_IS_NOT_ASSIGNABLE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), elementToCheck.getSimpleName(), attribute.get().getClassValue().getQualifiedName());
                return false;
            }
        }

        return true;
    }
}