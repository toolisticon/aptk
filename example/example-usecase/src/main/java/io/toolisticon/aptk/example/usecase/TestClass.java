package io.toolisticon.aptk.example.usecase;

import io.toolisticon.aptk.example.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.aptk.example.annotations.SpecificInterface;
import io.toolisticon.aptk.example.annotations.ImplementsSpecificInterfaceCheckAnnotation;

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
