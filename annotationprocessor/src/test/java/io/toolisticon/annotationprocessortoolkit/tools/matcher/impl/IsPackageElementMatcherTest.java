package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link IsPackageElementMatcher}.
 */
@RunWith(Parameterized.class)
public class IsPackageElementMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public IsPackageElementMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "check : matching PackageElement ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        // find field
                                                        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(element, ElementKind.PACKAGE);
                                                        MatcherAssert.assertThat("Precondition: should have found one method", result != null);
                                                        MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result instanceof PackageElement);

                                                        MatcherAssert.assertThat("Should return true for method : ", CoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(result));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : mismatching PackageElement (class)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Should return false for non PackageElement : ", !CoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(element));


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


                                                        MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(null));


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
