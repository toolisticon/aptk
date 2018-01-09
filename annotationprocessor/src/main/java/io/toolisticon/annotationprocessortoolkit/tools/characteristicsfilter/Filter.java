package io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter;

/**
 * Wrapper class for filters.
 */
public class Filter<T> {


    private final GenericCharacteristicsFilter<T> filter;

    /**
     * Constructor to pass in the Filter to be wrapped.
     *
     * @param filter the Filter to use
     */
    public Filter(GenericCharacteristicsFilter<T> filter) {
        this.filter = filter;
    }

    /**
     * Gets the wrapped Filter.
     *
     * @return the wrapped filter
     */
    public GenericCharacteristicsFilter<T> getFilter() {
        return filter;
    }


}
