package de.holisticon.annotationprocessortoolkit.tools.generics;

/**
 * Type parameters in generic classes can contain two different kind of declaration: Generic types or wildcards.
 * <p/>
 * This interface is helping to determine the two different kinds.
 */
public interface GenericTypeParameter {
    /**
     * Gets the kind of the type parameter.
     *
     * @return the type of the type parameter
     */
    GenericTypeKind getType();
}
