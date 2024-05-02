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
public class TargetMustBeAnnotatedWithsConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return TargetMustBeAnnotatedWiths.class;
    }

    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        TargetMustBeAnnotatedWithsWrapper constraintWrapper = TargetMustBeAnnotatedWithsWrapper.wrap(annotatedElement, constraintAnnotationMirror);

        boolean result = true;

        for (TargetMustBeAnnotatedWithWrapper constraint : constraintWrapper.value()) {
            result = result & new TargetMustBeAnnotatedWithConstraintImpl().checkConstraints(annotatedElement, annotationMirrorToCheck, constraint._annotationMirror(),attributeNameToBeCheckedByConstraint);
        }

        return result;
    }
}