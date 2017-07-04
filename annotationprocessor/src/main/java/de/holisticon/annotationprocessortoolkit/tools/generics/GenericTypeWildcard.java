package de.holisticon.annotationprocessortoolkit.tools.generics;

/**
 * Created by tobiasstamann on 03.07.17.
 */
public class GenericTypeWildcard implements GenericTypeType {

    private final GenericType superBound;
    private final GenericType extendsBound;

    public GenericTypeWildcard(GenericType superBound, GenericType extendsBound) {
        this.superBound = superBound;
        this.extendsBound = extendsBound;
    }

    public GenericType getSuperBound() {
        return superBound;
    }

    public GenericType getExtendsBound() {
        return extendsBound;
    }

    public boolean isPureWildcard() {
        return superBound == null && extendsBound == null;
    }

    public boolean hasExtendsBound() {
        return extendsBound != null;
    }

    public boolean hasSuperBound() {
        return superBound != null;
    }

    @Override
    public GenericTypeKind getType() {
        return GenericTypeKind.WILDCARD;
    }

}
