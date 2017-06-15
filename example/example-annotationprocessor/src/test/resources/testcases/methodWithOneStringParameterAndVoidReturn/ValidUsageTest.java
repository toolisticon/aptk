package de.holisticon.example.annotationprocessortoolkit;

import de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class ValidUsageTest {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(String x) {

    }

}