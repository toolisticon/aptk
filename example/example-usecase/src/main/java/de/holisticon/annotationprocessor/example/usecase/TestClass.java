package de.holisticon.annotationprocessor.example.usecase;

import de.holisticon.annotationprocessor.example.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import de.holisticon.annotationprocessor.example.annotations.SomeInterface;
import de.holisticon.annotationprocessor.example.annotations.TypeThatIsAssignableToInterfaceAnnotation;

/**
 * Test class to show that the annotation processors are working correctly
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
