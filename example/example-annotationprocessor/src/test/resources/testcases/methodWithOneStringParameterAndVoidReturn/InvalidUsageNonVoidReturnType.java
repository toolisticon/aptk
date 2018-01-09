package io.toolisticon.example.annotationprocessortoolkit;

import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonVoidReturnType {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public String test(String x) {
        return "XXX";
    }

}