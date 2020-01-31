package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;

/**
 * Matcher to check if Type Element is assignable to passed class.
 */
public class IsAssignableToMatcher implements CriteriaMatcher<Element, Class> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.getCanonicalName() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class toCheckFor) {
        return element != null && toCheckFor != null && TypeUtils.getTypes()
                .isAssignable(
                        element.asType(),
                        TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor.getCanonicalName())
                );

    }

}