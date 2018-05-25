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
 * Unit test for {@link RemoveDuplicatesTransitionFilter}.
 */
@RunWith(Parameterized.class)
public class RemoveDuplicatesTransitionFilterTest extends AbstractAnnotationProcessorUnitTest {




    public RemoveDuplicatesTransitionFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        {
                                "RemoveDuplicatesTransitionFilter test - test transition to remove duplicate values",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {



                                                              List<Element> list = FluentElementFilter.createFluentElementFilter(element, element).applyTransitionFilter(TransitionFilters.REMOVE_DUPLICATES_ELEMENTS).getResult();
                                                              MatcherAssert.assertThat(list, Matchers.containsInAnyOrder((Element)element));


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
