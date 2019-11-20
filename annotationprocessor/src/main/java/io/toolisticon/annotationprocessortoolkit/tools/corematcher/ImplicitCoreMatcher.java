package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ImplicitFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;

import javax.lang.model.element.Element;

/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ImplicitCoreMatcher<
        ELEMENT extends Element
        > extends AbstractBaseCoreMatcher {


    /**
     * The wrapped implicit matcher.
     */
    private final ImplicitMatcher<ELEMENT> matcher;

    /**
     * The constructor.
     *
     * @param matcher                 the implicit matcher to use
     * @param defaultValidatorMessage the default message to use with validator
     */
    public ImplicitCoreMatcher(ImplicitMatcher<ELEMENT> matcher, ValidationMessage defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped implicit matcher.
     *
     * @return the wrapped implicit matcher
     */
    public ImplicitMatcher<ELEMENT> getMatcher() {
        return matcher;
    }

    /**
     * Gets the validator for the wrapped implicit matcher.
     *
     * @return the implicit validator instance
     */
    public ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>> getValidator() {
        return new ImplicitValidator<>(matcher, this.getDefaultValidatorMessage());
    }

    /**
     * Gets the filter for the wrapped implicit matcher.
     *
     * @return the criteria filter instance
     */
    public ImplicitFilter<ELEMENT, ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>>> getFilter() {
        return new ImplicitFilter<>(getValidator());
    }


}
