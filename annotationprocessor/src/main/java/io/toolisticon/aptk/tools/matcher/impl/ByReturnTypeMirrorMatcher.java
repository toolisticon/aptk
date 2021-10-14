package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Checks if Executable Element has aspecific return type.
 */
public class ByReturnTypeMirrorMatcher implements CriteriaMatcher<ExecutableElement, TypeMirror> {

    public ByReturnTypeMirrorMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, TypeMirror toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // check if number of parameters is the same
        return TypeUtils.TypeComparison.isTypeEqual(element.getReturnType(), toCheckFor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(TypeMirror toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : "";
    }

}