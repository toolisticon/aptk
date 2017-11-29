package de.holisticon.annotationprocessortoolkit.tools.annotationutilstestclasses;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for a class array annotation attribute.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ClassArrayAttributeAnnotation {

    Class<?>[] value() default {};

    Class<?>[] classArrayAttribute() default {};

}