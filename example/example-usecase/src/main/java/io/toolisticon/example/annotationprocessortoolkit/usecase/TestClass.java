package io.toolisticon.example.annotationprocessortoolkit.usecase;

import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.example.annotationprocessortoolkit.annotations.SpecificInterface;
import io.toolisticon.example.annotationprocessortoolkit.annotations.ImplementsSpecificInterfaceCheckAnnotation;

/**
 * Test class to show that the annotation processors are working correctly.
 */


@ImplementsSpecificInterfaceCheckAnnotation
public class TestClass implements SpecificInterface {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void testMethod(String a) {

    }

    @Override
    public String testMethod() {
        return null;
    }
}
