package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.regex.Pattern;

/**
 * Class for checking for find matching elements by name and regular expressions.
 */
public class ByQualifiedNameRegexMatcher implements CriteriaMatcher<Element, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
        if (element != null && toCheckFor != null) {

            String name = null;
            switch (element.getKind()) {
                case ENUM:
                case INTERFACE:
                case ANNOTATION_TYPE:
                case CLASS: {
                    TypeElement typeElement = ElementUtils.CastElement.castToTypeElement(element);
                    name = typeElement.getQualifiedName().toString();
                    break;
                }
                case PACKAGE: {
                    PackageElement packageElement = (PackageElement) element;
                    name = packageElement.getQualifiedName().toString();
                    break;
                }
                default: {
                    // use simple name
                    name = element.getSimpleName().toString();
                }
            }

            Pattern pattern = Pattern.compile(toCheckFor);
            return pattern.matcher(name).matches();

        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor;
    }

}
