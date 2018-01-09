package io.toolisticon.example.annotationprocessortoolkit.usecase;

import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.example.annotationprocessortoolkit.annotations.SomeInterface;
import io.toolisticon.example.annotationprocessortoolkit.annotations.TypeThatIsAssignableToInterfaceAnnotation;

/**
 * Test class to show that the annotation processors are working correctly.
 */


@TypeThatIsAssignableToInterfaceAnnotation
public class TestClass implements SomeInterface {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void testMethod(String a) {

    }

    @Override
    public String testMethod() {
        return null;
    }
}
