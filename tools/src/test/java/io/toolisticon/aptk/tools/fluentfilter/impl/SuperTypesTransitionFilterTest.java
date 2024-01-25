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
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Unit test for {@link SuperTypesTransitionFilter}.
 */
public class SuperTypesTransitionFilterTest {

    public interface TestInterface {

    }

    public static class SuperClass implements TestInterface {

    }

    public static class InheritingClass extends SuperClass {

    }


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Test
    public void parentElementTransitionFilter_testTransitionWithSingleInputElements() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                TypeElement testElement = TypeUtils.TypeRetrieval.getTypeElement(InheritingClass.class);

                                MatcherAssert.assertThat("PRECONDITION : testELement must not be null", testElement, Matchers.notNullValue());

                                TypeElement expectedSuperType1 = TypeUtils.TypeRetrieval.getTypeElement(SuperClass.class);
                                TypeElement expectedSuperType2 = TypeUtils.TypeRetrieval.getTypeElement(Object.class);
                                TypeElement expectedSuperType3 = TypeUtils.TypeRetrieval.getTypeElement(TestInterface.class);

                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType1 must not be null", expectedSuperType1, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType2 must not be null", expectedSuperType2, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType3 must not be null", expectedSuperType3, Matchers.notNullValue());


                                List<TypeElement> list = FluentElementFilter.createFluentElementFilter(testElement).applyTransitionFilter(TransitionFilters.SUPER_TYPE_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedSuperType1, expectedSuperType2, expectedSuperType3));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void parentElementTransitionFilter_testTransitionWithMultipleInputElements() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement testElement = TypeUtils.TypeRetrieval.getTypeElement(InheritingClass.class);

                                MatcherAssert.assertThat("PRECONDITION : testELement must not be null", testElement, Matchers.notNullValue());

                                TypeElement expectedSuperType1 = TypeUtils.TypeRetrieval.getTypeElement(SuperClass.class);
                                TypeElement expectedSuperType2 = TypeUtils.TypeRetrieval.getTypeElement(Object.class);
                                TypeElement expectedSuperType3 = TypeUtils.TypeRetrieval.getTypeElement(TestInterface.class);

                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType1 must not be null", expectedSuperType1, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType2 must not be null", expectedSuperType2, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : expectedSuperType3 must not be null", expectedSuperType3, Matchers.notNullValue());


                                List<TypeElement> list = FluentElementFilter.createFluentElementFilter(testElement, testElement).applyTransitionFilter(TransitionFilters.SUPER_TYPE_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedSuperType1, expectedSuperType2, expectedSuperType3, expectedSuperType1, expectedSuperType2, expectedSuperType3));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();

    }

}
