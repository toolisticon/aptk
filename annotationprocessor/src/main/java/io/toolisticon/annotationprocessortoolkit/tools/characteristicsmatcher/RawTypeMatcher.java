package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;

/**
 * Matcher for raw types.
 */
public class RawTypeMatcher extends GenericMatcherWithToolsSupport<Class> {

    public RawTypeMatcher() {
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
        TypeUtils typeUtils = TypeUtils.getTypeUtils();

        return typeUtils.doTypeComparison().isErasedTypeEqual(element.asType(), typeUtils.doTypeRetrieval().getTypeMirror(toCheckFor));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }
}
