package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ExecutableElement;

/**
 * Implicit matcher that checks if a passed ExecutableElement has void return type.
 */
public class HasVoidReturnTypeMatcher implements ImplicitMatcher<ExecutableElement> {

    @Override
    public boolean check(ExecutableElement element) {
        return element != null && TypeUtils.CheckTypeKind.isVoid(element.getReturnType());
    }

}
