package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.InclusiveElementValidator;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;

import javax.lang.model.element.Element;

/**
 * A generic validator that allows checking of characteristics of an element.
 */
public class GenericElementCharacteristicValidator<T> implements InclusiveElementValidator<T> {

    private GenericElementCharacteristicMatcher<T> elementComparator;

    public GenericElementCharacteristicValidator(GenericElementCharacteristicMatcher<T> elementComparator) {
        this.elementComparator = elementComparator;
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


    public boolean hasAllOf(Element element, T... characteristicsToCheck) {

        if (element == null || characteristicsToCheck == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (!this.elementComparator.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

    public boolean hasAtLeastOneOf(Element element, T... characteristicsToCheck) {
        if (element == null || characteristicsToCheck == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (this.elementComparator.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return true;
            }

        }

        return false;
    }


    public boolean hasOneOf(Element element, T... characteristicsToCheck) {
        if (element == null || characteristicsToCheck == null) {
            return false;
        }

        int count = 0;
        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (this.elementComparator.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                count++;
            }

        }

        return count == 1;
    }

    public boolean hasNoneOf(Element element, T... characteristicsToCheck) {
        if (element == null || characteristicsToCheck == null) {
            return false;
        }

        // convert array to set to filter doubled elements
        for (T characteristicToCheck : Utilities.convertArrayToSet(characteristicsToCheck)) {

            if (this.elementComparator.checkForMatchingCharacteristic(element, characteristicToCheck)) {
                return false;
            }

        }

        return true;
    }

}
