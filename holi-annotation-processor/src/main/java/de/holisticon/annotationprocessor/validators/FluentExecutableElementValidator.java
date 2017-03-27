package de.holisticon.annotationprocessor.validators;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessor.tools.ElementUtils;
import de.holisticon.annotationprocessor.tools.MessagerUtils;
import de.holisticon.annotationprocessor.tools.TypeUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

/**
 * Fluent validator for {@link ExecutableElement} instances.
 */
public class FluentExecutableElementValidator extends AbstractFluentValidator<FluentExecutableElementValidator> {

    private final FrameworkToolWrapper frameworkToolWrapper;
    private final MessagerUtils messagerUtils;
    private final TypeUtils typeUtils;
    private final ExecutableElement methodElement;

    private final boolean currentValidationResult;

    public FluentExecutableElementValidator(FrameworkToolWrapper frameworkToolWrapper, ExecutableElement methodElement) {

        super(null);

        // config validator
        this.frameworkToolWrapper = frameworkToolWrapper;
        this.messagerUtils = MessagerUtils.getMessagerUtils(frameworkToolWrapper);
        this.typeUtils = TypeUtils.getTypeUtils(frameworkToolWrapper);
        this.methodElement = methodElement;


        this.currentValidationResult = true;
    }

    public FluentExecutableElementValidator(FluentExecutableElementValidator previousFluentExecutableElementValidator, boolean currentValidationResult) {

        super(previousFluentExecutableElementValidator);

        // get config of previous validator instance
        this.frameworkToolWrapper = previousFluentExecutableElementValidator.frameworkToolWrapper;
        this.messagerUtils = previousFluentExecutableElementValidator.messagerUtils;
        this.typeUtils = previousFluentExecutableElementValidator.typeUtils;
        this.methodElement = previousFluentExecutableElementValidator.methodElement;

        this.currentValidationResult = currentValidationResult;
    }

    /**
     * Returns the current validation result.
     *
     * @return true if all validations succeeded, otherwise false
     */
    public boolean validate() {
        return currentValidationResult;
    }

    /**
     * Validates if element is a method.
     *
     * @return true if element is of kind method, otherwise false
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
     * @return true if elements kind matches the passed kind, otherwise false
     */
    public FluentExecutableElementValidator isOfKind(ElementKind kind) {

        boolean nextResult = this.currentValidationResult;

        if (!ElementUtils.getElementUtils().isOfKind(methodElement, kind)) {

            // validation failed - output message
            messagerUtils.printMessage(methodElement, getMessageLevel(), "Element must be of kind %s", kind);
            nextResult = isErrorLevel() ? false : nextResult;

        }

        return new FluentExecutableElementValidator(this, nextResult);
    }


    public FluentExecutableElementValidator hasVoidReturnType() {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(methodElement) && !typeUtils.isVoidType(methodElement.getReturnType())) {

            // validation failed - output message
            messagerUtils.printMessage(methodElement, getMessageLevel(), "Method must have void return type");
            nextResult = isErrorLevel() ? false : nextResult;


        }


        return new FluentExecutableElementValidator(this, nextResult);
    }

    public FluentExecutableElementValidator hasNonVoidReturnType() {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(methodElement) && typeUtils.isVoidType(methodElement.getReturnType())) {

            // validation failed - output message
            messagerUtils.printMessage(methodElement, getMessageLevel(), "Method must have non void return type");
            nextResult = isErrorLevel() ? false : nextResult;

        }

        return new FluentExecutableElementValidator(this, nextResult);
    }

    public FluentExecutableElementValidator hasReturnType(Class type) {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(methodElement) && hasNonVoidReturnType().validate()) {

            if (type == null || !typeUtils.getTypes().isAssignable(methodElement.getReturnType(), typeUtils.getTypeMirrorForClass(type))) {

                // validation failed - output message
                messagerUtils.printMessage(methodElement, getMessageLevel(), "Methods return type must be assignable to type %s", type.getSimpleName());
                nextResult = isErrorLevel() ? false : nextResult;

            }

        } else {
            nextResult = false;
        }

        return new FluentExecutableElementValidator(this, nextResult);
    }

    public FluentExecutableElementValidator hasName(String name) {
        boolean nextResult = this.currentValidationResult;

        if (name == null || !name.equals(methodElement.getSimpleName().toString())) {
            messagerUtils.printMessage(methodElement, getMessageLevel(), "Element must have name %s, but has name", name, methodElement.getSimpleName());
            nextResult = isErrorLevel() ? false : nextResult;
        }

        return new FluentExecutableElementValidator(this, nextResult);
    }

    public FluentExecutableElementValidator hasParameters() {
        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(methodElement) && methodElement.getParameters().isEmpty()) {
            messagerUtils.printMessage(methodElement, getMessageLevel(), "Method must have parameters, but has none");
            nextResult = isErrorLevel() ? false : nextResult;
        }

        return new FluentExecutableElementValidator(this, nextResult);
    }

    public FluentExecutableElementValidator hasParameters(Class... parameterTypes) {

        boolean nextResult = this.currentValidationResult;

        if (ElementUtils.getElementUtils().isMethod(methodElement)) {
            if (methodElement.getParameters().size() != parameterTypes.length) {
                messagerUtils.printMessage(methodElement, getMessageLevel(), "Method number of parameters is %d but expected %d", methodElement.getParameters().size(), parameterTypes.length);
                nextResult = isErrorLevel() ? false : nextResult;
            } else {
                for (int i = 0; i < methodElement.getParameters().size(); i++) {
                    if (!methodElement.getParameters().get(i).asType().equals(typeUtils.getTypeMirrorForClass(parameterTypes[i]))) {
                        messagerUtils.printMessage(methodElement, getMessageLevel(), "Method must have parameters, but has none");
                        nextResult = isErrorLevel() ? false : nextResult;
                    }
                }
            }
        }

        return new FluentExecutableElementValidator(this, nextResult);

    }


}
