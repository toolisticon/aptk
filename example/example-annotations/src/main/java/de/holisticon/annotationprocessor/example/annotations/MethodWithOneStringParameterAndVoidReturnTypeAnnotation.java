package de.holisticon.annotationprocessor.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for annotating methods that must have exactly one Parameter of type String and a void return type
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface MethodWithOneStringParameterAndVoidReturnTypeAnnotation {
}
