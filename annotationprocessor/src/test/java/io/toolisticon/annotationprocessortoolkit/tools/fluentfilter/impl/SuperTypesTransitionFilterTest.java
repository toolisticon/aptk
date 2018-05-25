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
 * Unit test for {@link SuperTypesTransitionFilter}.
 */
@RunWith(Parameterized.class)
public class SuperTypesTransitionFilterTest extends AbstractAnnotationProcessorUnitTest {

    public interface TestInterface {

    }

    public static class SuperClass implements TestInterface {

    }

    public static class InheritingClass extends SuperClass {

    }


    public SuperTypesTransitionFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        {
                                "ParentElementTransitionFilter test - test transition with single input elements",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                              MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedSuperType1,expectedSuperType2,expectedSuperType3));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "ParentElementTransitionFilter test - test transition with single input elements",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                              MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(expectedSuperType1,expectedSuperType2,expectedSuperType3,expectedSuperType1,expectedSuperType2,expectedSuperType3));


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
