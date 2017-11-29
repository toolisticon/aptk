package de.holisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import de.holisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassArrayAttributeAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassAttributeAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;


/**
 * Integration test for {@link de.holisticon.annotationprocessortoolkit.tools.ElementUtils}.
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classAttribute_empty")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classAttribute_atDefaultValue")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classAttribute_atNamedAttribute")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classArrayAttribute_empty")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classArrayAttribute_atDefaultValue")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classArrayAttribute_atNamedAttribute")
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(ClassArrayAttributeAnnotation.class)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              Element testElement = FluentElementFilter.createFluentFilter(result)
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("test_classArrayAttribute_atNamedAttribute_withUncompiledClass")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class, "classArrayAttribute")), Matchers.contains(Long.class.getCanonicalName(), Integer.class.getCanonicalName(), "de.holisticon.annotationprocessor.AnnotationClassAttributeTestClass"));

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
        return JavaFileObjects.forResource("AnnotationClassAttributeTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }
}