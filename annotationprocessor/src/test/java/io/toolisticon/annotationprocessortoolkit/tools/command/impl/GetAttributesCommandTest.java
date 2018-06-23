package io.toolisticon.annotationprocessortoolkit.tools.command.impl;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit Test for {@link GetAttributesCommand}.
 */
@RunWith(Parameterized.class)
public class GetAttributesCommandTest extends AbstractAnnotationProcessorUnitTest {

    public GetAttributesCommandTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

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


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "execute - Data annotated class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestDataAnnotatedClass.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - Getter annotated class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestJustGetterAnnotatedClass.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - Setter annotated class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestJustSetterAnnotatedClass.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - Getter and Setter annotated class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestGetterAndSetterAnnotatedClass.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - Mixed Getter and Setter annotated class and field 2",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestMixedGetterAndSetterAnnotatedClassAndField1.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - Mixed Getter and Setter annotated class and field 2",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestMixedGetterAndSetterAnnotatedClassAndField2.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - getter annotated field",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestJustGetterAnnotatedField.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - setter annotated field",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestJustSetterAnnotatedField.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "execute - getter and setter annotated field",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestGetterAndSetterAnnotatedField.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "execute - getter and setter annotated method",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestFieldGetterAndSetterMethods.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                                              MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "execute - getter and setter annotated method with invalid parameter type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType.class);

                                                              BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.INSTANCE.execute(typeElement);

                                                              MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                }

        );


    }


    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
