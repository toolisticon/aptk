package de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.GenericElementCharacteristicValidator;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic class for filtering passed lists via characteristic validators.
 */
public class GenericCharacteristicsFilter<C> {

    /**
     * The validator to be used by this Filter.
     */
    private final GenericElementCharacteristicValidator<C> elementValidator;

    /**
     * Constructor for passing in the {@link Validator} to use.
     *
     * @param elementValidator the wrapped validator to use
     */
    public GenericCharacteristicsFilter(Validator<C> elementValidator) {
        this.elementValidator = elementValidator != null ? elementValidator.getValidator() : null;
    }


    /**
     * Filters passed list by passed characteristics.
     * Elements must suffice all of the passed characteristics.
     *
     * @param listToFilter    the list to filter
     * @param characteristics the characteristics to filter by
     * @return A fresh filtered list
     */
    public <T extends Element> List<T> filterByAllOf(final List<T> listToFilter, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.ALL_OF, false, listToFilter, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Elements must suffice at least one of the passed characteristics.
     *
     * @param listToFilter    the list to filter
     * @param characteristics the characteristics to filter by
     * @return A fresh filtered list
     */
    public <T extends Element> List<T> filterByAtLeastOneOf(final List<T> listToFilter, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.AT_LEAST_ONE_OF, false, listToFilter, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Elements must suffice exactly one of the passed characteristics.
     *
     * @param listToFilter    the list to filter
     * @param characteristics the characteristics to filter by
     * @return A fresh filtered list
     */
    public <T extends Element> List<T> filterByOneOf(final List<T> listToFilter, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.ONE_OF, false, listToFilter, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Ellements must suffice none of the passed characteristics.
     *
     * @param listToFilter    the list to filter
     * @param characteristics the characteristics to filter by
     * @return A fresh filtered list
     */
    public <T extends Element> List<T> filterByNoneOf(final List<T> listToFilter, final C... characteristics) {
        return filterByCharacteristics(ValidatorKind.NONE_OF, false, listToFilter, characteristics);
    }

    /**
     * Filters passed list by passed characteristics.
     * Elements must suffice passed characteristics depending on passed kind.
     *
     * @param kind            the kind of the filter to use
     * @param invertFilter    should be the filter used in an inverted way
     * @param listToFilter    the list to filters
     * @param characteristics the characteristics to filter by
     * @return A fresh filtered list
     */

    public <T extends Element> List<T> filterByCharacteristics(
            final ValidatorKind kind,
            boolean invertFilter,
            final List<T> listToFilter, final C... characteristics) {

        // return empty List if list to filter is null
        if (listToFilter == null) {
            return new ArrayList<T>();
        }

        // return copy of list if null valued elementValidator has been passed
        if (elementValidator == null) {
            return new ArrayList<T>(listToFilter);
        }

        List<T> result = new ArrayList<T>();

        for (T element : listToFilter) {

            if (elementValidator.hasOf(kind, element, characteristics)) {

                // add element if non inverted filter should be used
                if (!invertFilter) {
                    result.add(element);
                }

            } else {

                // add element if inverted filter should be used
                if (invertFilter) {
                    result.add(element);
                }

            }

        }

        return result;

    }
}
