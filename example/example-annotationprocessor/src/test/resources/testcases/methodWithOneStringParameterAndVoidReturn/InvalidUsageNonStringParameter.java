package de.holisticon.example.annotationprocessortoolkit;

import de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonStringParameter {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public void test(Object x) {
    }

}