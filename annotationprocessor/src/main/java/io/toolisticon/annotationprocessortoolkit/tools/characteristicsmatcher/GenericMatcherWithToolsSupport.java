package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;

/**
 * Interface for checking one Element.
 * Matcher which implement this interface do need tool support offered by the process environment.
 */
public abstract class GenericMatcherWithToolsSupport<T> implements GenericMatcher<T> {


    /**
     * Constructor.
     */
    public GenericMatcherWithToolsSupport() {
        super();
    }


    /**
     * Provides access to {@link TypeUtils}.
     *
     * @return a TypeUtils instance
     */
    protected TypeUtils getTypeUtils() {
        return TypeUtils.getTypeUtils();
    }

}
