package de.holisticon.annotationprocessortoolkit.filter;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filter;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.GenericCharacteristicsFilter;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.ValidatorKind;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Fluent filter utility class to filter lists of Elements.
 * Each filter operation produces a FluentElementFilter instance.
 *
 * @param <T>
 */
public class FluentElementFilter<T extends Element> {


    public class ApplyFilter<C> {

        private final GenericCharacteristicsFilter<C> filter;
        private boolean invertFiltering = false;

        private ApplyFilter(Filter<C> filter) {
            this.filter = filter != null ? filter.getFilter() : null;
        }


        /**
         * Triggers an inverted filtering.
         *
         * @return
         */
        public ApplyFilter<C> invert() {
            invertFiltering = true;
            return this;
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice all of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to filter by
         * @return A fresh filtered list
         */
        public FluentElementFilter<T> filterByAllOf(final C... filteringCharacteristics) {
            return new FluentElementFilter<T>(filter.filterByCharacteristics(ValidatorKind.ALL_OF, invertFiltering, result, filteringCharacteristics));
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice at least one of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to filter by
         * @return A fresh filtered list
         */
        public FluentElementFilter<T> filterByAtLeastOneOf(final C... filteringCharacteristics) {
            return new FluentElementFilter<T>(filter.filterByCharacteristics(ValidatorKind.AT_LEAST_ONE_OF, invertFiltering, result, filteringCharacteristics));
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice exactly one of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to filter by
         * @return A fresh filtered list
         */
        public FluentElementFilter<T> filterByOneOf(final C... filteringCharacteristics) {
            return new FluentElementFilter<T>(filter.filterByCharacteristics(ValidatorKind.ONE_OF, invertFiltering, result, filteringCharacteristics));
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice none of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to filter by
         * @return A fresh filtered list
         */
        public FluentElementFilter<T> filterByNoneOf(final C... filteringCharacteristics) {
            return new FluentElementFilter<T>(filter.filterByCharacteristics(ValidatorKind.NONE_OF, invertFiltering, result, filteringCharacteristics));
        }

    }


    private final List<T> result;

    /**
     * Hide constructor.
     * Use static method instead.
     *
     * @param elementList the list to be processed
     */
    private FluentElementFilter(List<T> elementList) {
        result = elementList;
    }


    public FluentElementFilter<TypeElement> filterAndCastToTypeElement() {
        return genericFilterAndCastToTypeElement(new ApplyFilter<ElementKind>(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.CLASS).getResult(), TypeElement.class);

    }

    private <Y extends Element> FluentElementFilter<Y> genericFilterAndCastToTypeElement
            (List<? extends Element> elements, Class<Y> typeToCastTo) {
        return new FluentElementFilter<Y>(ElementUtils.CastElement.castElementList(elements, typeToCastTo));
    }


    /**
     * Fluently apply filter.
     *
     * @param filter the filter to use
     * @param <C>
     * @return The fluent filtering interface
     */
    public <C> ApplyFilter<C> applyFilter(Filter<C> filter) {
        return new ApplyFilter<C>(filter);
    }

    /**
     * Gets the filter result as a list
     *
     * @return the filtered list
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * Checks if filter result is empty
     *
     * @return true if filter result is empty, otherwise false
     */
    public boolean isEmpty() {
        return result == null || result.isEmpty();
    }

    /**
     * Checks if filter result contains exactly one element
     *
     * @return true if filter result contains exactly one element, otherwise false
     */
    public boolean hasSingleElement() {
        return result != null && result.size() == 1;
    }

    /**
     * Checks if filter result contains more than one element.
     *
     * @return true if filter result contains more than one element, otherwise false
     */
    public boolean hasMultipleElements() {
        return result != null && result.size() > 1;
    }

    /**
     * Checks whether the filter result has exactly the passed size.
     *
     * @param size the size to check for
     * @return true if passed size matches the filter results size, otherwise false
     */
    public boolean hasSize(int size) {
        return result != null && result.size() == size;
    }

    /**
     * Gets the result size.
     *
     * @return the filtered results size.
     */
    public int getResultSize() {
        return result == null ? 0 : result.size();
    }


    /**
     * Convenience method for the creation of FluentElementFilter.
     *
     * @param listToFilter the list to filter
     * @param <X>
     * @return the freshly created filter instance
     */
    public static <X extends Element> FluentElementFilter<X> createFluentFilter(List<X> listToFilter) {
        return new FluentElementFilter<X>(listToFilter);
    }

}