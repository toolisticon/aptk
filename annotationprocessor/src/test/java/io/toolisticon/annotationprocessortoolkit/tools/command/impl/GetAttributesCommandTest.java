package io.toolisticon.annotationprocessortoolkit.tools.command.impl;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.BeanUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestDataAnnotatedClass")
                                                                      .getResult().get(0);
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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustGetterAnnotatedClass")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustSetterAnnotatedClass")
                                                                      .getResult().get(0);


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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestGetterAndSetterAnnotatedClass")
                                                                      .getResult().get(0);


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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestMixedGetterAndSetterAnnotatedClassAndField1")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestMixedGetterAndSetterAnnotatedClassAndField2")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustGetterAnnotatedField")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestJustSetterAnnotatedField")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestGetterAndSetterAnnotatedField")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethods")
                                                                      .getResult().get(0);

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
                                        .useCustomSourceFile("testcases.commands/GetAttributesCommandTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CLASS)
                                                                      .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType")
                                                                      .getResult().get(0);

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




    @Test
    public void test() {
        super.test();
    }

    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
