package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;

/**
 * Interface for checking one Element
 */
public interface GenericElementCharacteristicMatcher<T> {

    boolean checkForMatchingCharacteristic(Element element, T toCheckFor);

    String getStringRepresentationOfPassedCharacteristic(T toGetStringRepresentationFor);

}
