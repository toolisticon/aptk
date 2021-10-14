package io.toolisticon.aptk.tools.annotationutilstestclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation to test extraction of default values.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface DefaultValueAnnotation {
    long value() default 5L;
    long mandatoryValue();
}
