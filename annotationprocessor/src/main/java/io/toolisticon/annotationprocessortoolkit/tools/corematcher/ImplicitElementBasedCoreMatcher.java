package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ImplicitFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ImplicitElementBasedCoreMatcher extends AbstractBaseCoreMatcher {

    /**
     * The wrapped implicit matcher.
     */
    private final ImplicitMatcher<Element> matcher;

    /**
     * The constructor.
     *
     * @param matcher                 the implicit matcher to use
     * @param defaultValidatorMessage the default message to use with validator
     */
    public ImplicitElementBasedCoreMatcher(ImplicitMatcher<Element> matcher, ValidationMessage defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped implicit matcher.
     *
     * @return the wrapped implicit matcher
     */
    public ImplicitMatcher<Element> getMatcher() {
        return matcher;
    }

    /**
     * Gets the validator for the wrapped implicit matcher.
     *
     * @return the implicit validator instance
     */
    public ImplicitValidator<Element, ImplicitMatcher<Element>> getValidator() {
        return new ImplicitValidator<>(matcher, this.getDefaultValidatorMessage());
    }

    /**
     * Gets the filter for the wrapped implicit matcher.
     *
     * @return the criteria filter instance
     */
    public ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> getFilter() {
        return new ImplicitFilter<>(getValidator());
    }


}