package io.toolisticon.aptk.tools.filter.impl;

import io.toolisticon.aptk.tools.Utilities;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.validator.impl.CriteriaElementValidatorImpl;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation class for criteria filters.
 */
public class CriteriaElementFilterImpl {

    /**
     * Validator to check if passed Element matches exactly one of the passed criteria.
     *
     * @param matcher         The matcher to use
     * @param invert          invert filter
     * @param elements        The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches exactly one of the passed criteria, otherwise false.
     */
    @SafeVarargs
    private static <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> List<ELEMENT> filterByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind validatorKind, MATCHER matcher, boolean invert, List<ELEMENT> elements, CRITERIA... criteriaToCheck) {
        if (elements == null || matcher == null) {
            return new ArrayList<>();
        }

        // always return true for null valued characteristics
        if (criteriaToCheck == null || criteriaToCheck.length == 0) {
            if (!invert) {
                return elements;
            } else {
                return new ArrayList<>();
            }

        }

        // always return true for empty or null valued criteriaToCheck
        Set<CRITERIA> criteriaToCheckSet = Utilities.convertArrayToSet(criteriaToCheck);
        if (criteriaToCheckSet.size() == 0) {
            return elements;
        }


        List<ELEMENT> result = new ArrayList<>();

        for (ELEMENT element : elements) {

            boolean validatorResult = CriteriaElementValidatorImpl.INSTANCE.validateByValidatorKind(validatorKind, matcher, element, criteriaToCheck);
            if ((!invert && validatorResult) || (invert && !validatorResult)) {
                result.add(element);
            }


        }

        return result;
    }


    /**
     * Validator to check if passed Element matches exactly one of the passed criteria.
     *
     * @param matcher         The matcher to use
     * @param invert          invert filter
     * @param elements        The element to be validated
     * @param criteriaToCheck the characteristics to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches exactly one of the passed criteria, otherwise false.
     */
    @SafeVarargs
    public static <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> List<ELEMENT> filterByOneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CRITERIA... criteriaToCheck) {
        return filterByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, matcher, invert, elements, criteriaToCheck);
    }

    /**
     * Validator to check if passed Element matches none of the passed criteria.
     *
     * @param matcher         The matcher to use
     * @param invert          invert filter
     * @param elements        The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches none of the passed criteria, otherwise false.
     */
    @SafeVarargs
    public static <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> List<ELEMENT> filterByNoneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CRITERIA... criteriaToCheck) {
        return filterByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcher, invert, elements, criteriaToCheck);
    }

    /**
     * Validator to check if passed Element matches all of the passed criteria.
     *
     * @param matcher         The matcher to use
     * @param invert          invert filter
     * @param elements        The element to be validated
     * @param criteriaToCheck the criteria to check
     * @param <ELEMENT>       The Element type
     * @param <CRITERIA>      The criteria type
     * @param <MATCHER>       The matcher type
     * @return true, if passed element matches all of the passed criteria, otherwise false.
     */
    @SafeVarargs
    public static <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> List<ELEMENT> filterByAllOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CRITERIA... criteriaToCheck) {
        return filterByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcher, invert, elements, criteriaToCheck);
    }

    /**
     * Validator to check if passed Element matches at least one of the passed criteria.
     *
     * @param matcher                The matcher to use
     * @param invert                 invert filter
     * @param elements               The element to be validated
     * @param characteristicsToCheck the criteria to check
     * @param <ELEMENT>              The Element type
     * @param <CRITERIA>             The criteria type
     * @param <MATCHER>              The matcher type
     * @return true, if passed element matches at least one of the passed criteria, otherwise false.
     */
    @SafeVarargs
    public static <ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> List<ELEMENT> filterByAtLeastOneOf(MATCHER matcher, boolean invert, List<ELEMENT> elements, CRITERIA... characteristicsToCheck) {
        return filterByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcher, invert, elements, characteristicsToCheck);
    }


}
