package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

import io.toolisticon.annotationprocessortoolkit.tools.filter.ImplicitFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;

import javax.lang.model.element.Element;


/**
 * Convenience class to use just one class which can be used in Fluent validators and filters.
 */
public class ImplicitElementBasedCoreMatcher extends AbstractBaseCoreMatcher{


    private final ImplicitMatcher<Element> matcher;

    public ImplicitElementBasedCoreMatcher(ImplicitMatcher<Element> matcher, CoreMatcherValidationMessages defaultValidatorMessage) {
        super(defaultValidatorMessage);
        this.matcher = matcher;
    }

    public ImplicitMatcher<Element> getMatcher() {
        return matcher;
    }

    public ImplicitValidator<Element, ImplicitMatcher<Element>> getValidator() {
        return new ImplicitValidator<Element, ImplicitMatcher<Element>>(matcher,this.getDefaultValidatorMessage());
    }

    public ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>> getFilter() {
        return new ImplicitFilter<Element, ImplicitValidator<Element, ImplicitMatcher<Element>>>(getValidator());
    }


}