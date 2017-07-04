package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericType;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericTypeKind;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericTypeType;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericTypeWildcard;

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
public class TypeUtils {

    protected final FrameworkToolWrapper frameworkToolWrapper;

    public final TypeRetrieval TYPE_RETRIEVAL = new TypeRetrieval();
    public final TypeComparison TYPE_COMPARISON = new TypeComparison();
    public final Arrays ARRAYS = new Arrays();
    public final CheckTypeKind CHECK_TYPE_KIND = CheckTypeKind.INSTANCE;
    public final Generics GENERICS = new Generics();


    private TypeUtils(FrameworkToolWrapper frameworkToolWrapper) {
        this.frameworkToolWrapper = frameworkToolWrapper;
    }


    /**
     * Embedded class for handling the TypeKind checks.
     */
    public final static class CheckTypeKind {

        public final static CheckTypeKind INSTANCE = new CheckTypeKind();

        /**
         * Hidden Constructor
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


    public class TypeRetrieval {

        /**
         * Hidden Constructor
         */
        private TypeRetrieval() {

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

            // handle null safety and anonymous types for which we cannot get TypeMirror of a class
            if (type == null || type.isAnonymousClass()) {
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


    public class TypeComparison {

        /**
         * Hidden Constructor
         */
        private TypeComparison() {

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
         * Check if passed TypeElement matches the passed type.
         *
         * @param typeElement the type element to check
         * @param type        the class which the typeElement must match
         * @return true if all parameters are not null, the TypeElement for passed class exists and types are equal, otherwise false
         */

        public boolean isTypeEqual(TypeElement typeElement, Class type) {

            if (typeElement == null || type == null) {
                return false;
            }

            TypeElement _2ndTypeElement = frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName());
            return _2ndTypeElement != null && isTypeEqual(typeElement, _2ndTypeElement.asType());
        }

        /**
         * Check if passed TypeElement matches the passed TypeElement.
         *
         * @param typeElement the type element to check
         * @param typeMirror  the TypeMirror which the typeElement must match
         * @return true if all parameters are not null and TypeMirrors are equal, otherwise false
         */
        public boolean isTypeEqual(TypeElement typeElement, TypeMirror typeMirror) {
            return typeElement != null && typeMirror != null && frameworkToolWrapper.getTypes().isSameType(typeElement.asType(), typeMirror);
        }

        /**
         * Check if passed TypeElement matches the passed TypeElement.
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
         *
         * @param typeMirror the TypeMirror to check
         * @param type       the type in form of a Class to check for
         * @return true if passed type represents same type as passed type, otherwise false
         */
        public boolean isTypeEqual(TypeMirror typeMirror, Class type) {
            return typeMirror != null && type != null && isTypeEqual(typeMirror, TYPE_RETRIEVAL.getTypeMirror(type));
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


    }


    public class Arrays {

        /**
         * Hidden Constructor
         */
        private Arrays() {

        }

        /**
         * Gets the component type of an array TypeMirror.
         *
         * @param typeMirror
         * @return returns the component TypeMirror of the passed array TypeMirror, returns null if passed TypeMirror isn't an array or null
         */
        public TypeMirror getArraysComponentType(TypeMirror typeMirror) {
            return typeMirror != null && CHECK_TYPE_KIND.isArray(typeMirror) ? ((ArrayType) typeMirror).getComponentType() : null;
        }

        /**
         * Checks whether passed {@link TypeMirror} is a void type or not.
         *
         * @param typeMirror the {@link TypeMirror} to check
         * @param type       the component type to check for
         * @return true id passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, Class type) {
            return type != null & isArrayOfType(typeMirror, TYPE_RETRIEVAL.getTypeMirror(type));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a void type or not.
         *
         * @param typeMirror             the {@link TypeMirror} to check
         * @param fullQualifiedClassName the component type to check for
         * @return true id passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, String fullQualifiedClassName) {
            return fullQualifiedClassName != null & isArrayOfType(typeMirror, TYPE_RETRIEVAL.getTypeMirror(fullQualifiedClassName));
        }

        /**
         * Checks whether passed {@link TypeMirror} is a void type or not.
         *
         * @param typeMirror    the {@link TypeMirror} to check
         * @param componentType the arrays component type to check for
         * @return true id passed type mirror is of kind array with component type, otherwise false
         */
        public boolean isArrayOfType(TypeMirror typeMirror, TypeMirror componentType) {
            return typeMirror != null && componentType != null && CHECK_TYPE_KIND.isArray(typeMirror) && frameworkToolWrapper.getTypes().isSameType(getArraysComponentType(typeMirror), componentType);
        }


    }


    public class Generics {

        private GenericType castToDeclaredType(GenericTypeType instance) {
            if (instance != null && instance instanceof GenericType) {
                return (GenericType) instance;
            }
            return null;
        }

        private GenericType castToWildcard(GenericTypeType instance) {
            if (instance != null && instance instanceof GenericType) {
                return (GenericType) instance;
            }
            return null;
        }

        public <T extends GenericTypeType> GenericType createGenericType(TypeMirror rawType, T... typeParameters) {
            return new GenericType(rawType, typeParameters);
        }

        public <T extends GenericTypeType> GenericType createGenericType(Class rawType, T... typeParameters) {
            return createGenericType(TYPE_RETRIEVAL.getTypeMirror(rawType), typeParameters);
        }

        public <T extends GenericTypeType> GenericType createGenericType(String rawType, T... typeParameters) {
            return createGenericType(TYPE_RETRIEVAL.getTypeMirror(rawType), typeParameters);
        }

        public GenericTypeWildcard createWildcard(GenericType superBound, GenericType extendsBound) {
            return new GenericTypeWildcard(superBound, extendsBound);
        }

        public GenericTypeWildcard createWildcardWithExtendsBound(GenericType extendsBound) {
            return createWildcard(null, extendsBound);
        }

        public GenericTypeWildcard createWildcardWithSuperBound(GenericType superBound) {
            return createWildcard(superBound, null);
        }

        public GenericTypeWildcard createWildcardPure() {
            return createWildcard(null, null);
        }


        public <T extends GenericTypeType> boolean genericTypeEquals(TypeMirror typeMirror, GenericType genericType) {

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
            if (!TYPE_COMPARISON.isErasedTypeEqual(typeMirror, typeMirrorToCompareWith)) {
                return false;
            }


            // Check type parameters
            if (genericType.typeParameters.length > 0) {

                // Compare typeParameters
                if (!(typeMirror instanceof DeclaredType)) {
                    return false;
                }

                DeclaredType tmDeclaredType = (DeclaredType) typeMirror;

                // check if number of type parameters is matching
                if (tmDeclaredType.getTypeArguments().size() != genericType.typeParameters.length) {
                    return false;
                }

                // Now check type parameters recursively
                for (int i = 0; i < genericType.getTypeParameters().length; i++) {

                    TypeMirror currentTypeParameter = tmDeclaredType.getTypeArguments().get(i);
                    GenericTypeType currentGenericTypeType = genericType.getTypeParameters()[i];

                    if (currentTypeParameter instanceof WildcardType) {

                        // check wildcard type
                        if (!compareGenericTypeWildcardRecursively((WildcardType) currentTypeParameter, currentGenericTypeType)) {
                            return false;
                        }


                    } else if (currentTypeParameter instanceof DeclaredType) {


                        if (!compareGenericTypeDeclaredTypeRecursively((DeclaredType) currentTypeParameter, currentGenericTypeType)) {
                            return false;
                        }

                    }


                }

            }

            return true;

        }


        protected boolean compareGenericTypeWildcardRecursively(WildcardType wildcardType, GenericTypeType genericTypeType) {

            if (genericTypeType.getType() != GenericTypeKind.WILDCARD) {
                return false;
            } else {

                GenericTypeWildcard wc = (GenericTypeWildcard) genericTypeType;

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

        private boolean compareGenericTypeDeclaredTypeRecursively(DeclaredType declaredType, GenericTypeType genericTypeType) {

            if (genericTypeType.getType() != GenericTypeKind.DECLARED_TYPE) {
                return false;
            } else {

                GenericType genericType = (GenericType) genericTypeType;

                if (declaredType != null && genericType != null && !compareGenericTypesRecursively(declaredType, genericType)) {
                    return false;
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
