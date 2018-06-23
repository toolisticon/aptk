package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link IsConstructorMatcher}.
 */
@RunWith(Parameterized.class)
public class HasPublicNoargConstructorMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public static class DefaultNoargConstructor {

    }

    public static class NonNoargConstructor {
        public NonNoargConstructor(String test) {

        }
    }

    public static class NoPublicNoargConstructor {
        private NoPublicNoargConstructor() {

        }
    }


    public static class PublicNoargConstructorNextToOtherConstructors {

        private PublicNoargConstructorNextToOtherConstructors(String arg) {

        }

        public PublicNoargConstructorNextToOtherConstructors(boolean arg) {

        }

        public PublicNoargConstructorNextToOtherConstructors() {

        }
    }

    private PublicNoargConstructorNextToOtherConstructors testField;


    public HasPublicNoargConstructorMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
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
                                "check : default constructor ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(DefaultNoargConstructor.class);

                                                        MatcherAssert.assertThat("Must return true for class with default constructor", CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : with no noarg constructor ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(NonNoargConstructor.class);

                                                        MatcherAssert.assertThat("Must return false for class with no noarg constructor", !CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : with no public noarg constructor ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(NoPublicNoargConstructor.class);

                                                        MatcherAssert.assertThat("Must return false for class with no public noarg constructor", !CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : With public noarg constructor next to other constructors ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(PublicNoargConstructorNextToOtherConstructors.class);

                                                        MatcherAssert.assertThat("Must return true for class with default constructor", CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : No TypeElement with public noarg constructor next to other constructors ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {

                                                        Element testElement = FluentElementFilter.createFluentElementFilter(
                                                                TypeUtils.TypeRetrieval.getTypeElement(HasPublicNoargConstructorMatcherTest.class).getEnclosedElements()
                                                        ).applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                                                .getResult().get(0);

                                                        MatcherAssert.assertThat("Must return true for class with default constructor", CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(testElement));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        {
                                "check : test null safety ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Must return false for null valued parameter", !CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(null));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        // -----------------------------------------------
                        // -- Lombok related tests
                        // -----------------------------------------------

                        /*-
                        {
                                "check : check lombok no constructor but with AllArgsConstructor annotation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.matchers.hasnoargsconstructormatcher/LombokNoConstructorWithAllArgsConstructorAnnotation.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Must return false for Class with standard constructor annotated with AllArgsConstructor annotation", !CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(element));


                                                    }

                                                }
                                        )
                                        .build()


                        },
                        */
                        {
                                "check : check lombok no constructor but with AllArgsConstructor annotation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.matchers.hasnoargsconstructormatcher/LombokNoArgsConstructorAnnotation.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        MatcherAssert.assertThat("Must return false for Class with arg constructor annotated with NoArgsConstructor annotation", CoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(element));


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