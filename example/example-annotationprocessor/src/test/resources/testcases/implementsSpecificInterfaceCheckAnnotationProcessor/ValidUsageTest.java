package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.ImplementsSpecificInterfaceCheckAnnotation;
import io.toolisticon.aptk.example.annotations.SpecificInterface;

@ImplementsSpecificInterfaceCheckAnnotation
public class ValidUsageTest implements SpecificInterface{


    public String testMethod() {
        return null;
    }

}