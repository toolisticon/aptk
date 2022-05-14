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
        HIJ;
    }

    @interface MyValueAnnotation{

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
    @MyTestAnnotation(annotationValue=@MyValueAnnotation)
    static class MyTestClass {

    }

    @Test
    public void test_wrap_and_unwrap() {
        AnnotationValue annotationMirror = Mockito.mock(AnnotationValue.class);

        MatcherAssert.assertThat(AnnotationValueWrapper.wrap(annotationMirror).unwrap(), Matchers.is(annotationMirror));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_wrap_with_null() {
        AnnotationValueWrapper.wrap(null);
    }

    @Test
    public void test_isInteger_and_getIntegerValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Integer value", annotationMirrorWrapper.getAttributeWithDefault("intValue").get().isInteger());
                MatcherAssert.assertThat("Shouldn't be detected as Integer value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isInteger());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("intValue").get().getIntegerValue().get(), Matchers.is(1));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getIntegerValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }


    @Test
    public void test_isLong_and_getLongValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Long value", annotationMirrorWrapper.getAttributeWithDefault("longValue").get().isLong());
                MatcherAssert.assertThat("Shouldn't be detected as Long value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isLong());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("longValue").get().getLongValue().get(), Matchers.is(2L));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getLongValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isBoolean_and_getBooleanValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Boolean value", annotationMirrorWrapper.getAttributeWithDefault("booleanValue").get().isBoolean());
                MatcherAssert.assertThat("Shouldn't be detected as Boolean value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isBoolean());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("booleanValue").get().getBooleanValue().get(), Matchers.is(true));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getBooleanValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }


    @Test
    public void test_isFloat_and_getFloatValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Float value", annotationMirrorWrapper.getAttributeWithDefault("floatValue").get().isFloat());
                MatcherAssert.assertThat("Shouldn't be detected as Float value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isFloat());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("floatValue").get().getFloatValue().get(), Matchers.is(3.0f));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getFloatValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isDouble_and_getDoubleValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Double value", annotationMirrorWrapper.getAttributeWithDefault("doubleValue").get().isDouble());
                MatcherAssert.assertThat("Shouldn't be detected as Double value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isDouble());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("doubleValue").get().getDoubleValue().get(), Matchers.is(4.0));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getDoubleValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isString_and_getStringValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as String value", annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isString());
                MatcherAssert.assertThat("Shouldn't be detected as String value", !annotationMirrorWrapper.getAttributeWithDefault("doubleValue").get().isString());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getStringValue().get(), Matchers.is("XOXO"));
                MatcherAssert.assertThat("Must be empty Optional",!annotationMirrorWrapper.getAttributeWithDefault("doubleValue").get().getStringValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isChar_and_getCharValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Char value", annotationMirrorWrapper.getAttributeWithDefault("charValue").get().isChar());
                MatcherAssert.assertThat("Shouldn't be detected as Char value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isChar());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("charValue").get().getCharValue().get(), Matchers.is('X'));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getCharValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isEnum_and_getEnumValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as Enum value", annotationMirrorWrapper.getAttributeWithDefault("enumValue").get().isEnum());
                MatcherAssert.assertThat("Shouldn't be detected as Enum value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isEnum());

                // now test getting the value as VariableElement
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("enumValue").get().getEnumValue().get().getSimpleName(), Matchers.is("ABC"));
                MatcherAssert.assertThat("Should get empty Optional for non enum attribute", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getEnumValue().isPresent());

                // now getting the enum value as real enum value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("enumValue").get().getEnumValue(TestEnum.class).get(), Matchers.is(TestEnum.ABC));
                MatcherAssert.assertThat("Should get empty Optional for non enum attribute", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getEnumValue(TestEnum.class).isPresent());


            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isClass_and_getClassValue_and_getTypeMirrorValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as class value", annotationMirrorWrapper.getAttributeWithDefault("classValue").get().isClass());
                MatcherAssert.assertThat("Shouldn't be detected as class value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isClass());

                // now test getting the value as TypeMirrorWrapper
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("classValue").get().getClassValue().get().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat("Should get empty Optional for non class attribute", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getClassValue().isPresent());


            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isAnnotation_and_getAnnotationValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as array value", annotationMirrorWrapper.getAttributeWithDefault("annotationValue").get().isAnnotation());
                MatcherAssert.assertThat("Shouldn't be detected as array value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isAnnotation());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("annotationValue").get().getAnnotationValue().get().asElement().getQualifiedName(), Matchers.is(MyValueAnnotation.class.getCanonicalName()));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getArrayValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

    @Test
    public void test_isArray_and_getArrayValue () {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MyTestClass.class, PassIn.class, ((processingEnvironment, element) -> {

            try {
                ToolingProvider.setTooling(processingEnvironment);

                AnnotationMirrorWrapper annotationMirrorWrapper = TypeElementWrapper.wrap(element).getAnnotationMirror(MyTestAnnotation.class).get();

                MatcherAssert.assertThat("Should be detected as array value", annotationMirrorWrapper.getAttributeWithDefault("arrayValue").get().isArray());
                MatcherAssert.assertThat("Shouldn't be detected as array value", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().isArray());

                // now test getting the value
                MatcherAssert.assertThat(annotationMirrorWrapper.getAttributeWithDefault("arrayValue").get().getArrayValue().get().stream().map(e -> e.getStringValue().get()).collect(Collectors.toList()), Matchers.containsInAnyOrder("ABC", "DEF"));
                MatcherAssert.assertThat("Must be empty Optional", !annotationMirrorWrapper.getAttributeWithDefault("stringValue").get().getArrayValue().isPresent());

            } finally {
                ToolingProvider.clearTooling();
            }

        })).executeTest();

    }

}
