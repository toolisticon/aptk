package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.AnnotationValue;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.stream.Collectors;

/**
 * Unit test for {@link AnnotationValueWrapper}.
 */
public class AnnotationValueWrapperTest {

    enum TestEnum {
        ABC,
        DEF,
        HIJ
    }

    @interface MyValueAnnotation {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MyTestAnnotation {

        char charValue() default 'X';

        String stringValue() default "XOXO";

        int intValue() default 1;

        long longValue() default 2;

        float floatValue() default 3.0f;

        double doubleValue() default 4.0f;

        boolean booleanValue() default true;

        TestEnum enumValue() default TestEnum.ABC;

        Class<?> classValue() default String.class;

        MyValueAnnotation annotationValue();

        String[] arrayValue() default {"ABC", "DEF"};
    }

    @PassIn
    @MyTestAnnotation(annotationValue = @MyValueAnnotation)
    static class MyTestClass {

    }

    @Test
    public void test_wrap_and_unwrap() {
        AnnotationValue annotationMirror = Mockito.mock(AnnotationValue.class);

        MatcherAssert.assertThat(AnnotationValueWrapper.wrap(annotationMirror).unwrap(), Matchers.is(annotationMirror));
    }


    @Test
    public void test_isInteger_and_getIntegerValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Integer value", annotationMirrorWrapper.getAttributeWithDefault("intValue").isInteger());
                MatcherAssert.assertThat("Shouldn't be detected as Integer value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isInteger());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("intValue").getIntegerValue(), Matchers.is(1));
                MatcherAssert.assertThat("Must be null", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getIntegerValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }


    @Test
    public void test_isLong_and_getLongValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Long value", annotationMirrorWrapper.getAttributeWithDefault("longValue").isLong());
                MatcherAssert.assertThat("Shouldn't be detected as Long value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isLong());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("longValue").getLongValue(), Matchers.is(2L));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getLongValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isBoolean_and_getBooleanValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Boolean value", annotationMirrorWrapper.getAttributeWithDefault("booleanValue").isBoolean());
                MatcherAssert.assertThat("Shouldn't be detected as Boolean value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isBoolean());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("booleanValue").getBooleanValue(), Matchers.is(true));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getBooleanValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }


    @Test
    public void test_isFloat_and_getFloatValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Float value", annotationMirrorWrapper.getAttributeWithDefault("floatValue").isFloat());
                MatcherAssert.assertThat("Shouldn't be detected as Float value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isFloat());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("floatValue").getFloatValue(), Matchers.is(3.0f));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getFloatValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isDouble_and_getDoubleValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Double value", annotationMirrorWrapper.getAttributeWithDefault("doubleValue").isDouble());
                MatcherAssert.assertThat("Shouldn't be detected as Double value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isDouble());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("doubleValue").getDoubleValue(), Matchers.is(4.0));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getDoubleValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isString_and_getStringValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as String value", annotationMirrorWrapper.getAttributeWithDefault("stringValue").isString());
                MatcherAssert.assertThat("Shouldn't be detected as String value", !annotationMirrorWrapper.getAttributeWithDefault("doubleValue").isString());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("stringValue").getStringValue(), Matchers.is("XOXO"));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("doubleValue").getStringValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isChar_and_getCharValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Char value", annotationMirrorWrapper.getAttributeWithDefault("charValue").isChar());
                MatcherAssert.assertThat("Shouldn't be detected as Char value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isChar());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("charValue").getCharValue(), Matchers.is('X'));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getCharValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isEnum_and_getEnumValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Enum value", annotationMirrorWrapper.getAttributeWithDefault("enumValue").isEnum());
                MatcherAssert.assertThat("Shouldn't be detected as Enum value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isEnum());

                // now test getting the value as VariableElement
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("enumValue").getEnumValue().getSimpleName(), Matchers.is("ABC"));
                MatcherAssert.assertThat("Should get empty Optional for non enum attribute", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getEnumValue() == null);

                // now getting the enum value as real enum value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("enumValue").getEnumValue(TestEnum.class), Matchers.is(TestEnum.ABC));
                MatcherAssert.assertThat("Should get empty Optional for non enum attribute", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getEnumValue(TestEnum.class) == null);


            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isClass_and_getClassValue_and_getTypeMirrorValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as class value", annotationMirrorWrapper.getAttributeWithDefault("classValue").isClass());
                MatcherAssert.assertThat("Shouldn't be detected as class value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isClass());

                // now test getting the value as TypeMirrorWrapper
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("classValue").getClassValue().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat("Should get empty Optional for non class attribute", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getClassValue() == null);


            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isAnnotation_and_getAnnotationValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as array value", annotationMirrorWrapper.getAttributeWithDefault("annotationValue").isAnnotation());
                MatcherAssert.assertThat("Shouldn't be detected as array value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isAnnotation());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("annotationValue").getAnnotationValue().asElement().getQualifiedName(), Matchers.is(MyValueAnnotation.class.getCanonicalName()));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getArrayValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isArray_and_getArrayValue() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as array value", annotationMirrorWrapper.getAttributeWithDefault("arrayValue").isArray());
                MatcherAssert.assertThat("Shouldn't be detected as array value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").isArray());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("arrayValue").getArrayValue().stream().map(e -> e.getStringValue()).collect(Collectors.toList()), Matchers.containsInAnyOrder("ABC", "DEF"));
                MatcherAssert.assertThat("Must be empty Optional", annotationMirrorWrapper.getAttributeWithDefault("stringValue").getArrayValue() == null);

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

}
