package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Optional;

@SpiService(value = AnnotationConstraintSpi.class)
public class WithIntegerInBoundsConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return WithIntegerInBounds.class;
    }


    @DeclareCompilerMessage(code = "WITHINTEGERBOUNDS_001", enumValueName = "WITHINTEGERBOUNDS_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Annotation attribute ${1} is out of bounds (${2})", processorClass = BasicConstraints.class)
    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        WithIntegerInBoundsWrapper constraintWrapper = WithIntegerInBoundsWrapper.wrap(constraintAnnotationMirror);


        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);


        if (attribute.isPresent()) {

            if (attribute.get().isArray()) {

                for (AnnotationValueWrapper annotationValueWrapper : attribute.get().getArrayValue()) {
                    if (!checkBounds(annotationValueWrapper, constraintWrapper)) {
                        wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint, createConstraintString(constraintWrapper));
                        return false;
                    }

                }

            } else {
                if (!checkBounds(attribute.get(), constraintWrapper)) {
                    wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.WITHINTEGERBOUNDS_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint, createConstraintString(constraintWrapper));
                    return false;
                }
            }


        }


        return true;
    }

    private boolean checkBounds(AnnotationValueWrapper annotationValueWrapper, WithIntegerInBoundsWrapper constraintWrapper) {

        if (annotationValueWrapper.isInteger()) {

            Integer value = annotationValueWrapper.getIntegerValue();

            // check lower bound
            if (!constraintWrapper.lowerBoundIsDefaultValue()
                    && (
                    (constraintWrapper.inclusiveLowerBound() && value < constraintWrapper.lowerBound())
                            || (!constraintWrapper.inclusiveLowerBound() && value <= constraintWrapper.lowerBound())
            )) {
                return false;
            }

            // check upper bound
            if (!constraintWrapper.upperBoundIsDefaultValue()
                    && (
                    (constraintWrapper.inclusiveUpperBound() && value > constraintWrapper.upperBound())
                            || (!constraintWrapper.inclusiveUpperBound() && value >= constraintWrapper.upperBound())
            )) {
                return false;
            }


        }

        return true;

    }

    private String createConstraintString(WithIntegerInBoundsWrapper constraintWrapper) {

        return (!constraintWrapper.lowerBoundIsDefaultValue() ?  constraintWrapper.lowerBound() + " <" + (constraintWrapper.inclusiveLowerBound() ? "" : "=") + " " : "")
                + " value "
                + (!constraintWrapper.upperBoundIsDefaultValue() ? "<" + (constraintWrapper.inclusiveUpperBound() ? "" : "=") +  " " + constraintWrapper.upperBound(): "");


    }
}
