package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.impl.CharacteristicsElementValidatorImpl;

import javax.lang.model.element.Element;

/**
 * Interface for validators for characteristics that don't exclude each other.
 */
public class InclusiveCharacteristicsElementValidator<ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> extends AbstractBaseValidator {

    private final MATCHER matcher;

    public InclusiveCharacteristicsElementValidator(MATCHER matcher, String message) {
        super(message);
        this.matcher = matcher;
    }

    /**
     * Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasOneOf(ELEMENT element, CHARACTERISTIC... characteristicsToCheck){

        return CharacteristicsElementValidatorImpl.hasOneOf(matcher,element,characteristicsToCheck);

    }

    /**
     * Checks if passed element is annotated with NONE of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasNoneOf(ELEMENT element, CHARACTERISTIC... characteristicsToCheck){

        return CharacteristicsElementValidatorImpl.hasNoneOf(matcher,element,characteristicsToCheck);

    }

    /**
     * Checks if passed elements has all of the passed characteristics.
     *
     * @param element                the element to check the characteristic for
     * @param characteristicsToCheck the characteristics to check
     * @return true if all passed characteristics are present, otherwise false
     */
    public boolean hasAllOf(ELEMENT element, CHARACTERISTIC... characteristicsToCheck){

        return CharacteristicsElementValidatorImpl.hasAllOf(matcher,element,characteristicsToCheck);

    }

    /**
     * Checks if passed element is annotated with at least one of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if at least one of the characteristics are present
     */
    public boolean hasAtLeastOneOf(ELEMENT element, CHARACTERISTIC... characteristicsToCheck){

        return CharacteristicsElementValidatorImpl.hasAtLeastOneOf(matcher,element,characteristicsToCheck);

    }

    public MATCHER getMatcher() {
        return matcher;
    }


}
