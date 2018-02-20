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
        > extends AbstractBaseCoreMatcher{


    private final ImplicitMatcher<ELEMENT> matcher;

    public ImplicitCoreMatcher(ImplicitMatcher<ELEMENT> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public ImplicitMatcher<ELEMENT> getMatcher() {
        return matcher;
    }

    public ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>> getValidator() {
        return new ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>>(matcher, this.getDefaultValidatorMessage());
    }

    public ImplicitFilter<ELEMENT, ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>>> getFilter() {
        return new ImplicitFilter<ELEMENT, ImplicitValidator<ELEMENT, ImplicitMatcher<ELEMENT>>>(getValidator());
    }


}
