package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Fluent and immutable validator for validation of {@link TypeElement}.
 */
public class FluentTypeElementValidator extends AbstractFluentElementValidator<FluentTypeElementValidator, TypeElement> {


    public FluentTypeElementValidator(FrameworkToolWrapper frameworkToolWrapper, TypeElement typeElement) {

        super(frameworkToolWrapper, typeElement);

    }

    private FluentTypeElementValidator(FluentTypeElementValidator previousFluentTypeElementValidator, boolean currentResult) {

        super(previousFluentTypeElementValidator, currentResult);

    }

    /**
     * Checks whether type element under validation is assignable to passed {@link Class}.
     *
     * @param type the type to check for
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentTypeElementValidator isAssignableTo(Class type) {
        return type != null ? isAssignableTo(typeUtils.getTypeElementForClass(type)) : new FluentTypeElementValidator(this, false);
    }

    /**
     * Checks whether type element under validation is assignable to passed {@link TypeElement}.
     *
     * @param typeElementToCheck
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentTypeElementValidator isAssignableTo(TypeElement typeElementToCheck) {
        return typeElementToCheck != null ? isAssignableTo(typeElementToCheck.asType()) : new FluentTypeElementValidator(this, false);
    }

    /**
     * Checks whether type element under validation is assignable to passed {@link TypeMirror}.
     *
     * @param typeMirror
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentTypeElementValidator isAssignableTo(TypeMirror typeMirror) {
        boolean check = this.currentValidationResult;

        if (typeMirror == null || !typeUtils.isAssignableToTypeMirror(element, typeMirror)) {
            messagerUtils.printMessage(element, getMessageLevel(), getCustomOrDefaultMessage("type must be assignable to ${0}", typeMirror));
            check = isErrorLevel() ? false : check;
        }

        return createNextFluentValidator(check);
    }

    /**
     * Checks whether the type element under validation has a no arg constructor or the default noarg constructor.
     *
     * @return an immutable FluentExecutableElementValidator instance
     */
    public FluentTypeElementValidator hasNoArgConstructor() {
        boolean check = this.currentValidationResult;


        List<ExecutableElement> constructors = (List<ExecutableElement>) ElementUtils.getElementUtils().castElementList(new FluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.CONSTRUCTOR).getResult(), ExecutableElement.class);
        if (constructors.size() > 0) {

            boolean found = false;

            for (ExecutableElement executableElement : constructors) {

                if (executableElement.getParameters().size() == 0) {
                    found = true;
                    break;
                }

            }

            if (!found) {
                check = false;
            }

        }


        return createNextFluentValidator(check);
    }


    protected FluentTypeElementValidator createNextFluentValidator(boolean nextResult) {
        return new FluentTypeElementValidator(this, nextResult);
    }

}
