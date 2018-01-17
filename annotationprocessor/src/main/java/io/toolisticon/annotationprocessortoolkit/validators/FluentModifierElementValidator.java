package io.toolisticon.annotationprocessortoolkit.validators;

import javax.lang.model.element.Element;

/**
 * Fluent and immutable validator for validation of {@link javax.lang.model.element.Modifier} of {@link Element}.
 */
public class FluentModifierElementValidator extends AbstractFluentElementValidator<FluentModifierElementValidator, Element> {


    public FluentModifierElementValidator(Element elementToValidate) {

        super(elementToValidate);

    }

    private FluentModifierElementValidator(FluentModifierElementValidator previousFluentModifierElementValidator, boolean currentResult) {

        super(previousFluentModifierElementValidator, currentResult);

    }


    protected FluentModifierElementValidator createNextFluentValidator(boolean nextResult) {
        return new FluentModifierElementValidator(this, nextResult);
    }

    public static FluentModifierElementValidator createFluentModifierElementValidator(Element executableElement) {
        return new FluentModifierElementValidator(executableElement);
    }


}
