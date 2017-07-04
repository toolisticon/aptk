package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

/**
 * Wrapper class for validators.
 */
public class Validator<T> {


    private final GenericElementCharacteristicValidator<T> validator;


    public Validator(GenericElementCharacteristicValidator<T> validator) {
        this.validator = validator;
    }


    public GenericElementCharacteristicValidator<T> getValidator() {
        return validator;
    }


}
