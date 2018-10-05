package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.CompileTestBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.JavaFileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.TransitionFilters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

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

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .createCompileTestBuilder()
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Test
    public void parentElementTransitionFilter_testTransitionWithSingleInputElements() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

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
                .testCompilation();

    }

    @Test
    public void parentElementTransitionFilter_testTransitionWithMultipleInputElements() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

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
                .testCompilation();

    }

}
