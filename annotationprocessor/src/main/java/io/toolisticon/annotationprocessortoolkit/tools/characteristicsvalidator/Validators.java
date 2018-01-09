package io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matchers;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;

/**
 * Convenience class to access existing validators.
 */
public final class Validators {

    /**
     * Hidden constructor.
     */
    private Validators() {

    }

    private static final Validator<Modifier> MODIFIER_VALIDATOR =
            new Validator<Modifier>(new GenericElementCharacteristicValidator<Modifier>(Matchers.getModifierMatcher()));
    private static final Validator<String> NAME_VALIDATOR =
            new Validator<String>(new GenericElementCharacteristicValidator<String>(Matchers.getNameMatcher()));
    private static final Validator<String> REGEX_NAME_VALIDATOR =
            new Validator<String>(new GenericElementCharacteristicValidator<String>(Matchers.getRegexNameMatcher()));
    private static final Validator<Class<? extends Annotation>> ANNOTATION_VALIDATOR =
            new Validator<Class<? extends Annotation>>(new GenericElementCharacteristicValidator<Class<? extends Annotation>>(Matchers.getAnnotationMatcher()));
    private static final Validator<ElementKind> ELEMENT_KIND_VALIDATOR =
            new Validator<ElementKind>(new GenericElementCharacteristicValidator<ElementKind>(Matchers.getElementKindMatcher()));

    public static Validator<Modifier> getModifierValidator() {
        return MODIFIER_VALIDATOR;
    }

    public static Validator<String> getNameValidator() {
        return NAME_VALIDATOR;
    }

    public static Validator<String> getRegexNameValidator() {
        return REGEX_NAME_VALIDATOR;
    }

    public static Validator<Class<? extends Annotation>> getAnnotationValidator() {
        return ANNOTATION_VALIDATOR;
    }

    public static Validator<ElementKind> getElementKindValidator() {
        return ELEMENT_KIND_VALIDATOR;
    }

    public static Validator<Class[]> getParameterValidator(FrameworkToolWrapper tools) {
        return new Validator<Class[]>(new GenericElementCharacteristicValidator<Class[]>(Matchers.getParameterMatcher(tools)));
    }

    public static Validator<String[]> getParameterFqnValidator(FrameworkToolWrapper tools) {
        return new Validator<String[]>(new GenericElementCharacteristicValidator<String[]>(Matchers.getParameterFqnMatcher(tools)));
    }

    public static Validator<Class> getRawTypeValidator(FrameworkToolWrapper tools) {
        return new Validator<Class>(new GenericElementCharacteristicValidator<Class>(Matchers.getRawTypeMatcher(tools)));
    }

    public static Validator<GenericType> getGenericTypeValidator(FrameworkToolWrapper tools) {
        return new Validator<GenericType>(new GenericElementCharacteristicValidator<GenericType>(Matchers.getGenericTypeMatcher(tools)));
    }


    public static class InAndExclusiveElementValidators {
        /**
         * Convenience method for getting and using a Modifier matching validator.
         *
         * @return the validator instance
         */
        public static InclusiveElementValidator<Modifier> getModifierValidator() {
            return MODIFIER_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using a name matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<String> getNameValidator() {
            return NAME_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using a name matching validator by regular expressions.
         *
         * @return the validator instance
         */
        public static InclusiveElementValidator<String> getRegexNameValidator() {
            return REGEX_NAME_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using an annotation matching validator.
         *
         * @return the validator instance
         */
        public static InclusiveElementValidator<Class<? extends Annotation>> getAnnotationValidator() {
            return ANNOTATION_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using a ElementKind matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<ElementKind> getElementKindValidator() {
            return ELEMENT_KIND_VALIDATOR.getValidator();
        }


        /**
         * Convenience method for getting and using a parameter type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<Class[]> getParameterValidator(FrameworkToolWrapper frameworkToolWrapper) {
            return Validators.getParameterValidator(frameworkToolWrapper).getValidator();
        }

        /**
         * Convenience method for getting and using a parameter type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<String[]> getParameterFqnValidator(FrameworkToolWrapper frameworkToolWrapper) {
            return Validators.getParameterFqnValidator(frameworkToolWrapper).getValidator();
        }


        /**
         * Convenience method for getting and using a raw type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<Class> getRawTypeValidator(FrameworkToolWrapper frameworkToolWrapper) {
            return Validators.getRawTypeValidator(frameworkToolWrapper).getValidator();
        }

        /**
         * Convenience method for getting and using a raw type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<GenericType> getGenericTypeValidator(FrameworkToolWrapper frameworkToolWrapper) {
            return Validators.getGenericTypeValidator(frameworkToolWrapper).getValidator();
        }

    }
}
