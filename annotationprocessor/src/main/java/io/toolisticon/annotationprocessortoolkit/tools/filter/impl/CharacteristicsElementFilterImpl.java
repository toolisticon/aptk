package io.toolisticon.annotationprocessortoolkit.tools.filter.impl;

import io.toolisticon.annotationprocessortoolkit.internal.Utilities;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.impl.CharacteristicsElementValidatorImpl;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation class for characteristic filters.
 */
public class CharacteristicsElementFilterImpl {

    /**
     * Validator to check if passed Element matches exactly one of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches exactly one of the passedcharacteristics, otherwise false.
     */
    private static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> List<ELEMENT> filterByValidatorKind(CharacteristicsElementValidatorImpl.ValidatorKind validatorKind, MATCHER matcher, boolean invert, List<ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {
        if (elements == null || matcher == null) {
            return new ArrayList<ELEMENT>();
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null || characteristicsToCheck.length == 0) {
            if (!invert) {
                return elements;
            } else {
                return new ArrayList<ELEMENT>();
            }

        }

        // always return true for empty or null valued characteristicsToCheck
        Set<CHARACTERISTIC> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return elements;
        }


        List<ELEMENT> result = new ArrayList<ELEMENT>();

        for (ELEMENT element : elements) {

            boolean validatorResult = CharacteristicsElementValidatorImpl.validateByValidatorKind(validatorKind, matcher, element, characteristicsToCheck);
            if ((!invert && validatorResult) || (invert && !validatorResult)) {
                result.add(element);
            }


        }

        return result;
    }


    /**
     * Validator to check if passed Element matches exactly one of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches exactly one of the passedcharacteristics, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> List<ELEMENT> filterByOneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {
        return filterByValidatorKind(CharacteristicsElementValidatorImpl.ValidatorKind.HAS_ONE_OF, matcher, invert, elements, characteristicsToCheck);
    }

    /**
     * Validator to check if passed Element matches none of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches none of the passedcharacteristics, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> List<ELEMENT> filterByNoneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {
        return filterByValidatorKind(CharacteristicsElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcher, invert, elements, characteristicsToCheck);
    }

    /**
     * Validator to check if passed Element matches all of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches all of the passedcharacteristics, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> List<ELEMENT> filterByAllOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {
        return filterByValidatorKind(CharacteristicsElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcher, invert, elements, characteristicsToCheck);
    }

    /**
     * Validator to check if passed Element matches at least one of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches at least one of the passedcharacteristics, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> List<ELEMENT> filterByAtLeastOneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CHARACTERISTIC... characteristicsToCheck) {
        return filterByValidatorKind(CharacteristicsElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcher, invert, elements, characteristicsToCheck);
    }


}
