package io.toolisticon.aptk.annotationwrapper.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks method to be included as custom code to wrapper api.
 * <p>
 * Annotated methods must be non private, static and their first parameter must match the Annotation wrapper type to which the method should be bound.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomCodeMethod {
}
