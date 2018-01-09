package io.toolisticon.example.annotationprocessortoolkit;

import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonStringParameter {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(Object x) {
    }

}