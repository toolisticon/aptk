package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.ProcessingEnvironmentUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TestCoreMatcherFactory;
import io.toolisticon.annotationprocessortoolkit.tools.command.Command;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
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

    @Before
    public void init() {
        CoreMatcherValidationMessages.setPrintMessageCodes(true);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "validate by none of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by none of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by one of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by one of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by all of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by all of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by at least one of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by at least one of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with defaults - failing validation (ELEMENT CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_EXECUTABLE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with defaults - failing validation (ELEMENT KIND CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).is(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_INTERFACE.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by implicit criteria (has return type) with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by implicit criteria (has return type) with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },


                        // Now do the same test with custom message or message scope
                        {
                                "validate by none of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by none of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by one of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by one of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by all of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by all of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by at least one of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by at least one of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").is(CoreMatchers.IS_TYPE_ELEMENT).setCustomMessage("UPS").is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with custom settings - failing validation (ELEMENT CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").is(CoreMatchers.IS_EXECUTABLE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by is criteria (element based and type element based) with custom settings - failing validation (ELEMENT KIND CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).setCustomMessage("UPS").is(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_INTERFACE.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate by implicit criteria (has return type) with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).warning().applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate by implicit criteria (has return type) with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).setCustomMessage("UPS").applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },

                        // --------------------------------------------------
                        // -- Now do the same with inverted validator
                        // --------------------------------------------------

                        {
                                "validate inverted by none of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by none of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by one of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by one of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by all of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PROTECTED, Modifier.FINAL).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by all of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by at least one of criteria with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by at least one of criteria with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC, Modifier.ABSTRACT).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).isNot(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with defaults - failing validation (ELEMENT CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_TYPE_ELEMENT).isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_TYPE_ELEMENT.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with defaults - failing validation (ELEMENT KIND CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_CLASS.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by implicit criteria (has return type) with defaults",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by implicit criteria (has return type) with defaults - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())
                                        .finishMessageValidator()
                                        .build()


                        },


                        // Now do the same test with custom message or message scope
                        {
                                "validate inverted by none of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by none of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by one of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by one of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by all of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PROTECTED).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by all of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by at least one of criteria with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by at least one of criteria with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).setCustomMessage("UPS").isNot(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with custom settings - failing validation (ELEMENT CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").isNot(CoreMatchers.IS_TYPE_ELEMENT).isNot(CoreMatchers.IS_METHOD).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_TYPE_ELEMENT.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by is criteria (element based and type element based) with custom settings - failing validation (ELEMENT KIND CHECK)",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).setCustomMessage("UPS").isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.IS_CLASS.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate inverted by implicit criteria (has return type) with custom settings",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).warning().applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "validate inverted by implicit criteria (has return type) with custom settings - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "UPS")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate note, warning and error message - failing validation with setCustomMessage done upfront",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .setCustomMessage("NOTE").note().applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .setCustomMessage("WARNING").warning().applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .setCustomMessage("ERROR").error().applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setNoteChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "NOTE")
                                        .setWarningChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "WARNING")
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "ERROR")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate note, warning and error message - failing validation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                                              MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                                              MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .note().setCustomMessage("NOTE").applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .warning().setCustomMessage("WARNING").applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                              MatcherAssert.assertThat(FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0)))
                                                                              .error().setCustomMessage("ERROR").applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE)
                                                                              .validateAndIssueMessages()
                                                                      , Matchers.equalTo(false));

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setNoteChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "NOTE")
                                        .setWarningChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "WARNING")
                                        .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "ERROR")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Implicit Element based validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("SUCCESS", true)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("FAILURE", false)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Implicit validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "SUCCESS", true)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "FAILURE", false)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Implicit Element based validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("SUCCESS", false)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedImplicitCoreMatcher("FAILURE", true)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Implicit validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "SUCCESS", false)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createImplicitCoreMatcher(TypeElement.class, "FAILURE", true)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics Element based validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, true)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, true)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics Element based validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics Element based validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics Element based validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics Element based validator -  at least one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, false)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics validator - at least one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, false)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics Element based validator -  at least one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, true)).hasAtLeastOneOf("XX", "YY").justValidate()
                                                                      , Matchers.equalTo(false));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", true, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics validator - at least one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS!", false, true)).hasAtLeastOneOf("XX", "YY").justValidate()
                                                                      , Matchers.equalTo(false));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", true, true)).hasAtLeastOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics Element based validator - all of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, true)).hasAllOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Inclusive Characteristics validator - all of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, true)).hasAllOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics Element based validator - all of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, true)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedInclusiveCriteriaCoreMatcher(String.class, "FAILURE", true, true)).hasAllOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Inclusive Characteristics validator - all of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, true)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasAllOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));


                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createInclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", true, true)).hasAllOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Exclusive Characteristics Element based validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, true)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Exclusive Characteristics validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, true)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Exclusive Characteristics Element based validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "FAILURE", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Exclusive Characteristics validator - none of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, false)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasNoneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", false, false)).hasNoneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Exclusive Characteristics Element based validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "FAILURE", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate Exclusive Characteristics validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, false)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", true, false)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Exclusive Characteristics Element based validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", false, false)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "SUCCESS", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyInvertedValidator(TestCoreMatcherFactory.createElementBasedExclusiveCriteriaCoreMatcher(String.class, "FAILURE", true, false)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate INVERTED Exclusive Characteristics validator - one of",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element)
                                                                              .error().applyValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "SUCCESS", false, true)).hasOneOf("XX", "YY").validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().applyValidator(TestCoreMatcherFactory.createExclusiveCriteriaCoreMatcher(TypeElement.class, String.class, "FAILURE", true, true)).hasOneOf("XX", "YY").validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate IS Element based validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator((Element) element).is(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "SUCCESS", true)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator((Element) element).is(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "FAILURE", false)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "validate IS validator",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(
                                                                      FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "SUCCESS", true)).validateAndIssueMessages()
                                                                      , Matchers.equalTo(true));

                                                              FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false)).validateAndIssueMessages();

                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "execute command - if validation succeeds",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "SUCCESS", true))
                                                                      .executeCommand(
                                                                              new Command<TypeElement>() {
                                                                                  @Override
                                                                                  public void execute(TypeElement element) {
                                                                                      ProcessingEnvironmentUtils.getMessager().printMessage(Diagnostic.Kind.NOTE, "EXECUTED COMMAND");
                                                                                  }
                                                                              });


                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setNoteChecks("EXECUTED COMMAND")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "don't execute command but trigger validation message - if validation fails",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                                                      .executeCommand(
                                                                              new Command<TypeElement>() {
                                                                                  @Override
                                                                                  public void execute(TypeElement element) {
                                                                                      ProcessingEnvironmentUtils.getMessager().printMessage(Diagnostic.Kind.ERROR, "EXECUTED COMMAND");
                                                                                  }
                                                                              });
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "don't execute command - if validation fails",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                                                      .executeCommandAndIssueMessages(
                                                                              new Command<TypeElement>() {
                                                                                  @Override
                                                                                  public void execute(TypeElement element) {
                                                                                      ProcessingEnvironmentUtils.getMessager().printMessage(Diagnostic.Kind.ERROR, "EXECUTED COMMAND");
                                                                                  }
                                                                              });
                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("FAILURE")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "custom message - with ValidationMessage class and Messageargs",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().setCustomMessage(
                                                                      new ValidationMessage() {
                                                                          @Override
                                                                          public String getCode() {
                                                                              return "XXX";
                                                                          }

                                                                          @Override
                                                                          public String getMessage() {
                                                                              return "ERROR ${0} ${0} ${1}!";
                                                                          }
                                                                      }, "YES", "AGAIN"
                                                              ).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                                                      .validateAndIssueMessages();
                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("ERROR YES YES AGAIN!")
                                        .finishMessageValidator()
                                        .build()


                        },
                        {
                                "custom message - with ValidationMessage class without MessageArgs",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              FluentElementValidator.createFluentElementValidator(element)
                                                                      .error().setCustomMessage(
                                                                      new ValidationMessage() {
                                                                          @Override
                                                                          public String getCode() {
                                                                              return "XXX";
                                                                          }

                                                                          @Override
                                                                          public String getMessage() {
                                                                              return "ERROR!";
                                                                          }
                                                                      }
                                                              ).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                                                      .validateAndIssueMessages();
                                                          }
                                                      }
                                        )
                                        .addMessageValidator()
                                        .setErrorChecks("ERROR!")
                                        .finishMessageValidator()
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

    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }

}
