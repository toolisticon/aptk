package io.toolisticon.annotationprocessortoolkit.test;

import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@TestAnnotation
public class TypeHierarchyTestClass {


    @Data
    public static class SuperType {

        private boolean superBooleanField;
        private String superStringField;

    }


    public static class InheritingType extends SuperType{

        @Getter
        @Setter
        private boolean booleanField;

        @Getter
        @Setter
        private String stringField;

        @Getter
        private String nonAttributeField;


    }

}