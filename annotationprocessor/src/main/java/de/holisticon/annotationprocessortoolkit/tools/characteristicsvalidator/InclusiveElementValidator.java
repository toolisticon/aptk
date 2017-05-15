package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import javax.lang.model.element.Element;

/**
 * Interface for validators for characteristics that don't exclude each other.
 */
public interface InclusiveElementValidator<T> extends ExclusiveElementValidator<T> {


    /**
     * Checks if passed elements has all of the passed characteristics.
     *
     * @param element                the element to check the characteristic for
     * @param characteristicsToCheck the characteristics to check
     * @return true if all passed characteristics are present, otherwise false
     */
    public boolean hasAllOf(Element element, T... characteristicsToCheck);

    /**
     * Checks if passed element is annotated with at least one of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if at least one of the characteristics are present
     */
    public boolean hasAtLeastOneOf(Element element, T... characteristicsToCheck);


}
