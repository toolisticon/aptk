package io.toolisticon.annotationprocessortoolkit.tools.generics;

import javax.lang.model.type.TypeMirror;

/**
 * This class represents a generic type or a generic type based type parameter.
 */
public class GenericType implements GenericTypeParameter {

    private final TypeMirror rawType;
    private final GenericTypeParameter[] typeParameters;

    /**
     * Constructor.
     *
     * @param rawType        the raw type of the generic type
     * @param typeParameters the type parameters of the generic type (wildcards and generic types)
     * @param <T>
     */
    public <T extends GenericTypeParameter> GenericType(TypeMirror rawType, T... typeParameters) {
        this.rawType = rawType;
        this.typeParameters = typeParameters;
    }

    /**
     * Gets the raw type.
     *
     * @return the raw type
     */
    public TypeMirror getRawType() {
        return rawType;
    }

    /**
     * Gets the type parameters.
     *
     * @return the type parameters (wildcards and generic types)
     */
    public GenericTypeParameter[] getTypeParameters() {
        return typeParameters;
    }

    /**
     * Convenience method to check if there are type parameters.
     *
     * @return true if there are type parameters, otherwise false
     */
    public boolean hasTypeParameters() {
        return typeParameters != null && typeParameters.length > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericTypeKind getType() {
        return GenericTypeKind.GENERIC_TYPE;
    }
}
