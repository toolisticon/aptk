package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class ValidUsageTest {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(String x) {

    }

}