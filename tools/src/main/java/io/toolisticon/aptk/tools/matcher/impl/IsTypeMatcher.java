package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;


/**
 * Implicit matcher that checks if a passed element is a type (i.e of Kind CLASS, INTERFACE or RECORD).
 */
public class IsTypeMatcher<ELEMENT extends Element> implements ImplicitMatcher<ELEMENT> {

    @Override
    public boolean check(ELEMENT element) {
        return ElementUtils.CheckKindOfElement.isClass(element)
        		|| ElementUtils.CheckKindOfElement.isInterface(element)
        		|| ElementUtils.CheckKindOfElement.isEnum(element)
        		|| ElementUtils.CheckKindOfElement.isAnnotation(element)
        		|| ElementUtils.CheckKindOfElement.isRecord(element);
    }

}


