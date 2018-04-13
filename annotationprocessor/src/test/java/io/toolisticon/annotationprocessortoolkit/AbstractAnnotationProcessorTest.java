package io.toolisticon.annotationprocessortoolkit;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
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

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat(result, Matchers.hasSize(8));


                                                              result = FluentElementFilter.createFluentElementFilter(
                                                                      element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                                                      .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                                                      .getResult();
                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "createSupportedAnnotationSet : without parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(), Matchers.<String>empty());

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "createSupportedAnnotationSet : with parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(Override.class, TestAnnotation.class), Matchers.contains(Override.class.getCanonicalName(), TestAnnotation.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "createSupportedAnnotationSet : with null value parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(null), Matchers.<String>empty());
                                                              MatcherAssert.assertThat(AbstractAnnotationProcessor.createSupportedAnnotationSet(Override.class, null, TestAnnotation.class), Matchers.contains(Override.class.getCanonicalName(), TestAnnotation.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "wrapToArray : with null value parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Should return null",AbstractAnnotationProcessor.wrapToArray((String) null)[0] == null);

                                                              Class[] resultArray = AbstractAnnotationProcessor.wrapToArray(Override.class, null, TestAnnotation.class);
                                                              MatcherAssert.assertThat(Arrays.asList(resultArray), Matchers.hasSize(3));
                                                              MatcherAssert.assertThat(resultArray[0], Matchers.equalTo((Class) Override.class));
                                                              MatcherAssert.assertThat(resultArray[1], Matchers.nullValue());
                                                              MatcherAssert.assertThat(resultArray[2], Matchers.equalTo((Class) TestAnnotation.class));

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
