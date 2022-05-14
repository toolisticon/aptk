package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationValueUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Wrapper class for AnnotationValues.
 */
public class AnnotationValueWrapper {

    final AnnotationValue annotationValue;

    private AnnotationValueWrapper(AnnotationValue annotationValue) {
        if (annotationValue == null) {
            throw new IllegalArgumentException("Passed AnnotationValue to wrap must not be null.");
        }
        this.annotationValue = annotationValue;
    }

    /**
     * Returns the wrapped AnnotationValue.
     *
     * @return the wrapped AnnotationValue
     */
    public AnnotationValue unwrap() {
        return this.annotationValue;
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @return true, if annotation value is of type Integer, otherwise false
     */
    public boolean isInteger() {
        return AnnotationValueUtils.isInteger(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @return true, if  annotation value is of type Integer, otherwise false
     */
    public boolean isLong() {
        return AnnotationValueUtils.isLong(annotationValue);
    }


    /**
     * Checks if passed annotation value is of type Boolean.
     *
     * @return true, if passed annotation is of type Boolean, otherwise false
     */
    public boolean isBoolean() {
        return AnnotationValueUtils.isBoolean(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type Float.
     *
     * @return true, if passed annotation is of type Float, otherwise false
     */
    public boolean isFloat() {
        return AnnotationValueUtils.isFloat(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @return true, if  annotation is of type Integer, otherwise false
     */
    public boolean isDouble() {
        return AnnotationValueUtils.isDouble(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @return true, if annotation is of type String, otherwise false
     */
    public boolean isString() {
        return AnnotationValueUtils.isString(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @return true, if annotation is of type String, otherwise false
     */
    public boolean isChar() {
        return AnnotationValueUtils.isChar(annotationValue);
    }

    /**
     * Checks if passed annotation value is an enum.
     *
     * @return true, if annotation is of type Integer, otherwise false
     */
    public boolean isEnum() {
        return AnnotationValueUtils.isEnum(annotationValue);
    }

    /**
     * Checks if passed annotation value is of type AnnotationValue.
     *
     * @return true, if annotation is of type AnnotationValue, otherwise false
     */
    public boolean isClass() {
        return AnnotationValueUtils.isClass(annotationValue);
    }

    /**
     * Checks if passed annotation value is an array.
     *
     * @return true, if annotation is an array, otherwise false
     */
    public boolean isArray() {
        return AnnotationValueUtils.isArray(annotationValue);
    }

    /**
     * Checks if passed annotation value is an AnnotationValue.
     *
     * @return true, if annotation is of type AnnotationValue, otherwise false
     */
    public boolean isAnnotation() {
        return AnnotationValueUtils.isAnnotation(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @return the annotationValues value as an Optional of type Long, or an empty Optional if value has not the correct type.
     */
    public Optional<Long> getLongValue() {
        return Optional.ofNullable(AnnotationValueUtils.getLongValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as Integer.
     *
     * @return the annotationValues value as an Optional of type Integer, or an empty Optional if value has not the correct type.
     */
    public Optional<Integer> getIntegerValue() {
        return Optional.ofNullable(AnnotationValueUtils.getIntegerValue(annotationValue));
    }


    /**
     * Tries to get the annotationValues value as Boolean.
     *
     * @return the annotationValues value as an Optional of type Boolean, or an empty Optional if value has not the correct type.
     */
    public Optional<Boolean> getBooleanValue() {
        return Optional.ofNullable(AnnotationValueUtils.getBooleanValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as Float.
     *
     * @return the annotationValues value as an Optional of type Float, or an empty Optional if value has not the correct type.
     */
    public Optional<Float> getFloatValue() {
        return Optional.ofNullable(AnnotationValueUtils.getFloatValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as Double.
     *
     * @return the annotationValues value as an Optional of type Double, or an empty Optional if value has not the correct type.
     */
    public Optional<Double> getDoubleValue() {
        return Optional.ofNullable(AnnotationValueUtils.getDoubleValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @return the annotationValues value as an Optional of type String, or an empty Optional if value has not the correct type.
     */
    public Optional<String> getStringValue() {
        return Optional.ofNullable(AnnotationValueUtils.getStringValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @return the annotationValues value as an Optional of type Char, or an empty Optional if value has not the correct type.
     */
    public Optional<Character> getCharValue() {
        return Optional.ofNullable(AnnotationValueUtils.getCharValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as TypeMirrorWrapper.
     *
     * @return the annotationValues value as an Optional of type TypeMirrorWrapper, or an empty Optional if value has not the correct type.
     */
    public Optional<TypeMirrorWrapper> getClassValue() {
        TypeMirror typeMirror = AnnotationValueUtils.getTypeMirrorValue(annotationValue);
        return typeMirror!= null ? Optional.of(TypeMirrorWrapper.wrap(typeMirror)) : Optional.empty();
    }

    /**
     * Tries to get the annotationValues value as VariableElement (== enum value).
     *
     * @return the annotationValues value as an Optional of type VariableElementWrapper, or an empty Optional if value has not the correct type.
     */
    public Optional<VariableElementWrapper> getEnumValue() {
        VariableElement result = AnnotationValueUtils.getEnumValue(annotationValue);
        return result != null ? Optional.of(VariableElementWrapper.wrap(result)):Optional.empty();
    }

    /**
     * Tries to get the annotation value as enum value.
     *
     * @param enumType The enum Type
     * @param <T>      The enum type
     * @return the annotationValues value as an Optional of the enum value type, or an empty Optional if value has not the correct type.
     */
    public <T extends Enum<T>> Optional<T> getEnumValue(Class<T> enumType) {
        return  Optional.ofNullable(AnnotationValueUtils.getEnumValue(enumType, annotationValue));
}

    /**
     * Tries to get the annotationValues value as AnnotationMirror (== annotation value).
     *
     * @return the annotationValues value cast as an Optional of AnnotationMirrorWrapper, or an empty Optional if value has not the correct type.
     */
    public Optional<AnnotationMirrorWrapper> getAnnotationValue() {
        AnnotationMirror annotationMirror = AnnotationValueUtils.getAnnotationValue(annotationValue);
        return annotationMirror != null ? Optional.of(AnnotationMirrorWrapper.wrap(annotationMirror)) : Optional.empty();
    }

    /**
     * Tries to get the annotationValues value as List (== array value).
     *
     * @return the annotationValues value cast as List of Attributes, or null if value has not the correct type.
     */
    public Optional<List<AnnotationValueWrapper>> getArrayValue() {
        List<? extends AnnotationValue> annotationMirrors = AnnotationValueUtils.getArrayValue(annotationValue);
        return annotationMirrors != null ? Optional.of(annotationMirrors.stream().map(AnnotationValueWrapper::wrap).collect(Collectors.toList())) : Optional.empty();
    }

    /**
     * Wraps an AnnotationValue for convenient function access.
     *
     * @param annotationValue the annotation value to wrap
     * @return the annotation value
     */
    public static AnnotationValueWrapper wrap(AnnotationValue annotationValue) {
        return new AnnotationValueWrapper(annotationValue);
    }


}
