package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonVoidReturnType {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public String test(String x) {
        return "XXX";
    }

}