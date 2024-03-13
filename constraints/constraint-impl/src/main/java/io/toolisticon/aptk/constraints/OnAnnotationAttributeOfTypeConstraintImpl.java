package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;

@SpiService(value = AnnotationConstraintSpi.class)
public class OnAnnotationAttributeOfTypeConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return OnAnnotationAttributeOfType.class;
    }


    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        if (!ElementWrapper.wrap(annotatedElement).isExecutableElement()) {
            // TODO: must send warning that constraint is ignored because its not placed correctly
            return true;
        }

        // It's safe to cast now
        ExecutableElementWrapper attributeElementWrapper = ExecutableElementWrapper.wrap((ExecutableElement) annotatedElement);

        // Now check if annotation
        OnAnnotationAttributeOfTypeWrapper onAnnotation = OnAnnotationAttributeOfTypeWrapper.wrap(constraintAnnotationMirror);

        boolean foundMatchingElementType = false;
        loop:
        for (OnAnnotationAttributeOfType.AttributeType targetAttributeType : onAnnotation.value()) {

            switch (targetAttributeType) {

                case LONG: {
                    if (
                            attributeElementWrapper.getReturnType().isPrimitive()
                                    && attributeElementWrapper.getReturnType().getSimpleName().equals(long.class.getSimpleName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }
                case STRING: {
                    if (
                            attributeElementWrapper.getReturnType().getQualifiedName().equals(String.class.getCanonicalName())
                    ) {
                        foundMatchingElementType = true;
                        break loop;
                    }
                    break;
                }

            }


        }

        // trigger error message if matching type hasn't been found
        if (!foundMatchingElementType) {
            MessagerUtils.error(annotatedElement, annotationMirrorToCheck, BasicConstraintsCompilerMessages.ON_ERROR_WRONG_USAGE, UtilityFunctions.getSimpleName(constraintAnnotationMirror), UtilityFunctions.getSimpleName(annotationMirrorToCheck), onAnnotation.value());
            return false;
        }

        return true;
    }
}