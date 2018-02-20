package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCharacteristicCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCharacteristicCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.filter.ExclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.filter.InclusiveCharacteristicsElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CharacteristicsMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCharacteristicsElementValidator;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCharacteristicsElementValidator;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * Fluent Element Filter.
 */
public class FluentElementFilter<ELEMENT extends Element> {


    private List<ELEMENT> elements;


    private FluentElementFilter(List<ELEMENT> elements) {
        this.elements = elements;
    }


    /**
     * Validator step for exlusive characteristics validators.
     *
     * @param <FILTER_ELEMENT>
     * @param <CHARACTERISTIC>
     */
    public class ExclusiveCharacteristicFluentFilter<FILTER_ELEMENT extends Element, CHARACTERISTIC> {

        private final boolean inverted;
        private final ExclusiveCharacteristicsElementFilter<FILTER_ELEMENT, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter;

        private ExclusiveCharacteristicFluentFilter(ExclusiveCharacteristicsElementFilter<FILTER_ELEMENT, CHARACTERISTIC, ExclusiveCharacteristicsElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter, boolean inverted) {
            this.filter = filter;
            this.inverted = inverted;
        }

        public FluentElementFilter<ELEMENT> filterByOneOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByOneOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }

        public FluentElementFilter<ELEMENT> filterByNoneOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByNoneOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }


    }

    /**
     * Validator step for inclusive characteristics validators.
     *
     * @param <FILTER_ELEMENT>
     * @param <CHARACTERISTIC>
     */
    public class InclusiveCharacteristicFluentFilter<FILTER_ELEMENT extends Element, CHARACTERISTIC> {

        private final boolean inverted;
        private final InclusiveCharacteristicsElementFilter<FILTER_ELEMENT, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter;

        private InclusiveCharacteristicFluentFilter(InclusiveCharacteristicsElementFilter<FILTER_ELEMENT, CHARACTERISTIC, InclusiveCharacteristicsElementValidator<FILTER_ELEMENT, CHARACTERISTIC, CharacteristicsMatcher<FILTER_ELEMENT, CHARACTERISTIC>>> filter, boolean inverted) {
            this.filter = filter;
            this.inverted = inverted;
        }

        public FluentElementFilter<ELEMENT> filterByOneOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByOneOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }

        public FluentElementFilter<ELEMENT> filterByNoneOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByNoneOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }

        public FluentElementFilter<ELEMENT> filterByAtLeastOneOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByAtLeastOneOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }

        public FluentElementFilter<ELEMENT> filterByAllOf(CHARACTERISTIC... params) {
            elements = (List<ELEMENT>) filter.filterByAllOf((List<FILTER_ELEMENT>) elements, inverted, params);
            return FluentElementFilter.this;
        }


    }

    // -----------------------------------------------
    // -- IMPLICIT FILTERS
    // -----------------------------------------------

    // apply implicit filter
    public FluentElementFilter<ELEMENT> applyFilter(ImplicitCoreMatcher coreMatcher) {

        elements = (List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements);
        return FluentElementFilter.this;

    }

    // apply inverted implicit filter
    public FluentElementFilter<ELEMENT> applyInvertedFilter(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

        elements = coreMatcher.getFilter().filter(elements, true);
        return FluentElementFilter.this;

    }

    // apply implicit element based filter
    public FluentElementFilter<ELEMENT> applyFilter(ImplicitElementBasedCoreMatcher coreMatcher) {

        elements = (List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements);
        return FluentElementFilter.this;

    }

    // apply implicit element based filter
    public FluentElementFilter<ELEMENT> applyInvertedFilter(ImplicitElementBasedCoreMatcher coreMatcher) {

        elements = (List<ELEMENT>) coreMatcher.getFilter().filter((List<Element>) elements, true);
        return FluentElementFilter.this;

    }

    // -----------------------------------------------
    // -- INCLUSIVE CHARACTERISTIC FILTERS
    // -----------------------------------------------

    // apply implicit filter
    public <CHARACTERISTIC> InclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyFilter(InclusiveCharacteristicCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new InclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC>(coreMatcher.getFilter(), false);

    }

    // apply inverted implicit filter
    public <CHARACTERISTIC> InclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyInvertedFilter(InclusiveCharacteristicCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new InclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC>(coreMatcher.getFilter(), true);

    }

    // apply implicit element based filter
    public <CHARACTERISTIC> InclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyFilter(InclusiveCharacteristicElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new InclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC>(coreMatcher.getFilter(), false);


    }

    // apply implicit element based filter
    public <CHARACTERISTIC> InclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyInvertedFilter(InclusiveCharacteristicElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new InclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC>(coreMatcher.getFilter(), true);

    }

    // -----------------------------------------------
    // -- EXPLICIT FILTERS
    // -----------------------------------------------

    // apply implicit filter
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyFilter(ExclusiveCharacteristicCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC>(coreMatcher.getFilter(), false);

    }

    // apply inverted implicit filter
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC> applyInvertedFilter(ExclusiveCharacteristicCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<ELEMENT, CHARACTERISTIC>(coreMatcher.getFilter(), true);

    }

    // apply implicit element based filter
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyFilter(ExclusiveCharacteristicElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC>(coreMatcher.getFilter(), false);


    }

    // apply implicit element based filter
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC> applyInvertedFilter(ExclusiveCharacteristicElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {

        return new ExclusiveCharacteristicFluentFilter<Element, CHARACTERISTIC>(coreMatcher.getFilter(), true);

    }

    public List<ELEMENT> getResult() {

        return elements;

    }

    /**
     * Checks if filter result is empty.
     *
     * @return true if filter result is empty, otherwise false
     */
    public boolean isEmpty() {
        return elements == null || elements.isEmpty();
    }

    /**
     * Checks if filter result contains exactly one element.
     *
     * @return true if filter result contains exactly one element, otherwise false
     */
    public boolean hasSingleElement() {
        return elements != null && elements.size() == 1;
    }

    /**
     * Checks if filter result contains more than one element.
     *
     * @return true if filter result contains more than one element, otherwise false
     */
    public boolean hasMultipleElements() {
        return elements != null && elements.size() > 1;
    }

    /**
     * Checks whether the filter result has exactly the passed size.
     *
     * @param size the size to check for
     * @return true if passed size matches the filter results size, otherwise false
     */
    public boolean hasSize(int size) {
        return elements != null && elements.size() == size;
    }

    /**
     * Gets the result size.
     *
     * @return the filtered results size.
     */
    public int getResultSize() {
        return elements == null ? 0 : elements.size();
    }


    public static <E extends Element> FluentElementFilter<E> createFluentElementFilter(List<E> elements) {
        return new FluentElementFilter<E>(elements);
    }


}