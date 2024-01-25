package io.toolisticon.aptk.test;

import io.toolisticon.cute.TestAnnotation;

@TestAnnotation
public class GetAttributesCommandWithInheritanceTestClass {


    private static class TestDataAnnotatedClass {

        private String field1;
        private static String field2;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }
    }

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


    private static class TestInheritedDataAnnotatedClass extends TestDataAnnotatedClass {

        private String field3;
        private static String field4;

        public String getField3() {
            return field3;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }
    }

}