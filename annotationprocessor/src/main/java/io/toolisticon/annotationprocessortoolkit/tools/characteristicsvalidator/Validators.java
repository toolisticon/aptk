package io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

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

    public static final Validator<Modifier> MODIFIER_VALIDATOR =
            new Validator<Modifier>(new GenericElementCharacteristicValidator<Modifier>(Matchers.MODIFIER_MATCHER, ValidatorMesssageEnum.MODIFIER));

    public static final Validator<String> NAME_VALIDATOR =
            new Validator<String>(new GenericElementCharacteristicValidator<String>(Matchers.NAME_MATCHER, ValidatorMesssageEnum.NAME));

    public static final Validator<String> REGEX_NAME_VALIDATOR =
            new Validator<String>(new GenericElementCharacteristicValidator<String>(Matchers.REGEX_NAME_MATCHER, ValidatorMesssageEnum.NAME_REGEX));

    public static final Validator<Class<? extends Annotation>> ANNOTATION_VALIDATOR =
            new Validator<Class<? extends Annotation>>(new GenericElementCharacteristicValidator<Class<? extends Annotation>>(Matchers.ANNOTATION_MATCHER, ValidatorMesssageEnum.ANNOTATION));

    public static final Validator<ElementKind> ELEMENT_KIND_VALIDATOR =
            new Validator<ElementKind>(new GenericElementCharacteristicValidator<ElementKind>(Matchers.ELEMENT_KIND_MATCHER, ValidatorMesssageEnum.ELEMENT_KIND));

    public static Validator<Class[]> PARAMETER_TYPE_VALIDATOR = new Validator<Class[]>(new GenericElementCharacteristicValidator<Class[]>(Matchers.PARAMETER_TYPE_MATCHER, ValidatorMesssageEnum.PARAMETER_TYPES));


    public static Validator<String[]> PARAMETER_TYPE_FQN_VALIDATOR = new Validator<String[]>(new GenericElementCharacteristicValidator<String[]>(Matchers.PARAMETER_TYPE_FQN_MATCHER, ValidatorMesssageEnum.FQN_PARAMETER_TYPES));


    public static Validator<Class> RAW_TYPE_VALIDATOR = new Validator<Class>(new GenericElementCharacteristicValidator<Class>(Matchers.RAW_TYPE_MATCHER, ValidatorMesssageEnum.RAW_TYPE));

    public static Validator<GenericType> GENERIC_TYPE_VAIDATOR = new Validator<GenericType>(new GenericElementCharacteristicValidator<GenericType>(Matchers.GENERIC_TYPE_MATCHER, ValidatorMesssageEnum.GENERIC_TYPE));


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
        public static ExclusiveElementValidator<Class[]> getParameterValidator() {
            return PARAMETER_TYPE_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using a parameter type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<String[]> getParameterFqnValidator() {
            return PARAMETER_TYPE_FQN_VALIDATOR.getValidator();
        }


        /**
         * Convenience method for getting and using a raw type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<Class> getRawTypeValidator() {
            return RAW_TYPE_VALIDATOR.getValidator();
        }

        /**
         * Convenience method for getting and using a raw type matching validator.
         *
         * @return the validator instance
         */
        public static ExclusiveElementValidator<GenericType> getGenericTypeValidator() {
            return GENERIC_TYPE_VAIDATOR.getValidator();
        }

    }
}
