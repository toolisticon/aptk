package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Utility class which helps to handle different {@link AnnotationValue} related tasks.
 */
@SuppressWarnings("unchecked")
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
    private static boolean hasAnnotationValueOneOfPassedTypes(AnnotationValue annotationValue, Class<?>... types) {

        if (annotationValue == null || types == null || types.length == 0) {
            return false;
        }

        for (Class<?> type : types) {

            if (type.isAssignableFrom(annotationValue.getValue().getClass())) {
                return true;
            }

        }


        return false;
    }

    // -----------------------------------------------------------------------------------------
    // -- Methods to get the cast AnnotationValue
    // -----------------------------------------------------------------------------------------

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public static Long getLongValue(AnnotationValue annotationValue) {
        return !isLong(annotationValue) ? null : (Long) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Integer.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Integer, or null if value has not the correct type.
     */
    public static Integer getIntegerValue(AnnotationValue annotationValue) {
        return !isInteger(annotationValue) ? null : (Integer) annotationValue.getValue();
    }


    /**
     * Tries to get the annotationValues value as Boolean.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Boolean, or null if value has not the correct type.
     */
    public static Boolean getBooleanValue(AnnotationValue annotationValue) {
        return !isBoolean(annotationValue) ? null : (Boolean) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Float.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public static Float getFloatValue(AnnotationValue annotationValue) {
        return !isFloat(annotationValue) ? null : (Float) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Double.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public static Double getDoubleValue(AnnotationValue annotationValue) {
        return !isDouble(annotationValue) ? null : (Double) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public static String getStringValue(AnnotationValue annotationValue) {
        return !isString(annotationValue) ? null : (String) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as Long.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as Long, or null if value has not the correct type.
     */
    public static Character getCharValue(AnnotationValue annotationValue) {
        return !isChar(annotationValue) ? null : (Character) annotationValue.getValue();
    }


    /**
     * Tries to get the annotationValues value as TypeMirror.
     * This is potentially an unsafe operation since it's never guaranteed that a class has already been compiled.
     * Prefer working with TypeMirrors instead.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value as a Class value, or null if value has not the correct type or hasn't been compiled yet.
     */
    public static Class<?> getClassValue(AnnotationValue annotationValue) {
        TypeMirror typeMirror = getTypeMirrorValue(annotationValue);
        if (typeMirror != null) {
            try {
                return Class.forName(typeMirror.toString());
            } catch (ClassNotFoundException e) {
                // ignore will just return null
            }
        }

        return null;
    }

    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as TypeMirror, or null if value has not the correct type.
     */
    public static String getClassValueAsFQN(AnnotationValue annotationValue) {
        return ((TypeElement) ToolingProvider.getTooling().getTypes().asElement(getTypeMirrorValue(annotationValue))).getQualifiedName().toString();
    }

    /**
     * Tries to get the annotationValues value as TypeMirror.
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as TypeMirror, or null if value has not the correct type.
     */
    public static TypeMirror getTypeMirrorValue(AnnotationValue annotationValue) {
        return !isClass(annotationValue) ? null : (TypeMirror) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as VariableElement (== enum value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as VariableElement, or null if value has not the correct type.
     */
    public static VariableElement getEnumValue(AnnotationValue annotationValue) {
        return !isEnum(annotationValue) ? null : (VariableElement) annotationValue.getValue();
    }

    /**
     * Tries to get the annotation value as enum value.
     *
     * @param enumType        The enum Type
     * @param annotationValue the value to get the value from.
     * @param <T>             The enum type
     * @return the annotationValues value as enum value, or null if value has not the correct type.
     */
    public static <T extends Enum<T>> T getEnumValue(Class<T> enumType, AnnotationValue annotationValue) {

        VariableElement enumValueElement = getEnumValue(annotationValue);

        return enumValueElement != null ? Enum.valueOf(enumType, enumValueElement.getSimpleName().toString()) : null;

    }

    /**
     * Tries to get the annotationValues value as AnnotationMirror (== annotation value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as AnnotationMirror, or null if value has not the correct type.
     */
    public static AnnotationMirror getAnnotationValue(AnnotationValue annotationValue) {
        return !isAnnotation(annotationValue) ? null : (AnnotationMirror) annotationValue.getValue();
    }

    /**
     * Tries to get the annotationValues value as List (== array value).
     *
     * @param annotationValue the value to get the value from.
     * @return the annotationValues value cast as List of Attributes, or null if value has not the correct type.
     */
    public static List<? extends AnnotationValue> getArrayValue(AnnotationValue annotationValue) {
        return !isArray(annotationValue) ? null : (List<? extends AnnotationValue>) annotationValue.getValue();
    }


    // -----------------------------------------------------------------------------------------
    // -- Commonly used functions
    // -----------------------------------------------------------------------------------------


    /**
     * Gets value as a TypeMirror array for Class array based annotation attributes.
     *
     * @param annotationValue the annotation value
     * @return The TypeMirror array or null if passed in annotationValue is null or doesn't represent an array
     */
    public static TypeMirror[] getTypeAttributeValueArray(AnnotationValue annotationValue) {
        List<? extends AnnotationValue> arrayValue = getArrayValue(annotationValue);
        return arrayValue != null ? getTypeAttributeValueArray(arrayValue) : new TypeMirror[0];
    }

    /**
     * Gets value as a TypeMirror array for Class array based annotation attributes.
     *
     * @param annotationValues the annotation values to be converted to a TypeMirror array
     * @return The TypeMirror array
     */
    public static TypeMirror[] getTypeAttributeValueArray(List<? extends AnnotationValue> annotationValues) {
        TypeMirror[] result = new TypeMirror[annotationValues.size()];

        for (int i = 0; i < annotationValues.size(); i++) {

            result[i] = (DeclaredType) annotationValues.get(i).getValue();

        }

        return result;
    }


    /**
     * Converts an Attribute List to an array of passed type.
     *
     * @param annotatedValues The list to convert
     * @param type            The arrays target type
     * @param <T>             generic Type
     * @return an array of passed type containing all cast elements of passed annotatedValues list or null if passed annotatedValues are null.
     * @throws ClassCastException will be thrown if Attributes of List cannot be cast to passed type
     */
    public static <T> T[] convertAndCastAttributeValueListToArray(List<? extends AnnotationValue> annotatedValues, Class<T> type) {

        if (type == null) {
            throw new IllegalArgumentException("passed type must not be null!");
        }

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
     * @param annotationValues the annotation values
     * @return the long array containing the attributes values, or null if passed attributes list is null
     */
    public static Long[] getLongValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Long.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Long.class);
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
     * @param annotationValues the annotation values
     * @return the integer array containing the attributes values, or null if passed attributes list is null
     */
    public static Integer[] getIntegerValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Integer.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Integer.class);
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
     * @param annotationValues The annotation Values
     * @return the double array containing the annotationValues values, or null if passed annotationValues list is null
     */
    public static Double[] getDoubleValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Double.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Double.class);
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
     * @param annotationValues the annotation values
     * @return the float array containing the attributes values, or null if passed attributes list is null
     */
    public static Float[] getFloatValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Float.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Float.class);
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
     *
     * @param annotationValues the annotation values
     * @return the boolean array containing the attributes values, or null if passed attributes list is null
     */
    public static Boolean[] getBooleanValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Boolean.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Boolean.class);
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
     *
     * @param annotationValues the annotation values
     * @return the char array containing the attributes values, or null if passed attributes list is null
     */
    public static Character[] getCharValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, Character.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, Character.class);
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
     *
     * @param annotationValues the annotation values
     * @return the String array containing the attributes values, or null if passed attributes list is null
     */
    public static String[] getStringValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, String.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, String.class);
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
     *
     * @param annotationValues the annotation values
     * @return the enum array containing the attributes values, or null if passed attributes list is null
     */
    public static VariableElement[] getEnumValueArray(List<? extends AnnotationValue> annotationValues) {
        if (annotationValues == null || !isAnnotationValueArray(annotationValues, VariableElement.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, VariableElement.class);
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
     *
     * @param annotationValues the annotation values
     * @return the annotation array containing the annotationValues values, or null if passed annotationValues list is null
     */
    public static AnnotationMirror[] getAnnotationValueArray(List<? extends AnnotationValue> annotationValues) {
        if (!isAnnotationValueArray(annotationValues, AnnotationMirror.class)) {
            return null;
        }

        return convertAndCastAttributeValueListToArray(annotationValues, AnnotationMirror.class);
    }


    // -----------------------------------------------------------------------------------------
    // -- Array value functions
    // -----------------------------------------------------------------------------------------


    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isIntegerArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Integer.class);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isLongArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Long.class);
    }


    /**
     * Checks if passed annotation value is of type Boolean.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Boolean, otherwise false
     */
    public static boolean isBooleanArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Boolean.class);
    }

    /**
     * Checks if passed annotation value is of type Float.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Float, otherwise false
     */
    public static boolean isFloatArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Float.class);
    }

    /**
     * Checks if passed annotation value is of type Integer.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isDoubleArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Double.class);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type String, otherwise false
     */
    public static boolean isStringArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, String.class);
    }

    /**
     * Checks if passed annotation value is of type String.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type String, otherwise false
     */
    public static boolean isCharArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, Character.class);
    }

    /**
     * Checks if passed annotation value is an enum.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type Integer, otherwise false
     */
    public static boolean isEnumArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, VariableElement.class);
    }

    /**
     * Checks if passed annotation value is of type AnnotationValue.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type AnnotationValue, otherwise false
     */
    public static boolean isClassArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, TypeMirror.class);
    }

    /**
     * Checks if passed annotation value is an array.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is an array, otherwise false
     */
    public static boolean isArrayArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, List.class);
    }


    /**
     * Checks if passed annotation value is an AnnotationValue array.
     *
     * @param annotationValue the value to check
     * @return true, if passed annotation is of type AnnotationValue, otherwise false
     */
    public static boolean isAnnotationArray(AnnotationValue annotationValue) {
        return isAnnotationValueArray(annotationValue, AnnotationMirror.class);
    }


    /**
     * Checks if annotation value is array of passed type.
     *
     * @param annotationValue the annotation value
     * @param type            the array type to check for
     * @return true if annotation value is array of passed type, otherwise false
     */
    public static boolean isAnnotationValueArray(AnnotationValue annotationValue, Class<?> type) {

        return annotationValue != null && type != null
                && annotationValue.getValue() instanceof List
                && isAnnotationValueArray((List<? extends AnnotationValue>) annotationValue.getValue(), type);
    }

    /**
     * Checks if passed AnnotationValue list represents array of passed type.
     *
     * @param annotationValues the annotation value list
     * @param type             the array type to check for
     * @return true if annotation value is array of passed type, otherwise false
     */
    public static boolean isAnnotationValueArray(List<? extends AnnotationValue> annotationValues, Class<?> type) {

        return annotationValues != null && type != null
                && annotationValues.size() >= 1
                && hasAnnotationValueOneOfPassedTypes(annotationValues.get(0), type);
    }


}
