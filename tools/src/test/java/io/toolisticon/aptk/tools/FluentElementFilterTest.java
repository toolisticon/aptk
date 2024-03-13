package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import io.toolisticon.cute.TestAnnotation;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link FluentElementFilter}.
 */

public class FluentElementFilterTest {

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void filterByKinds() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(8));

                                for (Element resultElement : results) {
                                    MatcherAssert.assertThat(resultElement.getKind(), Matchers.is(ElementKind.FIELD));
                                }

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void filterByKinds_and_filterByModifiers() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .applyFilter(AptkCoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByKinds_nullValuedElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByKinds_nullValuedFilterArgument_shouldReturnUnfilteredListElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByKinds_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    //--

    @Test
    public void inverseFilterByKinds_returnListForMatchingFilterArguments() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size() - 8));

                                for (Element resultElement : results) {
                                    MatcherAssert.assertThat(resultElement.getKind(), Matchers.not(ElementKind.FIELD));
                                }


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByKinds_nullValuedElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.hasSize(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void inverseFilterByKinds_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.<Element>empty());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByKinds_shouldReturnEmptyListForNonInvertedFNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(results, Matchers.empty());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnListForOneMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // one search attribute
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("publicStaticField")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void filterByNames_shouldReturnListForTwoMatchingFilterArguments() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // two search attributes
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("publicStaticField", "synchronizedMethod")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnEmptyListForNonMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("XXX")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle no passed filter args correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // null valued element list
                                List<? extends Element> result =
                                        FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(null)
                                                .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // ....

    @Test
    public void inverseFilterByNames_shouldReturnListForOneMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // one search attribute
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("publicStaticField")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                for (Element resultElement : result) {
                                    MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("publicStaticField"));
                                }


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnListForTwoMatchingFilterArguments() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // two search attributes
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("publicStaticField", "synchronizedMethod")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 2));
                                for (Element resultElement : result) {
                                    MatcherAssert.assertThat("Must nor be publicStaticField or synchronizedMethod", !resultElement.getSimpleName().equals("publicStaticField") && !resultElement.getSimpleName().equals("synchronizedMethod"));
                                }


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForNonMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("XXX")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // handle no passed filter args correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.<Element>empty());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // null valued element list
                                List<? extends Element> result =
                                        FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                                .applyInvertedFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(null)
                                                .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnNonEmptyListForOneMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // one search attribute
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf("publicSt.*Field")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void filterByNameWithRegularExpressions_shouldReturnListForTwoMatchingFilterArguments() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // two search attributes
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf("publicSt.*Field", "synchr.*Method")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnEmptyListForNonMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf("XXX")
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle no passed filter args correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForOneMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void filterByAnnotation_shouldReturnListForTwoMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // two search attributes
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString()), Matchers.containsInAnyOrder("synchronizedMethod"));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForNonMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(TestAnnotation.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle no passed filter args correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf()
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // --


    @Test
    public void inverseFilterByAnnotation_shouldReturnListForOneMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // one search attribute
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                for (Element resultElement : result) {
                                    MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                }

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnListForTwoMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // two search attributes
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                for (Element resultElement : result) {
                                    MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                }

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnUnfilteredListForNonMatchingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // returns empty result
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(null)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.<Element>empty());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // handle nulls correctly
                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                        .applyInvertedFilter(AptkCoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
                                        .getResult();

                                MatcherAssert.assertThat(result, Matchers.hasSize(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_succeedingValidationWithEmptyElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList()).isEmpty(), Matchers.is(true));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_failingValidationWithNonEmptyElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects non empty result correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).isEmpty(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_succeedingValidationWithNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects non empty result correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).isEmpty(), Matchers.is(true));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithEmptyElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasSingleElement(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_succeedingValidationWithElementListWithSizeOne() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects single result elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasSingleElement(), Matchers.is(true));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithElementListWithSizeBiggerOne() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects multiple elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSingleElement(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects multiple elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasSingleElement(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_succeedingValidationWithMultipleElements() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects multiple elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasMultipleElements(), Matchers.is(true));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_succeedingValidationWithEmptyList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasMultipleElements(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_failingValidationWithListOfSizeOne() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects single result elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasMultipleElements(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_failingValidationWithNullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects single result elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasMultipleElements(), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithEmptyElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasSize(0), Matchers.is(true));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithElementListOfSizeOne() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects single result elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasSize(1), Matchers.is(true));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithElementListOfSizeBiggerThanOne() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects multiple elements correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSize(element.getEnclosedElements().size()), Matchers.is(true));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithNullValued() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects null valued element list correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasSize(3), Matchers.is(false));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_failingValidation1() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void geResultSize_nullValuedElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).getResultSize(), Matchers.is(0));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void geResultSize_emptyElementList() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // detects empty results correctly
                                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).getResultSize(), Matchers.is(element.getEnclosedElements().size()));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
