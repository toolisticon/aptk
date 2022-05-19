package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

/**
 * Class for checking for NestingKind of TypeElement.
 */
public class ByNestingKindMatcher implements CriteriaMatcher<TypeElement, NestingKind> {

    @Override
    public boolean checkForMatchingCharacteristic(TypeElement element, NestingKind toCheckFor) {
        return element.getNestingKind() == toCheckFor;
    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(NestingKind toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : null;
    }
}
