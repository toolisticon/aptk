package io.toolisticon.example.annotationprocessortoolkit.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for annotating classes that must implement a the {@link SomeInterface} interface.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TypeThatIsAssignableToInterfaceAnnotation {
}
