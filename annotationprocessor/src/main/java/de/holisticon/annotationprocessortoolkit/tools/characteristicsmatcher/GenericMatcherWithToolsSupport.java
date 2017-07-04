package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

/**
 * Interface for checking one Element.
 * Matchers who implement this interface do need tool support offered by the process environment.
 */
public abstract class GenericMatcherWithToolsSupport<T> implements GenericMatcher<T> {

    protected final FrameworkToolWrapper tools;
    protected final TypeUtils typeUtils;


    public GenericMatcherWithToolsSupport(FrameworkToolWrapper tools) {
        super();
        this.tools = tools;
        this.typeUtils = TypeUtils.getTypeUtils(tools);
    }

}
