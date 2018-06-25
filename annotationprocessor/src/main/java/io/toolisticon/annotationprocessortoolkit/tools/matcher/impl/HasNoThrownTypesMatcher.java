package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ExecutableElement;

/**
 * Implicit matcher that checks if a passed ExecutableElement has no throws declaration.
 */
public class HasNoThrownTypesMatcher implements ImplicitMatcher<ExecutableElement> {

    @Override
    public boolean check(ExecutableElement element) {
        return element != null ? element.getThrownTypes().size() == 0 : false;
    }

}
