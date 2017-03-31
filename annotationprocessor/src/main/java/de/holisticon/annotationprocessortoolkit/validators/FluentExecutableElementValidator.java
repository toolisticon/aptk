package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

/**
 * Fluent validator for {@link ExecutableElement} instances.
 */
public class FluentExecutableElementValidator extends AbstractFluentElementValidator<FluentExecutableElementValidator, ExecutableElement> {

    public FluentExecutableElementValidator(FrameworkToolWrapper frameworkToolWrapper, ExecutableElement methodElement) {
        super(frameworkToolWrapper, methodElement);
    }

    public FluentExecutableElementValidator(FluentExecutableElementValidator previousFluentExecutableElementValidator, boolean currentValidationResult) {

        super(previousFluentExecutableElementValidator, currentValidationResult);

    }

    /**
     * Validates if element is a method.
     *
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator isMethod() {
        return isOfKind(ElementKind.METHOD);
    }

    /**
     * Validates if element is a method.
     *
     * @return true if element is of kind constructor, otherwise false
     */
    public FluentExecutableElementValidator isConstructor() {
        return isOfKind(ElementKind.CONSTRUCTOR);
    }

    /**
     * Validates the kind of an element.
     *
     * @param kind the element kind to check for
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator isOfKind(ElementKind kind) {

        boolean nextResult = this.currentValidationResult;

        if (!ElementUtils.getElementUtils().isOfKind(element, kind)) {

            // validation failed - output message
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Element must be of kind %s", kind));
            nextResult = isErrorLevel() ? false : nextResult;

        }

        return createNextFluentValidator(nextResult);
    }


    /**
     * Checks whether the method has a void return type.
     *
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasVoidReturnType() {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(element) && !typeUtils.isVoidType(element.getReturnType())) {

            // validation failed - output message
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Method must have void return type"));
            nextResult = isErrorLevel() ? false : nextResult;


        }


        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks whether the method has a non void return type.
     *
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasNonVoidReturnType() {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(element) && typeUtils.isVoidType(element.getReturnType())) {

            // validation failed - output message
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Method must have non void return type"));
            nextResult = isErrorLevel() ? false : nextResult;

        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Validates if return type is assignable to passed type.
     *
     * @param type the type that the return type should be assignable to
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasReturnType(Class type) {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(element) && hasNonVoidReturnType().getValidationResult()) {

            if (type == null || !typeUtils.getTypes().isAssignable(element.getReturnType(), typeUtils.getTypeMirrorForClass(type))) {

                // validation failed - output message
                messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Methods return type must be assignable to type %s", type.getSimpleName()));
                nextResult = isErrorLevel() ? false : nextResult;

            }

        } else {
            nextResult = false;
        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks whether the element under validation has the passed name.
     *
     * @param name the name to check for
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasName(String name) {
        boolean nextResult = this.currentValidationResult;

        if (name == null || !name.equals(element.getSimpleName().toString())) {
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Element must have name %s, but has name", name, element.getSimpleName()));
            nextResult = isErrorLevel() ? false : nextResult;
        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks whether the method has parameters.
     *
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasParameters() {
        boolean nextResult = this.currentValidationResult;

        if ((ElementUtils.getElementUtils().isMethod(element) || ElementUtils.getElementUtils().isConstructor(element)) && element.getParameters().isEmpty()) {
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Method must have parameters, but has none"));
            nextResult = isErrorLevel() ? false : nextResult;
        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks whether the method has parameters that are assignable to passed typed
     *
     * @param parameterTypes The parameter types
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasParameters(Class... parameterTypes) {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(element)) {
            if (element.getParameters().size() != parameterTypes.length) {
                messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Method number of parameters is %d but expected %d", element.getParameters().size(), parameterTypes.length));
                nextResult = isErrorLevel() ? false : nextResult;
            } else {
                for (int i = 0; i < element.getParameters().size(); i++) {
                    if (!element.getParameters().get(i).asType().equals(typeUtils.getTypeMirrorForClass(parameterTypes[i]))) {
                        messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("Method must have parameters, but has none"));
                        nextResult = isErrorLevel() ? false : nextResult;
                    }
                }
            }
        }

        return createNextFluentValidator(nextResult);

    }


    protected FluentExecutableElementValidator createNextFluentValidator(boolean nextResult) {
        return new FluentExecutableElementValidator(this, nextResult);
    }


}
