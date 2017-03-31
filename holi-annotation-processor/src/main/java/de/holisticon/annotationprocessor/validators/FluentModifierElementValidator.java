package de.holisticon.annotationprocessor.validators;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Fluent and immutable validator for validation of {@link Modifier} of {@link Element}.
 */
public class FluentModifierElementValidator<T extends Element> extends AbstractFluentElementValidator<FluentModifierElementValidator, T> {


    public FluentModifierElementValidator(FrameworkToolWrapper frameworkToolWrapper, T elementToValidate) {

        super(frameworkToolWrapper, elementToValidate);

    }

    private FluentModifierElementValidator(FluentModifierElementValidator<T> previousFluentModifierElementValidator, boolean currentResult) {

        super(previousFluentModifierElementValidator, currentResult);

    }

    protected FluentModifierElementValidator<T> createNextFluentValidator(boolean nextResult) {
        return new FluentModifierElementValidator<T>(this, nextResult);
    }

}
