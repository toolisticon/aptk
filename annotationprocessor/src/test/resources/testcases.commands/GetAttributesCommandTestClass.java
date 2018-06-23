package io.toolisticon.annotationprocessortoolkit.test;

import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@TestAnnotation
public class GetAttributesCommandTestClass {
    @Data
    private static class TestDataAnnotatedClass {

        private String field1;
        private static String field2;

    }

    @Getter
    private static class TestJustGetterAnnotatedClass {

        private String field1;
        private static String field2;

    }

    @Getter
    private static class TestJustSetterAnnotatedClass {

        private String field1;
        private static String field2;

    }

    @Getter
    @Setter
    private static class TestGetterAndSetterAnnotatedClass {

        private String field1;
        private static String field2;

    }

    @Setter
    private static class TestMixedGetterAndSetterAnnotatedClassAndField1 {

        @Getter
        private String field1;

    }

    @Getter
    private static class TestMixedGetterAndSetterAnnotatedClassAndField2 {

        @Setter
        private String field1;

    }


    private static class TestJustSetterAnnotatedField {

        @Setter
        private String field1;

    }

    private static class TestJustGetterAnnotatedField {

        @Setter
        private String field1;

    }

    private static class TestGetterAndSetterAnnotatedField {

        @Setter
        @Getter
        private String field1;

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

}