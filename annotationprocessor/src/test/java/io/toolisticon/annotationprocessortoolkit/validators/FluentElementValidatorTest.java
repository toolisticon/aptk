package io.toolisticon.annotationprocessortoolkit.validators;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
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
 * Unit test for {@link io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentElementValidatorTest extends AbstractAnnotationProcessorUnitTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC, Modifier.FINAL).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.STATIC, Modifier.FINAL).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.SYNCHRONIZED, Modifier.FINAL, Modifier.ABSTRACT).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as true", FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.FINAL, Modifier.STATIC).validateAndIssueMessages());

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
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(testElement).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.SYNCHRONIZED, Modifier.PUBLIC).validateAndIssueMessages());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "test is matcher - Successful Validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);


                                                              // Test
                                                              MatcherAssert.assertThat("Should be successfully validated", FluentElementValidator.createFluentElementValidator(elements.get(0)).is(CoreMatchers.IS_EXECUTABLE_ELEMENT).validateAndIssueMessages())
                                                              ;

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "test is matcher - Failing Validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .addMessageValidator()
                                            .setErrorChecks(CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT.getCode())
                                        .finishMessageValidator()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.FIELD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() >= 1);


                                                              // Test
                                                              MatcherAssert.assertThat("Should be validated as false", !FluentElementValidator.createFluentElementValidator(elements.get(0)).is(CoreMatchers.IS_EXECUTABLE_ELEMENT).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC,Modifier.STATIC).validateAndIssueMessages())
                                                              ;

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
