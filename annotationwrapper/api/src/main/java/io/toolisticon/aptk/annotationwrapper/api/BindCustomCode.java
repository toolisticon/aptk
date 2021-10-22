package io.toolisticon.aptk.annotationwrapper.api;

import java.lang.annotation.Annotation;

/**
 * Bind custom code into the wrapper api.
 * Code will be copied into the.
 * <p>
 * All static methods with the Annotation wrapper class as first parameter will be picked up.
 */
public @interface BindCustomCode {

    /**
     * Adds annotation to create wrapper class with custom code in it's api.
     * @return The annotation to wrap
     */
    Class<? extends Annotation> annotation();

    /**
     * An array containing classes that should be scanned for methods annotated with {@link CustomCodeMethod} annotation.
     * @return The classes to scan
     */
    Class<?>[] customCodeClass();

}
