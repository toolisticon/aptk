package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Matcher to check if Type Element is assignable to passed class.
 */
public class IsAssignableToTypeMirrorMatcher implements CriteriaMatcher<Element, TypeMirror> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(TypeMirror toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, TypeMirror toCheckFor) {
        return element != null && toCheckFor != null && TypeUtils.getTypes()
                .isAssignable(
                        element.asType(),
                        toCheckFor
                );

    }

}