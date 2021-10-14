package io.toolisticon.aptk.tools.generics;

/**
 * Enum to determine if a {@link GenericTypeParameter} is a {@link GenericTypeWildcard} or {@link GenericType}.
 */
public enum GenericTypeKind {
    /**
     * Defines a wildcard type parameter.
     */
    WILDCARD,
    /**
     * Defines a generic type type parameter.
     */
    GENERIC_TYPE
}
