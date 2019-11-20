package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;

/**
 * Matcher for raw types.
 */
public class ByRawTypeMatcher implements CriteriaMatcher<Element, Class> {

    public ByRawTypeMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // cast to executable element for further checks

        return TypeUtils.TypeComparison.isErasedTypeEqual(element.asType(), TypeUtils.TypeRetrieval.getTypeMirror(toCheckFor));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.getCanonicalName() : null;
    }
}
