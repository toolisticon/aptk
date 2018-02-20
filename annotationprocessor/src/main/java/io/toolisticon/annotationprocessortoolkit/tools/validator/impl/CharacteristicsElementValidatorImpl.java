package io.toolisticon.annotationprocessortoolkit.tools.validator.impl;

import io.toolisticon.annotationprocessortoolkit.internal.Utilities;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;

import javax.lang.model.element.Element;
import java.util.Set;

/**
 * Implementation class for characteristic validators.
 */
public class CharacteristicsElementValidatorImpl {

    public enum ValidatorKind {
        HAS_ONE_OF,
        HAS_NONE_OF,
        HAS_AT_LEAST_ONE_OF,
        HAS_ALL_OF;
    }

    /**
     * Function to programatically call the different validator kinds.
     *
     * @param validatorKind          The validator kind to use
     * @param matcher                The matcher to use
     * @param element                The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches the passed characteristics related to the passed validator kind, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> boolean validateByValidatorKind(ValidatorKind validatorKind, MATCHER matcher, ELEMENT element, CHARACTERISTIC... characteristicsToCheck) {

        if (validatorKind == null) {
            return false;
        }

        switch (validatorKind) {
            case HAS_ONE_OF:
                return hasOneOf(matcher, element, characteristicsToCheck);
            case HAS_NONE_OF:
                return hasNoneOf(matcher, element, characteristicsToCheck);
            case HAS_AT_LEAST_ONE_OF:
                return hasAtLeastOneOf(matcher, element, characteristicsToCheck);
            case HAS_ALL_OF:
                return hasAllOf(matcher, element, characteristicsToCheck);
        }

        return false;

    }


    /**
     * Validator to check if passed Element matches exactly one of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param element                The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches exactly one of the passedcharacteristics, otherwise false.
     */
    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> boolean hasOneOf(MATCHER matcher, ELEMENT element, CHARACTERISTIC... characteristicsToCheck) {
        if (element == null || matcher == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<CHARACTERISTIC> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }


        int count = 0;
        // convert array to set to filter doubled elements
        for (CHARACTERISTIC characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                count++;
            }

        }

        return count == 1;
    }

    /**
     * Validator to check if passed Element matches none of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param element                The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches none of the passedcharacteristics, otherwise false.
     */

    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> boolean hasNoneOf(MATCHER matcher, ELEMENT element, CHARACTERISTIC... characteristicsToCheck) {
        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<CHARACTERISTIC> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (CHARACTERISTIC characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Validator to check if passed Element matches all of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param element                The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches all of the passedcharacteristics, otherwise false.
     */

    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> boolean hasAllOf(MATCHER matcher, ELEMENT element, CHARACTERISTIC... characteristicsToCheck) {

        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<CHARACTERISTIC> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (CHARACTERISTIC characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (!matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Validator to check if passed Element matches at least one of the passed characteristcs.
     *
     * @param matcher                The matcher to use
     * @param element                The element to be validated
     * @param characteristicsToCheck the characteristics to check
     * @param <ELEMENT>
     * @param <CHARACTERISTIC>
     * @param <MATCHER>
     * @return true, if passed element matches at least one of the passedcharacteristics, otherwise false.
     */

    public static <ELEMENT extends Element, CHARACTERISTIC, MATCHER extends CharacteristicsMatcher<ELEMENT, CHARACTERISTIC>> boolean hasAtLeastOneOf(MATCHER matcher, ELEMENT element, CHARACTERISTIC... characteristicsToCheck) {
        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<CHARACTERISTIC> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }


        // convert array to set to filter doubled elements
        for (CHARACTERISTIC characteristicToCheck : characteristicsToCheck) {

            if (matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return true;
            }

        }

        return false;
    }


}
