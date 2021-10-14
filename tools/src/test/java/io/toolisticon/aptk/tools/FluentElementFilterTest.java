package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

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

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void filterByKinds() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                        .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                        .getResult();

                MatcherAssert.assertThat(results, Matchers.hasSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByKinds_nullValuedFilterArgument_shouldReturnUnfilteredListElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByKinds_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf()
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                        .applyInvertedFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                        .getResult();

                MatcherAssert.assertThat(results, Matchers.hasSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void inverseFilterByKinds_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(results, Matchers.<Element>empty());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByKinds_shouldReturnEmptyListForNonInvertedFNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> results = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf()
                        .getResult();

                MatcherAssert.assertThat(results, Matchers.empty());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnListForOneMatchingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // one search attribute
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("publicStaticField")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // two search attributes
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("publicStaticField", "synchronizedMethod")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("XXX")
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle no passed filter args correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf()
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNames_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // null valued element list
                List<? extends Element> result =
                        FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf(null)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // one search attribute
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf("publicStaticField")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // two search attributes
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf("publicStaticField", "synchronizedMethod")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf("XXX")
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle no passed filter args correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf()
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.<Element>empty());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByNames_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // null valued element list
                List<? extends Element> result =
                        FluentElementFilter.createFluentElementFilter((List<Element>) null)
                                .applyInvertedFilter(CoreMatchers.BY_NAME).filterByOneOf(null)
                                .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnNonEmptyListForOneMatchingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // one search attribute
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf("publicSt.*Field")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // two search attributes
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf("publicSt.*Field", "synchr.*Method")
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf("XXX")
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnUnfilteredListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle no passed filter args correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf()
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByNameWithRegularExpressions_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                        .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForOneMatchingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // two search attributes
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(TestAnnotation.class)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle no passed filter args correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf()
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnUnfilteredListForNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void filterByAnnotation_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // one search attribute
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // two search attributes
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForNonExistingFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // returns empty result
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForInvertedFilteringWithNullValuedFilterArgument() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(null)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.<Element>empty());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void inverseFilterByAnnotation_shouldReturnEmptyListForNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // handle nulls correctly
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter((List<Element>) null)
                        .applyInvertedFilter(CoreMatchers.BY_ANNOTATION).filterByOneOf(FilterTestAnnotation1.class)
                        .getResult();

                MatcherAssert.assertThat(result, Matchers.hasSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_succeedingValidationWithEmptyElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList()).isEmpty(), Matchers.is(true));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_failingValidationWithNonEmptyElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects non empty result correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).isEmpty(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isEmpty_succeedingValidationWithNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects non empty result correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).isEmpty(), Matchers.is(true));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithEmptyElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasSingleElement(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_succeedingValidationWithElementListWithSizeOne() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects single result elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasSingleElement(), Matchers.is(true));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithElementListWithSizeBiggerOne() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects multiple elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSingleElement(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSingleElement_failingValidationWithNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects multiple elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasSingleElement(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_succeedingValidationWithMultipleElements() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects multiple elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasMultipleElements(), Matchers.is(true));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_succeedingValidationWithEmptyList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasMultipleElements(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_failingValidationWithListOfSizeOne() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects single result elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasMultipleElements(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasMultipleElement_failingValidationWithNullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects single result elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasMultipleElements(), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithEmptyElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(new ArrayList<Element>()).hasSize(0), Matchers.is(true));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithElementListOfSizeOne() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects single result elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(convertToList(element)).hasSize(1), Matchers.is(true));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithElementListOfSizeBiggerThanOne() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects multiple elements correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSize(element.getEnclosedElements().size()), Matchers.is(true));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_succeedingValidationWithNullValued() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects null valued element list correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).hasSize(3), Matchers.is(false));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSize_failingValidation1() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void geResultSize_nullValuedElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // detects empty results correctly
                MatcherAssert.assertThat(FluentElementFilter.createFluentElementFilter((List<Element>) null).getResultSize(), Matchers.is(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void geResultSize_emptyElementList() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

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
