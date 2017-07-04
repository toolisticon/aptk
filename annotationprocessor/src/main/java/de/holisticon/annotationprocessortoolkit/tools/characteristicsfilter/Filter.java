package de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter;

/**
 * Wrapper class for filters.
 */
public class Filter<T> {


    private final GenericCharacteristicsFilter<T> filter;


    public Filter(GenericCharacteristicsFilter<T> filter) {
        this.filter = filter;
    }

    public GenericCharacteristicsFilter<T> getFilter() {
        return filter;
    }


}
