package io.toolisticon.aptk.validators;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link FluentElementValidator}.
 */
public class FluentElementValidatorTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Test
    public void shouldValidateSuccessfullyWithAllOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldValidateWithFailureWithAllOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC, Modifier.FINAL).validateAndIssueMessages());

                            }
                        })

                .compilationShouldFail()
                .executeTest();

    }

    @Test
    public void shouldValidateSuccessfullyWithAtLeastOneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldValidateWithFailureWithAtLeastOneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.STATIC, Modifier.FINAL).validateAndIssueMessages());

                            }
                        })

                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void shouldValidateSuccessfullyWithOneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasOneOf(Modifier.SYNCHRONIZED, Modifier.FINAL, Modifier.ABSTRACT).validateAndIssueMessages());

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldValidateWithFailureWithOneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

                            }
                        })

                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void shouldValidateSuccessfullyWithNoneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.FINAL, Modifier.STATIC).validateAndIssueMessages());

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldValidateWithFailureWithNoneOf() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                // Test
                                MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

                            }

                        })

                .compilationShouldFail()
                .executeTest();

    }

    @Test
    public void shouldValidateSuccessfullyIsMatcher() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);


                                // Test
                                MatcherAssert.assertThat("Should be successfully validated", FluentElementValidator.createFluentElementValidator(elements.get(0)).is(AptkCoreMatchers.IS_EXECUTABLE_ELEMENT).validateAndIssueMessages())
                                ;

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldValidateWithFailureIsMatcher() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // check null value
                                List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .getResult();
                                MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() >= 1);


                                // Test
                                MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(elements.get(0)).is(AptkCoreMatchers.IS_EXECUTABLE_ELEMENT).applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.STATIC).validateAndIssueMessages())
                                ;

                            }
                        })

                .compilationShouldFail()
                .executeTest();

    }

}
