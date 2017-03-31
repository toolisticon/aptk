package de.holisticon.annotationprocessor.tools;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Utility class and wrapper for / of {@link Types}.
 */
public class TypeUtils {

    protected final FrameworkToolWrapper frameworkToolWrapper;

    private TypeUtils(FrameworkToolWrapper frameworkToolWrapper) {
        this.frameworkToolWrapper = frameworkToolWrapper;
    }

    /**
     * Gets {@link TypeElement} for class.
     *
     * @param type the class to get the {@link TypeElement} for
     * @return The {@link TypeElement} that is related with the passed class
     */
    public TypeElement getTypeElementForClass(Class type) {
        return type == null ? null : frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName());
    }

    /**
     * Gets {@link TypeMirror} for class.
     *
     * @param type the class to get the {@link TypeMirror} for
     * @return The {@link TypeMirror} that is related with the passed class
     */
    public TypeMirror getTypeMirrorForClass(Class type) {
        return type != null ? getTypeElementForClass(type).asType() : null;
    }

    /**
     * Checks if passed typeElement is assignable to passed type
     *
     * @param typeElement the type element to check
     * @param type        the class which typeElement must assignable to
     * @return true if typeElement is assignable to type otherwise false.
     */
    public boolean isAssignableToType(TypeElement typeElement, Class type) {
        return isAssignableToTypeMirror(typeElement, frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName()).asType());
    }

    /**
     * Checks if passed typeElement is assignable to passed typeMirror
     *
     * @param typeElement the type element to check
     * @param typeMirror  the type mirror which typeElement must assignable to
     * @return true if typeElement is assignable to typeMirror otherwise false.
     */
    public boolean isAssignableToTypeMirror(TypeElement typeElement, TypeMirror typeMirror) {
        return typeElement == null || typeMirror == null ? false : frameworkToolWrapper.getTypes().isAssignable(typeElement.asType(), typeMirror);
    }

    /**
     * Checks whether passed first {@link TypeElement} is assignable to passed second {@link TypeElement}.
     *
     * @param typeElement1 the type element to check
     * @param typeElement2 the type element which typeElement1 must be assignable to
     * @return true if typeElement1 is assignable to typeElement2 otherwise false.
     */
    public boolean isAssignableToTypeElement(TypeElement typeElement1, TypeElement typeElement2) {
        return isAssignableToTypeMirror(typeElement1, typeElement2.asType());
    }

    /**
     * Checks whether passed {@link TypeMirror} is a void type or not.
     *
     * @param typeMirror the {@link TypeMirror} to check
     * @return
     */
    public boolean isVoidType(TypeMirror typeMirror) {
        return typeMirror != null && TypeKind.VOID.equals(typeMirror.getKind());
    }

    /**
     * Gets the wrapped {@link Types} instance.
     *
     * @return
     */
    public Types getTypes() {
        return frameworkToolWrapper.getTypes();
    }

    /**
     * Gets an instance of this TypeUtils class.
     *
     * @param frameworkToolWrapper the wrapper instance that provides the {@link javax.annotation.processing.ProcessingEnvironment} tools
     * @return the type utils
     */
    public static TypeUtils getTypeUtils(FrameworkToolWrapper frameworkToolWrapper) {
        return new TypeUtils(frameworkToolWrapper);
    }


}
