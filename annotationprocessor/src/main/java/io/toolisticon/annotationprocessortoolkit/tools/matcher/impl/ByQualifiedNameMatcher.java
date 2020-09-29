package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Class for checking for find matching elements by qualified name.
 * For Element kinds not related with types or packages the simple name will be used.
 */
public class ByQualifiedNameMatcher implements CriteriaMatcher<Element, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {

        if (element == null || toCheckFor == null) {
            return false;
        }

        switch (element.getKind()) {
            case ENUM:
            case INTERFACE:
            case ANNOTATION_TYPE:
            case CLASS: {
                TypeElement typeElement = ElementUtils.CastElement.castToTypeElement(element);
                return (typeElement != null && toCheckFor != null) && typeElement.getQualifiedName().toString().equals(toCheckFor);

            }
            case PACKAGE: {
                PackageElement packageElement = (PackageElement) element;
                return (packageElement != null && toCheckFor != null) && packageElement.getQualifiedName().toString().equals(toCheckFor);
            }
            default: {
                // use simple name
                return (element != null && toCheckFor != null) && element.getSimpleName().toString().equals(toCheckFor);
            }
        }


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor;
    }

}
