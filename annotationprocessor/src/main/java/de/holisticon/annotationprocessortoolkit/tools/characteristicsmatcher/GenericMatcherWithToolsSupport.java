package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

/**
 * Interface for checking one Element.
 * Matcher which implement this interface do need tool support offered by the process environment.
 */
public abstract class GenericMatcherWithToolsSupport<T> implements GenericMatcher<T> {

    private final FrameworkToolWrapper frameworkTools;
    private final TypeUtils typeUtils;

    /**
     * Constructor that allows passing in of {@link FrameworkToolWrapper} that wraps utility classes offered by {@link ProcessEnvironment}.
     *
     * @param frameworkTools the wrapped utility classes offered by {@link ProcessEnvironment}
     */
    public GenericMatcherWithToolsSupport(FrameworkToolWrapper frameworkTools) {
        super();
        this.frameworkTools = frameworkTools;
        this.typeUtils = TypeUtils.getTypeUtils(frameworkTools);
    }

    /**
     * Getter the wrapper for the utility classes offered by {@link ProcessEnvironment}
     *
     * @return
     */
    protected FrameworkToolWrapper getFrameworkTools() {
        return frameworkTools;
    }

    /**
     * Provides access to {@link TypeUtils};
     *
     * @return a TypeUtils instance
     */
    protected TypeUtils getTypeUtils() {
        return typeUtils;
    }

}
