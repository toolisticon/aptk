package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationValueUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * Wrapper class for AnnotationValues.
 */
public class AnnotationValueWrapper {

    final AnnotationValue annotationValue;

    private AnnotationValueWrapper(AnnotationValue annotationValue) {
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
    public boolean isLong(AnnotationValue annotationValue) {
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
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public Long getLongValue() {
        return AnnotationValueUtils.getLongValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Integer.
     *
     * @return the annotationValues value cast as Integer, or null if value has not the correct type.
     */
    public Integer getIntegerValue() {
        return AnnotationValueUtils.getIntegerValue(annotationValue);
    }


    /**
     * Tries to get the annotationValues value as Boolean.
     *
     * @return the annotationValues value cast as Boolean, or null if value has not the correct type.
     */
    public Boolean getBooleanValue() {
        return AnnotationValueUtils.getBooleanValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Float.
     *
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public Float getFloatValue() {
        return AnnotationValueUtils.getFloatValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Double.
     *
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public Double getDoubleValue() {
        return AnnotationValueUtils.getDoubleValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public String getStringValue() {
        return AnnotationValueUtils.getStringValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public Character getCharValue() {
        return AnnotationValueUtils.getCharValue(annotationValue);
    }


    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @return the annotationValues value cast as TypeMirror, or null if value has not the correct type.
     */
    public TypeMirrorWrapper getClassValue() {
        return TypeMirrorWrapper.wrap(AnnotationValueUtils.getClassValue(annotationValue));
    }

    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @return the annotationValues value cast as TypeMirror, or null if value has not the correct type.
     */
    public TypeMirror getTypeMirrorValue() {
        return AnnotationValueUtils.getTypeMirrorValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as VariableElement (== enum value).
     *
     * @return the annotationValues value cast as VariableElement, or null if value has not the correct type.
     */
    public VariableElement getEnumValue() {
        return AnnotationValueUtils.getEnumValue(annotationValue);
    }

    /**
     * Tries to get the annotation value as enum value.
     *
     * @param enumType The enum Type
     * @param <T>      The enum type
     * @return the annotationValues value as enum value, or null if value has not the correct type.
     */
    public <T extends Enum<T>> T getEnumValue(Class<T> enumType) {

        return AnnotationValueUtils.getEnumValue(enumType, annotationValue);

    }

    /**
     * Tries to get the annotationValues value as AnnotationMirror (== annotation value).
     *
     * @return the annotationValues value cast as AnnotationMirror, or null if value has not the correct type.
     */
    public AnnotationMirror getAnnotationValue() {
        return AnnotationValueUtils.getAnnotationValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as List (== array value).
     *
     * @return the annotationValues value cast as List of Attributes, or null if value has not the correct type.
     */
    public List<? extends AnnotationValue> getArrayValue() {
        return AnnotationValueUtils.getArrayValue(annotationValue);
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
