package de.holisticon.annotationprocessor.validators;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessor.tools.ElementUtils;
import de.holisticon.annotationprocessor.tools.MessagerUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Fluent and immutable validator for validation of {@link Modifier} of {@link Element}.
 */
public class FluentModifierElementValidator<T extends Element> extends AbstractFluentValidator<FluentModifierElementValidator>{

    private final FrameworkToolWrapper frameworkToolWrapper;
    private final MessagerUtils messagerUtils;

    private final boolean currentResult;
    private final T elementToValidate;

    public FluentModifierElementValidator(FrameworkToolWrapper frameworkToolWrapper, T elementToValidate) {

        super(null);

        // config fluent validator
        this.frameworkToolWrapper = frameworkToolWrapper;
        messagerUtils = MessagerUtils.getMessagerUtils(frameworkToolWrapper);
        this.elementToValidate = elementToValidate;

        currentResult = true;
    }

    private FluentModifierElementValidator(FluentModifierElementValidator<T> previousFluentModifierElementValidator, boolean currentResult) {

        super(previousFluentModifierElementValidator);

        // use config of previous fluent validator
        this.frameworkToolWrapper = previousFluentModifierElementValidator.frameworkToolWrapper;
        this.messagerUtils = previousFluentModifierElementValidator.messagerUtils;
        this.elementToValidate = previousFluentModifierElementValidator.elementToValidate;

        this.currentResult = currentResult;
    }

    public FluentModifierElementValidator hasModifiers(Modifier... modifiers) {
        boolean nextResult = currentResult;

        if (modifiers != null) {
            if (ElementUtils.getElementUtils().hasModifiers(elementToValidate, modifiers)) {
                messagerUtils.printMessage(elementToValidate, getMessageLevel(), "Element must have the following modifiers %s", getModifierString(modifiers));
                nextResult = isErrorLevel() ? false : nextResult;
            }
        }

        return new FluentModifierElementValidator<T>(this, nextResult);
    }

    public FluentModifierElementValidator hasNotModifiers(Modifier... modifiers) {
        boolean nextResult = currentResult;

        if (modifiers != null) {
            if (ElementUtils.getElementUtils().hasNotModifiers(elementToValidate, modifiers)) {
                messagerUtils.printMessage(elementToValidate,getMessageLevel(), "Element must have the following modifiers %s", getModifierString(modifiers));
                nextResult = isErrorLevel() ? false : nextResult;
            }
        }

        return new FluentModifierElementValidator<T>(this, nextResult);
    }

    private String getModifierString(Modifier[] modifiers) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (modifiers != null) {
            boolean first = true;
            for (Modifier modifier : modifiers) {

                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }

                sb.append(modifier.name());
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean validate() {
        return currentResult;
    }

}
