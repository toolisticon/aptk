package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorTest extends AbstractAnnotationProcessorUnitTest {

    public AbstractAnnotationProcessorTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "FluentElementFilter : Do filterings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat(result, Matchers.hasSize(8));


                                                              result = FluentElementFilter.createFluentFilter(
                                                                      element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.FIELD)
                                                                      .applyFilter(Filters.getModifierFilter()).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                                                      .getResult();
                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        /*-
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                    }
                                },
                                true


                        },
                        */

                }

        );


    }


    @Test
    public void test() {
        super.test();
    }

}
