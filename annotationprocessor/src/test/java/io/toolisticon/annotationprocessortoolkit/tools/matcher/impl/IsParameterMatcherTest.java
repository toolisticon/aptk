package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link IsParameterMatcher}.
 */
@RunWith(Parameterized.class)
public class IsParameterMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsParameterMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public void testMethod(String parameter) {

    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "check : matching parameter ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(IsParameterMatcherTest.class);
                                                        List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "testMethod");
                                                        MatcherAssert.assertThat("Precondition: found test method", methods.size() == 1);
                                                        ExecutableElement testMethod = (ExecutableElement) methods.get(0);
                                                        MatcherAssert.assertThat("Precondition: found at least one parameter", ((ExecutableElement) methods.get(0)).getParameters().size() >= 1);

                                                        MatcherAssert.assertThat("Should return true for parameter : ", CoreMatchers.IS_PARAMETER.getMatcher().check(testMethod.getParameters().get(0)));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : mismatching parameter (class)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for non parameter : ", !CoreMatchers.IS_PARAMETER.getMatcher().check(element));


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


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_PARAMETER.getMatcher().check(null));


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