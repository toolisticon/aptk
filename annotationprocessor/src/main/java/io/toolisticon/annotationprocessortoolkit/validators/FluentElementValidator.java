package io.toolisticon.annotationprocessortoolkit.validators;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filter;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.GenericElementCharacteristicValidator;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;

import javax.lang.model.element.Element;


/**
 * Fluent validator utility class to validator lists of Elements.
 * Each validator operation produces a FluentElementValidator instance.
 *
 * @param <T>
 */
public abstract class FluentElementValidator<T extends Element> extends AbstractFluentValidator<FluentModifierElementValidator, T> {


    public final class ApplyValidator<C> {

        private final GenericElementCharacteristicValidator<C> validator;
        private boolean invertFiltering = false;

        private ApplyValidator(Validator<C> filter) {
            this.validator = filter != null ? filter.getValidator() : null;
        }


        /**
         * Triggers an inverted filtering.
         *
         * @return
         */
        public ApplyValidator<C> invert() {
            invertFiltering = true;
            return this;
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice all of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByAllOf(final C... filteringCharacteristics) {
            //return new FluentElementValidator<T>(validator.hasAllOf().filterByCharacteristics(ValidatorKind.ALL_OF, invertFiltering, result, filteringCharacteristics));
            return null;
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice at least one of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByAtLeastOneOf(final C... filteringCharacteristics) {
            //return new FluentElementValidator<T>(validator.filterByCharacteristics(ValidatorKind.AT_LEAST_ONE_OF, invertFiltering, result, filteringCharacteristics));
            return null;
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice exactly one of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByOneOf(final C... filteringCharacteristics) {
            //return new FluentElementValidator<T>(validator.filterByCharacteristics(ValidatorKind.ONE_OF, invertFiltering, result, filteringCharacteristics));
            return null;
        }

        /**
         * Filters list by passed characteristics.
         * Ellements must suffice none of the passed characteristics.
         *
         * @param filteringCharacteristics the characteristics to validator by
         * @return A fresh filtered list
         */
        public FluentElementValidator<T> validateByNoneOf(final C... filteringCharacteristics) {
            //return new FluentElementValidator<T>(validator.filterByCharacteristics(ValidatorKind.NONE_OF, invertFiltering, result, filteringCharacteristics));
            return null;
        }

    }

    /**
     * Hide constructor.
     * Use static method instead.
     *
     * @param element the list to be processed
     */
    private FluentElementValidator(FrameworkToolWrapper frameworkToolWrapper, T element) {
        super(frameworkToolWrapper, element);
    }


    /**
     * Fluently apply validator.
     *
     * @param filter the validator to use
     * @param <C>
     * @return The fluent filtering interface
     */
    public <C> ApplyValidator<C> applyValidator(Filter<C> filter) {

        return null; //new ApplyValidator<C>(filter);
    }


    /**
     * Convenience method for the creation of FluentElementFilter.
     *
     * @param element the list to validator
     * @param <X>
     * @return the freshly created validator instance
     */
    public static <X extends Element> FluentElementValidator<X> createFluentValidator(FrameworkToolWrapper frameworkToolWrapper, X element) {
        return null; //new FluentElementValidator<X>(frameworkToolWrapper, element);
    }

}