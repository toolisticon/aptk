package io.toolisticon.aptk.tools.annotationutilstestclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for a class annotation attribute.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface ClassAttributeAnnotation {

    Class<?> value() default Void.class;

    Class<?> classAttribute() default Void.class;

}
