package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.TransitionFilters;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link ChildElementsTransitionFilter}.
 */
public class ChildElementsTransitionFilterTest {

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


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Test
    public void childElementsTransitionFilter_testTransitionWithSingleInputElement() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        TypeElement testElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);

                        MatcherAssert.assertThat("PRECONDITION : testELement must not be null", testElement, Matchers.notNullValue());

                        List<Element> expectedElements = (List<Element>) testElement.getEnclosedElements();


                        List<Element> list = FluentElementFilter.createFluentElementFilter(testElement).applyTransitionFilter(TransitionFilters.CHILD_ELEMENTS).getResult();
                        MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedElements.get(0), expectedElements.get(1), expectedElements.get(2)));


                    }
                })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void childElementsTransitionFilter_testTransitionWithMultipleInputElement() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
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
                })

                .compilationShouldSucceed()
                .executeTest();

    }


}
