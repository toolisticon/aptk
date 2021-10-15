package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;

public class ByNumberOfParametersMatcher implements CriteriaMatcher<ExecutableElement, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, Integer toCheckFor) {
        return (element != null && toCheckFor != null) && element.getParameters().size() == toCheckFor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Integer toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : null;
    }

}