package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;

import javax.lang.model.element.TypeElement;

/**
 * Matcher for raw types.
 */
public class ByRawTypeMatcher implements CharacteristicsMatcher<TypeElement, Class> {

    public ByRawTypeMatcher() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(TypeElement element, Class toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // cast to executable element for further checks
        TypeUtils typeUtils = TypeUtils.getTypeUtils();

        return typeUtils.doTypeComparison().isErasedTypeEqual(element.asType(), typeUtils.doTypeRetrieval().getTypeMirror(toCheckFor));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.getCanonicalName() : null;
    }
}
