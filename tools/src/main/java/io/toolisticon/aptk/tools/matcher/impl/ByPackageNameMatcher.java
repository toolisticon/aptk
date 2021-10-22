package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;

/**
 * Matcher class to check if (enclosing) package element names are equal
 */
public class ByPackageNameMatcher implements CriteriaMatcher<Element, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {

        // return false if either one input parameter is null
        if (element == null || toCheckFor == null) {
            return false;
        }

        PackageElement packageElement = ElementKind.PACKAGE.equals(element.getKind()) ? (PackageElement) element : ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(element, ElementKind.PACKAGE);
        return toCheckFor.equals(packageElement.getQualifiedName().toString());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor;
    }

}