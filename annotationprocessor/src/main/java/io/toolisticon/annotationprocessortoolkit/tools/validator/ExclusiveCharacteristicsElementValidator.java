package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.impl.CharacteristicsElementValidatorImpl;

import javax.lang.model.element.Element;

/**
 * Validators for characteristics that exclude each other.
 */
public class ExclusiveCharacteristicsElementValidator<ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> extends AbstractBaseValidator {

    private final MATCHER matcher;


    public ExclusiveCharacteristicsElementValidator(MATCHER matcher, String message) {
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

    public MATCHER getMatcher() {
        return matcher;
    }


}
