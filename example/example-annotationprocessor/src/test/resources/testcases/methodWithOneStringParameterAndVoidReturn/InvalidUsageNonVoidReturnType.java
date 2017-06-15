package de.holisticon.example.annotationprocessortoolkit;

import de.holisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;

public class InvalidUsageNonVoidReturnType {

    @MethodWithOneStringParameterAndVoidReturnTypeAnnotation
    public String test(String x) {
        return "XXX";
    }

}