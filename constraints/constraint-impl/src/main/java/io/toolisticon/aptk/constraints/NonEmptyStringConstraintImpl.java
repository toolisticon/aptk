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
public class NonEmptyStringConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return NonEmptyString.class;
    }



    @DeclareCompilerMessage(code = "NOTEMPTYSTRING_001", enumValueName = "NOTEMPTYSTRING_DETECTED_EMPTY_STRING", message = "'${0}' Constraint violated: String based annotation attribute '${1}' must not be empty String!", processorClass = BasicConstraints.class)
    @DeclareCompilerMessage(code = "NOTEMPTYSTRING_002", enumValueName = "NOTEMPTYSTRING_DETECTED_EMPTY_STRING_IN_ARRAY", message = "'${0}' Constraint violated: String array based annotation attribute '${1}' must not contain empty Strings!", processorClass = BasicConstraints.class)

    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);

        if (attribute.isPresent() ) {

            if (attribute.get().isArray()) {

                for (AnnotationValueWrapper annotationValueWrapper : attribute.get().getArrayValue()) {
                    if (annotationValueWrapper.isString() && annotationValueWrapper.getStringValue().isEmpty()) {
                        wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.NOTEMPTYSTRING_DETECTED_EMPTY_STRING_IN_ARRAY, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint);
                        return false;
                    }
                }

            } else {
                if (attribute.get().isString() && attribute.get().getStringValue().isEmpty()) {
                    wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.NOTEMPTYSTRING_DETECTED_EMPTY_STRING, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint);
                    return false;
                }
            }


        }


        return true;
    }



}