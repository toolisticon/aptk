package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator;

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCriteriaCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ExclusiveCriteriaElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ImplicitElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.InclusiveCriteriaCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.IsCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.IsElementBasedCoreMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.impl.FluentValidatorState;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.ImplicitMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ExclusiveCriteriaElementValidator;
import io.toolisticon.annotationprocessortoolkit.tools.validator.ImplicitValidator;
import io.toolisticon.annotationprocessortoolkit.tools.validator.InclusiveCriteriaElementValidator;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Fluent element validator.
 */
public class FluentElementValidator<ELEMENT extends Element> {


    /**
     * The element under validation.
     */
    private ELEMENT element;

    /**
     * The validations state.
     * Maybe passed over to other FluentElementValidator instance.
     */
    private final FluentValidatorState fluentValidatorState;

    /**
     * The base class for all validators.
     *
     * @param <VALIDATOR_ELEMENT>
     * @param <VALIDATOR>
     */
    public abstract class AbstractFluentValidatorBase<VALIDATOR_ELEMENT extends Element, VALIDATOR extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, VALIDATOR>> {

        private Diagnostic.Kind messageScope = Diagnostic.Kind.ERROR;
        private String message = null;
        private boolean invert = false;


        public VALIDATOR not() {

            invert = true;
            return self();

        }

        public VALIDATOR warn() {

            messageScope = Diagnostic.Kind.MANDATORY_WARNING;
            return self();

        }

        public VALIDATOR message(String messsage) {

            this.message = messsage;
            return self();

        }

        protected abstract VALIDATOR self();

        protected boolean isInverted() {
            return invert;
        }

        private String getMessage() {

            return message;

        }


        /**
         * Sets the validation result depending on inverted state and validation result.
         *
         * @param validationResult the validation result
         */
        protected void setValidationResult(boolean validationResult, String defaultMessage, Object... messsageParameter) {

            if ((!validationResult && !invert) || (validationResult && invert)) {
                fluentValidatorState.setAsFailedValidation();

                if (message != null) {
                    MessagerUtils.getMessagerUtils().printMessage(element, messageScope, message);
                } else {
                    MessagerUtils.getMessagerUtils().printMessage(element, messageScope, defaultMessage, messsageParameter);
                }

            }

        }


    }


    /**
     * Validator step for implicit validators.
     *
     * @param <VALIDATOR_ELEMENT>
     */
    public class ImplicitFluentValidator<VALIDATOR_ELEMENT extends Element> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, ImplicitFluentValidator<VALIDATOR_ELEMENT>> {

        private final ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator;

        private ImplicitFluentValidator(ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator) {
            this.validator = validator;
        }

        public FluentElementValidator<ELEMENT> apply() {

            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.validate((VALIDATOR_ELEMENT) element), validator.getMessage(), isInverted() ? "not" : "");
            }
            return FluentElementValidator.this;
        }

        @Override
        protected ImplicitFluentValidator<VALIDATOR_ELEMENT> self() {
            return this;
        }
    }

    /**
     * Validator step for is validators.
     *
     * @param <VALIDATOR_ELEMENT>
     */
    public class IsFluentValidator<VALIDATOR_ELEMENT extends Element, TARGET_ELEMENT extends Element> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, IsFluentValidator<VALIDATOR_ELEMENT, TARGET_ELEMENT>> {

        private final ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator;

        private IsFluentValidator(ImplicitValidator<VALIDATOR_ELEMENT, ImplicitMatcher<VALIDATOR_ELEMENT>> validator) {
            this.validator = validator;
        }

        public FluentElementValidator<TARGET_ELEMENT> apply() {


            boolean validationResult = false;

            // just validate if element != null
            if (element != null) {
                validationResult = validator.validate((VALIDATOR_ELEMENT) element);
                setValidationResult(validationResult, validator.getMessage());
            }

            return new FluentElementValidator<TARGET_ELEMENT>(validationResult ? (TARGET_ELEMENT) element : null, fluentValidatorState);

        }

        @Override
        protected IsFluentValidator<VALIDATOR_ELEMENT, TARGET_ELEMENT> self() {
            return this;
        }
    }


    /**
     * Validator step for exlusive characteristics validators.
     *
     * @param <VALIDATOR_ELEMENT>
     * @param <CHARACTERISTIC>
     */
    public class ExclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT extends Element, CHARACTERISTIC> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, ExclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC>> {

        private final ExclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator;

        private ExclusiveCharacteristicFluentValidator(ExclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator) {
            this.validator = validator;
        }

        public FluentElementValidator<ELEMENT> hasOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasOneOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        public FluentElementValidator<ELEMENT> hasNoneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasNoneOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        @Override
        protected ExclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC> self() {
            return this;
        }
    }

    /**
     * Validator step for inclusive characteristics validators.
     *
     * @param <VALIDATOR_ELEMENT>
     * @param <CHARACTERISTIC>
     */
    public class InclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT extends Element, CHARACTERISTIC> extends AbstractFluentValidatorBase<VALIDATOR_ELEMENT, InclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC>> {

        private final InclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator;

        private InclusiveCharacteristicFluentValidator(InclusiveCriteriaElementValidator<VALIDATOR_ELEMENT, CHARACTERISTIC, CriteriaMatcher<VALIDATOR_ELEMENT, CHARACTERISTIC>> validator) {
            this.validator = validator;
        }

        public FluentElementValidator<ELEMENT> hasOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasOneOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        public FluentElementValidator<ELEMENT> hasNoneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasNoneOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        public FluentElementValidator<ELEMENT> hasAtLeastOneOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasAtLeastOneOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        public FluentElementValidator<ELEMENT> hasAllOf(CHARACTERISTIC... params) {
            // just validate if element != null
            if (element != null) {
                setValidationResult(validator.hasAllOf((VALIDATOR_ELEMENT) element, params), validator.getMessage(), isInverted() ? "not" : "", params);
            }
            return FluentElementValidator.this;
        }

        @Override
        protected InclusiveCharacteristicFluentValidator<VALIDATOR_ELEMENT, CHARACTERISTIC> self() {
            return this;
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


    // apply implicit validators
    public ImplicitFluentValidator<ELEMENT> applyValidator(ImplicitCoreMatcher<ELEMENT> coreMatcher) {
        return new ImplicitFluentValidator<ELEMENT>(coreMatcher.getValidator());
    }

    public ImplicitFluentValidator<Element> applyValidator(ImplicitElementBasedCoreMatcher coreMatcher) {
        return new ImplicitFluentValidator<Element>(coreMatcher.getValidator());
    }

    // apply inclusive characteristics validators
    public <CHARACTERISTIC> InclusiveCharacteristicFluentValidator<ELEMENT, CHARACTERISTIC> applyValidator(InclusiveCriteriaCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {
        return new InclusiveCharacteristicFluentValidator<ELEMENT, CHARACTERISTIC>(coreMatcher.getValidator());
    }

    public <CHARACTERISTIC> InclusiveCharacteristicFluentValidator<Element, CHARACTERISTIC> applyValidator(InclusiveCharacteristicElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {
        return new InclusiveCharacteristicFluentValidator<Element, CHARACTERISTIC>(coreMatcher.getValidator());
    }

    // apply exclusive characteristics validators
    public <CHARACTERISTIC> ExclusiveCharacteristicFluentValidator<ELEMENT, CHARACTERISTIC> applyValidator(ExclusiveCriteriaCoreMatcher<ELEMENT, CHARACTERISTIC> coreMatcher) {
        return new ExclusiveCharacteristicFluentValidator<ELEMENT, CHARACTERISTIC>(coreMatcher.getValidator());
    }

    public <CHARACTERISTIC> ExclusiveCharacteristicFluentValidator<Element, CHARACTERISTIC> applyValidator(ExclusiveCriteriaElementBasedCoreMatcher<CHARACTERISTIC> coreMatcher) {
        return new ExclusiveCharacteristicFluentValidator<Element, CHARACTERISTIC>(coreMatcher.getValidator());
    }

    // apply is validators
    public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsCoreMatcher<ELEMENT, TARGET_ELEMENT> coreMatcher) {
        return new IsFluentValidator<ELEMENT, TARGET_ELEMENT>(coreMatcher.getValidator()).apply();
    }

    public <TARGET_ELEMENT extends Element> FluentElementValidator<TARGET_ELEMENT> is(IsElementBasedCoreMatcher<TARGET_ELEMENT> coreMatcher) {
        return new IsFluentValidator<Element, TARGET_ELEMENT>(coreMatcher.getValidator()).apply();
    }


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


    public static <E extends Element> FluentElementValidator<E> createFluentElementValidator(E element) {
        return new FluentElementValidator<E>(element);
    }


}
