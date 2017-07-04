package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;

/**
 * Created by tobiasstamann on 09.06.17.
 */
public class RawTypeMatcher extends GenericMatcherWithToolsSupport<Class> {

    public RawTypeMatcher(FrameworkToolWrapper tools) {
        super(tools);
    }


    @Override
    public boolean checkForMatchingCharacteristic(Element element, Class toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        // cast to executable element for further checks
        TypeUtils typeUtils = TypeUtils.getTypeUtils(tools);

        return typeUtils.TYPE_COMPARISON.isErasedTypeEqual(element.asType(), typeUtils.TYPE_RETRIEVAL.getTypeMirror(toCheckFor));

    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(Class toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? "" : null;
    }
}
