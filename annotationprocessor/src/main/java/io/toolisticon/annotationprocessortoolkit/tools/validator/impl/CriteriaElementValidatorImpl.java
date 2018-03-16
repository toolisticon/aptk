package io.toolisticon.annotationprocessortoolkit.tools.validator.impl;

import io.toolisticon.annotationprocessortoolkit.tools.Utilities;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import java.util.Set;

/**
 * Implementation class for characteristic validators.
 */
public class CriteriaElementValidatorImpl {

    public enum ValidatorKind {
        HAS_ONE_OF,
        HAS_NONE_OF,
        HAS_AT_LEAST_ONE_OF,
        HAS_ALL_OF;
    }

    public static CriteriaElementValidatorImpl INSTANCE = new CriteriaElementValidatorImpl();

    protected CriteriaElementValidatorImpl() {

    }


    /**
     * Function to programmatically call the different validator kinds.
     * Null valued criteria values won't be used for validation.
     *
     * @param validatorKind   The validator kind to use
     * @param matcher         The matcher to use
     * @param element         The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches the passed criteria related to the passed validator kind, otherwise false.
     */
    public <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> boolean validateByValidatorKind(ValidatorKind validatorKind, MATCHER matcher, ELEMENT element, CRITERIA... criteriaToCheck) {

        if (validatorKind == null) {
            return false;
        }

        switch (validatorKind) {
            case HAS_ONE_OF:
                return hasOneOf(matcher, element, criteriaToCheck);
            case HAS_NONE_OF:
                return hasNoneOf(matcher, element, criteriaToCheck);
            case HAS_AT_LEAST_ONE_OF:
                return hasAtLeastOneOf(matcher, element, criteriaToCheck);
            case HAS_ALL_OF:
                return hasAllOf(matcher, element, criteriaToCheck);
        }

        return false;

    }


    /**
     * Validator to check if passed Element matches exactly one of the passed criteria.
     * Null valued criteria values won't be used for validation.
     *
     * @param matcher         The matcher to use
     * @param element         The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches exactly one of the passed criteria, otherwise false.
     */
    public  <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> boolean hasOneOf(MATCHER matcher, ELEMENT element, CRITERIA... criteriaToCheck) {

        if (element == null || matcher == null) {
            return false;
        }

        // always return true for null valued criteria
        if (criteriaToCheck == null) {
            return true;
        }

        // always return true for empty or null valued criteriaToCheck
        Set<CRITERIA> criteriaToCheckSet = Utilities.convertArrayToSet(criteriaToCheck);
        if (criteriaToCheckSet.size() == 0) {
            return true;
        }


        int count = 0;
        // convert array to set to filter doubled elements
        for (CRITERIA criteria : Utilities.convertArrayToSet(criteriaToCheck)) {

            if (matcher.checkForMatchingCharacteristic(element, criteria)) {
                count++;
            }

        }

        return count == 1;
    }

    /**
     * Validator to check if passed Element matches none of the passed criteria.
     * Null valued criteria values won't be used for validation.
     *
     * @param matcher         The matcher to use
     * @param element         The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches none of the passed criteria, otherwise false.
     */

    public  <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> boolean hasNoneOf(MATCHER matcher, ELEMENT element, CRITERIA... criteriaToCheck) {

        if (element == null || matcher == null) {
            return false;
        }

        // always return true for null valued criteria
        if (criteriaToCheck == null) {
            return true;
        }

        // always return true for empty or null valued criteriaToCheck
        Set<CRITERIA> criteriaToCheckSet = Utilities.convertArrayToSet(criteriaToCheck);
        if (criteriaToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (CRITERIA criteria : Utilities.convertArrayToSet(criteriaToCheck)) {

            if (matcher.checkForMatchingCharacteristic(element, criteria)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Validator to check if passed Element matches all of the passed criteria.
     * Null valued criteria values won't be used for validation.
     *
     * @param matcher         The matcher to use
     * @param element         The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches all of the passed criteria, otherwise false.
     */

    public  <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> boolean hasAllOf(MATCHER matcher, ELEMENT element, CRITERIA... criteriaToCheck) {

        if (element == null || matcher == null) {
            return false;
        }

        // always return true for null valued criteria
        if (criteriaToCheck == null) {
            return true;
        }

        // always return true for empty or null valued criteriaToCheck
        Set<CRITERIA> criteriaToCheckSet = Utilities.convertArrayToSet(criteriaToCheck);
        if (criteriaToCheckSet.size() == 0) {
            return true;
        }

        // convert array to set to filter doubled elements
        for (CRITERIA criteria : Utilities.convertArrayToSet(criteriaToCheck)) {

            if (!matcher.checkForMatchingCharacteristic(element, criteria)) {
                return false;
            }

        }

        return true;
    }

    /**
     * Validator to check if passed Element matches at least one of the passed criteria.
     * Null valued criteria values won't be used for validation.
     *
     * @param matcher         The matcher to use
     * @param element         The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches at least one of the passed criteria, otherwise false.
     */

    public  <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> boolean hasAtLeastOneOf(MATCHER matcher, ELEMENT element, CRITERIA... criteriaToCheck) {

        if (element == null || matcher == null) {
            return false;
        }

        // always return true for null valued criteria
        if (criteriaToCheck == null) {
            return true;
        }

        // always return true for empty or null valued criteriaToCheck
        Set<CRITERIA> criteriaToCheckSet = Utilities.convertArrayToSet(criteriaToCheck);
        if (criteriaToCheckSet.size() == 0) {
            return true;
        }


        // convert array to set to filter doubled elements
        for (CRITERIA criteria : criteriaToCheck) {

            if (matcher.checkForMatchingCharacteristic(element, criteria)) {
                return true;
            }

        }

        return false;
    }


}
