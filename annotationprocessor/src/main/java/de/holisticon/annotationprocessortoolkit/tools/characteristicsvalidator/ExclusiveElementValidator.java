package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import javax.lang.model.element.Element;

/**
 * Interface for validators for characteristics that exclude each other.
 */
public interface ExclusiveElementValidator<T> {

    /**
     * Checks if passed element is annotated with EXACTLY one of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasOneOf(Element element, T... characteristicsToCheck);

    /**
     * Checks if passed element is annotated with NONE of the passed characteristics.
     *
     * @param element                the element to check
     * @param characteristicsToCheck the annotation types to check for
     * @return true if all passed annotationTypes are present
     */
    public boolean hasNoneOf(Element element, T... characteristicsToCheck);

}
