package io.toolisticon.aptk.test;

import io.toolisticon.aptk.tools.TestAnnotation;
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