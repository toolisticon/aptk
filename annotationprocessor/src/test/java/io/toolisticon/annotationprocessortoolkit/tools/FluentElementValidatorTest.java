package io.toolisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.FluentElementValidator;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
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

                                                              List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod" );
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
