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


    public GenericMatcherWithToolsSupport(FrameworkToolWrapper frameworkTools) {
        super();
        this.frameworkTools = frameworkTools;
        this.typeUtils = TypeUtils.getTypeUtils(frameworkTools);
    }

    protected FrameworkToolWrapper getFrameworkTools() {
        return frameworkTools;
    }

    protected TypeUtils getTypeUtils() {
        return typeUtils;
    }

}
