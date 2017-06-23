package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
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
     * Embedded class for hanlding the TypeKind checks.
     */
    public class CheckTypeKind {

        /**
         * Checks whether passed {@link TypeMirror} is a void type or not.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @return true if passed typeMirror has the TypeKind.VOID, otherwise false
         */
        public boolean isVoid(TypeMirror typeMirror) {
            return isOfTypeKind(typeMirror, TypeKind.VOID);
        }

        /**
         * Checks whether passed {@link TypeMirror} is a void type or not.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @return true if passed typeMirror has the TypeKind.ARRAY, otherwise false
         */
        public boolean isArray(TypeMirror typeMirror) {
            return isOfTypeKind(typeMirror, TypeKind.ARRAY);
        }


        /**
         * Checks whether passed {@link TypeMirror} is of passed {@link TypeKind}
         *
         * @param typeMirror
         * @param kind
         * @return
         */
        public boolean isOfTypeKind(TypeMirror typeMirror, TypeKind kind) {
            return typeMirror != null && kind != null && kind.equals(typeMirror.getKind());
        }

    }

    public CheckTypeKind checkTypeKind() {
        return new CheckTypeKind();
    }

    /**
     * Check wheteher passed typeMirror is of passed kind
     *
     * @param typeMirror
     * @param typeKind
     * @return true if passed typeMirror is of passed kind, otherwise false
     */
    public boolean checkTypeKind(TypeMirror typeMirror, TypeKind typeKind) {
        return checkTypeKind().isOfTypeKind(typeMirror, typeKind);
    }


    /**
     * Gets a type element for a full qualified class name
     *
     * @param fullQualifiedClassName
     * @return the type element for the passed full qualified class name or null if type element can't be found
     */
    public TypeElement getTypeElement(String fullQualifiedClassName) {

        if (fullQualifiedClassName == null) {
            return null;
        }

        return frameworkToolWrapper.getElements().getTypeElement(fullQualifiedClassName);

    }

    /**
     * Gets a TypeMirror for a full qualified class name
     *
     * @param fullQualifiedClassName
     * @return the type mirror for the passed full qualified class name or null if corresponding type element can't be found
     */
    public TypeMirror getTypeMirror(String fullQualifiedClassName) {

        TypeElement typeElement = getTypeElement(fullQualifiedClassName);
        return typeElement != null ? typeElement.asType() : null;

    }

    /**
     * Gets {@link TypeElement} for class.
     *
     * @param type the class to get the {@link TypeElement} for
     * @return The {@link TypeElement} that is related with the passed class or null if a TypeElement can't be found for passed class (f.e. if passed type represents an array)
     */
    public TypeElement getTypeElement(Class type) {

        TypeMirror typeMirror = getTypeMirror(type);
        return typeMirror == null ? null : (TypeElement) frameworkToolWrapper.getTypes().asElement(typeMirror);

    }

    /**
     * Gets {@link TypeMirror} for class.
     *
     * @param type the class to get the {@link TypeMirror} for
     * @return The {@link TypeMirror} that is related with the passed class
     */
    public TypeMirror getTypeMirror(Class type) {

        if (type == null) {
            return null;
        }

        if (type.isArray()) {
            return frameworkToolWrapper.getTypes().getArrayType(getTypeMirror(type.getComponentType()));
        }

        if (type.isPrimitive()) {
            return getPrimitiveTypeMirror(type);
        }
        return frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName()).asType();
    }

    public TypeMirror getPrimitiveTypeMirror(Class primitiveType) {

        if (primitiveType == null || !primitiveType.isPrimitive()) {
            return null;
        }

        if (boolean.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.BOOLEAN);
        } else if (byte.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.BYTE);
        } else if (char.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.CHAR);
        } else if (double.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.DOUBLE);
        } else if (float.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.FLOAT);
        } else if (int.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.INT);
        } else if (long.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.LONG);
        } else if (short.class.equals(primitiveType)) {
            return getTypes().getPrimitiveType(TypeKind.SHORT);
        }

        return null;
    }

    /**
     * Checks if passed typeElement is assignable to passed type
     *
     * @param typeElement the type element to check
     * @param type        the class which typeElement must assignable to
     * @return true if typeElement is assignable to type otherwise false.
     */
    public boolean isAssignableTo(TypeElement typeElement, Class type) {
        return isAssignableTo(typeElement, frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName()).asType());
    }

    /**
     * Checks if passed typeElement is assignable to passed typeMirror
     *
     * @param typeElement the type element to check
     * @param typeMirror  the type mirror which typeElement must assignable to
     * @return true if typeElement is assignable to typeMirror otherwise false.
     */
    public boolean isAssignableTo(TypeElement typeElement, TypeMirror typeMirror) {
        return typeElement != null && isAssignableTo(typeElement.asType(), typeMirror);
    }

    /**
     * Checks whether passed first {@link TypeElement} is assignable to passed second {@link TypeElement}.
     *
     * @param typeElement1 the type element to check
     * @param typeElement2 the type element which typeElement1 must be assignable to
     * @return true if typeElement1 is assignable to typeElement2 otherwise false.
     */
    public boolean isAssignableTo(TypeElement typeElement1, TypeElement typeElement2) {
        return isAssignableTo(typeElement1, typeElement2.asType());
    }

    public boolean isAssignableTo(TypeMirror typeMirror1, TypeMirror typeMirror2) {
        return typeMirror1 != null && typeMirror2 != null && frameworkToolWrapper.getTypes().isAssignable(typeMirror1, typeMirror2);
    }

    /**
     * Check if passed typeElement matches the passed type.
     *
     * @param typeElement the type element to check
     * @param type        the class which the typeElement must match
     * @return
     */

    public boolean isTypeEqual(TypeElement typeElement, Class type) {
        return isTypeEqual(typeElement, frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName()).asType());
    }


    public boolean isTypeEqual(TypeElement typeElement, TypeMirror typeMirror) {
        return typeElement == null || typeMirror == null ? false : frameworkToolWrapper.getTypes().isSameType(typeElement.asType(), typeMirror);
    }

    public boolean isTypeEqual(TypeElement typeElement1, TypeElement typeElement2) {
        return isTypeEqual(typeElement1, typeElement2.asType());
    }


    /**
     * Check if passed TypeMirror matches passed type.
     *
     * @param typeMirror the TypeMirror to check
     * @param type       the type in form of a Class to check for
     * @return true if passed type represents same type as passed type, otherwise false
     */
    public boolean isTypeEqual(TypeMirror typeMirror, Class type) {
        return typeMirror == null || typeMirror == null ? false : isTypeEqual(typeMirror, getTypeMirror(type));
    }

    /**
     * Checks whether both TypeMirror parameters represent the same type.
     * Generic type attributes will be taken into account during the comparison.
     *
     * @param typeMirror1
     * @param typeMirror2
     * @return true if both TypeMirrors represent the same type
     */
    public boolean isTypeEqual(TypeMirror typeMirror1, TypeMirror typeMirror2) {
        return typeMirror1 != null && typeMirror2 != null && getTypes().isSameType(typeMirror1, typeMirror2);
    }

    /**
     * Checks whether both erased TypeMirror parameters represent the same type.
     *
     * @param typeMirror1
     * @param typeMirror2
     * @return true if both TypeMirrors represent the same type
     */
    public boolean isErasedTypeEqual(TypeMirror typeMirror1, TypeMirror typeMirror2) {
        return typeMirror1 != null && typeMirror2 != null && getTypes().isSameType(getTypes().erasure(typeMirror1), getTypes().erasure(typeMirror2));
    }


    /**
     * Gets the component type of an array TypeMirror.
     *
     * @param typeMirror
     * @return returns the component TypeMirror of the passed array TypeMirror, returns null if passed TypeMirror isn't an array or null
     */
    public TypeMirror getArraysComponentType(TypeMirror typeMirror) {
        return typeMirror != null && checkTypeKind().isArray(typeMirror) ? ((ArrayType) typeMirror).getComponentType() : null;
    }

    /**
     * Checks whether passed {@link TypeMirror} is a void type or not.
     *
     * @param typeMirror the {@link TypeMirror} to check
     * @param type       the component type to check for
     * @return true id passed type mirror is of kind array with component type, otherwise false
     */
    public boolean isArrayOfType(TypeMirror typeMirror, Class type) {
        return type != null & isArrayOfType(typeMirror, getTypeMirror(type));
    }

    /**
     * Checks whether passed {@link TypeMirror} is a void type or not.
     *
     * @param typeMirror             the {@link TypeMirror} to check
     * @param fullQualifiedClassName the component type to check for
     * @return true id passed type mirror is of kind array with component type, otherwise false
     */
    public boolean isArrayOfType(TypeMirror typeMirror, String fullQualifiedClassName) {
        return fullQualifiedClassName != null & isArrayOfType(typeMirror, getTypeMirror(fullQualifiedClassName));
    }

    /**
     * Checks whether passed {@link TypeMirror} is a void type or not.
     *
     * @param typeMirror    the {@link TypeMirror} to check
     * @param componentType the arrays component type to check for
     * @return true id passed type mirror is of kind array with component type, otherwise false
     */
    public boolean isArrayOfType(TypeMirror typeMirror, TypeMirror componentType) {
        return typeMirror != null && componentType != null && checkTypeKind().isArray(typeMirror) && frameworkToolWrapper.getTypes().isSameType(getArraysComponentType(typeMirror), componentType);
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
