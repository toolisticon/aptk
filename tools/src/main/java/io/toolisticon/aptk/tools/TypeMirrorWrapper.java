package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a convenience wrapper for TypeMirrors.
 * This is extremely useful for template based code generation and doing programmatically checks.
 */
public class TypeMirrorWrapper {

    private final TypeMirror typeMirror;

    /**
     * Constructor.
     *
     * @param typeMirror the TypeMirror instance to wrap - must not be null
     * @throws IllegalArgumentException if passed typeMirror is null.
     */
    TypeMirrorWrapper(TypeMirror typeMirror) {
        if (typeMirror == null) {
            throw new IllegalArgumentException("Passed typeMirror must not be null");
        }
        this.typeMirror = typeMirror;
    }

    /**
     * Returns the erasure of a type.
     * Strips all generic information from type mirror and returns TypeMirror of raw type.
     *
     * @return the erasure of the type
     */
    public TypeMirrorWrapper erasure() {
        return TypeMirrorWrapper.wrap(TypeUtils.getTypes().erasure(this.typeMirror));
    }


    /**
     * Gets the kind of the wrapped TypeMirror.
     *
     * @return the kind of the wrapped TypeMirror
     */
    public TypeKind getKind() {
        return this.typeMirror.getKind();
    }

    /**
     * Checks if wrapped TypeMirror is a primitive type.
     *
     * @return true if wrapped TypeMirror is primitive, otherwise false
     */
    public boolean isPrimitive() {
        return isPrimitive(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a primitive type.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is primitive, otherwise false
     */
    public static boolean isPrimitive(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isPrimitive(typeMirror);
    }


    /**
     * Gets wrapped TypeMirror as a PrimitiveType
     *
     * @return the wrapped TypeMirror cast to a PrimitiveType, or null if TypeMirror does not represent a primitive type.
     */
    public PrimitiveType getPrimitiveType() {
        return getPrimitiveType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a PrimitiveType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a PrimitiveType, or null if TypeMirror does not represent a primitive type.
     */
    public static PrimitiveType getPrimitiveType(TypeMirror typeMirror) {
        return isPrimitive(typeMirror) ? (PrimitiveType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is a TypeVar type.
     *
     * @return true if wrapped TypeMirror is a TypeVar, otherwise false
     */
    public boolean isTypeVar() {
        return isTypeVar(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a primitive type.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is TypeVar, otherwise false
     */
    public static boolean isTypeVar(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isTypeVar(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a TypeVariable
     *
     * @return the wrapped TypeMirror cast to a TypeVariable, or null if TypeMirror does not represent a primitive type.
     */
    public TypeVariable getTypeVar() {
        return getTypeVar(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a TypeVariable
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a TypeVariable, or null if TypeMirror does not represent a TypeVariable type.
     */
    public static TypeVariable getTypeVar(TypeMirror typeMirror) {
        return isTypeVar(typeMirror) ? (TypeVariable) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror represents an interface.
     *
     * @return true if wrapped TypeMirror represents an interface, otherwise false
     */
    public boolean isInterface() {
        return isInterface(this.typeMirror);
    }


    /**
     * Checks if wrapped TypeMirror represents an interface.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror represents a Class, otherwise false
     */
    public static boolean isInterface(TypeMirror typeMirror) {
        Optional<TypeElementWrapper> typeMirrorWrapper = getTypeElement(typeMirror);
        return typeMirrorWrapper.isPresent() && typeMirrorWrapper.get().isInterface();
    }

    /**
     * Checks if wrapped TypeMirror represents an enum.
     *
     * @return true if wrapped TypeMirror represents an enum, otherwise false
     */
    public boolean isEnum() {
        return isEnum(typeMirror);
    }

    /**
     * Checks if passed TypeMirror represents an enum.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror represents an enum, otherwise false
     */
    public static boolean isEnum(TypeMirror typeMirror) {
        Optional<TypeElementWrapper> typeMirrorWrapper = getTypeElement(typeMirror);
        return typeMirrorWrapper.isPresent() && typeMirrorWrapper.get().isEnum();
    }

    /**
     * Checks if wrapped TypeMirror represents a Class.
     *
     * @return true if wrapped TypeMirror represents a Class, otherwise false
     */
    public boolean isClass() {
        return isClass(typeMirror);
    }

    /**
     * Checks if passed TypeMirror represents a Class.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror represents a Class, otherwise false
     */
    public static boolean isClass(TypeMirror typeMirror) {
        Optional<TypeElementWrapper> typeMirrorWrapper = getTypeElement(typeMirror);
        return typeMirrorWrapper.isPresent() && typeMirrorWrapper.get().isClass();
    }

    /**
     * Checks if wrapped TypeMirror represents an annotation.
     *
     * @return true if wrapped TypeMirror represents an annotation, otherwise false
     */
    public boolean isAnnotation() {
        return isAnnotation(typeMirror);
    }

    /**
     * Checks if passed TypeMirror represents an annotation.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror represents an annotation, otherwise false
     */
    public static boolean isAnnotation(TypeMirror typeMirror) {
        Optional<TypeElementWrapper> typeMirrorWrapper = getTypeElement(typeMirror);
        return typeMirrorWrapper.isPresent() && typeMirrorWrapper.get().isAnnotation();
    }

    /**
     * Checks if wrapped TypeMirror represents a Collection.
     *
     * @return true if wrapped TypeMirror represents a Collection, otherwise false
     */
    public boolean isCollection() {
        return isCollection(typeMirror);
    }

    /**
     * Checks if passed TypeMirror represents a Collection.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror represents a Collection, otherwise false
     */
    public static boolean isCollection(TypeMirror typeMirror) {

        return isDeclared(typeMirror) && TypeUtils.TypeComparison.isAssignableTo(TypeUtils.getTypes().erasure(typeMirror), TypeUtils.TypeRetrieval.getTypeMirror(Collection.class));

    }

    /**
     * Checks if wrapped TypeMirror represents an Iterable.
     *
     * @return true if wrapped TypeMirror represents an Iterable, otherwise false
     */
    public boolean isIterable() {
        return isIterable(typeMirror);
    }

    /**
     * Checks if passed TypeMirror represents an Iterable.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror represents an Iterable, otherwise false
     */
    public static boolean isIterable(TypeMirror typeMirror) {

        return isDeclared(typeMirror) && TypeUtils.TypeComparison.isAssignableTo(TypeUtils.getTypes().erasure(typeMirror), TypeUtils.TypeRetrieval.getTypeMirror(Iterable.class));

    }


    /**
     * Checks if wrapped TypeMirror is an array.
     *
     * @return true if wrapped TypeMirror is an array, otherwise false
     */
    public boolean isArray() {
        return isArray(typeMirror);
    }

    /**
     * Checks if passed TypeMirror is an array.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror is an array, otherwise false
     */
    public static boolean isArray(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isArray(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a ArrayType
     *
     * @return the wrapped TypeMirror cast to a ArrayType, or null if ArrayType does not represent an array type.
     */
    public ArrayType getArrayType() {
        return getArrayType(typeMirror);
    }

    /**
     * Gets passed TypeMirror as a ArrayType
     *
     * @param typeMirror the TypeMirror to check
     * @return the passed TypeMirror cast to an ArrayType, or null if TypeMirror does not represent an array type.
     */
    public static ArrayType getArrayType(TypeMirror typeMirror) {
        return isArray(typeMirror) ? (ArrayType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is a DeclaredType.
     *
     * @return true if wrapped TypeMirror is a DeclaredType, otherwise false
     */
    public boolean isDeclared() {
        return isDeclared(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a DeclaredType.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is a DeclaredType, otherwise false
     */
    public static boolean isDeclared(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isDeclared(typeMirror);
    }

    /**
     * Gets passed TypeMirror as a DeclaredType
     *
     * @return the passed TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public DeclaredType getDeclaredType() {
        return getDeclaredType(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public static DeclaredType getDeclaredType(TypeMirror typeMirror) {
        return isDeclared(typeMirror) ? (DeclaredType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror  is the void type.
     *
     * @return true if wrapped TypeMirror is the void type, otherwise false
     */
    public boolean isVoidType() {
        return isVoidType(typeMirror);
    }

    /**
     * Checks if passed TypeMirror is the void type.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror is the void type, otherwise false
     */
    public static boolean isVoidType(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isVoid(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is a Wildcard type.
     *
     * @return true if wrapped TypeMirror is a Wildcard type, otherwise false
     */
    public boolean isWildcardType() {
        return isWildcardType(typeMirror);
    }

    /**
     * Checks if passed TypeMirror is a WildcardTYpe.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror is a WildcardTYpe, otherwise false
     */
    public static boolean isWildcardType(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isWildcard(typeMirror);
    }

    /**
     * Gets wrapped TypeMirror as a WildcardTYpe
     *
     * @return the wrapped TypeMirror cast to a WildcardTYpe, or null if TypeMirror does not represent a wildcard type.
     */
    public WildcardType getWildcardType() {
        return getWildcardType(typeMirror);
    }


    /**
     * Gets wrapped TypeMirror as a DeclaredType
     *
     * @param typeMirror the TypeMirror to check
     * @return the wrapped TypeMirror cast to a DeclaredType, or null if TypeMirror does not represent a declared type.
     */
    public static WildcardType getWildcardType(TypeMirror typeMirror) {
        return isWildcardType(typeMirror) ? (WildcardType) typeMirror : null;
    }

    /**
     * Checks if wrapped TypeMirror is of kind ERROR.
     *
     * @return true if wrapped TypeMirror is of kind ERROR, otherwise false
     */
    public boolean isErrorType() {
        return isErrorType(typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is of kind ERROR.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if wrapped TypeMirror is of kind ERROR, otherwise false
     */
    public static boolean isErrorType(TypeMirror typeMirror) {
        return TypeUtils.CheckTypeKind.isError(typeMirror);
    }

    /**
     * Checks if TypeMirror has component type.
     *
     * @return true if the TypeMirror represents either an array or a Collection, otherwise false.
     */
    public boolean hasComponentType() {
        return hasComponentType(typeMirror);
    }

    /**
     * Checks if TypeMirror has component type.
     *
     * @param typeMirror the TypeMirror to check
     * @return true if the TypeMirror represents either an array or a Collection, otherwise false.
     */
    public static boolean hasComponentType(TypeMirror typeMirror) {
        return getComponentType(typeMirror) != null;
    }

    /**
     * Gets the ComponentType of TypeMirror representing an array, a Collection or an Iterable.
     * Will return TypeMirror of Object if Collections/Iterables component type isn't explicitly set.
     *
     * @return The component TypeMirror when passed typeMirror represents an array or collection, otherwise null.
     */
    public TypeMirror getComponentType() {
        return getComponentType(typeMirror);
    }

    /**
     * Gets the ComponentType of TypeMirror representing an array, a Collection or an Iterable.
     * Will return TypeMirror of Object if Collections/Iterablea component type isn't explicitly set.
     *
     * @param typeMirror the TypeMirror to check
     * @return The component TypeMirror when passed typeMirror represents an array, a Collection or Iterable, otherwise null.
     */
    public static TypeMirror getComponentType(TypeMirror typeMirror) {

        if (isArray(typeMirror)) {
            return ((ArrayType) typeMirror).getComponentType();
        }

        if (isCollection(typeMirror) || isIterable(typeMirror)) {
            if (hasTypeArguments(typeMirror)) {

                List<? extends TypeMirror> typeArgumentTypeMirrors = getTypeArguments(typeMirror);
                return typeArgumentTypeMirrors.get(0);

            } else {
                return TypeUtils.TypeRetrieval.getTypeMirror(Object.class);
            }
        }

        return null;
    }

    /**
     * Gets the wrapped component type of TypeMirror representing an array or a collection
     *
     * @return The component TypeMirror when passed typeMirror represents an array or a collection, otherwise null.
     */
    public TypeMirrorWrapper getWrappedComponentType() {
        return getWrappedComponentType(typeMirror);
    }

    /**
     * Gets the wrapped Component type of TypeMirror representing an array or collection
     *
     * @param typeMirror the TypeMirror to check
     * @return The component TypeMirror when passed typeMirror represents an array or a collection, otherwise null.
     */
    public static TypeMirrorWrapper getWrappedComponentType(TypeMirror typeMirror) {
        TypeMirror componentTypeMirror = getComponentType(typeMirror);
        return componentTypeMirror != null ? TypeMirrorWrapper.wrap(componentTypeMirror) : null;
    }

    /**
     * Check if wrapped TypeMirror has TypeArguments
     *
     * @return true if wrapped TypeMirror is a DeclaredType and has type arguments, otherwise false.
     */
    public boolean hasTypeArguments() {
        return hasTypeArguments(typeMirror);
    }

    /**
     * Check if passed TypeMirror has TypeArguments
     *
     * @param typeMirror the TypeMirror to check
     * @return true if passed TypeMirror is a DeclaredType and has type arguments, otherwise false.
     */
    public static boolean hasTypeArguments(TypeMirror typeMirror) {
        return isDeclared(typeMirror) && getDeclaredType(typeMirror).getTypeArguments().size() > 0;
    }

    /**
     * Get type arguments.
     *
     * @return a List containing TypeMirrors for all type arguments if passed typeMirror is a declared type with type arguments, otherwise null.
     */
    public List<? extends TypeMirror> getTypeArguments() {
        return getTypeArguments(typeMirror);
    }

    /**
     * Get type arguments.
     *
     * @param typeMirror the TypeMirror to check
     * @return a List containing TypeMirrorWrappers for all type arguments if passed typeMirror is a declared type with type arguments, otherwise null.
     */
    public static List<? extends TypeMirror> getTypeArguments(TypeMirror typeMirror) {

        if (isDeclared(typeMirror) && hasTypeArguments(typeMirror)) {
            return getDeclaredType(typeMirror).getTypeArguments();
        }

        return null;
    }


    /**
     * Check if passed TypeMirror has TypeArguments
     *
     * @return a List containing TypeMirrorWrappers for all type arguments if passed typeMirror is a declared type with type arguments, otherwise null.
     */
    public List<TypeMirrorWrapper> getWrappedTypeArguments() {
        return getWrappedTypeArguments(typeMirror);
    }

    /**
     * Check if passed TypeMirror has TypeArguments
     *
     * @param typeMirror the TypeMirror to check
     * @return a List containing TypeMirrorWrappers for all type arguments if passed typeMirror is a declared type with type arguments, otherwise null.
     */
    public static List<TypeMirrorWrapper> getWrappedTypeArguments(TypeMirror typeMirror) {

        List<? extends TypeMirror> typeArguments = getTypeArguments(typeMirror);

        if (typeArguments != null) {
            List<TypeMirrorWrapper> wrappedTypeArguments = new ArrayList<>();
            for (TypeMirror typeArgumentTypeMirror : typeArguments) {
                wrappedTypeArguments.add(TypeMirrorWrapper.wrap(typeArgumentTypeMirror));
            }
            return wrappedTypeArguments;
        }

        return null;
    }

    /**
     * Gets the package of the wrapped TypeMirror
     *
     * @return the qualified package name if wrapped TypeMirror is a DeclaredType or an array, otherwise null;
     */
    public String getPackage() {
        return getPackage(typeMirror);
    }

    /**
     * Gets the package of the passed TypeMirror
     *
     * @param typeMirror the TypeMirror to check
     * @return the qualified package name if passed TypeMirror is a DeclaredType or an array, otherwise null;
     */
    public static String getPackage(TypeMirror typeMirror) {

        if (isArray(typeMirror)) {
            return getPackage(new TypeMirrorWrapper(typeMirror).getComponentType());
        }

        if (isDeclared(typeMirror)) {
            DeclaredType declaredType = (DeclaredType) typeMirror;

            // get enclosing Package
            return ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(declaredType.asElement(), ElementKind.PACKAGE).getQualifiedName().toString();

        }

        // return null for primitive type mirrors and all other kinds
        return null;
    }

    /**
     * Gets the qualified name of the wrapped TypeMirror.
     *
     * @return the qualified name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the qualified name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public String getQualifiedName() {
        return getQualifiedName(typeMirror);
    }

    /**
     * Gets the qualified name of the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to check
     * @return the qualified name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the qualified name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public static String getQualifiedName(TypeMirror typeMirror) {
        if (isDeclared(typeMirror)) {
            return ((TypeElement) (getDeclaredType(typeMirror).asElement())).getQualifiedName().toString();
        } else if (isArray(typeMirror)) {
            return getQualifiedName(getArrayType(typeMirror).getComponentType());
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        }

        return null;
    }

    /**
     * Gets the binary name of the wrapped TypeMirror.
     *
     * @return the binary name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the binary name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public String getBinaryName() {
        return getBinaryName(typeMirror);
    }

    /**
     * Gets the binary name of the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to check
     * @return the binary name of the TypeMirror if wrapped TypeMirror is a DeclaredType or the binary name of the component type if wrapped TypeMirror is an Array or the simple name if for primitive types, otherwise null.
     */
    public static String getBinaryName(TypeMirror typeMirror) {
        if (isDeclared(typeMirror)) {
            return TypeElementWrapper.wrap ((TypeElement) (getDeclaredType(typeMirror).asElement())).getBinaryName();
        } else if (isArray(typeMirror)) {
            return getBinaryName(getArrayType(typeMirror).getComponentType());
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        }

        return null;
    }

    /**
     * Gets the simple name of the wrapped TypeMirror.
     *
     * @return the simple name if passed TypeMirror is a DeclaredType or Primitive, the component types simple name for arrays, otherwise null.
     */
    public String getSimpleName() {
        return getSimpleName(typeMirror);
    }

    /**
     * Gets the simple name of the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to check
     * @return the simple name if passed TypeMirror is a DeclaredType or Primitive, the component types simple name for arrays, otherwise null.
     */
    public static String getSimpleName(TypeMirror typeMirror) {

        if (isDeclared(typeMirror)) {
            return ((TypeElement) (getDeclaredType(typeMirror).asElement())).getSimpleName().toString();
        } else if (isArray(typeMirror)) {
            return getSimpleName(getArrayType(typeMirror).getComponentType());
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        }

        return null;
    }

    /**
     * Gets the String containing the type declaration of wrapped TypeMirror.
     *
     * @return the type declaration String
     */
    public String getTypeDeclaration() {
        return getTypeDeclaration(typeMirror);
    }

    /**
     * Gets the String containing the type declaration for passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to get the declaration for
     * @return the type declaration String
     */
    public static String getTypeDeclaration(TypeMirror typeMirror) {

        if (typeMirror.getKind() == TypeKind.VOID) {
            return "void";
        } else if (isPrimitive(typeMirror)) {
            return typeMirror.toString();
        } else if (isArray(typeMirror)) {
            return getTypeDeclaration(getComponentType(typeMirror)) + "[]";
        } else if (isTypeVar(typeMirror)) {
            return typeMirror.toString();
        } else if (isDeclared(typeMirror)) {

            TypeMirrorWrapper tmw = TypeMirrorWrapper.wrap(typeMirror);

            return tmw.getSimpleName() + (
                    tmw.hasTypeArguments() ? "<" + tmw.getWrappedTypeArguments().stream().map(e -> getTypeDeclaration(e.unwrap())).collect(Collectors.joining(", ")) + ">" : ""
            );

        } else if (isWildcardType(typeMirror)) {
            WildcardType wildcardType = getWildcardType(typeMirror);
            if (wildcardType.getSuperBound() != null) {
                return "? super " + getTypeDeclaration(wildcardType.getSuperBound());
            } else if (wildcardType.getExtendsBound() != null) {
                return "? extends " + getTypeDeclaration(wildcardType.getExtendsBound());
            } else {
                return "?";
            }
        }

        return typeMirror.toString();
    }

    /**
     * Returns a String that helps to instantiation of the Class represented by the passed TypeMirror.
     *
     * @return The instantiation String with Diamond operator if the type has type parameters
     */
    public String getTypeInitializationWithDiamondOperator() {
        return getTypeInitializationWithDiamondOperator(typeMirror);
    }

    /**
     * Returns a String that helps to instantiation of the Class represented by the passed TypeMirror.
     *
     * @param typeMirror the TypeMirror to get the instantiation String for
     * @return The instantiation String with Diamond operator if the type has type parameters
     */
    public static String getTypeInitializationWithDiamondOperator(TypeMirror typeMirror) {
        return getSimpleName(typeMirror) + (hasTypeArguments(typeMirror) ? "<>" : "");
    }

    /**
     * TypeMirrors can contain references to multiple other classes as TypeParameters.
     * This method returns all full qualified class names referenced by the TypeMirror.
     * This is quite helpful to create java source codes with complex parameterized Types involved.
     *
     * @return A set containing all necessary imports. Doesn't contain references of java.lang package which is implicitly bound.
     */
    public Set<String> getImports() {
        return getImports(typeMirror);
    }

    /**
     * TypeMirrors can contain references to multiple other classes as TypeParameters.
     * This method returns all full qualified class names referenced by the TypeMirror.
     * This is quite helpful to create java source codes with complex parameterized Types involved.
     *
     * @param typeMirror The TypeMirror to get the imports for
     * @return A set containing all necessary imports. Doesn't contain references of java.lang package which is implicitly bound.
     */
    public static Set<String> getImports(TypeMirror typeMirror) {

        // null value
        if (typeMirror == null) {
            return Collections.emptySet();
        }

        // array : must check component type
        if (isArray(typeMirror)) {
            return getImports(((ArrayType) typeMirror).getComponentType());
        }

        // primitive : no need for imports
        if (isPrimitive(typeMirror)) {
            return Collections.emptySet();
        }

        // wildcard type argument#
        if (TypeUtils.CheckTypeKind.isOfTypeKind(typeMirror, TypeKind.WILDCARD)) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            Set<String> result = new HashSet<>();

            result.addAll(getImports(wildcardType.getExtendsBound()));
            result.addAll(getImports(wildcardType.getSuperBound()));

            return result;
        }

        // declared type : must add imports for both type and type parameters
        if (TypeUtils.CheckTypeKind.isDeclared(typeMirror)) {
            Set<String> result = new HashSet<>();

            // get enclosing Package
            String packageName = getPackage(typeMirror);

            if (packageName != null && !"java.lang".equals(packageName)) {
                result.add(getQualifiedName(typeMirror));
            }

            if (hasTypeArguments(typeMirror)) {
                for (TypeMirror argumentsTypeMirror : getDeclaredType(typeMirror).getTypeArguments()) {
                    result.addAll(getImports(argumentsTypeMirror));
                }
            }

            return result;
        }

        return Collections.emptySet();
    }

    /**
     * Gets an Optional containing the TypeElement for a typeMirror, if it exists.
     *
     * @return an Optional containing the TypeElement for a typeMirror, that represents no arrays or primitives (non DeclaredTypes)
     */
    public Optional<TypeElementWrapper> getTypeElement() {
        return getTypeElement(typeMirror);
    }


    /**
     * Checks if wrapped TypeMirror is assignable to passed class.
     *
     * @param clazz the class to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableTo(Class<?> clazz) {
        return isAssignableTo(TypeMirrorWrapper.wrap(clazz));
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param otherTypeMirror the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableTo(TypeMirror otherTypeMirror) {
        return isAssignableTo(TypeMirrorWrapper.wrap(otherTypeMirror));
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param otherTypeMirrorWrapper the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableTo(TypeMirrorWrapper otherTypeMirrorWrapper) {
        return TypeUtils.TypeComparison.isAssignableTo(this.typeMirror, otherTypeMirrorWrapper.unwrap());
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param typeElementWrapper the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableTo(TypeElementWrapper typeElementWrapper) {
        return isAssignableTo(typeElementWrapper.asType());
    }

    /**
     * Checks if wrapped TypeMirror is assignable from passed class.
     *
     * @param clazz the class to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableFrom(Class<?> clazz) {
        return isAssignableFrom(TypeMirrorWrapper.wrap(clazz));
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param otherTypeMirror the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableFrom(TypeMirror otherTypeMirror) {
        return isAssignableFrom(TypeMirrorWrapper.wrap(otherTypeMirror));
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param otherTypeMirrorWrapper the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableFrom(TypeMirrorWrapper otherTypeMirrorWrapper) {
        return TypeUtils.TypeComparison.isAssignableTo(otherTypeMirrorWrapper.unwrap(), this.typeMirror);
    }

    /**
     * Checks if wrapped TypeMirror is assignable to passed TypeMirrorWrapper.
     *
     * @param typeElementWrapper the TypeMirrorWrapper to check against
     * @return true if wrapped TypeMirror is assignable to passed class, otherwise false
     */
    public boolean isAssignableFrom(TypeElementWrapper typeElementWrapper) {
        return isAssignableFrom(typeElementWrapper.asType());
    }


    @Override
    public int hashCode() {
        return this.typeMirror.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TypeMirrorWrapper) {
            return this.typeMirror.equals(((TypeMirrorWrapper) obj).unwrap());
        } else if (obj instanceof TypeMirror) {
            return this.typeMirror.equals(obj);
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return this.typeMirror.toString();
    }

    /**
     * Unwraps the wrapped TypeMirror instance.
     *
     * @return the wrapped TypeMirror instance
     */
    public TypeMirror unwrap() {
        return this.typeMirror;
    }

    public static Optional<TypeElementWrapper> getTypeElement(TypeMirror typeMirror) {
        if (typeMirror != null && isDeclared(typeMirror)) {
            return Optional.of(TypeElementWrapper.wrap((TypeElement) getDeclaredType(typeMirror).asElement()));
        }
        return Optional.empty();
    }

    /**
     * Gets the TypeElements wrapped TypeMirror.
     *
     * @param typeElement the TypeElement to get the TypeMirror for
     * @return The wrapped TypeMirror of the TypeElement
     */
    public static TypeMirrorWrapper wrap(TypeElement typeElement) {
        return wrap(TypeElementWrapper.wrap(typeElement));
    }

    /**
     * Gets the TypeElements wrapped TypeMirror.
     *
     * @param typeElement the TypeElement to get the TypeMirror for
     * @return The wrapped TypeMirror of the TypeElement
     */
    public static TypeMirrorWrapper wrap(TypeElementWrapper typeElement) {
        return typeElement.asType();
    }

    /**
     * Wraps a TypeMirror instance to provide some convenience methods
     *
     * @param typeMirror the TypeMirror to wrap
     * @return The wrapped TypeMirror
     */
    public static TypeMirrorWrapper wrap(TypeMirror typeMirror) {
        return new TypeMirrorWrapper(typeMirror);
    }

    /**
     * Gets and Wraps a TypeMirror instance for a fully qualified name to provide some convenience methods
     *
     * @param fqn the fully qualified name of the type
     * @return The wrapped TypeMirror
     */
    public static TypeMirrorWrapper wrap(String fqn) {
        return wrap(TypeUtils.TypeRetrieval.getTypeMirror(fqn));
    }

    /**
     * Gets and Wraps a TypeMirror instance for a Class to provide some convenience methods
     *
     * @param clazz the clazz to get the TypeMirror for
     * @return The wrapped TypeMirror
     */
    public static TypeMirrorWrapper wrap(Class<?> clazz) {
        return wrap(TypeUtils.TypeRetrieval.getTypeMirror(clazz));
    }
}
