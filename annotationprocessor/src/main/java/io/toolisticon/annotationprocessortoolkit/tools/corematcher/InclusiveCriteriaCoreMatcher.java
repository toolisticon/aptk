package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.InclusiveCriteriaElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class InclusiveCriteriaCoreMatcher<
        ELEMENT extends Element,
        CHARACTERISTIC
        > extends AbstractBaseCoreMatcher {

    /**
     * The wrapped criteria matcher.
     */
    private final CriteriaMatcher<ELEMENT, CHARACTERISTIC> matcher;

    /**
     * The constructor.
     * @param matcher the criteria matcher to use
     * @param defaultValidatorMessage the default message to use with validator
     */
    public InclusiveCriteriaCoreMatcher(CriteriaMatcher<ELEMENT, CHARACTERISTIC> matcher, ValidationMessage defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped criteria matcher.
     *
     * @return the wrapped criteria matcher
     */
    public CriteriaMatcher<ELEMENT, CHARACTERISTIC> getMatcher() {
        return matcher;
    }

    /**
     * Gets the validator for the wrapped criteria matcher.
     * @return the criteria validator instance
     */
    public InclusiveCriteriaElementValidator<ELEMENT, CHARACTERISTIC, CriteriaMatcher<ELEMENT, CHARACTERISTIC>> getValidator() {
        return new InclusiveCriteriaElementValidator<ELEMENT, CHARACTERISTIC, CriteriaMatcher<ELEMENT, CHARACTERISTIC>>(matcher, this.getDefaultValidatorMessage());
    }

    /**
     * Gets the filter for the wrapped criteria matcher.
     * @return the criteria filter instance
     */
    public InclusiveCriteriaElementFilter<ELEMENT, CHARACTERISTIC, InclusiveCriteriaElementValidator<ELEMENT, CHARACTERISTIC, CriteriaMatcher<ELEMENT, CHARACTERISTIC>>> getFilter() {
        return new InclusiveCriteriaElementFilter<ELEMENT, CHARACTERISTIC, InclusiveCriteriaElementValidator<ELEMENT, CHARACTERISTIC, CriteriaMatcher<ELEMENT, CHARACTERISTIC>>>(getValidator());
    }


}