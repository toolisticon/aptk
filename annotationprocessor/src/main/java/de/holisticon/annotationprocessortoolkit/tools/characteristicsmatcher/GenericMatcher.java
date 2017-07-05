package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;

/**
 * Interface for checking if an Element matches a characteristic.
 */
public interface GenericMatcher<T> {

    boolean checkForMatchingCharacteristic(Element element, T toCheckFor);

    String getStringRepresentationOfPassedCharacteristic(T toGetStringRepresentationFor);

}
