package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;

/**
 * Class for checking for find matching elements by name.
 */
public class ByNameMatcher implements CriteriaMatcher<Element, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
        return (element != null && toCheckFor != null) && element.getSimpleName().toString().equals(toCheckFor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor : null;
    }

}
