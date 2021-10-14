package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.example.annotations.ImplementsSpecificInterfaceCheckAnnotation;
import io.toolisticon.aptk.example.annotations.SpecificInterface;


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