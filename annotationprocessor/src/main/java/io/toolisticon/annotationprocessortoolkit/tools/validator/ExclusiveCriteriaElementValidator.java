package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.impl.CriteriaElementValidatorImpl;

import javax.lang.model.element.Element;

/**
 * Validators for criteria that exclude each other.
 */
public class ExclusiveCriteriaElementValidator<ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> extends AbstractBaseValidator {

    private final MATCHER matcher;


    public ExclusiveCriteriaElementValidator(MATCHER matcher, String message) {
        super(message);
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

        return CriteriaElementValidatorImpl.hasOneOf(matcher, element, criteriaToCheck);

    }

    /**
     * Checks if passed element is annotated with NONE of the passed criteria.
     *
     * @param element         the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasNoneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.hasNoneOf(matcher, element, criteriaToCheck);

    }

    public MATCHER getMatcher() {
        return matcher;
    }


}
