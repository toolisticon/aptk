package io.toolisticon.example.annotationprocessortoolkit;

import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class ValidUsageTest {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(String x) {

    }

}