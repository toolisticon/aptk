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
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link IsEnumMatcher}.
 */
@RunWith(Parameterized.class)
public class IsEnumMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsEnumMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public enum TestEnum {
        TEST
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "check : matching enum ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(IsEnumMatcherTest.class);
                                                        List<? extends Element> enumList = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "TestEnum");
                                                        MatcherAssert.assertThat("Precondition: must have found a enum", enumList.size() >= 1);


                                                        MatcherAssert.assertThat("Should return true for enum : ", CoreMatchers.IS_ENUM.getMatcher().check(enumList.get(0)));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : mismatching enum (class)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for non enum : ", !CoreMatchers.IS_ENUM.getMatcher().check(element));


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


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_ENUM.getMatcher().check(null));


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