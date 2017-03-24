package de.holisticon.annotationprocessor.tools;

import de.holisticon.annotationprocessor.internal.FrameworkToolWrapper;

import javax.lang.model.element.TypeElement;
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

    public TypeElement getTypeElementForClass(Class type) {
        return type == null ? null : frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName());
    }

    public TypeMirror getTypeMirrorForClass(Class type) {
        return type != null ? getTypeElementForClass(type).asType() : null;
    }

    public boolean isAssignableToType(TypeElement typeElement, Class type) {
        return isAssignableToTypeMirror(typeElement, frameworkToolWrapper.getElements().getTypeElement(type.getCanonicalName()).asType());
    }

    public boolean isAssignableToTypeMirror(TypeElement typeElement, TypeMirror typeMirror) {
        return typeElement == null || typeMirror == null ? false : frameworkToolWrapper.getTypes().isAssignable(typeElement.asType(), typeMirror);
    }

    public boolean isAssignableToTypeElement(TypeElement typeElement1, TypeElement typeElement2) {
        return isAssignableToTypeMirror(typeElement1, typeElement2.asType());
    }

    public boolean isVoidType(TypeMirror typeMirror) {
        return typeMirror != null && TypeKind.VOID.equals(typeMirror.getKind());
    }

    public Types getTypes() {
        return frameworkToolWrapper.getTypes();
    }

    public static TypeUtils getTypeUtils(FrameworkToolWrapper frameworkToolWrapper) {
        return new TypeUtils(frameworkToolWrapper);
    }


}
