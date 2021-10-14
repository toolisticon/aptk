package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Class for matching {@link Modifier}.
 */
public class ByModifierMatcher implements CriteriaMatcher<Element, Modifier> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, Modifier toCheckFor) {
        return (element != null && toCheckFor != null) && element.getModifiers().contains(toCheckFor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Modifier toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.name() : null;
    }

}
