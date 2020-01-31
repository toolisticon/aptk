package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.ExecutableElement;

/**
 * Implicit matcher that checks if a passed ExecutableElement has no arguments.
 */
public class HasNoParametersMatcher implements ImplicitMatcher<ExecutableElement> {

    @Override
    public boolean check(ExecutableElement element) {
        return element != null && element.getParameters().size() == 0;
    }

}