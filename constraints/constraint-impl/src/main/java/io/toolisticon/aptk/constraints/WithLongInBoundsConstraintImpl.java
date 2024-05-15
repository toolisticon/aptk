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
public class WithLongInBoundsConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return WithLongInBounds.class;
    }


    @DeclareCompilerMessage(code = "WITHLONGBOUNDS_001", enumValueName = "WITHLONGBOUNDS_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Annotation attribute ${1} is out of bounds (${2})", processorClass = BasicConstraints.class)
    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        WithLongInBoundsWrapper constraintWrapper = WithLongInBoundsWrapper.wrap(constraintAnnotationMirror);


        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);


        if (attribute.isPresent()) {

            if (attribute.get().isArray()) {

                for (AnnotationValueWrapper annotationValueWrapper : attribute.get().getArrayValue()) {
                    if (!checkBounds(annotationValueWrapper, constraintWrapper)) {
                        wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.WITHLONGBOUNDS_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint, createConstraintString(constraintWrapper));
                        return false;
                    }

                }

            } else {
                if (!checkBounds(attribute.get(), constraintWrapper)) {
                    wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.WITHLONGBOUNDS_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint, createConstraintString(constraintWrapper));
                    return false;
                }
            }


        }


        return true;
    }

    private boolean checkBounds(AnnotationValueWrapper annotationValueWrapper, WithLongInBoundsWrapper constraintWrapper) {

        if (annotationValueWrapper.isLong()) {

            Long value = annotationValueWrapper.getLongValue();

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

    private String createConstraintString(WithLongInBoundsWrapper constraintWrapper) {

        return (!constraintWrapper.lowerBoundIsDefaultValue() ?  constraintWrapper.lowerBound() + " <" + (constraintWrapper.inclusiveLowerBound() ? "" : "=") + " " : "")
                + " value "
                + (!constraintWrapper.upperBoundIsDefaultValue() ? "<" + (constraintWrapper.inclusiveUpperBound() ? "" : "=") +  " " + constraintWrapper.upperBound(): "");


    }
}
