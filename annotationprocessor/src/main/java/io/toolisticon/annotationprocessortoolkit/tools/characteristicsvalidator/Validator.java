package io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

/**
 * Wrapper class for validators.
 */
public class Validator<T> {

    /**
     * The wrapped {@link Validator}.
     */
    private final GenericElementCharacteristicValidator<T> validator;

    /**
     * Constructor to pass in the validator to be wrapped.
     *
     * @param validator the validator to use
     */
    public Validator(GenericElementCharacteristicValidator<T> validator) {
        this.validator = validator;
    }


    /**
     * Gets the wrapped validator.
     *
     * @return the wrapped validator
     */
    public GenericElementCharacteristicValidator<T> getValidator() {
        return validator;
    }


}
