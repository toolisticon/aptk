package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter}.
 */
@RunWith(Parameterized.class)
public class FluentElementFilterTest extends AbstractAnnotationProcessorTestBaseClass {

    public FluentElementFilterTest(String message, AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(FluentElementFilterTest.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "filterByKinds",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.FIELD)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(8));

                                        for (Element resultElement : results) {
                                            MatcherAssert.assertThat(resultElement.getKind(), Matchers.is(ElementKind.FIELD));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "filterByKinds and filterByModifiers",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.FIELD)
                                                .applyFilter(Filters.MODIFIER_FILTER).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));

                                    }
                                },
                                true


                        },
                        {
                                "filterByKinds : null valued element",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(null)
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.FIELD)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },
                        {
                                "filterByKinds : null valued filter argument should reurn unfiltered list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "filterByKinds : should return unfiltered list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },

                        //--
                        {
                                "inverseFilterByKinds : return list for matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).invert().filterByOneOf(ElementKind.FIELD)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size() - 8));

                                        for (Element resultElement : results) {
                                            MatcherAssert.assertThat(resultElement.getKind(), Matchers.not(ElementKind.FIELD));
                                        }


                                    }
                                },
                                true


                        },


                        {
                                "inverseFilterByKinds : null valued element",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(null)
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).invert().filterByOneOf(ElementKind.FIELD)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByKinds : should return empty list for inverted filtering with null valued filter argument ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).invert().filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByKinds : should return empty list noninverted, non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).invert().filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(results, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },


                        {
                                "filterByNames : returns list for one matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // one search attribute
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("publicStaticField")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNames : returns list for two matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // two search attributes
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("publicStaticField", "synchronizedMethod")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                        MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNames : returns empty list for non matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // returns empty result
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("XXX")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNames : returns unfiltered list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle no passed filter args correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                                    }
                                },
                                true


                        },
                        {
                                "filterByNames : should return unfiltered list for null valued filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                                    }
                                },
                                true


                        },

                        {
                                "filterByNames : returns empty list for null value element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // null valued element list
                                        List<? extends Element> result =
                                                createFluentElementFilter(null)
                                                        .applyFilter(Filters.NAME_FILTER).filterByOneOf(null)
                                                        .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));

                                    }
                                },
                                true


                        },

                        // ....


                        {
                                "inverseFilterByNames : returns list for one matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // one search attribute
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf("publicStaticField")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                        for (Element resultElement : result) {
                                            MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("publicStaticField"));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByNames : returns list for two matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // two search attributes
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf("publicStaticField", "synchronizedMethod")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 2));
                                        for (Element resultElement : result) {
                                            MatcherAssert.assertThat("Must nor be publicStaticField or synchronizedMethod", !resultElement.getSimpleName().equals("publicStaticField") && !resultElement.getSimpleName().equals("synchronizedMethod"));
                                        }

                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByNames : returns empty list for non matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // returns empty result
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf("XXX")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByNames : should return empty list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle no passed filter args correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByNames : should return empty list for inverted filtering with null valued filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },

                        {
                                "inverseFilterByNames : returns empty list for null value element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // null valued element list
                                        List<? extends Element> result =
                                                createFluentElementFilter(null)
                                                        .applyFilter(Filters.NAME_FILTER).invert().filterByOneOf(null)
                                                        .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));

                                    }
                                },
                                true


                        },


                        {
                                "filterByNameWithRegularExpressions : returns empty list for one matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // one search attribute
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf("publicSt.*Field")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNameWithRegularExpressions : returns list for two matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // two search attributes
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf("publicSt.*Field", "synchr.*Method")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                        MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNameWithRegularExpressions : returns empty list for non matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // returns empty result
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf("XXX")
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNameWithRegularExpressions : returns unfiltered list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle no passed filter args correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNameWithRegularExpressions : should return unfiltered list for null valued filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "filterByNameWithRegularExpressions : returns empty list for null value element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(null)
                                                .applyFilter(Filters.REGEX_NAME_FILTER).filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },


                        {
                                "filterByAnnotation : returns empty list for one matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // one search attribute
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByOneOf(FilterTestAnnotation1.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByAnnotation : returns list for two matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // two search attributes
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString()), Matchers.containsInAnyOrder("synchronizedMethod"));


                                    }
                                },
                                true


                        },
                        {
                                "filterByAnnotation : returns empty list for non matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // returns empty result
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByOneOf(TestAnnotation.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },
                        {
                                "filterByAnnotation : returns empty list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle no passed filter args correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "filterByAnnotation : should returns unfiltered list for null valued filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "filterByAnnotation : returns empty list for null value element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(null)
                                                .applyFilter(Filters.ANNOTATION_FILTER).filterByOneOf(FilterTestAnnotation1.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },


                        // --

                        {
                                "inverseFilterByAnnotation : returns list for one matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // one search attribute
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByOneOf(FilterTestAnnotation1.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                        for (Element resultElement : result) {
                                            MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                        }

                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByAnnotation : returns list for two matching filter arguments",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // two search attributes
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                        for (Element resultElement : result) {
                                            MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByAnnotation : returns full list for non matching filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // returns empty result
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByAnnotation : returns empty list for non existing filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle no passed filter args correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByOneOf()
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByAnnotation : should return empty list for inverted filtering with null valued filter argument",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByOneOf(null)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                    }
                                },
                                true


                        },
                        {
                                "inverseFilterByAnnotation : returns empty list for null value element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // handle nulls correctly
                                        List<? extends Element> result = createFluentElementFilter(null)
                                                .applyFilter(Filters.ANNOTATION_FILTER).invert().filterByOneOf(FilterTestAnnotation1.class)
                                                .getResult();

                                        MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                    }
                                },
                                true


                        },

                        {
                                "isEmpty : succeeding validation with empty element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(new ArrayList<Element>()).isEmpty(), Matchers.is(true));


                                    }
                                },
                                true
                        },
                        {
                                "isEmpty : failing validation with  multiple element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects non empty result correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).isEmpty(), Matchers.is(false));

                                    }
                                },
                                true
                        },
                        {
                                "isEmpty : succeeding validation with null valued element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects non empty result correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(null).isEmpty(), Matchers.is(true));

                                    }
                                },
                                true
                        },
                        {
                                "hasSingleElemen : failing validation with empty element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(new ArrayList<Element>()).hasSingleElement(), Matchers.is(false));

                                    }
                                },
                                true
                        },
                        {
                                "hasSingleElement : succeeding validation with one element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects single result elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(convertToList(element)).hasSingleElement(), Matchers.is(true));


                                    }
                                },
                                true
                        },
                        {
                                "hasSingleElement : failing validation with multiple element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects multiple elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).hasSingleElement(), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasSingleElement : failing validation with null valued element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects multiple elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(null).hasSingleElement(), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasMultipleElements : succeeding validation with multiple elements",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects multiple elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).hasMultipleElements(), Matchers.is(true));


                                    }
                                },
                                true
                        },
                        {
                                "hasMultipleElements : failing validation with empty list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(new ArrayList<Element>()).hasMultipleElements(), Matchers.is(false));

                                    }
                                },
                                true
                        },
                        {
                                "hasMultipleElements : failing validation with one element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects single result elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(convertToList(element)).hasMultipleElements(), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasMultipleElements : failing validation with null valued element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects single result elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(null).hasMultipleElements(), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasSize : suucceeding validition with empty empty list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(new ArrayList<Element>()).hasSize(0), Matchers.is(true));

                                    }
                                },
                                true
                        },
                        {
                                "hasSize : succeeding validition with one element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects single result elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(convertToList(element)).hasSize(1), Matchers.is(true));


                                    }
                                },
                                true
                        },
                        {
                                "hasSize : succeeding validition with multi element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects multiple elements correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).hasSize(element.getEnclosedElements().size()), Matchers.is(true));


                                    }
                                },
                                true
                        },
                        {
                                "hasSize : succeeding validition with null valued empty list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        // detects null valued element list correctly
                                        MatcherAssert.assertThat(createFluentElementFilter((List<Element>) null).hasSize(3), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasSize : failing validation",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "hasSize : failing validation",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


                                    }
                                },
                                true
                        },
                        {
                                "geResultSize : null valued element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(null).getResultSize(), Matchers.is(0));


                                    }
                                },
                                true
                        },
                        {
                                "geResultSize : null valued element list",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // detects empty results correctly
                                        MatcherAssert.assertThat(createFluentElementFilter(element.getEnclosedElements()).getResultSize(), Matchers.is(element.getEnclosedElements().size()));


                                    }
                                },
                                true
                        }

                }

        );


    }


    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
