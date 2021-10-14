package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonStringParameter {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(Object x) {
    }

}