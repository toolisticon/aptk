package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

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

        boolean nextResult = this.getValidationResult();

        if (!ElementUtils.CheckKindOfElement.isOfKind(getElement(), kind)) {

            // validation failed - output message
            getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage("Element must be of kind %s", kind));
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

        boolean nextResult = this.getValidationResult();

        if (ElementUtils.CheckKindOfElement.isMethod(getElement()) && !TypeUtils.CheckTypeKind.INSTANCE.isVoid(getElement().getReturnType())) {

            // validation failed - output message
            getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage("Method must have void return type"));
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

        boolean nextResult = this.getValidationResult();

        if (ElementUtils.CheckKindOfElement.isMethod(getElement()) && TypeUtils.CheckTypeKind.INSTANCE.isVoid(getElement().getReturnType())) {

            // validation failed - output message
            getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage("Method must have non void return type"));
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

        boolean nextResult = this.getValidationResult();

        if (type != null) {
            if (ElementUtils.CheckKindOfElement.isMethod(getElement()) && hasNonVoidReturnType().getValidationResult()) {

                if (!getTypeUtils().getTypes().isAssignable(getElement().getReturnType(), getTypeUtils().doTypeRetrieval().getTypeMirror(type))) {

                    // validation failed - output message
                    getMessagerUtils().printMessage(
                            getElement(),
                            getMessageLevel(),
                            getCustomOrDefaultMessage("Methods return type must be assignable to type ${0}", type.getSimpleName())
                    );
                    nextResult = isErrorLevel() ? false : nextResult;

                }

            } else {
                nextResult = false;
            }
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
        boolean nextResult = this.getValidationResult();

        if (name == null || !name.equals(getElement().getSimpleName().toString())) {

            getMessagerUtils().printMessage(
                    getElement(),
                    getMessageLevel(),
                    getCustomOrDefaultMessage("Element must have name ${0}, but has name ${1}", name, getElement().getSimpleName())
            );
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
        boolean nextResult = this.getValidationResult();

        if ((ElementUtils.CheckKindOfElement.isMethod(getElement()) || ElementUtils.CheckKindOfElement.isConstructor(getElement()))
                && getElement().getParameters().isEmpty()) {

            getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage("Method must have parameters, but has none"));
            nextResult = isErrorLevel() ? false : nextResult;

        }

        return createNextFluentValidator(nextResult);
    }

    /**
     * Checks whether the method has parameters that are assignable to passed typed.
     *
     * @param parameterTypes The parameter types
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentExecutableElementValidator hasParameters(Class... parameterTypes) {

        boolean nextResult = this.getValidationResult();

        if (ElementUtils.CheckKindOfElement.isMethod(getElement())) {
            if (getElement().getParameters().size() != parameterTypes.length) {

                triggerMismatchingParameterError(parameterTypes);
                nextResult = isErrorLevel() ? false : nextResult;

            } else {

                for (int i = 0; i < getElement().getParameters().size(); i++) {

                    if (!getElement().getParameters().get(i).asType().equals(getTypeUtils().doTypeRetrieval().getTypeMirror(parameterTypes[i]))) {
                        triggerMismatchingParameterError(parameterTypes);
                        nextResult = isErrorLevel() ? false : nextResult;
                    }

                }
            }
        }

        return createNextFluentValidator(nextResult);

    }

    private void triggerMismatchingParameterError(Class... parameterTypes) {
        getMessagerUtils().printMessage(getElement(), getMessageLevel(), getCustomOrDefaultMessage(
                "Method must have parameters of types ${1}, but has parameters of types ${0}",
                createStringRepresentationOfPassedTypes(getElement().getParameters()), createStringRepresentationOfPassedTypes(parameterTypes))
        );
    }

    private String createStringRepresentationOfPassedTypes(Class[] types) {


        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (types != null) {

            boolean isFirst = true;
            for (Class type : types) {

                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(", ");
                }

                sb.append(type.getCanonicalName());

            }
        }

        sb.append("]");

        return sb.toString();
    }

    private String createStringRepresentationOfPassedTypes(List<? extends VariableElement> parameters) {


        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (parameters != null) {

            boolean isFirst = true;
            for (VariableElement element : parameters) {

                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(", ");
                }

                sb.append(element.asType().toString());

            }
        }

        sb.append("]");

        return sb.toString();
    }


    protected FluentExecutableElementValidator createNextFluentValidator(boolean nextResult) {
        return new FluentExecutableElementValidator(this, nextResult);
    }


}
