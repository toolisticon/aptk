package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import javax.lang.model.element.Element;

/**
 * Interface for checking if an Element matches a characteristic.
 */
public interface GenericMatcher<T> {

    /**
     * Applies matcher to element to determine if element matches a characteristic or not.
     *
     * @param element    the element  to check
     * @param toCheckFor the characteristic to check for
     * @return true if the element matches the passed characteristic, otherwise false
     */
    boolean checkForMatchingCharacteristic(Element element, T toCheckFor);

    /**
     * Creates a string representation for the passed characteristic.
     *
     * @param toGetStringRepresentationFor the characteristic to get a string representation for.
     * @return the string representation
     */
    String getStringRepresentationOfPassedCharacteristic(T toGetStringRepresentationFor);

}
