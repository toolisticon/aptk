package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.ProcessingEnvironmentUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TestCoreMatcherFactory;
import io.toolisticon.annotationprocessortoolkit.tools.command.Command;
import io.toolisticon.annotationprocessortoolkit.tools.command.CommandWithReturnType;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.PlainValidationMessage;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link FluentElementValidator}.
 */
public class FluentElementValidatorTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Test
    public void validateByNoneOfCriteriaWithDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                    }
                })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void validateByNoneOfCriteriaWithDefaults_failingValidation() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                    }
                })

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void validateByOneOfCriteriaWithDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                    }
                })

                .compilationShouldSucceed()
                .executeTest();

    }


    @Test
    public void validateByOneOfCriteriaWithDefaults_failingValidation() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                    }
                })


                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())

                .compilationShouldFail()
                .executeTest();

    }

    @Test
    public void validateByAllOfCriteriaWithDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                    }
                })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void validateByAllOfCriteriaWithDefaults_failingValidation() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                    }
                })
                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                .compilationShouldFail()
                .executeTest();

    }

    @Test
    public void validateByAtLeastOneOfCriteriaWithDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();
                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void validateByAtLeastOneOfCriteriaWithDefaults_failingValidation() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                    }
                })

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())
                .compilationShouldFail()
                .executeTest();

    }

    @Test
    public void validateByIsCriteria_elementBasedAndTypeElementBased_withDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void validateByIsCriteria_elementBasedAndTypeElementBased_withDefaults_failingValidation_ELEMENT_CHECK() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_EXECUTABLE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                    }
                })

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT.getCode())

                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void validateByIsCriteria_elementBasedAndTypeElementBased_withDefaults_failingValidation_ELEMENT_KIND_CHECK() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).is(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                    }
                })
                .compilationShouldFail()
                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_INTERFACE.getCode())

                .executeTest();


    }


    @Test
    public void validateByImplicitCriteria_hasReturnType_withDefaults() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                        MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                        MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                        FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                    }
                })
                .compilationShouldSucceed()
                .executeTest();


    }

    @Test
    public void validateByImplicitCriteria_hasReturnType_withDefaults_failibgValidation() {
        unitTestBuilder.compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())

                .executeTest();


    }


    // Now do the same test_with_custom_message or message scope

    @Test
    public void validateByNoneOfCriteriaWithCustomSettings() {
        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

                        FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                    }
                })

                .compilationShouldSucceed()

                .executeTest();


    }

    @Test
    public void validateByNoneOfCriteriaWithCustomSettings_failingValidation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validatByOneOfCriteriaWithCustomSettings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validateByOneOfCriteriaWithCustomSettings_failingValidation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validateByAllOfCriteriaWithCustomSettings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validateByAllOfCriteriaWithCustomSettings_failingValidation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validateByAtLeastOneOfCriteriaWithCustomSettings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_by_at_least_one_of_criteria_with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validateby_is_criteria__element_based_and_type_element_based__with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").is(CoreMatchers.IS_TYPE_ELEMENT).setCustomMessage("UPS").is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_by_is_criteria__element_based_and_type_element_based__with_custom_settings__failing_validation__ELEMENT_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").is(CoreMatchers.IS_EXECUTABLE_ELEMENT).is(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_EXECUTABLE_ELEMENT.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_by_is_criteria__element_based_and_type_element_based__with_custom_settings__failing_validation__ELEMENT_KIND_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).is(CoreMatchers.IS_TYPE_ELEMENT).setCustomMessage("UPS").is(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_INTERFACE.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_by_implicit_criteria__has_return_type__with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).warning().applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_by_implicit_criteria__has_return_type__with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).setCustomMessage("UPS").applyValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "UPS")

                .executeTest();


    }

    // --------------------------------------------------
    // -- Now do the same_with_inverted validator
    // --------------------------------------------------


    @Test
    public void validate_inverted_by_none_of_criteria_with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_none_of_criteria_with_defaults__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_one_of_criteria_with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_one_of_criteria_with_defaults__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_all_of_criteria_with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PROTECTED, Modifier.FINAL).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_all_of_criteria_with_defaults__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_at_least_one_of_criteria_with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_at_least_one_of_criteria_with_defaults__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC, Modifier.ABSTRACT).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).isNot(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_defaults__failing_validation__ELEMENT_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_TYPE_ELEMENT).isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_TYPE_ELEMENT.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_defaults__failing_validation__ELEMENT_KIND_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_CLASS.getCode())

                .executeTest();


    }


    @Test
    public void validate_inverted_by_implicit_criteria__has_return_type__with_defaults() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }

    @Test
    public void validate_inverted_by_implicit_criteria__has_return_type__with_defaults__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())

                .executeTest();


    }


    // Now do the same test_with_custom_message or message scope

    @Test
    public void validate_inverted_by_none_of_criteria_with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_none_of_criteria_with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_one_of_criteria_with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_one_of_criteria_with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasOneOf(Modifier.ABSTRACT, Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_all_of_criteria_with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PROTECTED).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_all_of_criteria_with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_at_least_one_of_criteria_with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).warning().applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PROTECTED, Modifier.ABSTRACT).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_at_least_one_of_criteria_with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.BY_MODIFIER).hasAtLeastOneOf(Modifier.PUBLIC).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).setCustomMessage("UPS").isNot(CoreMatchers.IS_INTERFACE).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_custom_settings__failing_validation__ELEMENT_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).setCustomMessage("UPS").isNot(CoreMatchers.IS_TYPE_ELEMENT).isNot(CoreMatchers.IS_METHOD).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_TYPE_ELEMENT.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_is_criteria__element_based_and_type_element_based__with_custom_settings__failing_validation__ELEMENT_KIND_CHECK() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator((Element) element).isNot(CoreMatchers.IS_EXECUTABLE_ELEMENT).setCustomMessage("UPS").isNot(CoreMatchers.IS_CLASS).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_CLASS.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_inverted_by_implicit_criteria__has_return_type__with_custom_settings() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).warning().applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )
                .executeTest();


    }


    @Test
    public void validate_inverted_by_implicit_criteria__has_return_type__with_custom_settings__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod");
                                MatcherAssert.assertThat("Precondition : must have found exactly one element", methods.size() == 1);
                                MatcherAssert.assertThat("Precondition : element must be method", ElementUtils.CheckKindOfElement.isMethod(methods.get(0)));


                                FluentElementValidator.createFluentElementValidator(ElementUtils.CastElement.castMethod(methods.get(0))).setCustomMessage("UPS").applyInvertedValidator(CoreMatchers.HAS_VOID_RETURN_TYPE).validateAndIssueMessages();


                            }
                        }
                )

                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "UPS")

                .executeTest();


    }


    @Test
    public void validate_note_warning_and_error_message__failing_validation_with_setCustomMessage_done_upfront() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectNoteMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "NOTE")
                .expectWarningMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "WARNING")
                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "ERROR")

                .executeTest();


    }


    @Test
    public void validate_note_warning_and_error_message__failing_validation() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectNoteMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "NOTE")
                .expectWarningMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "WARNING")
                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode(), "ERROR")

                .executeTest();


    }


    @Test
    public void validate_Implicit_Element_based_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }

    @Test
    public void validate_Implicit_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Implicit_Element_based_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Implicit_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_Element_based_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_Element_based_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_Element_based_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_Element_based_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_Element_based_validator___at_least_one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_validator__at_least_one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_Element_based_validator___at_least_one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_validator__at_least_one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_Element_based_validator__all_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Inclusive_Characteristics_validator__all_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_Element_based_validator__all_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Inclusive_Characteristics_validator__all_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Exclusive_Characteristics_Element_based_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Exclusive_Characteristics_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Exclusive_Characteristics_Element_based_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Exclusive_Characteristics_validator__none_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Exclusive_Characteristics_Element_based_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_Exclusive_Characteristics_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Exclusive_Characteristics_Element_based_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_INVERTED_Exclusive_Characteristics_validator__one_of() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_IS_Element_based_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                MatcherAssert.assertThat(
                                        FluentElementValidator.createFluentElementValidator((Element) element).is(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "SUCCESS", true)).validateAndIssueMessages()
                                        , Matchers.equalTo(true));

                                FluentElementValidator.createFluentElementValidator((Element) element).is(TestCoreMatcherFactory.createElementBasedIsCoreMatcher(TypeElement.class, "FAILURE", false)).validateAndIssueMessages();

                            }
                        }
                )

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void validate_IS_validator() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                MatcherAssert.assertThat(
                                        FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "SUCCESS", true)).validateAndIssueMessages()
                                        , Matchers.equalTo(true));

                                FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false)).validateAndIssueMessages();

                            }
                        }
                )

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void execute_command__if_validation_succeeds() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectNoteMessagesThatContain("EXECUTED COMMAND")

                .executeTest();


    }


    @Test
    public void execute_command_with_return_value__if_validation_succeeds() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {


                                MatcherAssert.assertThat(
                                        FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "SUCCESS", true))
                                                .executeCommand(
                                                        new CommandWithReturnType<TypeElement, String>() {
                                                            @Override
                                                            public String execute(TypeElement element) {
                                                                ProcessingEnvironmentUtils.getMessager().printMessage(Diagnostic.Kind.NOTE, "EXECUTED COMMAND");
                                                                return "YES!";
                                                            }
                                                        }),
                                        Matchers.is("YES!")
                                );

                            }
                        }
                )

                .expectNoteMessagesThatContain("EXECUTED COMMAND")

                .executeTest();


    }


    @Test
    public void dont_execute_command_but_trigger_validation_message__if_validation_fails() {
        unitTestBuilder
                .compilationShouldSucceed()
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                .executeTest();


    }


    @Test
    public void dont_execute_command__if_validation_fails() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                        .executeCommandAndIssueMessages(
                                                new Command<TypeElement>() {
                                                    @Override
                                                    public void execute(TypeElement element) {
                                                        throw new IllegalStateException("Shouldn't execute_command if_validation_fails");
                                                    }
                                                });
                            }
                        }
                )

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void dont_execute_command_with_return_value__if_validation_fails() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                        .executeCommandAndIssueMessages(
                                                new CommandWithReturnType<TypeElement, String>() {
                                                    @Override
                                                    public String execute(TypeElement element) {
                                                        throw new IllegalStateException("Shouldn't execute_command if_validation_fails");
                                                    }
                                                });
                            }
                        }
                )

                .expectErrorMessagesThatContain("FAILURE")

                .executeTest();


    }


    @Test
    public void custom_message__with_ValidationMessage_class_and_Messageargs() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
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

                .expectErrorMessagesThatContain("ERROR YES YES AGAIN!")

                .executeTest();


    }


    public void custom_message__with_ValidationMessage_class_without_MessageArgs() {
        unitTestBuilder
                .compilationShouldFail()
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                                FluentElementValidator.createFluentElementValidator(element)
                                        .error().setCustomMessage(
                                        PlainValidationMessage.create("XXX", "ERROR!")

                                ).is(TestCoreMatcherFactory.createIsCoreMatcher(TypeElement.class, TypeElement.class, "FAILURE", false))
                                        .validateAndIssueMessages();
                            }
                        }
                )

                .expectErrorMessagesThatContain("ERROR!")

                .executeTest();


    }


    public static <T> List<T> convertToList(T... element) {

        return Arrays.asList(element);

    }


}
