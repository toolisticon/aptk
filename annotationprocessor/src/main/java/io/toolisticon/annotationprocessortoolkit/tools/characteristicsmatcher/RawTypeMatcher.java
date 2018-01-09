package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;

/**
 * Created by tobiasstamann on 09.06.17.
 */
public class RawTypeMatcher extends GenericMatcherWithToolsSupport<Class> {

    public RawTypeMatcher(FrameworkToolWrapper tools) {
        super(tools);
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
        TypeUtils typeUtils = TypeUtils.getTypeUtils(getFrameworkTools());

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
