package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;


/**
 * Unit test for {@link IsConstructorMatcher}.
 */
public class HasPublicNoargConstructorMatcherTest {

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


    public enum TestEnum {
        TEST
    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkDefaultConstructor() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(DefaultNoargConstructor.class);

                                MatcherAssert.assertThat("Must return true for class with default constructor", AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkWithNoargConstructor() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(NonNoargConstructor.class);

                                MatcherAssert.assertThat("Must return false for class with no noarg constructor", !AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void checkWithPublicNoargConstructor() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(NoPublicNoargConstructor.class);

                                MatcherAssert.assertThat("Must return false for class with no public noarg constructor", !AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkWithPublicNoargConstructorNextToOtherConstructor() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(PublicNoargConstructorNextToOtherConstructors.class);

                                MatcherAssert.assertThat("Must return true for class with default constructor", AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(typeElement));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkWithPublicNoargConstructorNextToOtherConstructors() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Element testElement = FluentElementFilter.createFluentElementFilter(
                                                TypeUtils.TypeRetrieval.getTypeElement(HasPublicNoargConstructorMatcherTest.class).getEnclosedElements()
                                        ).applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testField")
                                        .getResult().get(0);

                                MatcherAssert.assertThat("Must return true for class with default constructor", AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(testElement));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkestNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Must return false for null valued parameter", !AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------------------------------
    // -- Lombok related tests
    // -----------------------------------------------

    @Test
    public void checkestLombokNoConstructoButWithAllArgConstructorAnnotation() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Must return false for Class with arg constructor annotated with NoArgsConstructor annotation", AptkCoreMatchers.HAS_PUBLIC_NOARG_CONSTRUCTOR.getMatcher().check(element));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

}
