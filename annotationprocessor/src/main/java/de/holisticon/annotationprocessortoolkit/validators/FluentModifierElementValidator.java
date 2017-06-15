package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Fluent and immutable validator for validation of {@link Modifier} of {@link Element}.
 */
public class FluentModifierElementValidator extends AbstractFluentElementValidator<FluentModifierElementValidator, Element> {


    public FluentModifierElementValidator(FrameworkToolWrapper frameworkToolWrapper, Element elementToValidate) {

        super(frameworkToolWrapper, elementToValidate);

    }

    private FluentModifierElementValidator(FluentModifierElementValidator previousFluentModifierElementValidator, boolean currentResult) {

        super(previousFluentModifierElementValidator, currentResult);

    }


    protected FluentModifierElementValidator createNextFluentValidator(boolean nextResult) {
        return new FluentModifierElementValidator(this, nextResult);
    }

}
