package io.toolisticon.aptk.tools.fluentvalidator;

import io.toolisticon.aptk.tools.command.Command;
import io.toolisticon.aptk.tools.command.CommandWithReturnType;
import io.toolisticon.aptk.tools.corematcher.ExclusiveCriteriaCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ExclusiveCriteriaElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ImplicitCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ImplicitElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.InclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.InclusiveCriteriaCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.IsCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.IsElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.PlainValidationMessage;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;
import io.toolisticon.aptk.tools.fluentvalidator.impl.FluentValidatorMessage;
import io.toolisticon.aptk.tools.fluentvalidator.impl.FluentValidatorState;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;
import io.toolisticon.aptk.tools.validator.ExclusiveCriteriaElementValidator;
import io.toolisticon.aptk.tools.validator.ImplicitValidator;
import io.toolisticon.aptk.tools.validator.InclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Fluent element validator.
 */
@SuppressWarnings("unchecked")
public class FluentElementValidator<ELEMENT extends Element> {

    /**
     * Apply validator interface.
     */
    public class PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT extends Element> {

        /**
         * Set error scope for next validation.
         *
         * @return the fluent validator instance
         */
        public PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> error() {
            return (PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT>) FluentElementValidator.this.error();
        }


        /**
         * Set warning scope for next validation.
         *
         * @return the fluent validator instance
         */
        public PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> warning() {
            return (PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT>) FluentElementValidator.this.warning();
        }

        /**
         * Set note scope for next validation.
         *
         * @return the fluent validator instance
         */
        public PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> note() {
            return (PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT>) FluentElementValidator.this.note();
        }


        /**
         * Set custom message .
         *
         * @param customMessage the custom message to use.
         * @return the fluent validator instance
         */
        public PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> setCustomMessage(String customMessage) {
            return setCustomMessage(PlainValidationMessage.create(customMessage));
        }

        /**
         * Set custom message.
         *
         * @param customMessage the custom message
         * @param messagArgs    the message arguments
         * @return the fluent validator instance
         */
        public final PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> setCustomMessage(String customMessage, Object... messagArgs) {
            return setCustomMessage(PlainValidationMessage.create(customMessage), messagArgs);
        }


        /**
         * Set custom message .
         *
         * @param customMessage the custom message to use.
         * @return the fluent validator instance
         */
        public PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> setCustomMessage(ValidationMessage customMessage) {
            return (PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT>) FluentElementValidator.this.setCustomMessage(customMessage);
        }

        /**
         * Set custom message.
         *
         * @param customMessage the custom message
         * @param messagArgs    the message arguments
         * @return the fluent validator instance
         */
        public final PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT> setCustomMessage(ValidationMessage customMessage, Object... messagArgs) {
            return (PrepareApplyValidator<PREPARE_VALIDATOR_ELEMENT>) FluentElementValidator.this.setCustomMessage(customMessage, messagArgs);
        }

        /**
         * Applies is validator.
         * Changes generic type of fluent validator.
         *
         * @param coreMatcher      the implicit core matcher to use
         * @param <TARGET_ELEMENT> The target element type
         * @return the FluentElementValidator instance
         */
        public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

            return FluentElementValidator.this.is(coreMatcher);

        }

        /**
         * Applies inverted is filter.
         *
         * @param <TARGET_ELEMENT> The target element type
         * @param coreMatcher      the implicit core matcher to use
         * @return the FluentElementValidator instance
         */
        public <TARGET_ELEMENT extends Element> FluentElementValidator<ELEMENT> isNot(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

            return FluentElementValidator.this.isNot(coreMatcher);

        }

        /**
         * Applies is element based filter.
         * Changes generic type of fluent filter.
         *
         * @param coreMatcher      the implicit core matcher to use
         * @param <TARGET_ELEMENT> The target element type
         * @return the FluentElementValidator instance
         */
        public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

            return FluentElementValidator.this.is(coreMatcher);


        }

        /**
         * Applies inverted implicit element based filter.
         *
         * @param coreMatcher      the implicit core matcher to use
         * @param <TARGET_ELEMENT> The target element type
         * @return the FluentElementValidator instance
         */
        public <TARGET_ELEMENT extends Element> FluentElementValidator<ELEMENT> isNot(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

            return FluentElementValidator.this.isNot(coreMatcher);

        }

        // -----------------------------------------------
        // -- IMPLICIT FILTERS
        // -----------------------------------------------

        /**
         * Applies implicit filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @return the FluentElementValidator instance
         */
        public FluentElementValidator<ELEMENT> applyValidator(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);

        }

        /**
         * Applies inverted implicit filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @return the FluentElementValidator instance
         */
        public FluentElementValidator<ELEMENT> applyInvertedValidator(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

        /**
         * Applies implicit element based filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @return the FluentElementValidator instance
         */
        public FluentElementValidator<ELEMENT> applyValidator(ImplicitElementBasedCoreMatcher coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);

        }

        /**
         * Applies inverted implicit element based filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @return the FluentElementValidator instance
         */
        public FluentElementValidator<ELEMENT> applyInvertedValidator(ImplicitElementBasedCoreMatcher coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

        // -----------------------------------------------
        // -- INCLUSIVE CRITERIA FILTERS
        // -----------------------------------------------

        /**
         * Applies inclusive criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> InclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyValidator(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);

        }

        /**
         * Applies inverted inclusive criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> InclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyInvertedValidator(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

        /**
         * Applies inclusive criteria element based filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> InclusiveCharacteristicFluentValidator<Element, CRITERIA> applyValidator(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);


        }

        /**
         * Applies inverted inclusive element based criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> InclusiveCharacteristicFluentValidator<Element, CRITERIA> applyInvertedValidator(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

        // -----------------------------------------------
        // -- EXPLICIT FILTERS
        // -----------------------------------------------

        /**
         * Applies exclusive criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> ExclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyValidator(ExclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);

        }

        /**
         * Applies inverted exclusive criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> ExclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyInvertedValidator(ExclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

        /**
         * Applies exclusive criteria element based filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> ExclusiveCharacteristicFluentValidator<Element, CRITERIA> applyValidator(ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyValidator(coreMatcher);


        }


        /**
         * Applies inverted exclusive element based criteria filter.
         *
         * @param coreMatcher the implicit core matcher to use
         * @param <CRITERIA>  the criteria type
         * @return the FluentElementValidator instance
         */
        public <CRITERIA> ExclusiveCharacteristicFluentValidator<Element, CRITERIA> applyInvertedValidator(ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA> coreMatcher) {

            return FluentElementValidator.this.applyInvertedValidator(coreMatcher);

        }

    }


    /**
     * The element under validation.
     */
    private ELEMENT element;

    /**
     * The validations state.
     * Maybe passed over to other FluentElementValidator instance.
     */
    private final FluentValidatorState fluentValidatorState;

    private NextValidationContext nextValidationContext = new NextValidationContext();

    private class NextValidationContext {

        private ValidationMessage customMessage;
        private Diagnostic.Kind messageScope = Diagnostic.Kind.ERROR;
        private Object[] messageArgs;

        private void reset() {
            customMessage = null;
            messageScope = Diagnostic.Kind.ERROR;
        }

        public ValidationMessage getCustomMessage() {
            return customMessage;
        }

        public void setCustomMessage(final String customMessage) {
            this.customMessage = new ValidationMessage() {
                @Override
                public String getCode() {
                    return null;
                }

                @Override
                public String getMessage() {
                    return customMessage;
                }
            };
        }

        public final void setCustomMessage(ValidationMessage customMessage, Object... messageArgs) {
            this.customMessage = customMessage;
            this.messageArgs = messageArgs;

        }

        public Object[] getMessageArgs() {
            return messageArgs;
        }

        public Diagnostic.Kind getMessageScope() {
            return messageScope;
        }

        public void setMessageScope(Diagnostic.Kind messageScope) {
            this.messageScope = messageScope;
        }
    }

    /**
     * The base class for all validators.
     *
     * @param <VALIDATOR_ELEMENT> the validator element type
     * @param <VALIDATOR>         the validator type
     */
    public abstract class AbstractFluentValidatorBase<VALIDATOR_ELEMENT extends Element, VALIDATOR extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, VALIDATOR>> {


        private boolean inverted;

        public AbstractFluentValidatorBase(final boolean inverted) {
            this.inverted = inverted;
        }


        protected boolean isInverted() {
            return inverted;
        }


        /**
         * Sets the validation result depending on inverted state and validation result.
         *
         * @param validationResult  the validation result
         * @param defaultMessage    the default message
         * @param messsageParameter the message parameter values
         */
        protected final void setValidationResult(boolean validationResult, ValidationMessage defaultMessage, Object... messsageParameter) {

            if ((!validationResult && !inverted) || (validationResult && inverted)) {
                fluentValidatorState.setAsFailedValidation();

                final boolean hasCustomMessage = nextValidationContext.getCustomMessage() != null;

                ValidationMessage forgedValidationMessage = !hasCustomMessage ? defaultMessage : PlainValidationMessage.create(nextValidationContext.getCustomMessage().getCode() != null ? nextValidationContext.getCustomMessage().getCode() : defaultMessage.getCode(), nextValidationContext.getCustomMessage().getMessage());
                Object[] messageArgs = !hasCustomMessage ? messsageParameter : (nextValidationContext.getMessageArgs() != null ? nextValidationContext.getMessageArgs() : new Object[0]);

                fluentValidatorState.addMessage(new FluentValidatorMessage(element, nextValidationContext.getMessageScope(), forgedValidationMessage, messageArgs));


                // rest validation context for next validation
                nextValidationContext.reset();

            }

        }


    }


    /**
     * Validator step for implicit validators.
     *
     * @param <VALIDATOR_ELEMENT> the validators element type
     */
    public class ImplicitFluentValidator<VALIDATOR_ELEMENT extends Element> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, ImplicitFluentValidator<VALIDATOR_ELEMENT>> {

        private final ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator;

        private ImplicitFluentValidator(ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator, boolean inverted) {
            super(inverted);
            this.validator = validator;
        }

        public FluentElementValidator<ELEMENT> apply() {

            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.validate((VALIDATOR_ELEMENT) element), validator.getDefaultMessage(), isInverted() ? "not" : "");
            }
            return FluentElementValidator.this;
        }

    }

    /**
     * Validator step for is validators.
     *
     * @param <VALIDATOR_ELEMENT> the validators element type
     */
    public class IsFluentValidator<VALIDATOR_ELEMENT extends Element, TARGET_ELEMENT extends Element> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, IsFluentValidator<VALIDATOR_ELEMENT, TARGET_ELEMENT>> {

        private final ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator;

        private IsFluentValidator(ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator, boolean inverted) {
            super(inverted);
            this.validator = validator;
        }

        public FluentElementValidator<TARGET_ELEMENT> apply() {


            boolean validationResult = false;

            // just validate if element != null
            if (element != null) {
                validationResult = validator.validate((VALIDATOR_ELEMENT) element);
                setValidationResult(validationResult, validator.getDefaultMessage(), "");
            }

            return new FluentElementValidator<TARGET_ELEMENT>(validationResult ? (TARGET_ELEMENT) element : null, fluentValidatorState);

        }


    }

    /**
     * Validator step for inverted is validators.
     *
     * @param <VALIDATOR_ELEMENT> The validators element type
     */
    public class InvertedIsFluentValidator<VALIDATOR_ELEMENT extends Element, TARGET_ELEMENT extends Element> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, InvertedIsFluentValidator<VALIDATOR_ELEMENT, TARGET_ELEMENT>> {

        private final ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator;

        private InvertedIsFluentValidator(ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator, boolean inverted) {
            super(inverted);
            this.validator = validator;
        }

        public FluentElementValidator<ELEMENT> apply() {


            boolean validationResult;

            // just validate if element != null
            if (element != null) {
                validationResult = validator.validate((VALIDATOR_ELEMENT) element);
                setValidationResult(validationResult, validator.getDefaultMessage(), "not");
            }

            return FluentElementValidator.this;

        }


    }

    /**
     * Validator step for exlusive characteristics validators.
     *
     * @param <VALIDATOR_ELEMENT> the validators element type
     * @param <CHARACTERISTIC>    The characteristics type
     */
    public class ExclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT extends Element, CHARACTERISTIC> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, ExclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC>> {

        private final ExclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator;

        private ExclusiveCharacteristicFluentValidator(ExclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator, boolean inverted) {
            super(inverted);
            this.validator = validator;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasOneOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "one", params);
            }
            return FluentElementValidator.this;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasNoneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasNoneOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "none", params);
            }
            return FluentElementValidator.this;
        }
    }

    /**
     * Validator step for inclusive characteristics validators.
     *
     * @param <VALIDATOR_ELEMENT> the validators element type
     * @param <CHARACTERISTIC>    The characteristics type
     */
    public class InclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT extends Element, CHARACTERISTIC> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, InclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC>> {

        private final InclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator;

        private InclusiveCharacteristicFluentValidator(InclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator, boolean inverted) {
            super(inverted);
            this.validator = validator;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasOneOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "one", params);
            }
            return FluentElementValidator.this;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasNoneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasNoneOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "none", params);
            }
            return FluentElementValidator.this;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasAtLeastOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasAtLeastOneOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "at least one", params);
            }
            return FluentElementValidator.this;
        }

        @SafeVarargs
        public final FluentElementValidator<ELEMENT> hasAllOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasAllOf((VALIDATOR_ELEMENT) element, params), validator.getDefaultMessage(), isInverted() ? "not" : "", "all", params);
            }
            return FluentElementValidator.this;
        }


    }


    private FluentElementValidator(ELEMENT element) {
        this.element = element;
        this.fluentValidatorState = new FluentValidatorState();
    }

    private FluentElementValidator(ELEMENT element, FluentValidatorState fluentValidatorState) {
        this.element = element;
        this.fluentValidatorState = fluentValidatorState;
    }

    // -----------------------------------------------
    // -- IS VALIDATOR
    // -----------------------------------------------

    /**
     * Applies is validator.
     * Changes generic type of fluent validator.
     *
     * @param coreMatcher      the implicit core matcher to use
     * @param <TARGET_ELEMENT> The target element type
     * @return the FluentElementValidator instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

        return new IsFluentValidator<ELEMENT, TARGET_ELEMENT>(coreMatcher.getValidator(), false).apply();

    }

    /**
     * Applies inverted is filter.
     *
     * @param coreMatcher      the implicit core matcher to use
     * @param <TARGET_ELEMENT> The target element type
     * @return the FluentElementValidator instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementValidator<ELEMENT> isNot(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {

        return new InvertedIsFluentValidator<ELEMENT, TARGET_ELEMENT>(coreMatcher.getValidator(), true).apply();

    }

    /**
     * Applies is element based filter.
     * Changes generic type of fluent filter.
     *
     * @param coreMatcher      the implicit core matcher to use
     * @param <TARGET_ELEMENT> The target element type
     * @return the FluentElementValidator instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

        return new IsFluentValidator<Element, TARGET_ELEMENT>(coreMatcher.getValidator(), false).apply();


    }

    /**
     * Applies inverted implicit element based filter.
     *
     * @param coreMatcher      the implicit core matcher to use
     * @param <TARGET_ELEMENT> The target element type
     * @return the FluentElementValidator instance
     */
    public <TARGET_ELEMENT extends Element> FluentElementValidator<ELEMENT> isNot(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {

        return new InvertedIsFluentValidator<Element, TARGET_ELEMENT>(coreMatcher.getValidator(), true).apply();

    }

    // -----------------------------------------------
    // -- IMPLICIT FILTERS
    // -----------------------------------------------

    /**
     * Applies implicit filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementValidator instance
     */
    public FluentElementValidator<ELEMENT> applyValidator(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

        return new ImplicitFluentValidator<>(coreMatcher.getValidator(), false).apply();

    }

    /**
     * Applies inverted implicit filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementValidator instance
     */
    public FluentElementValidator<ELEMENT> applyInvertedValidator(ImplicitCoreMatcher<ELEMENT> coreMatcher) {

        return new ImplicitFluentValidator<>(coreMatcher.getValidator(), true).apply();

    }

    /**
     * Applies implicit element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementValidator instance
     */
    public FluentElementValidator<ELEMENT> applyValidator(ImplicitElementBasedCoreMatcher coreMatcher) {

        return new ImplicitFluentValidator<>(coreMatcher.getValidator(), false).apply();

    }

    /**
     * Applies inverted implicit element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @return the FluentElementValidator instance
     */
    public FluentElementValidator<ELEMENT> applyInvertedValidator(ImplicitElementBasedCoreMatcher coreMatcher) {

        return new ImplicitFluentValidator<>(coreMatcher.getValidator(), true).apply();

    }

    // -----------------------------------------------
    // -- INCLUSIVE CRITERIA FILTERS
    // -----------------------------------------------

    /**
     * Applies inclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> InclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyValidator(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new InclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), false);

    }

    /**
     * Applies inverted inclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> InclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyInvertedValidator(InclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new InclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), true);

    }

    /**
     * Applies inclusive criteria element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> InclusiveCharacteristicFluentValidator<Element, CRITERIA> applyValidator(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new InclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), false);


    }

    /**
     * Applies inverted inclusive element based criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> InclusiveCharacteristicFluentValidator<Element, CRITERIA> applyInvertedValidator(InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new InclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), true);

    }

    // -----------------------------------------------
    // -- EXPLICIT FILTERS
    // -----------------------------------------------

    /**
     * Applies exclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> ExclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyValidator(ExclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new ExclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), false);

    }

    /**
     * Applies inverted exclusive criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> ExclusiveCharacteristicFluentValidator<ELEMENT, CRITERIA> applyInvertedValidator(ExclusiveCriteriaCoreMatcher<ELEMENT, CRITERIA> coreMatcher) {

        return new ExclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), true);

    }

    /**
     * Applies exclusive criteria element based filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> ExclusiveCharacteristicFluentValidator<Element, CRITERIA> applyValidator(ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new ExclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), false);
    }

    /**
     * Applies inverted exclusive element based criteria filter.
     *
     * @param coreMatcher the implicit core matcher to use
     * @param <CRITERIA>  the criteria type
     * @return the FluentElementValidator instance
     */
    public <CRITERIA> ExclusiveCharacteristicFluentValidator<Element, CRITERIA> applyInvertedValidator(ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA> coreMatcher) {

        return new ExclusiveCharacteristicFluentValidator<>(coreMatcher.getValidator(), true);

    }

    // -------------------------------------------------------------
    // -------------------------------------------------------------

    public PrepareApplyValidator<ELEMENT> error() {
        this.nextValidationContext.setMessageScope(Diagnostic.Kind.ERROR);
        return new PrepareApplyValidator<>();
    }

    public PrepareApplyValidator<ELEMENT> warning() {
        this.nextValidationContext.setMessageScope(Diagnostic.Kind.WARNING);
        return new PrepareApplyValidator<>();
    }

    public PrepareApplyValidator<ELEMENT> note() {
        this.nextValidationContext.setMessageScope(Diagnostic.Kind.NOTE);
        return new PrepareApplyValidator<>();
    }

    public PrepareApplyValidator<ELEMENT> setCustomMessage(String customMessage) {
        return setCustomMessage(PlainValidationMessage.create(customMessage));
    }

    public PrepareApplyValidator<ELEMENT> setCustomMessage(String customMessage, Object... messageArgs) {
        return setCustomMessage(PlainValidationMessage.create(customMessage), messageArgs);
    }

    public PrepareApplyValidator<ELEMENT> setCustomMessage(ValidationMessage customMessage) {
        this.nextValidationContext.setCustomMessage(customMessage);
        return new PrepareApplyValidator<>();
    }


    public PrepareApplyValidator<ELEMENT> setCustomMessage(ValidationMessage customMessage, Object... messageArgs) {
        this.nextValidationContext.setCustomMessage(customMessage, messageArgs);
        return new PrepareApplyValidator<>();
    }

    // -------------------------------------------------------------
    // -------------------------------------------------------------


    /**
     * Just return the validation result without issuing any messages.
     *
     * @return true, if validation succeeds, otherwise false
     */
    public boolean justValidate() {

        return fluentValidatorState.getValidationResult();

    }

    /**
     * Issue messages and return the validation result.
     *
     * @return true, if validation succeeds, otherwise false
     */
    public boolean validateAndIssueMessages() {

        fluentValidatorState.issueMessages();
        return fluentValidatorState.getValidationResult();

    }

    /**
     * Executes passed command if validation was successful, issues messages afterwards.
     *
     * @param command the command
     */
    public void executeCommandAndIssueMessages(Command<ELEMENT> command) {
        executeCommand(command);
        fluentValidatorState.issueMessages();
    }

    /**
     * Executes passed command if validation was successful.
     *
     * @param command the command
     */
    public void executeCommand(Command<ELEMENT> command) {
        if (command != null && fluentValidatorState.getValidationResult()) {
            command.execute(element);
        }
    }


    /**
     * Executes passed command if validation was successful, issues messages afterwards.
     *
     * @param command       the command
     * @param <RETURN_TYPE> return type
     * @return the result of the command
     */
    public <RETURN_TYPE> RETURN_TYPE executeCommandAndIssueMessages(CommandWithReturnType<ELEMENT, RETURN_TYPE> command) {
        RETURN_TYPE result = executeCommand(command);
        fluentValidatorState.issueMessages();
        return result;
    }

    /**
     * Executes passed command if validation was successful.
     *
     * @param command       the command
     * @param <RETURN_TYPE> return type
     * @return the result of the command
     */
    public <RETURN_TYPE> RETURN_TYPE executeCommand(CommandWithReturnType<ELEMENT, RETURN_TYPE> command) {
        if (command != null && fluentValidatorState.getValidationResult()) {
            return command.execute(element);
        }
        return null;
    }

    /**
     * Factory method to create a FluentElementValidator instance
     *
     * @param element the element to validate
     * @param <E>     the ELEMENT type
     * @return the FluentElementValidator instance
     */
    public static <E extends Element> FluentElementValidator<E> createFluentElementValidator(E element) {
        return new FluentElementValidator<>(element);
    }


}
