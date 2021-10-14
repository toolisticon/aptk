package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;


/**
 * Implicit matcher that checks if a passed element is an TypeElement.
 */
public class IsTypeElementMatcher<ELEMENT extends Element> implements ImplicitMatcher<ELEMENT> {

    @Override
    public boolean check(ELEMENT element) {
        return ElementUtils.CastElement.isTypeElement(element);
    }

}