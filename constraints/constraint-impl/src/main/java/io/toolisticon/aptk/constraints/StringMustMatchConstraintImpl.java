package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.spiap.api.SpiService;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@SpiService(value = AnnotationConstraintSpi.class)
@DeclareCompilerMessage(code = "STRINGMUSTMATCH_001", enumValueName = "STRINGMUSTMATCH_ERROR_WRONG_USAGE", message = "'${0}' Constraint violated: Annotation ${1} attribute ${2} value(s) must match regular expression: ${3}", processorClass = BasicConstraints.class)
public class StringMustMatchConstraintImpl implements AnnotationConstraintSpi {

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return StringMustMatch.class;
    }


    @Override
    public boolean checkConstraints(Element annotatedElement, AnnotationMirror annotationMirrorToCheck, AnnotationMirror constraintAnnotationMirror, String attributeNameToBeCheckedByConstraint) {

        ElementWrapper<Element> wrappedElement = ElementWrapper.wrap(annotatedElement);
        Optional<ElementWrapper<Element>> enclosingElement = wrappedElement.getEnclosingElement();

        if (!enclosingElement.isPresent() || !enclosingElement.get().isAnnotation() || !wrappedElement.isExecutableElement()) {
            // TODO: must send warning that constraint is ignored because its not placed correctly
            wrappedElement.compilerMessage(annotationMirrorToCheck).asWarning().write(BasicConstraintsCompilerMessages.BASE_WARNING_INVALID_USAGE_OF_CONSTRAINT, constraintAnnotationMirror.getAnnotationType().asElement().getSimpleName());
            return true;
        }

        // It's safe to cast now
        StringMustMatchWrapper constraintWrapper = StringMustMatchWrapper.wrap(constraintAnnotationMirror);
        Optional<AnnotationValueWrapper> attribute = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck).getAttribute(attributeNameToBeCheckedByConstraint);

        if (attribute.isPresent()) {
            try {
                Pattern pattern = Pattern.compile(constraintWrapper.value());

                if (attribute.get().isArray()) {
                    for (AnnotationValueWrapper value : attribute.get().getArrayValue()) {
                        if (!pattern.matcher(value.getStringValue()).matches()) {
                            wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.STRINGMUSTMATCH_ERROR_WRONG_USAGE,UtilityFunctions.getSimpleName(constraintAnnotationMirror), UtilityFunctions.getSimpleName(annotationMirrorToCheck),attributeNameToBeCheckedByConstraint,constraintWrapper.value());
                            return false;
                        }
                    }
                } else {
                    if (!pattern.matcher(attribute.get().getStringValue()).matches()) {
                        wrappedElement.compilerMessage(annotationMirrorToCheck,attribute.get().unwrap()).asError().write(BasicConstraintsCompilerMessages.STRINGMUSTMATCH_ERROR_WRONG_USAGE,UtilityFunctions.getSimpleName(constraintAnnotationMirror), UtilityFunctions.getSimpleName(annotationMirrorToCheck),attributeNameToBeCheckedByConstraint,constraintWrapper.value());
                        return false;
                    }
                }



            } catch (PatternSyntaxException e) {
                //TODO: must print warning that constraint is wrong and must be ignored
                return true;
            }
        }

        AnnotationMirrorWrapper annotationMirrorWrapper = AnnotationMirrorWrapper.wrap(annotationMirrorToCheck);

        return true;
    }
}