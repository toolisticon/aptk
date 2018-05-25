package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.TransitionFilters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link ChildElementsTransitionFilter}.
 */
@RunWith(Parameterized.class)
public class ChildElementsTransitionFilterTest extends AbstractAnnotationProcessorUnitTest {

    public static class TestClass {

        private String testField;

        private String testMethod() {
            return "";
        }


    }

    public static class TestClass2 {

        private String testField2;

        private String testMethod2() {
            return "";
        }


    }


    public ChildElementsTransitionFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        {
                                "ChildElementsTransitionFilter test - test transition with single input element",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement testElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);

                                                              MatcherAssert.assertThat("PRECONDITION : testELement must not be null", testElement, Matchers.notNullValue());

                                                              List<Element> expectedElements = (List<Element>) testElement.getEnclosedElements();


                                                              List<Element> list = FluentElementFilter.createFluentElementFilter(testElement).applyTransitionFilter(TransitionFilters.CHILD_ELEMENTS).getResult();
                                                              MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedElements.get(0), expectedElements.get(1), expectedElements.get(2)));


                                                          }
                                                      }
                                        )
                                        .build()



                        },
                        {
                                "ChildElementsTransitionFilter test - test transition with multiple input elements",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement testElement1 = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);
                                                              TypeElement testElement2 = TypeUtils.TypeRetrieval.getTypeElement(TestClass2.class);

                                                              MatcherAssert.assertThat("PRECONDITION : testELement1 must not be null", testElement1, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : testELement2 must not be null", testElement2, Matchers.notNullValue());

                                                              List<Element> expectedElements1 = (List<Element>) testElement1.getEnclosedElements();
                                                              List<Element> expectedElements2 = (List<Element>) testElement2.getEnclosedElements();


                                                              List<Element> list = FluentElementFilter.createFluentElementFilter(testElement1, testElement2).applyTransitionFilter(TransitionFilters.CHILD_ELEMENTS).getResult();
                                                              MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(
                                                                      expectedElements1.get(0),
                                                                      expectedElements1.get(1),
                                                                      expectedElements1.get(2),
                                                                      expectedElements2.get(0),
                                                                      expectedElements2.get(1),
                                                                      expectedElements2.get(2)
                                                              ));


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