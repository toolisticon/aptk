package io.toolisticon.aptk.annotationwrapper.api;

import java.lang.annotation.Annotation;

/**
 * Bind custom code into the wrapper api.
 * Code will be copied into the.
 * <p>
 * All static methods with the Annotation wrapper class as first parameter will be picked up.
 */
public @interface BindCustomCode {

    Class<? extends Annotation> annotation();

    Class<?>[] customCodeClass();

}
