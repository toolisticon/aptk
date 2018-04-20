package io.toolisticon.example.annotationprocessortoolkit;

import io.toolisticon.example.annotationprocessortoolkit.annotations.ImplementsSpecificInterfaceCheckAnnotation;
import io.toolisticon.example.annotationprocessortoolkit.annotations.MethodWithOneStringParameterAndVoidReturnTypeAnnotation;
import io.toolisticon.example.annotationprocessortoolkit.annotations.SpecificInterface;

@ImplementsSpecificInterfaceCheckAnnotation
public class ValidUsageTest implements SpecificInterface{


    public String testMethod() {
        return null;
    }

}