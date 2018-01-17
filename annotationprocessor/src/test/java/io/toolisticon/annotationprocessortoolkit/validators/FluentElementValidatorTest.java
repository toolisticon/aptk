package io.toolisticon.annotationprocessortoolkit.validators;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link FluentElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentElementValidatorTest extends AbstractAnnotationProcessorUnitTest {


    public FluentElementValidatorTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "Should validate succesfully with 'all of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Validate should fail with 'all of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC, Modifier.FINAL).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Should validate succesfully with 'at least one of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByAtLeastOneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Validation should fail with 'at least one of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByAtLeastOneOf(Modifier.STATIC, Modifier.FINAL).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Should validate succesfully with 'one of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByOneOf(Modifier.SYNCHRONIZED, Modifier.FINAL, Modifier.ABSTRACT).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validation should fail with 'one of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "Validation should succeed with 'none of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByNoneOf(Modifier.FINAL, Modifier.STATIC).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Validation should fail with 'none of'",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(Validators.MODIFIER_VALIDATOR).validateByNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validate());

                                                          }
                                                      }
                                        )
                                        .build()


                        },


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

}
