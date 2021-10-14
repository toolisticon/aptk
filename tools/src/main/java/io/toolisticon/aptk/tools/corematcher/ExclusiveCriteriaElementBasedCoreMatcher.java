package io.toolisticon.aptk.tools.corematcher;

import io.toolisticon.aptk.tools.filter.ExclusiveCriteriaElementFilter;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.validator.ExclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;

/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ExclusiveCriteriaElementBasedCoreMatcher<
        CRITERIA
        > extends AbstractBaseCoreMatcher {


    /**
     * The wrapped criteria matcher.
     */
    private final CriteriaMatcher<Element, CRITERIA> matcher;

    /**
     * The constructor.
     *
     * @param matcher                 the criteria matcher to use
     * @param defaultValidatorMessage the default message to use with validator
     */
    public ExclusiveCriteriaElementBasedCoreMatcher(CriteriaMatcher<Element, CRITERIA> matcher, ValidationMessage defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped criteria matcher.
     *
     * @return the wrapped criteria matcher
     */
    public CriteriaMatcher<Element, CRITERIA> getMatcher() {
        return matcher;
    }

    /**
     * Gets the validator for the wrapped criteria matcher.
     *
     * @return the criteria validator instance
     */
    public ExclusiveCriteriaElementValidator<Element, CRITERIA, CriteriaMatcher<Element, CRITERIA>> getValidator() {
        return new ExclusiveCriteriaElementValidator<>(matcher, this.getDefaultValidatorMessage());
    }

    /**
     * Gets the filter for the wrapped criteria matcher.
     *
     * @return the criteria filter instance
     */
    public ExclusiveCriteriaElementFilter<Element, CRITERIA, ExclusiveCriteriaElementValidator<Element, CRITERIA, CriteriaMatcher<Element, CRITERIA>>> getFilter() {
        return new ExclusiveCriteriaElementFilter<>(getValidator());
    }


}