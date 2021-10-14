package io.toolisticon.aptk.tools.validator;

import io.toolisticon.aptk.tools.corematcher.ValidationMessage;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;

/**
 * Validator to check implicit criteria.
 */
public class ImplicitValidator<E extends Element, MATCHER extends ImplicitMatcher<E>> extends AbstractBaseValidator {

    private final MATCHER matcher;

    public ImplicitValidator(MATCHER matcher, ValidationMessage defaultMessage) {
        super(defaultMessage);
        this.matcher = matcher;
    }

    public boolean validate(E element) {
        return this.matcher.check(element);
    }

    public MATCHER getMatcher() {
        return matcher;
    }


}