package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;

/**
 * Testcase
 */
public class ValidJavaPropertyMatcher implements ImplicitMatcher<Element> {

    /**
     * {@inheritDoc}
     */
    public boolean check(Element element) {
        return false;
    }


}
