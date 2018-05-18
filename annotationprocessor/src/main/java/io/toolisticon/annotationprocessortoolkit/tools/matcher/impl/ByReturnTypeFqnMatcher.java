package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.ExecutableElement;

/**
 * Checks if Executable Element has aspecific return type.
 */
public class ByReturnTypeFqnMatcher implements CriteriaMatcher<ExecutableElement, String> {

    public ByReturnTypeFqnMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(ExecutableElement element, String toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // check if number of parameters is the same
        return TypeUtils.TypeComparison.isTypeEqual(element.getReturnType(), TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor : "";
    }

}
