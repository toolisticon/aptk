package io.toolisticon.aptk.tools.fluentfilter.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.fluentfilter.TransitionFilters;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link ParentElementTransitionFilter}.
 */
public class ParentElementTransitionFilterTest {

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

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void parentElementTransitionFilter_testTransitionWithSingleInputElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                TypeElement testElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);

                                MatcherAssert.assertThat("PRECONDITION : testELement must not be null", testElement, Matchers.notNullValue());

                                TypeElement expectedParentElement = (TypeElement) testElement.getEnclosingElement();


                                List<Element> list = FluentElementFilter.createFluentElementFilter(testElement).applyTransitionFilter(TransitionFilters.PARENT_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder((Element) expectedParentElement));


                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void parentElementTransitionFilter_testTransitionWithMultipleInputElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement testElement1 = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);
                                TypeElement testElement2 = TypeUtils.TypeRetrieval.getTypeElement(TestClass2.class);

                                MatcherAssert.assertThat("PRECONDITION : testElement1 must not be null", testElement1, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : testElement2 must not be null", testElement2, Matchers.notNullValue());

                                Element expectedElements1 = testElement1.getEnclosingElement();
                                Element expectedElements2 = testElement2.getEnclosingElement();


                                List<Element> list = FluentElementFilter.createFluentElementFilter(testElement1, testElement2).applyTransitionFilter(TransitionFilters.PARENT_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(
                                        expectedElements1,
                                        expectedElements2
                                ));


                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }


}
