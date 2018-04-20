package io.toolisticon.example.annotationprocessortoolkit;

import io.toolisticon.example.annotationprocessortoolkit.annotations.ImplementsSpecificInterfaceCheckAnnotation;
import io.toolisticon.example.annotationprocessortoolkit.annotations.SpecificInterface;


public class ValidUsageTestExtendsCase {

    public static class SuperClass implements SpecificInterface {

        public String testMethod() {
            return null;
        }

    }


    @ImplementsSpecificInterfaceCheckAnnotation
    public static class SubClass extends SuperClass {

    }

}