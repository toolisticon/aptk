package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;

/**
 * Class for checking for find matching elements by name.
 */
public class NameElementCharacteristicMatcher implements GenericElementCharacteristicMatcher<String> {

    @Override
    public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
        return (element != null && toCheckFor != null) && element.getSimpleName().toString().equals(toCheckFor);
    }

    @Override
    public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
        return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : null;
    }

}
