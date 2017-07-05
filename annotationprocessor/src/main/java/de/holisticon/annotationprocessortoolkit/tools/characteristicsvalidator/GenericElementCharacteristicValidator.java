package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.GenericMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matcher;

import javax.lang.model.element.Element;
import java.util.Set;

/**
 * A generic validator that allows checking of characteristics of an element.
 */
public class GenericElementCharacteristicValidator<T> implements InclusiveElementValidator<T> {

    /**
     * The {@link Matcher} to be used with this validator.
     */
    private final GenericMatcher<T> matcher;

    /**
     * Constructor that allows passing in of the {@link Matcher} to be used by the validator.
     *
     * @param matcher the matcher to be used
     */
    public GenericElementCharacteristicValidator(Matcher<T> matcher) {
        this.matcher = matcher != null ? matcher.getMatcher() : null;
    }

    /**
     * Delegation method to trigger validator by passed kind.
     *
     * @param kind                   the kind of validator to use
     * @param element                the element to check
     * @param characteristicsToCheck the characteristic to check for
     * @return true if element matches passed characteristic by validator kind
     */
    public boolean hasOf(ValidatorKind kind, Element element, T... characteristicsToCheck) {

        if (kind == null) {
            return false;
        }

        switch (kind) {
            case ALL_OF:
                return hasAllOf(element, characteristicsToCheck);
            case AT_LEAST_ONE_OF:
                return hasAtLeastOneOf(element, characteristicsToCheck);
            case ONE_OF:
                return hasOneOf(element, characteristicsToCheck);
            case NONE_OF:
                return hasNoneOf(element, characteristicsToCheck);
            default:
                return false;
        }

    }

    /**
     * {@inheritDoc}
     */
    public boolean hasAllOf(Element element, T... characteristicsToCheck) {

        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<T> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (!this.matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasAtLeastOneOf(Element element, T... characteristicsToCheck) {
        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<T> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }


        // convert array to set to filter doubled elements
        for (T characteristicToCheck : characteristicsToCheck) {

            if (this.matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return true;
            }

        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasOneOf(Element element, T... characteristicsToCheck) {
        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<T> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }


        int count = 0;
        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (this.matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                count++;
            }

        }

        return count == 1;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasNoneOf(Element element, T... characteristicsToCheck) {
        if (element == null) {
            return false;
        }

        // always return true for null valued characteristics
        if (characteristicsToCheck == null) {
            return true;
        }

        // always return true for empty or null valued characteristicsToCheck
        Set<T> characteristicsToCheckSet = Utilities.convertArrayToSet(characteristicsToCheck);
        if (characteristicsToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (this.matcher.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Gets the {@link Matcher} used by this validator.
     *
     * @return the matcher instance used by thois validator
     */
    public GenericMatcher<T> getMatcher() {
        return matcher;
    }

}
