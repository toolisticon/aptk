package io.toolisticon.aptk.constraints;

import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

@SpiService(value = AnnotationConstraintSpi.class)
public class WithTargetElementAnnotatedWithRepeatableConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return WithTargetElementAnnotatedWithRepeatable.class;
    }

    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        WithTargetElementAnnotatedWithRepeatableWrapper constraintWrapper = WithTargetElementAnnotatedWithRepeatableWrapper.wrap(annotatedElement, constraintAnnotationMirror);

        boolean result = true;

        for (WithTargetElementAnnotatedWithWrapper constraint : constraintWrapper.value()) {
            result = result & new WithTargetElementAnnotatedWithConstraintImpl().checkConstraints(annotatedElement, annotationMirrorToCheck, constraint._annotationMirror(),attributeNameToBeCheckedByConstraint);
        }

        return result;
    }
}