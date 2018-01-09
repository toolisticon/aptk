package io.toolisticon.annotationprocessortoolkit.tools;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;

/**
 * Utility class which helps to handle different {@link AnnotationValue} related tasks.
 */
public class AnnotationValueUtils {
    /**
     * Hidden constructor.
     */
    private AnnotationValueUtils() {

    }


    // -----------------------------------------------------------------------------------------
    // -- Methods to check if AnnotationValue is of a specific type
    // -----------------------------------------------------------------------------------------


    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isInteger(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Integer.class);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isLong(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Long.class);
    }


    /**
     * Checks if passed annotation value is of type Boolean.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Boolean, otherwise false
     */
    public static boolean isBoolean(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Boolean.class);
    }

    /**
     * Checks if passed annotation value is of type Float.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Float, otherwise false
     */
    public static boolean isFloat(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Float.class);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isDouble(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Double.class);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type String, otherwise false
     */
    public static boolean isString(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, String.class);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type String, otherwise false
     */
    public static boolean isChar(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, Character.class);
    }

    /**
     * Checks if passed annotation value is an enum.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isEnum(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, VariableElement.class);
    }

    /**
     * Checks if passed annotation value is of type AnnotationValue.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type AnnotationValue, otherwise false
     */
    public static boolean isClass(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, TypeMirror.class);
    }

    /**
     * Checks if passed annotation value is an array.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is an array, otherwise false
     */
    public static boolean isArray(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, List.class);
    }


    /**
     * Checks if passed annotation value is an AnnotationValue.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type AnnotationValue, otherwise false
     */
    public static boolean isAnnotation(AnnotationValue annotationValue) {
        return hasAnnotationValueOneOfPassedTypes(annotationValue, AnnotationMirror.class);
    }


    /**
     * Checks if annotation value has one of the passed types.
     *
     * @param annotationValue the annotation type to check
     * @param types           the type to check for
     * @return true, if annotationValues type is assignable to at least one of the passed types
     */
    private static boolean hasAnnotationValueOneOfPassedTypes(AnnotationValue annotationValue, Class... types) {

        if (annotationValue == null || types.length == 0) {
            return false;
        }

        for (Class type : types) {

            if (type.isAssignableFrom(annotationValue.getValue().getClass())) {
                return true;
            }

        }


        return false;
    }

    // -----------------------------------------------------------------------------------------
    // -- Methods to get the casted AnnotationValue
    // -----------------------------------------------------------------------------------------

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Long, or null if value has not the correct type.
     */
    public static Long getLongValue(AnnotationValue annotationValue) {
        return !isLong(annotationValue) ? null : (Long) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Integer.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Integer, or null if value has not the correct type.
     */
    public static Integer getIntegerValue(AnnotationValue annotationValue) {
        return !isInteger(annotationValue) ? null : (Integer) annotationValue.getValue();
    }


    /**
     * Tries to get the annotationValues value as Boolean.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Boolean, or null if value has not the correct type.
     */
    public static Boolean getBooleanValue(AnnotationValue annotationValue) {
        return !isBoolean(annotationValue) ? null : (Boolean) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Float.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Long, or null if value has not the correct type.
     */
    public static Float getFloatValue(AnnotationValue annotationValue) {
        return !isFloat(annotationValue) ? null : (Float) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Double.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Long, or null if value has not the correct type.
     */
    public static Double getDoubleValue(AnnotationValue annotationValue) {
        return !isDouble(annotationValue) ? null : (Double) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Long, or null if value has not the correct type.
     */
    public static String getStringValue(AnnotationValue annotationValue) {
        return !isString(annotationValue) ? null : (String) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as Long, or null if value has not the correct type.
     */
    public static Character getCharValue(AnnotationValue annotationValue) {
        return !isChar(annotationValue) ? null : (Character) annotationValue.getValue();
    }


    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as TypeMirror, or null if value has not the correct type.
     */
    public static TypeMirror getClassValue(AnnotationValue annotationValue) {
        return getTypeMirrorValue(annotationValue);
    }

    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as TypeMirror, or null if value has not the correct type.
     */
    public static TypeMirror getTypeMirrorValue(AnnotationValue annotationValue) {
        return !isClass(annotationValue) ? null : (TypeMirror) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as VariableElement (== enum value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as VariableElement, or null if value has not the correct type.
     */
    public static VariableElement getEnumValue(AnnotationValue annotationValue) {
        return !isEnum(annotationValue) ? null : (VariableElement) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as AnnotationMirror (== annotation value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as AnnotationMirror, or null if value has not the correct type.
     */
    public static AnnotationMirror getAnnotationValue(AnnotationValue annotationValue) {
        return !isAnnotation(annotationValue) ? null : (AnnotationMirror) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as List (== array value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value casted as List of Attributes, or null if value has not the correct type.
     */
    public static List<Attribute> getArrayValue(AnnotationValue annotationValue) {
        return !isArray(annotationValue) ? null : (List<Attribute>) annotationValue.getValue();
    }


    // -----------------------------------------------------------------------------------------
    // -- Commonly used functions
    // -----------------------------------------------------------------------------------------


    /**
     * Gets value as a TypeMirror array for Class array based annotation attributes.
     *
     * @param annotationValue the annotation value
     * @return
     */
    public static TypeMirror[] getTypeAttributeValueArray(AnnotationValue annotationValue) {
        return getTypeAttributeValueArray(getArrayValue(annotationValue));
    }

    /**
     * Gets value as a TypeMirror array for Class array based annotation attributes.
     *
     * @param annotationValues the annotation values to be converted to a TypeMirror array
     * @return
     */
    public static TypeMirror[] getTypeAttributeValueArray(List<Attribute> annotationValues) {
        TypeMirror[] result = new TypeMirror[annotationValues.size()];

        for (int i = 0; i < annotationValues.size(); i++) {
            result[i] = (TypeMirror) annotationValues.get(i).type.getTypeArguments().get(0);
        }

        return result;
    }


    /**
     * Converts an Attribute List to an array of passed type.
     *
     * @param annotatedValues The list to convert
     * @param type            The arrays target type
     * @param <T>             generic Type
     * @return an array of passed type containing all casted elements of passed annotatedValues list or null if passed annotatedValues are null.
     * @throws ClassCastException will be thrown if Attributes of List cannot be cast to passed type
     */
    public static <T> T[] convertAndCastAttributeValueListToArray(List<Attribute> annotatedValues, Class<T> type) {

        if (annotatedValues == null) {
            return null;
        }

        T[] result = (T[]) Array.newInstance(type, annotatedValues.size());

        for (int i = 0; i < annotatedValues.size(); i++) {
            result[i] = (T) annotatedValues.get(i).getValue();
        }

        return result;
    }

    /**
     * Convenience method to get long value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the long array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Long[] getLongValueArray(AnnotationValue annotationValue) {
        return getLongValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get long value array from annotation value.
     *
     * @param attributes
     * @return the long array containing the attributes's values, or null if passed attributes list is null
     */
    public static Long[] getLongValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Long.class);
    }

    /**
     * Convenience method to get integer value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the integer array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Integer[] getIntegerValueArray(AnnotationValue annotationValue) {
        return getIntegerValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get integer value array from annotation value.
     *
     * @param attributes
     * @return the integer array containing the attributes's values, or null if passed attributes list is null
     */
    public static Integer[] getIntegerValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Integer.class);
    }

    /**
     * Convenience method to get double value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the double array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Double[] getDoubleValueArray(AnnotationValue annotationValue) {
        return getDoubleValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get double value array from annotation value.
     *
     * @param attributes
     * @return the double array containing the attributes's values, or null if passed attributes list is null
     */
    public static Double[] getDoubleValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Double.class);
    }

    /**
     * Convenience method to get float value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the float array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Float[] getFloatValueArray(AnnotationValue annotationValue) {
        return getFloatValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get float value array from annotation value.
     *
     * @param attributes
     * @return the float array containing the attributes's values, or null if passed attributes list is null
     */
    public static Float[] getFloatValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Float.class);
    }

    /**
     * Convenience method to get boolean value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the boolean array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Boolean[] getBooleanValueArray(AnnotationValue annotationValue) {
        return getBooleanValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get boolean value array from annotation value.
     * @param attributes
     * @return the boolean array containing the attributes's values, or null if passed attributes list is null
     */
    public static Boolean[] getBooleanValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Boolean.class);
    }

    /**
     * Convenience method to get char value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the char array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static Character[] getCharValueArray(AnnotationValue annotationValue) {
        return getCharValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get char value array from annotation value.
     * @param attributes
     * @return the char array containing the attributes's values, or null if passed attributes list is null
     */
    public static Character[] getCharValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, Character.class);
    }

    /**
     * Convenience method to get string value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the String array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static String[] getStringValueArray(AnnotationValue annotationValue) {
        return getStringValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get String value array from annotation value.
     * @param attributes
     * @return the String array containing the attributes's values, or null if passed attributes list is null
     */
    public static String[] getStringValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, String.class);
    }

    /**
     * Convenience method to get class value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the class array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static TypeMirror[] getClassValueArray(AnnotationValue annotationValue) {
        return getClassValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get class value array from annotation value.
     * @param attributes
     * @return the class array containing the attributes's values, or null if passed attributes list is null
     */
    public static TypeMirror[] getClassValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, TypeMirror.class);
    }

    /**
     * Convenience method to get enum value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static VariableElement[] getEnumValueArray(AnnotationValue annotationValue) {
        return getEnumValueArray(getArrayValue(annotationValue));
    }


    /**
     * Convenience method to get enum value array from annotation value.
     * @param attributes
     * @return the enum array containing the attributes's values, or null if passed attributes list is null
     */
    public static VariableElement[] getEnumValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, VariableElement.class);
    }

    /**
     * Convenience method to get annotation value array from annotation value.
     *
     * @param annotationValue the annotation value
     * @return the annotation array containing the annotationValue's values, or null if passed annotationValue is null
     */
    public static AnnotationMirror[] getAnnotationValueArray(AnnotationValue annotationValue) {
        return getAnnotationValueArray(getArrayValue(annotationValue));
    }

    /**
     * Convenience method to get annotation value array from annotation value.
     * @param attributes
     * @return the annotation array containing the attributes's values, or null if passed attributes list is null
     */
    public static AnnotationMirror[] getAnnotationValueArray(List<Attribute> attributes) {
        if (attributes == null) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(attributes, AnnotationMirror.class);
    }

}
