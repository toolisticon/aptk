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
 * Unit test for {@link IsInterfaceMatcher}.
 */
@RunWith(Parameterized.class)
public class IsGetterMethodMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsGetterMethodMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public static abstract class TestClass {
        public void getNoReturnType() {

        }

        private int getNonVisible() {
            return 0;
        }

        public int getHasParam(String param) {
            return 0;
        }

        public static int getIsStatic() {
            return 0;
        }

        public abstract int getIsAbstract();

        public int getValid() {
            return 0;
        }

        public boolean getGetterValid() {
            return true;
        }

        public boolean hasGetterValid() {
            return true;
        }

        public boolean isGetterValid() {
            return true;
        }

        public String hasGetterInvalid() {
            return "";
        }

        public String isGetterInvalid() {
            return "";
        }

    }

    public static void checkGetter(TypeElement typeElement, String methodName, boolean expectedResult) {

        List<ExecutableElement> executableElements =
                FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                        .applyFilter(CoreMatchers.IS_METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(methodName)
                        .getResult();

        MatcherAssert.assertThat("Precondition: Single result for " + methodName, executableElements.size() == 1);

        MatcherAssert.assertThat("methodName", CoreMatchers.IS_GETTER_METHOD.getMatcher().check(executableElements.get(0)), Matchers.is(expectedResult));

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


                                                        checkGetter(typeElement, "getNoReturnType", false);
                                                        checkGetter(typeElement, "getNonVisible", false);
                                                        checkGetter(typeElement, "getIsStatic", false);
                                                        checkGetter(typeElement, "getIsAbstract", false);
                                                        checkGetter(typeElement, "getValid", true);
                                                        checkGetter(typeElement, "getGetterValid", true);
                                                        checkGetter(typeElement, "hasGetterValid", true);
                                                        checkGetter(typeElement, "isGetterValid", true);
                                                        checkGetter(typeElement, "hasGetterInvalid", false);
                                                        checkGetter(typeElement, "isGetterInvalid", false);


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


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_GETTER_METHOD.getMatcher().check(null));


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
