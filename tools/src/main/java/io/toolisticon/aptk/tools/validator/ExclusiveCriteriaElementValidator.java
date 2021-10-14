package io.toolisticon.aptk.tools.validator;

import io.toolisticon.aptk.tools.corematcher.ValidationMessage;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.validator.impl.CriteriaElementValidatorImpl;

import javax.lang.model.element.Element;

/**
 * Validators for criteria that exclude each other.
 */
public class ExclusiveCriteriaElementValidator<ELEMENT extends Element, CRITERIA, MATCHER extends CriteriaMatcher<ELEMENT, CRITERIA>> extends AbstractBaseValidator {

    private final MATCHER matcher;


    public ExclusiveCriteriaElementValidator(MATCHER matcher, ValidationMessage message) {
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
    @SafeVarargs
    public final boolean hasOneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasOneOf(matcher, element, criteriaToCheck);

    }

    /**
     * Checks if passed element is annotated with NONE of the passed criteria.
     *
     * @param element         the element to check
     * @param criteriaToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    @SafeVarargs
    public final boolean hasNoneOf(ELEMENT element, CRITERIA... criteriaToCheck) {

        return CriteriaElementValidatorImpl.INSTANCE.hasNoneOf(matcher, element, criteriaToCheck);

    }

    public MATCHER getMatcher() {
        return matcher;
    }


}
