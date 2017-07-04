package de.holisticon.annotationprocessortoolkit.tools.generics;

import javax.lang.model.type.TypeMirror;

/**
 * Created by tobiasstamann on 03.07.17.
 */
public class GenericType implements GenericTypeType {



    public final TypeMirror rawType;
    public final GenericTypeType[] typeParameters;

    public <T extends GenericTypeType> GenericType(TypeMirror rawType, T... typeParameters) {
        this.rawType = rawType;
        this.typeParameters = typeParameters;
    }

    public TypeMirror getRawType() {
        return rawType;
    }

    public GenericTypeType[] getTypeParameters() {
        return typeParameters;
    }



    public boolean hasTypeParameters() {
        return typeParameters != null && typeParameters.length > 0;
    }

    @Override
    public GenericTypeKind getType() {
        return GenericTypeKind.GENERIC_TYPE;
    }
}
