package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Fluent and immutable validator for validation of {@link javax.lang.model.element.Modifier} of {@link Element}.
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

    public static FluentModifierElementValidator createFluentModifierElementValidator(FrameworkToolWrapper frameworkToolWrapper, Element executableElement) {
        return new FluentModifierElementValidator(frameworkToolWrapper, executableElement);
    }

    public static FluentModifierElementValidator createFluentModifierElementValidator(ProcessingEnvironment processingEnvironment, Element executableElement) {
        return new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnvironment), executableElement);
    }

}
