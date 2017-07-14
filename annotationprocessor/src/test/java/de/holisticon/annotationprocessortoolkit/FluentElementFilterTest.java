package de.holisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter}.
 */
@RunWith(Parameterized.class)
public class FluentElementFilterTest extends AbstractAnnotationProcessorUnitTest {

    public FluentElementFilterTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "filterByKinds",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(8));

                                                              for (Element resultElement : results) {
                                                                  MatcherAssert.assertThat(resultElement.getKind(), Matchers.is(ElementKind.FIELD));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByKinds and filterByModifiers",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.FIELD)
                                                                      .applyFilter(Filters.getModifierFilter()).filterByAllOf(Modifier.PUBLIC, Modifier.STATIC)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByKinds : null valued element",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(null)
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByKinds : null valued filter argument should reurn unfiltered list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByKinds : should return unfiltered list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        //--
                        {
                                "inverseFilterByKinds : return list for matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).invert().filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(element.getEnclosedElements().size() - 8));

                                                              for (Element resultElement : results) {
                                                                  MatcherAssert.assertThat(resultElement.getKind(), Matchers.not(ElementKind.FIELD));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "inverseFilterByKinds : null valued element",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(null)
                                                                      .applyFilter(Filters.getElementKindFilter()).invert().filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByKinds : should return empty list for inverted filtering with null valued filter argument ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).invert().filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.<Element>empty());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByKinds : should return empty list noninverted, non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> results = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getElementKindFilter()).invert().filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(results, Matchers.<Element>empty());


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "filterByNames : returns list for one matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // one search attribute
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("publicStaticField")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNames : returns list for two matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // two search attributes
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("publicStaticField", "synchronizedMethod")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                                              MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNames : returns empty list for non matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // returns empty result
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf("XXX")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNames : returns unfiltered list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle no passed filter args correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNames : should return unfiltered list for null valued filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "filterByNames : returns empty list for null value element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // null valued element list
                                                              List<? extends Element> result =
                                                                      FluentElementFilter.createFluentFilter(null)
                                                                              .applyFilter(Filters.getNameFilter()).filterByOneOf(null)
                                                                              .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // ....


                        {
                                "inverseFilterByNames : returns list for one matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // one search attribute
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).invert().filterByOneOf("publicStaticField")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                                              for (Element resultElement : result) {
                                                                  MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("publicStaticField"));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByNames : returns list for two matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // two search attributes
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).invert().filterByOneOf("publicStaticField", "synchronizedMethod")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 2));
                                                              for (Element resultElement : result) {
                                                                  MatcherAssert.assertThat("Must nor be publicStaticField or synchronizedMethod", !resultElement.getSimpleName().equals("publicStaticField") && !resultElement.getSimpleName().equals("synchronizedMethod"));
                                                              }

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByNames : returns empty list for non matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // returns empty result
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).invert().filterByOneOf("XXX")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByNames : should return empty list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle no passed filter args correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).invert().filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByNames : should return empty list for inverted filtering with null valued filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getNameFilter()).invert().filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.<Element>empty());

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "inverseFilterByNames : returns empty list for null value element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // null valued element list
                                                              List<? extends Element> result =
                                                                      FluentElementFilter.createFluentFilter(null)
                                                                              .applyFilter(Filters.getNameFilter()).invert().filterByOneOf(null)
                                                                              .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));

                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "filterByNameWithRegularExpressions : returns empty list for one matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // one search attribute
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf("publicSt.*Field")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNameWithRegularExpressions : returns list for two matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // two search attributes
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf("publicSt.*Field", "synchr.*Method")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(2));
                                                              MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString(), result.get(1).getSimpleName().toString()), Matchers.containsInAnyOrder("publicStaticField", "synchronizedMethod"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNameWithRegularExpressions : returns empty list for non matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // returns empty result
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf("XXX")
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNameWithRegularExpressions : returns unfiltered list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle no passed filter args correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNameWithRegularExpressions : should return unfiltered list for null valued filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByNameWithRegularExpressions : returns empty list for null value element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(null)
                                                                      .applyFilter(Filters.getRegexNameFilter()).filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "filterByAnnotation : returns empty list for one matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // one search attribute
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByOneOf(FilterTestAnnotation1.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByAnnotation : returns list for two matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // two search attributes
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                                              MatcherAssert.assertThat(convertToList(result.get(0).getSimpleName().toString()), Matchers.containsInAnyOrder("synchronizedMethod"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByAnnotation : returns empty list for non matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // returns empty result
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByOneOf(TestAnnotation.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByAnnotation : returns empty list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle no passed filter args correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByAnnotation : should returns unfiltered list for null valued filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "filterByAnnotation : returns empty list for null value element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(null)
                                                                      .applyFilter(Filters.getAnnotationFilter()).filterByOneOf(FilterTestAnnotation1.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        // --

                        {
                                "inverseFilterByAnnotation : returns list for one matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // one search attribute
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByOneOf(FilterTestAnnotation1.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                                              for (Element resultElement : result) {
                                                                  MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                                              }

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByAnnotation : returns list for two matching filter arguments",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // two search attributes
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByAllOf(FilterTestAnnotation1.class, FilterTestAnnotation2.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size() - 1));

                                                              for (Element resultElement : result) {
                                                                  MatcherAssert.assertThat(resultElement.getSimpleName().toString(), Matchers.not("synchronizedMethod"));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByAnnotation : returns full list for non matching filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // returns empty result
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByAllOf(FilterTestAnnotation1.class, TestAnnotation.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByAnnotation : returns empty list for non existing filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle no passed filter args correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByOneOf()
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByAnnotation : should return empty list for inverted filtering with null valued filter argument",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByOneOf(null)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.<Element>empty());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "inverseFilterByAnnotation : returns empty list for null value element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // handle nulls correctly
                                                              List<? extends Element> result = FluentElementFilter.createFluentFilter(null)
                                                                      .applyFilter(Filters.getAnnotationFilter()).invert().filterByOneOf(FilterTestAnnotation1.class)
                                                                      .getResult();

                                                              MatcherAssert.assertThat(result, Matchers.hasSize(0));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "isEmpty : succeeding validation with empty element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(new ArrayList<Element>()).isEmpty(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "isEmpty : failing validation with  multiple element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects non empty result correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).isEmpty(), Matchers.is(false));

                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "isEmpty : succeeding validation with null valued element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects non empty result correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(null).isEmpty(), Matchers.is(true));

                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSingleElemen : failing validation with empty element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(new ArrayList<Element>()).hasSingleElement(), Matchers.is(false));

                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSingleElement : succeeding validation with one element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects single result elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(convertToList(element)).hasSingleElement(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSingleElement : failing validation with multiple element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects multiple elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).hasSingleElement(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSingleElement : failing validation with null valued element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects multiple elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(null).hasSingleElement(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasMultipleElements : succeeding validation with multiple elements",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects multiple elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).hasMultipleElements(), Matchers.is(true));


                                                          }
                                                      }

                                        )
                                        .build()
                        },
                        {
                                "hasMultipleElements : failing validation with empty list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(new ArrayList<Element>()).hasMultipleElements(), Matchers.is(false));

                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasMultipleElements : failing validation with one element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects single result elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(convertToList(element)).hasMultipleElements(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasMultipleElements : failing validation with null valued element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects single result elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(null).hasMultipleElements(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : suucceeding validition with empty empty list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(new ArrayList<Element>()).hasSize(0), Matchers.is(true));

                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : succeeding validition with one element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects single result elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(convertToList(element)).hasSize(1), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : succeeding validition with multi element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects multiple elements correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).hasSize(element.getEnclosedElements().size()), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : succeeding validition with null valued empty list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // detects null valued element list correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter((List<Element>) null).hasSize(3), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "hasSize : failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).hasSize(0), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "geResultSize : null valued element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(null).getResultSize(), Matchers.is(0));


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "geResultSize : null valued element list",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // detects empty results correctly
                                                              MatcherAssert.assertThat(FluentElementFilter.createFluentFilter(element.getEnclosedElements()).getResultSize(), Matchers.is(element.getEnclosedElements().size()));


                                                          }
                                                      }
                                        )
                                        .build()
                        }

                }

        );


    }


    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
