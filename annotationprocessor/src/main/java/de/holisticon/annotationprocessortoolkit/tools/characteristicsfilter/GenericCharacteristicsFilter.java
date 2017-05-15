package de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.GenericElementCharacteristicValidator;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic class for filtering passed lists via characteristic validators.
 */
public class GenericCharacteristicsFilter<T extends Element> {

    /**
     * Filters passed list by passed characteristics.
     * Ellements must suffice all of the passed characteristics.
     *
     * @param listToFilter     the list to filter
     * @param elementValidator the element validator to use
     * @param characteristics  the characteristics to filter by
     * @param <C>
     * @return A fresh filtered list
     */
    public <C> List<T> filterByAllOf(final List<T> listToFilter, final GenericElementCharacteristicValidator<C> elementValidator, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.ALL_OF, listToFilter, elementValidator, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Ellements must suffice at least one of the passed characteristics.
     *
     * @param listToFilter     the list to filter
     * @param elementValidator the element validator to use
     * @param characteristics  the characteristics to filter by
     * @param <C>
     * @return A fresh filtered list
     */
    public <C> List<T> filterByAtLeastOneOf(final List<T> listToFilter, final GenericElementCharacteristicValidator<C> elementValidator, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.AT_LEAST_ONE_OF, listToFilter, elementValidator, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Ellements must suffice exactly one of the passed characteristics.
     *
     * @param listToFilter     the list to filter
     * @param elementValidator the element validator to use
     * @param characteristics  the characteristics to filter by
     * @param <C>
     * @return A fresh filtered list
     */
    public <C> List<T> filterByOneOf(final List<T> listToFilter, final GenericElementCharacteristicValidator<C> elementValidator, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.ONE_OF, listToFilter, elementValidator, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Ellements must suffice none of the passed characteristics.
     *
     * @param listToFilter     the list to filter
     * @param elementValidator the element validator to use
     * @param characteristics  the characteristics to filter by
     * @param <C>
     * @return A fresh filtered list
     */
    public <C> List<T> filterByNoneOf(final List<T> listToFilter, final GenericElementCharacteristicValidator<C> elementValidator, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.NONE_OF, listToFilter, elementValidator, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Elements must suffice passed characteristics depending on passed kind.
     *
     * @param kind             the kind of the filter to use
     * @param listToFilter     the list to filter
     * @param elementValidator the element validator to use
     * @param characteristics  the characteristics to filter by
     * @param <C>
     * @return A fresh filtered list
     */
    public <C> List<T> filterByCharacteristics(final ValidatorKind kind, final List<T> listToFilter, final GenericElementCharacteristicValidator<C> elementValidator, final C... characteristics) {

        // return null if list to filter is null
        if (listToFilter == null) {
            return null;
        }

        // return copy of list if null valued elementValidator has been passed
        if (elementValidator == null) {
            return new ArrayList<T>(listToFilter);
        }

        List<T> result = new ArrayList<T>();

        for (T element : listToFilter) {

            if (elementValidator.hasOf(kind, element, characteristics)) {
                result.add(element);
            }

        }

        return result;

    }
}