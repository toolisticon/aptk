package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ImplicitFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;

import javax.lang.model.element.Element;

/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class IsCoreMatcher<
        SOURCE_ELEMENT extends Element,
        TARGET_ELEMENT extends Element
        > extends AbstractBaseCoreMatcher {

    /**
     * The wrapped implicit matcher.
     */
    private final ImplicitMatcher<SOURCE_ELEMENT> matcher;

    /**
     * The constructor.
     * @param matcher the implicit matcher to use
     * @param defaultValidatorMessage the default message to use with validator
     */
    public IsCoreMatcher(ImplicitMatcher<SOURCE_ELEMENT> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped implicit matcher.
     * @return the wrapped implicit matcher
     */
    public ImplicitMatcher<SOURCE_ELEMENT> getMatcher() {
        return matcher;
    }


    /**
     * Gets the validator for the wrapped implicit matcher.
     * @return the implicit validator instance
     */
    public ImplicitValidator<SOURCE_ELEMENT, ImplicitMatcher<SOURCE_ELEMENT>> getValidator() {
        return new ImplicitValidator<SOURCE_ELEMENT, ImplicitMatcher<SOURCE_ELEMENT>>(matcher, this.getDefaultValidatorMessage());
    }

    /**
     * Gets the filter for the wrapped implicit matcher.
     * @return the criteria filter instance
     */
    public ImplicitFilter<SOURCE_ELEMENT, ImplicitValidator<SOURCE_ELEMENT, ImplicitMatcher<SOURCE_ELEMENT>>> getFilter() {
        return new ImplicitFilter<SOURCE_ELEMENT, ImplicitValidator<SOURCE_ELEMENT, ImplicitMatcher<SOURCE_ELEMENT>>>(getValidator());
    }


}
