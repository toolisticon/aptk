package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;



/**
 * Unit test for {@link IsSetterMethodMatcher}.
 */
@RunWith(Parameterized.class)
public class IsSetterMethodMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsSetterMethodMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public static abstract class TestClass {
        public String setWithReturnType(String param) {
            return "";
        }

        private void setNonVisible(String param) {
        }

        public void setHasMultipleParam(String param1, String param2) {

        }

        public void setHasNoParam() {

        }

        public static void setIsStatic(String param) {

        }

        public abstract void setIsAbstract(String param);



        public void xxxNameInvalid(String param) {

        }

        public  void setValid(String param) {
        }
    }

    public static void checkSetter (TypeElement typeElement, String methodName, boolean expectedResult) {

        List<ExecutableElement> executableElements =
                FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                        .applyFilter(CoreMatchers.IS_METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(methodName)
                        .getResult();

        MatcherAssert.assertThat("Precondition: Single result for " + methodName, executableElements.size() == 1);

        MatcherAssert.assertThat("methodName",CoreMatchers.IS_SETTER_METHOD.getMatcher().check(executableElements.get(0)), Matchers.is(expectedResult));

    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "check : matching interface ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);



                                                        checkSetter(typeElement, "setWithReturnType", false);
                                                        checkSetter(typeElement, "setNonVisible", false);
                                                        checkSetter(typeElement, "setHasMultipleParam", false);
                                                        checkSetter(typeElement, "setHasNoParam", false);
                                                        checkSetter(typeElement, "setIsStatic", false);
                                                        checkSetter(typeElement, "setIsAbstract", false);
                                                        checkSetter(typeElement, "xxxNameInvalid", false);
                                                        checkSetter(typeElement, "setValid", true);




                                                    }

                                                }
                                        )
                                        .build()


                        },

                        {
                                "check : null valued element",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_SETTER_METHOD.getMatcher().check(null));


                                                    }

                                                }
                                        )
                                        .build()


                        },


                }

        );


    }

    @Test
    public void test() {
        super.test();
    }

}
