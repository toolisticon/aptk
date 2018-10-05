package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.JavaFileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassArrayAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.DefaultValueAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;


/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
@RunWith(Parameterized.class)
public class AnnotationUtilsTest extends AbstractAnnotationProcessorUnitTest {

    public AnnotationUtilsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        // -----------------------------------
                        // Single class attribute
                        // -----------------------------------

                        {
                                "AnnotationUtils test - class attribute - empty value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_empty")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class), Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "AnnotationUtils test - class attribute - String.class value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_atDefaultValue")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class), Matchers.equalTo(String.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "AnnotationUtils test - class attribute with explicit attribute name - Long.class value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_atNamedAttribute")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class, "classAttribute"), Matchers.equalTo(Long.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------------------
                        // array class attribute
                        // -----------------------------------

                        {
                                "AnnotationUtils test - array class attribute - empty value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_empty")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class), Matchers.arrayWithSize(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "AnnotationUtils test - array class attribute - String.class, Double.class, Float.class value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atDefaultValue")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class)), Matchers.contains(String.class.getCanonicalName(), Double.class.getCanonicalName(), Float.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "AnnotationUtils test - array class attribute with explicit attribute name - Long.class, Integer.class value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atNamedAttribute")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class, "classArrayAttribute")), Matchers.contains(Long.class.getCanonicalName(), Integer.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "AnnotationUtils test - array class attribute with explicit attribute name - Long.class, Integer.class, AnnotationClassAttributeTestClass value",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentElementFilter(result)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atNamedAttribute_withUncompiledClass")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class, "classArrayAttribute")), Matchers.contains(Long.class.getCanonicalName(), Integer.class.getCanonicalName(), "io.toolisticon.annotationprocessor.AnnotationClassAttributeTestClass"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // --------------------------------------------
                        // -- getAnnotationValueOfAttribute
                        // --------------------------------------------

                        {
                                "AnnotationUtils test - getAnnotationValueOfAttribute - get implicitly set annotation value must return null",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);

                                                              AnnotationValue value = AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror);

                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(value, Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // --------------------------------------------
                        // -- getAnnotationValueOfAttributeWithDefaults
                        // --------------------------------------------

                        {
                                "AnnotationUtils test - getAnnotationValueOfAttributeWithDefaults - get implicitly set annotation value (default value)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);

                                                              AnnotationValue value = AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror);

                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat((Long) value.getValue(), Matchers.is(5L));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // --------------------------------------------
                        // -- getMandatoryAttributeValueNames
                        // --------------------------------------------

                        {
                                "AnnotationUtils test - getMandatoryAttributeValueNames - get mandatory attribute value names",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);

                                                              String[] names = AnnotationUtils.getMandatoryAttributeValueNames(annotationMirror);

                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(names), Matchers.contains("mandatoryValue"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // --------------------------------------------
                        // -- getOptionalAttributeValueNames
                        // --------------------------------------------

                        {
                                "AnnotationUtils test - getOptionalAttributeValueNames - get optional attribute value names",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);

                                                              String[] names = AnnotationUtils.getOptionalAttributeValueNames(annotationMirror);

                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(names), Matchers.contains("value"));

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
        return JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }
}