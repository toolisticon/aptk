package de.holisticon.annotationprocessor.validators;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessor.tools.MessagerUtils;
import de.holisticon.annotationprocessor.tools.TypeUtils;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Fluent and immutable validator for validation of {@link TypeElement}.
 */
public class FluentTypeElementValidator extends AbstractFluentValidator<FluentTypeElementValidator> {

    private final FrameworkToolWrapper frameworkToolWrapper;
    private final TypeElement typeElement;
    private final MessagerUtils messagerUtils;
    private final TypeUtils typeUtils;

    private final boolean currentResult;

    public FluentTypeElementValidator(FrameworkToolWrapper frameworkToolWrapper, TypeElement typeElement) {

        super(null);

        // setup element validator
        this.frameworkToolWrapper = frameworkToolWrapper;
        this.messagerUtils = MessagerUtils.getMessagerUtils(frameworkToolWrapper);
        this.typeUtils = TypeUtils.getTypeUtils(frameworkToolWrapper);
        this.typeElement = typeElement;

        this.currentResult = true;
    }

    private FluentTypeElementValidator(FluentTypeElementValidator previousFluentTypeElementValidator, boolean currentResult) {

        super(previousFluentTypeElementValidator);

        // take configuration of previous ElementValidator
        this.frameworkToolWrapper = previousFluentTypeElementValidator.frameworkToolWrapper;
        this.messagerUtils = previousFluentTypeElementValidator.messagerUtils;
        this.typeUtils = previousFluentTypeElementValidator.typeUtils;
        this.typeElement = previousFluentTypeElementValidator.typeElement;

        this.currentResult = currentResult;
    }

    public boolean validate() {
        return this.currentResult;
    }


    public FluentTypeElementValidator isAssignableTo(Class type) {
        return type != null ? isAssignableTo(typeUtils.getTypeElementForClass(type)) : new FluentTypeElementValidator(this, false);
    }

    public FluentTypeElementValidator isAssignableTo(TypeElement typeElementToCheck) {
        return typeElementToCheck != null ? isAssignableTo(typeElementToCheck.asType()) : new FluentTypeElementValidator(this, false);
    }

    public FluentTypeElementValidator isAssignableTo(TypeMirror typeMirror) {
        boolean check = this.currentResult;

        if (typeMirror == null || !typeUtils.isAssignableToTypeMirror(typeElement, typeMirror)) {
            messagerUtils.printMessage(typeElement, getMessageLevel(), "type must be assignable to %s", typeMirror != null ? typeMirror.toString() : null);
            check = isErrorLevel() ? false : check;
        }

        return new FluentTypeElementValidator(this, check);
    }

}
