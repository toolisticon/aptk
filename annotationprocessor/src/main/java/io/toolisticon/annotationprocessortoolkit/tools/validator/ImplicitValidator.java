package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;

/**
 * Validator to check implicit characteristics.
 */
public class ImplicitValidator<E extends Element, MATCHER extends ImplicitMatcher<E>> extends AbstractBaseValidator {

    private final MATCHER matcher;

    public ImplicitValidator(MATCHER matcher, String message) {
        super(message);
        this.matcher = matcher;
    }

    public boolean validate(E element) {
        return this.matcher.check(element);
    }

    public MATCHER getMatcher() {
        return matcher;
    }


}