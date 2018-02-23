package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * Class for matching {@link javax.lang.model.element.ElementKind}.
 */
public class ByElementKindMatcher implements CriteriaMatcher<Element, ElementKind> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, ElementKind toCheckFor) {
        return (element != null && toCheckFor != null) && element.getKind().equals(toCheckFor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(ElementKind toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.name() : null;
    }

}
