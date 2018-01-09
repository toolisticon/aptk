package io.toolisticon.annotationprocessortoolkit.tools.generics;

/**
 * This class represents a wildcard based type parameter.
 * <p/>
 * It may define extends and super wildcards or a pure wildcard.
 */
public final class GenericTypeWildcard implements GenericTypeParameter {

    private final GenericType superBound;
    private final GenericType extendsBound;

    /**
     * Constructor for a wildcard.
     * <p/>
     * Either superBound or extendsBound may not be null, because it's not possible to define both bounds in Java.
     * If both superBound and extendsBound are null then a pure wildcard is used.
     *
     * @param superBound   the super bound to use
     * @param extendsBound the extends bound to use
     */
    private GenericTypeWildcard(GenericType superBound, GenericType extendsBound) {
        this.superBound = superBound;
        this.extendsBound = extendsBound;
    }

    /**
     * Gets the super bound set.
     *
     * @return the super bound
     */
    public GenericType getSuperBound() {
        return superBound;
    }

    /**
     * Gets the extends bound set.
     *
     * @return the extends bound
     */
    public GenericType getExtendsBound() {
        return extendsBound;
    }

    /**
     * Convenience method to check if this instance represents a pure wildcard.
     * <p/>
     * This corresponds to neither super nor extends bounds are set.
     *
     * @return true if this instance represents a pure wildcard, otherwise false
     */
    public boolean isPureWildcard() {
        return superBound == null && extendsBound == null;
    }

    /**
     * Convenience method to check if this instance represents a extends wildcard.
     *
     * @return true if this instance represents an extends wildcard, otherwise false
     */
    public boolean hasExtendsBound() {
        return extendsBound != null;
    }

    /**
     * Convenience method to check if this instance represents a super wildcard.
     *
     * @return true if this instance represents an super wildcard, otherwise false
     */
    public boolean hasSuperBound() {
        return superBound != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericTypeKind getType() {
        return GenericTypeKind.WILDCARD;
    }


    /**
     * Creates a pure wildcard.
     *
     * @return the pure wildcard instance
     */
    public static GenericTypeWildcard createPureWildcard() {
        return createWildcard(null, null);
    }

    /**
     * Creates a extends wildcard.
     *
     * @return the extends wildcard instance
     */
    public static GenericTypeWildcard createExtendsWildcard(GenericType extendsBound) {
        return createWildcard(null, extendsBound);
    }

    /**
     * Creates a super wildcard.
     *
     * @return the super wildcard instance
     */
    public static GenericTypeWildcard createSuperWildcard(GenericType superBound) {
        return createWildcard(superBound, null);
    }

    private static GenericTypeWildcard createWildcard(GenericType superBound, GenericType extendsBound) {
        return new GenericTypeWildcard(superBound, extendsBound);
    }

}
