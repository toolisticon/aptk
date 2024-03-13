package io.toolisticon.aptk.test;


import io.toolisticon.cute.TestAnnotation;

@TestAnnotation
public class GetAttributesCommandTestClass {


    private static class TestFieldGetterAndSetterMethods {

        private String field1;

        public String getField1() {
            return field1;
        }

        public void setField1(String field) {
            field1 = field;
        }

    }

    private static class TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType {

        private String field1;

        public String getField1() {
            return field1;
        }

        public void setField1(Long field) {

        }

    }

}