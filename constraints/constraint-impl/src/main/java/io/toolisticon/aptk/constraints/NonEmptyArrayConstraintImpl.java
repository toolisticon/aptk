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
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@SpiService(value = AnnotationConstraintSpi.class)
public class NonEmptyArrayConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return NonEmptyArray.class;
    }



    @DeclareCompilerMessage(code = "NOTEMPTYARRAY_001", enumValueName = "NONEMPTYARRAY_DETECTED_EMPTY_ARRAY", message = "'${0}' Constraint violated: Array based annotation attribute '${1}' must not be empty!", processorClass = BasicConstraints.class)
    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);

        if (attribute.isPresent() && attribute.get().isArray()) {

            if (attribute.get().getArrayValue().isEmpty()) {
                wrappedElement.compilerMessage(annotationMirrorToCheck, attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.NONEMPTYARRAY_DETECTED_EMPTY_ARRAY, UtilityFunctions.getSimpleName(constraintAnnotationMirror), attributeNameToBeCheckedByConstraint);
                return false;
            }

        }


        return true;
    }
}