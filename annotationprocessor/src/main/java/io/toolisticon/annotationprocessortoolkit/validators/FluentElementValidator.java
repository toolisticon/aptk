package io.toolisticon.annotationprocessortoolkit.validators;

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.GenericElementCharacteristicValidator;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;


/**
 * Fluent validator utility class to validator Element.
 * Each validator operation produces a FluentElementValidator instance.
 *
 * @param <T>
 */
public class FluentElementValidator<T extends Element> extends AbstractFluentElementValidator<FluentElementValidator, T> {

    private final boolean validationResult;

    public final class ApplyValidator<C> {

        private final GenericElementCharacteristicValidator<C> validator;
        private boolean invertFiltering = false;
        private Diagnostic.Kind messageLevel = Diagnostic.Kind.ERROR;

        private ApplyValidator(Validator<C> filter) {
            this.validator = filter != null ? filter.getValidator() : null;
        }


        /**
         * Allows you to overrule message level if validation fails.
         * Default message level is ERROR.
         *
         * @param messageLevel
         * @return
         */
        public ApplyValidator<C> setMessageLevel(Diagnostic.Kind messageLevel) {
            this.messageLevel = messageLevel;
            return this;
        }


        /**
         * Validates element by passed characteristics.
         * Element must suffice all of the passed characteristics.
         *
         * @param validatorCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByAllOf(final C... validatorCharacteristics) {
            boolean result = validator.hasAllOf(getElement(), validatorCharacteristics);

            if (!result) {
                MessagerUtils.getMessagerUtils().printMessage(getElement(), Diagnostic.Kind.ERROR, validator.getFailingValidationMessage().getMessage(), "all of", ValidatorUtilities.createStringRepresentationOfArray(validatorCharacteristics));
            }

            return createNextFluentValidator(result);
        }

        /**
         * Validates element by passed characteristics.
         * Ellements must suffice at least one of the passed characteristics.
         *
         * @param validatorCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByAtLeastOneOf(final C... validatorCharacteristics) {
            boolean result = validator.hasAtLeastOneOf(getElement(), validatorCharacteristics);

            if (!result) {
                MessagerUtils.getMessagerUtils().printMessage(getElement(), Diagnostic.Kind.ERROR, validator.getFailingValidationMessage().getMessage(), "at least one of", ValidatorUtilities.createStringRepresentationOfArray(validatorCharacteristics));
            }

            return createNextFluentValidator(result);
        }

        /**
         * Validates element by passed characteristics.
         * Ellements must suffice exactly one of the passed characteristics.
         *
         * @param validatorCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByOneOf(final C... validatorCharacteristics) {
            boolean result = validator.hasOneOf(getElement(), validatorCharacteristics);

            if (!result) {
                MessagerUtils.getMessagerUtils().printMessage(getElement(), Diagnostic.Kind.ERROR, validator.getFailingValidationMessage().getMessage(), "one of", ValidatorUtilities.createStringRepresentationOfArray(validatorCharacteristics));
            }

            return createNextFluentValidator(result);
        }

        /**
         * Validates element by passed characteristics.
         * Ellements must suffice none of the passed characteristics.
         *
         * @param validatorCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByNoneOf(final C... validatorCharacteristics) {
            boolean result = validator.hasNoneOf(getElement(), validatorCharacteristics);
            if (!result) {
                MessagerUtils.getMessagerUtils().printMessage(getElement(), Diagnostic.Kind.ERROR, validator.getFailingValidationMessage().getMessage(), "none of", ValidatorUtilities.createStringRepresentationOfArray(validatorCharacteristics));
            }
            return createNextFluentValidator(result);
        }

    }

    /**
     * Hide constructor.
     * Use static method instead.
     *
     * @param element the list to be processed
     */
    private FluentElementValidator(T element) {
        super(element);
        this.validationResult = true;
    }

    /**
     * Hide constructor.
     * Use static method instead.
     *
     * @param element          the list to be processed
     * @param validationResult the validation result of previous validation
     */
    private FluentElementValidator(T element, boolean validationResult) {
        super(element);
        this.validationResult = validationResult;
    }

    @Override
    protected FluentElementValidator<T> createNextFluentValidator(boolean nextResult) {
        return new FluentElementValidator<T>(getElement(), nextResult);
    }

    /**
     * Fluently apply validator.
     *
     * @param validator the validator to use
     * @param <C>
     * @return The fluent filtering interface
     */
    public <C> ApplyValidator<C> applyValidator(Validator<C> validator) {

        return new ApplyValidator<C>(validator);
    }

    /**
     * Returns validation result.
     *
     * @return true if validation was successfull, otherwise false
     */
    public boolean validate() {
        return validationResult;
    }

    /**
     * Convenience method for the creation of FluentElementFilter.
     *
     * @param element the list to validator
     * @param <X>
     * @return the freshly created validator instance
     */
    public static <X extends Element> FluentElementValidator<X> createFluentElementValidator(X element) {
        return new FluentElementValidator<X>(element);
    }


}