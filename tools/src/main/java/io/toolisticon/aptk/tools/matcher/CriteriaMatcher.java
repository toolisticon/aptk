package io.toolisticon.aptk.tools.matcher;

import javax.lang.model.element.Element;

/**
 * Matcher that checks if passed criteria is fulfilled
 */
public interface CriteriaMatcher<E extends Element, T> extends BaseMatcher<E>{

    /**
     * Applies matcher to element to determine if element matches a criteria or not.
     *
     * @param element    the element  to check
     * @param toCheckFor the criteria to check for
     * @return true if the element matches the passed criteria, otherwise false
     */
    boolean checkForMatchingCharacteristic(E element, T toCheckFor);

    /**
     * Creates a string representation for the passed criteria.
     *
     * @param toGetStringRepresentationFor the criteria to get a string representation for.
     * @return the string representation
     */
    String getStringRepresentationOfPassedCharacteristic(T toGetStringRepresentationFor);

}
