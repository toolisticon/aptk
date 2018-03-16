package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.impl.CriteriaElementValidatorImpl;

import javax.lang.model.element.Element;

/**
 * Interface for validators for criteria that don't exclude each other.
 */
public class InclusiveCriteriaElementValidator<ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> extends AbstractBaseValidator {

    private final MATCHER matcher;

    public InclusiveCriteriaElementValidator(MATCHER matcher, ValidationMessage defaultMessage) {
        super(defaultMessage);
        this.matcher = matcher;
    }

    /**
     * Checks if passed element is annotated with EXACTLY one of the passed criteria.
     *
     * @param element         the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasOneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasOneOf(matcher, element, criteriaToCheck);

    }

    /**
     * Checks if passed element is annotated with NONE of the passed criteria.
     *
     * @param element         the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasNoneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasNoneOf(matcher, element, criteriaToCheck);

    }

    /**
     * Checks if passed elements has all of the passed criteria.
     *
     * @param element         the element to check the criteria for
     * @param criteriaToCheck the criteria to check
     * @return true if all passed criteria are present, otherwise false
     */
    public boolean hasAllOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasAllOf(matcher, element, criteriaToCheck);

    }

    /**
     * Checks if passed element is annotated with at least one of the passed criteria.
     *
     * @param element         the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if at least one of the criteria are present
     */
    public boolean hasAtLeastOneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasAtLeastOneOf(matcher, element, criteriaToCheck);

    }

    public MATCHER getMatcher() {
        return matcher;
    }


}
