package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericTypeKind;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericTypeParameter;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericTypeWildcard;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

/**
 * Utility class and wrapper for / of {@link Types}.
 */
public final class TypeUtils {


    private final TypeRetrieval typeRetrieval = new TypeRetrieval();
    private final TypeComparison typeComparison = new TypeComparison();
    private final Arrays arrays = new Arrays();
    private final CheckTypeKind checkTypeKind = CheckTypeKind.INSTANCE;
    private final Generics generics = new Generics();


    /**
     * Hidden constructor.
     */
    private TypeUtils() {

    }


    /**
     * Embedded class for handling the TypeKind checks.
     */
    public static final class CheckTypeKind {

        public static final CheckTypeKind INSTANCE = new CheckTypeKind();

        /**
         * Hidden Constructor.
         */
        private CheckTypeKind() {

        }

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
         * Checks whether passed TypeMirror represents primitive type.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @return true if passed typeMirror has primitive TypeKind, otherwise false
         */
        public boolean isPrimitive(TypeMirror typeMirror) {
            return typeMirror != null && typeMirror.getKind().isPrimitive();
        }

        /**
         * Checks whether passed TypeMirror represents a declared class, interface or enum.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @return true if passed typeMirror has primitive TypeKind, otherwise false
         */
        public boolean isDeclared(TypeMirror typeMirror) {
            return isOfTypeKind(typeMirror, TypeKind.DECLARED);
        }


        /**
         * Checks whether passed TypeMirror represents an executable method, constructor or a (static) init block.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @return true if passed typeMirror has primitive TypeKind, otherwise false
         */
        public boolean isExecutable(TypeMirror typeMirror) {
            return isOfTypeKind(typeMirror, TypeKind.EXECUTABLE);
        }

        /**
         * Checks whether passed {@link TypeMirror} is of passed {@link TypeKind}.
         *
         * @param typeMirror
         * @param kind
         * @return
         */
        public boolean isOfTypeKind(TypeMirror typeMirror, TypeKind kind) {
            return typeMirror != null && kind != null && kind.equals(typeMirror.getKind());
        }


    }


    public final class TypeRetrieval {

        /**
         * Hidden Constructor.
         */
        private TypeRetrieval() {

        }

        /**
         * Gets a type element for a full qualified class name.
         *
         * @param fullQualifiedClassName
         * @return the type element for the passed full qualified class name or null if type element can't be found
         */
        public TypeElement getTypeElement(String fullQualifiedClassName) {

            if (fullQualifiedClassName == null) {
                return null;
            }

            return ToolingProvider.getTooling().getElements().getTypeElement(fullQualifiedClassName);

        }


        /**
         * Gets a type element for a TypeMirror.
         *
         * @param typeMirror
         * @return the type element for the passed full qualified class name or null if type element can't be found
         */
        public TypeElement getTypeElement(TypeMirror typeMirror) {

            if (typeMirror == null) {
                return null;
            }

            return getTypeElement(typeMirror.toString());

        }

        /**
         * Gets a TypeMirror for a full qualified class name.
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
         * @return The {@link TypeElement} that is related with the passed class or null if a TypeElement can't be found for passed class (f.e. for an array)
         */
        public TypeElement getTypeElement(Class type) {

            TypeMirror typeMirror = getTypeMirror(type);
            return typeMirror == null ? null : (TypeElement) ToolingProvider.getTooling().getTypes().asElement(typeMirror);

        }

        /**
         * Gets {@link TypeMirror} for class.
         *
         * @param type the class to get the {@link TypeMirror} for
         * @return The {@link TypeMirror} that is related with the passed class
         */
        public TypeMirror getTypeMirror(Class type) {

            // handle null safety and anonymous types for which we cannot get TypeMirror of a class
            if (type == null || type.isAnonymousClass()) {
                return null;
            }

            if (type.isArray()) {
                return ToolingProvider.getTooling().getTypes().getArrayType(getTypeMirror(type.getComponentType()));
            }

            if (type.isPrimitive()) {
                return getPrimitiveTypeMirror(type);
            }

            return ToolingProvider.getTooling().getElements().getTypeElement(type.getCanonicalName()).asType();
        }

        /**
         * Gets the {@link TypeMirror} for a passed primitive type.
         *
         * @param primitiveType the primitive type to get the type mirror for
         * @return null if passed primitive type is null or doesn't represent a primitive type, otherwise the type mirror of the primitive type.
         */
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
    }


    public final class TypeComparison {

        /**
         * Hidden Constructor.
         */
        private TypeComparison() {

        }

        /**
         * Checks if passed typeElement is assignable to passed type.
         *
         * @param typeElement the type element to check
         * @param type        the class which typeElement must assignable to
         * @return true if typeElement is assignable to type otherwise false.
         */
        public boolean isAssignableTo(TypeElement typeElement, Class type) {
            return isAssignableTo(typeElement, ToolingProvider.getTooling().getElements().getTypeElement(type.getCanonicalName()).asType());
        }

        /**
         * Checks if passed typeElement is assignable to passed typeMirror.
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


        /**
         * Checks whether passed first {@link TypeMirror} is assignable to passed second {@link TypeMirror}.
         *
         * @param typeMirror1 the type mirror to check
         * @param typeMirror2 the type mirror which typeMirror1 must be assignable to
         * @return true if typeMirror1 is assignable to typeMirror2 otherwise false.
         */
        public boolean isAssignableTo(TypeMirror typeMirror1, TypeMirror typeMirror2) {
            return typeMirror1 != null && typeMirror2 != null && ToolingProvider.getTooling().getTypes().isAssignable(typeMirror1, typeMirror2);
        }

        /**
         * Checks whether passed typeElement is assignable to passed genericType.
         *
         * @param typeElement the type element to check
         * @param genericType the genric type to check
         * @return true if typeElement is assignable to genericType otherwise false.
         */
        public boolean isAssignableTo(TypeElement typeElement, GenericType genericType) {
            return typeElement != null && genericType != null && isAssignableTo(typeElement.asType(), genericType);
        }

        /**
         * Checks whether passed typeElement is assignable to passed genericType.
         *
         * @param typeMirror  the type element to check
         * @param genericType the genric type to check
         * @return true if typeElement is assignable to genericType otherwise false.
         */
        public boolean isAssignableTo(TypeMirror typeMirror, GenericType genericType) {
            return typeMirror != null && genericType != null && generics.genericIsAssignableTo(typeMirror, genericType);
        }


        /**
         * Check if passed TypeElement matches the passed type.
         * Generic type parameters won't be taken into account during the comparison.
         *
         * @param typeElement the type element to check
         * @param type        the class which the typeElement must match
         * @return true if all parameters are not null, the TypeElement for passed class exists and types are equal, otherwise false
         */

        public boolean isTypeEqual(TypeElement typeElement, Class type) {

            if (typeElement == null || type == null) {
                return false;
            }

            TypeElement secondTypeElement = typeRetrieval.getTypeElement(type);
            return secondTypeElement != null && isErasedTypeEqual(typeElement.asType(), secondTypeElement.asType());
        }

        /**
         * Check if passed TypeElement matches the passed TypeElement.
         * Generic type attributes will be taken into account during the comparison.
         *
         * @param typeElement the type element to check
         * @param typeMirror  the TypeMirror which the typeElement must match
         * @return true if all parameters are not null and TypeMirrors are equal, otherwise false
         */
        public boolean isTypeEqual(TypeElement typeElement, TypeMirror typeMirror) {
            return typeElement != null && typeMirror != null && ToolingProvider.getTooling().getTypes().isSameType(typeElement.asType(), typeMirror);
        }

        /**
         * Check if passed TypeElement matches the passed TypeElement.
         * Generic type attributes will be taken into account during the comparison.
         *
         * @param typeElement1 the type element to check
         * @param typeElement2 the TypeElement which the typeElement must match
         * @return true if all parameters are not null and their TypeMirrors are equal, otherwise false
         */
        public boolean isTypeEqual(TypeElement typeElement1, TypeElement typeElement2) {
            return typeElement1 != null && typeElement2 != null && isTypeEqual(typeElement1, typeElement2.asType());
        }


        /**
         * Check if passed TypeMirror matches passed type.
         * Generic type parameters won't be taken into account during the comparison.
         *
         * @param typeMirror the TypeMirror to check
         * @param type       the type in form of a Class to check for
         * @return true if passed type represents same type as passed type, otherwise false
         */
        public boolean isTypeEqual(TypeMirror typeMirror, Class type) {
            return typeMirror != null && type != null && isErasedTypeEqual(typeMirror, typeRetrieval.getTypeMirror(type));
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
         * Checks whether passed TypeMirror and GenericType parameters represent the same type.
         * Generic type attributes will be taken into account during the comparison.
         *
         * @param typeMirror  the type mirror to chck
         * @param genericType the generic type to check against
         * @return true if both typeMirror and genericType represent the same type
         */
        public boolean isTypeEqual(TypeMirror typeMirror, GenericType genericType) {
            return generics.genericTypeEquals(typeMirror, genericType);
        }

        /**
         * Checks whether both erased TypeMirror parameters represent the same type.
         * (== raw types are equal)
         *
         * @param typeMirror1
         * @param typeMirror2
         * @return true if both TypeMirrors represent the same type
         */
        public boolean isErasedTypeEqual(TypeMirror typeMirror1, TypeMirror typeMirror2) {
            return typeMirror1 != null && typeMirror2 != null && getTypes().isSameType(getTypes().erasure(typeMirror1), getTypes().erasure(typeMirror2));
        }


    }


    public final class Arrays {

        /**
         * Hidden Constructor.
         */
        private Arrays() {

        }

        /**
         * Checks if passed TypeMirror is an array.
         *
         * @param typeMirror the TypeMirror to check
         * @return true if passed TypeMiroor is not null and of kind array.
         */
        public boolean isArray(TypeMirror typeMirror) {
            return typeMirror == null && typeMirror.getKind().equals(TypeKind.ARRAY);
        }

        /**
         * Gets the component type of an array TypeMirror.
         *
         * @param typeMirror
         * @return returns the component TypeMirror of the passed array TypeMirror, returns null if passed TypeMirror isn't an array or null
         */
        public TypeMirror getArraysComponentType(TypeMirror typeMirror) {
            return typeMirror != null && checkTypeKind.isArray(typeMirror) ? ((ArrayType) typeMirror).getComponentType() : null;
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with passed component type.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @param type       the component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, Class type) {
            return type != null && isArrayOfType(typeMirror, typeRetrieval.getTypeMirror(type));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with passed component type.
         *
         * @param typeMirror             the {@link TypeMirror} to check
         * @param fullQualifiedClassName the component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, String fullQualifiedClassName) {
            return fullQualifiedClassName != null && isArrayOfType(typeMirror, typeRetrieval.getTypeMirror(fullQualifiedClassName));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with passed component type.
         *
         * @param typeMirror    the {@link TypeMirror} to check
         * @param componentType the arrays component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, TypeMirror componentType) {
            return typeMirror != null
                    && componentType != null
                    && checkTypeKind.isArray(typeMirror)
                    && doTypeComparison().isTypeEqual(getArraysComponentType(typeMirror), componentType);
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with passed component type.
         *
         * @param typeMirror  the {@link TypeMirror} to check
         * @param genericType the arrays generic component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, GenericType genericType) {
            return typeMirror != null
                    && genericType != null
                    && checkTypeKind.isArray(typeMirror)
                    && doTypeComparison().isTypeEqual(getArraysComponentType(typeMirror), genericType);
        }


        /**
         * Checks whether passed {@link TypeMirror} is a an array with component type that is assignable to passed type.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @param type       the component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayAssignableTo(TypeMirror typeMirror, Class type) {
            return type != null && isArrayAssignableTo(typeMirror, typeRetrieval.getTypeMirror(type));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with component type that is assignable to passed type.
         *
         * @param typeMirror             the {@link TypeMirror} to check
         * @param fullQualifiedClassName the component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayAssignableTo(TypeMirror typeMirror, String fullQualifiedClassName) {
            return fullQualifiedClassName != null && isArrayAssignableTo(typeMirror, typeRetrieval.getTypeMirror(fullQualifiedClassName));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with component type that is assignable to passed type.
         *
         * @param typeMirror    the {@link TypeMirror} to check
         * @param componentType the arrays component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayAssignableTo(TypeMirror typeMirror, TypeMirror componentType) {
            return typeMirror != null
                    && componentType != null
                    && checkTypeKind.isArray(typeMirror)
                    && doTypeComparison().isAssignableTo(getArraysComponentType(typeMirror), componentType);
        }

        /**
         * Checks whether passed {@link TypeMirror} is a an array with component type that is assignable to passed type.
         *
         * @param typeMirror  the {@link TypeMirror} to check
         * @param genericType the arrays generic component type to check for
         * @return true if passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayAssignableTo(TypeMirror typeMirror, GenericType genericType) {
            return typeMirror != null
                    && genericType != null
                    && checkTypeKind.isArray(typeMirror)
                    && doTypeComparison().isAssignableTo(getArraysComponentType(typeMirror), genericType);
        }


    }


    public class Generics {

        public <T extends GenericTypeParameter> GenericType createGenericType(TypeMirror rawType, T... typeParameters) {
            return new GenericType(rawType, typeParameters);
        }

        public <T extends GenericTypeParameter> GenericType createGenericType(Class rawType, T... typeParameters) {
            return createGenericType(typeRetrieval.getTypeMirror(rawType), typeParameters);
        }

        public <T extends GenericTypeParameter> GenericType createGenericType(String rawType, T... typeParameters) {
            return createGenericType(typeRetrieval.getTypeMirror(rawType), typeParameters);
        }


        public GenericTypeWildcard createWildcardWithExtendsBound(GenericType extendsBound) {
            return GenericTypeWildcard.createExtendsWildcard(extendsBound);
        }

        public GenericTypeWildcard createWildcardWithSuperBound(GenericType superBound) {
            return GenericTypeWildcard.createSuperWildcard(superBound);
        }

        public GenericTypeWildcard createPureWildcard() {
            return GenericTypeWildcard.createPureWildcard();
        }


        public <T extends GenericTypeParameter> boolean genericTypeEquals(TypeMirror typeMirror, GenericType genericType) {

            if (typeMirror == null || genericType == null) {
                return false;
            }

            return compareGenericTypesRecursively(typeMirror, genericType);


        }

        private boolean compareGenericTypesRecursively(TypeMirror typeMirror, GenericType genericType) {


            TypeMirror typeMirrorToCompareWith = genericType.getRawType();
            if (typeMirrorToCompareWith == null) {
                return false;
            }

            // Compare raw types - this will not work for super wildcard type since it has Object as raw type
            if (!typeComparison.isErasedTypeEqual(typeMirror, typeMirrorToCompareWith)) {
                return false;
            }

            // Compare typeParameters
            if (!(typeMirror instanceof DeclaredType)) {
                return false;
            }

            DeclaredType tmDeclaredType = (DeclaredType) typeMirror;

            // check if number of type parameters is matching
            if (tmDeclaredType.getTypeArguments().size() != genericType.getTypeParameters().length) {
                return false;
            }


            // Check type parameters
            if (genericType.getTypeParameters().length > 0) {

                // Now check type parameters recursively
                for (int i = 0; i < genericType.getTypeParameters().length; i++) {

                    TypeMirror currentTypeParameter = tmDeclaredType.getTypeArguments().get(i);
                    GenericTypeParameter currentGenericTypeParameter = genericType.getTypeParameters()[i];

                    if (currentTypeParameter instanceof WildcardType) {

                        // check wildcard type
                        if (!compareGenericTypeWildcardRecursively((WildcardType) currentTypeParameter, currentGenericTypeParameter)) {
                            return false;
                        }


                    } else if (currentTypeParameter instanceof DeclaredType) {


                        if (!compareGenericTypeDeclaredTypeRecursively((DeclaredType) currentTypeParameter, currentGenericTypeParameter)) {
                            return false;
                        }

                    }


                }

            }

            return true;

        }


        protected boolean compareGenericTypeWildcardRecursively(WildcardType wildcardType, GenericTypeParameter genericTypeParameter) {

            if (genericTypeParameter.getType() != GenericTypeKind.WILDCARD) {
                return false;
            } else {

                GenericTypeWildcard wc = (GenericTypeWildcard) genericTypeParameter;

                if (wc.isPureWildcard()) {

                    if (wildcardType.getExtendsBound() != null && wildcardType.getSuperBound() != null) {
                        return false;
                    }

                } else {

                    if (wildcardType.getExtendsBound() != null) {
                        if (!wc.hasExtendsBound() || !compareGenericTypesRecursively(wildcardType.getExtendsBound(), wc.getExtendsBound())) {
                            return false;
                        }
                    } else {
                        if (wc.hasExtendsBound()) {
                            return false;
                        }
                    }


                    if (wildcardType.getSuperBound() != null) {
                        if (!wc.hasSuperBound() || !compareGenericTypesRecursively(wildcardType.getSuperBound(), wc.getSuperBound())) {
                            return false;
                        }
                    } else {
                        if (wc.hasSuperBound()) {
                            return false;
                        }
                    }
                }

            }

            return true;
        }

        private boolean compareGenericTypeDeclaredTypeRecursively(DeclaredType declaredType, GenericTypeParameter genericTypeParameter) {

            if (genericTypeParameter == null || genericTypeParameter.getType() != GenericTypeKind.GENERIC_TYPE) {
                return false;
            } else {

                GenericType genericType = (GenericType) genericTypeParameter;

                if (declaredType != null && !compareGenericTypesRecursively(declaredType, genericType)) {
                    return false;
                }


            }

            return true;
        }


        /**
         * Checks whether passed typeElement is assignable to passed genericType.
         *
         * @param typeElement the type element to check
         * @param genericType the genric type to check
         * @return true if typeElement is assignable to genericType otherwise false.
         */
        public boolean genericIsAssignableTo(TypeElement typeElement, GenericType genericType) {
            return typeElement != null && genericType != null && genericIsAssignableTo(typeElement.asType(), genericType);
        }

        /**
         * Checks whether passed typeElement is assignable to passed genericType.
         *
         * @param typeMirror  the type element to check
         * @param genericType the genric type to check
         * @return true if typeElement is assignable to genericType otherwise false.
         */
        public boolean genericIsAssignableTo(TypeMirror typeMirror, GenericType genericType) {

            if (typeMirror == null || genericType == null) {
                return false;
            }

            TypeMirror typeMirrorToCompareWith = genericType.getRawType();
            if (typeMirrorToCompareWith == null) {
                return false;
            }

            // Compare raw types - this will not work for super wildcard type since it has Object as raw type
            if (!typeComparison.isAssignableTo(getTypes().erasure(typeMirror), getTypes().erasure(typeMirrorToCompareWith))) {
                return false;
            }

            return isAssignableToGenericTypeRecursively(typeMirror, genericType);
        }


        private boolean isAssignableToGenericTypeRecursively(TypeMirror typeMirror, GenericType genericType) {


            // Check type parameters
            if (genericType.getTypeParameters().length > 0) {

                // Compare typeParameters
                if (!(typeMirror instanceof DeclaredType)) {
                    return false;
                }

                DeclaredType tmDeclaredType = (DeclaredType) typeMirror;

                // check if number of type parameters is matching
                if (tmDeclaredType.getTypeArguments().size() != genericType.getTypeParameters().length) {
                    return false;
                }

                // Now check type parameters recursively
                for (int i = 0; i < genericType.getTypeParameters().length; i++) {

                    TypeMirror currentTypeParameter = tmDeclaredType.getTypeArguments().get(i);
                    GenericTypeParameter currentGenericTypeParameter = genericType.getTypeParameters()[i];

                    if (currentTypeParameter instanceof WildcardType) {

                        // check wildcard type
                        if (!isAssignableToGenericTypeHandleWildcardTypeRecursively((WildcardType) currentTypeParameter, currentGenericTypeParameter)) {
                            return false;
                        }


                    } else if (currentTypeParameter instanceof DeclaredType) {


                        if (!isAssignableToGenericTypeHandleDeclaredTypeRecursively((DeclaredType) currentTypeParameter, currentGenericTypeParameter)) {
                            return false;
                        }

                    }


                }

            }

            return true;

        }


        protected boolean isAssignableToGenericTypeHandleWildcardTypeRecursively(WildcardType wildcardType, GenericTypeParameter genericTypeParameter) {


            if (wildcardType.getExtendsBound() == null && wildcardType.getSuperBound() == null) {

                // handle pure wildcards - pure wildcard is only assignable to pure wildcard
                if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD && ((GenericTypeWildcard) genericTypeParameter).isPureWildcard()) {
                    return true;
                }

                return false;


            } else if (wildcardType.getExtendsBound() != null) {

                // handle extends wildcards - only EXT => EXT is may be applicable
                if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD && ((GenericTypeWildcard) genericTypeParameter).isPureWildcard()) {
                    return true;
                } else if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD && ((GenericTypeWildcard) genericTypeParameter).hasExtendsBound()) {

                    GenericTypeWildcard wildcard = (GenericTypeWildcard) genericTypeParameter;

                    if (!typeComparison.isAssignableTo(
                            getTypes().erasure(wildcardType.getExtendsBound()),
                            getTypes().erasure(wildcard.getExtendsBound().getRawType()))) {
                        return false;
                    }

                    return isAssignableToGenericTypeRecursively(wildcardType.getExtendsBound(), wildcard.getExtendsBound());

                } else {
                    return false;
                }


            } else if (wildcardType.getSuperBound() != null) {

                // handle super wildcards - only SUPER => SUPER is may be applicable
                if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD && ((GenericTypeWildcard) genericTypeParameter).isPureWildcard()) {
                    return true;
                } else if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD && ((GenericTypeWildcard) genericTypeParameter).hasSuperBound()) {

                    GenericTypeWildcard wildcard = (GenericTypeWildcard) genericTypeParameter;

                    if (!typeComparison.isAssignableTo(
                            getTypes().erasure(wildcard.getSuperBound().getRawType()),
                            getTypes().erasure(wildcardType.getSuperBound()))) {
                        return false;
                    }

                    return isAssignableToGenericTypeRecursively(wildcardType.getSuperBound(), wildcard.getSuperBound());

                } else {
                    return false;
                }


            }


            return true;
        }

        private boolean isAssignableToGenericTypeHandleDeclaredTypeRecursively(DeclaredType declaredType, GenericTypeParameter genericTypeParameter) {

            if (genericTypeParameter.getType() == GenericTypeKind.GENERIC_TYPE) {

                // RAW - RAW must have exactly the same type
                GenericType genericType = (GenericType) genericTypeParameter;
                if (!typeComparison.isErasedTypeEqual(getTypes().erasure(declaredType), getTypes().erasure(genericType.getRawType()))) {
                    return false;
                }

                // Now compare it's type parameters
                return isAssignableToGenericTypeRecursively(declaredType, genericType);

            } else if (genericTypeParameter.getType() == GenericTypeKind.WILDCARD) {

                GenericTypeWildcard wildcard = (GenericTypeWildcard) genericTypeParameter;

                if (wildcard.isPureWildcard()) {

                    return true;

                } else if (wildcard.hasSuperBound()) {

                    // GenericTypes super bound type mirror must be assignable to TypeMirrors super bound
                    if (!typeComparison.isAssignableTo(getTypes().erasure(wildcard.getSuperBound().getRawType()), getTypes().erasure(declaredType))) {
                        return false;
                    }

                    // Now compare it's type parameters
                    return isAssignableToGenericTypeRecursively(declaredType, wildcard.getSuperBound());

                } else if (wildcard.hasExtendsBound()) {

                    // type mirrors extends bound type mirror must be assignable to GenericTypes extends bound
                    if (!typeComparison.isAssignableTo(getTypes().erasure(declaredType), getTypes().erasure(wildcard.getExtendsBound().getRawType()))) {
                        return false;
                    }

                    // Now compare it's type parameters
                    return isAssignableToGenericTypeRecursively(declaredType, wildcard.getExtendsBound());

                }


            }

            return true;
        }


    }


    /**
     * Gets the wrapped {@link Types} instance.
     *
     * @return
     */
    public Types getTypes() {
        return ToolingProvider.getTooling().getTypes();
    }

    public TypeRetrieval doTypeRetrieval() {
        return typeRetrieval;
    }

    public TypeComparison doTypeComparison() {
        return typeComparison;
    }

    public Arrays doArrays() {
        return arrays;
    }

    public CheckTypeKind doCheckTypeKind() {
        return checkTypeKind;
    }

    public Generics doGenerics() {
        return generics;
    }

    /**
     * Gets an instance of this TypeUtils class.
     *
     * @return the type utils instance
     */
    public static TypeUtils getTypeUtils() {
        return new TypeUtils();
    }


}
