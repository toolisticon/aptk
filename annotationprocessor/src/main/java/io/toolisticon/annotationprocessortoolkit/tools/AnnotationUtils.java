package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class which helps to handle different {@link Annotation} related tasks.
 */
public final class AnnotationUtils {

    /**
     * Hidden constructor.
     */
    private AnnotationUtils() {

    }


    /**
     * Gets the AnnotationValue with default "value" key.
     *
     * @param annotationMirror the annotation mirror to get the value from
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttribute(AnnotationMirror annotationMirror) {
        return getAnnotationValueOfAttribute(annotationMirror, "value");
    }


    /**
     * Gets the AnnotationValue for the passed key.
     * Only explicitly set AnnotationValues will be found.
     *
     * @param annotationMirror the annotation mirror to get the value from
     * @param key              the attribute key to search for
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttribute(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Gets the AnnotationValue with default "value" key.
     *
     * @param frameworkToolWrapper used to pass wrapped Elements needed to get the default values
     * @param annotationMirror     the annotation mirror to get the value from
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttributeWithDefaults(FrameworkToolWrapper frameworkToolWrapper, AnnotationMirror annotationMirror) {
        return getAnnotationValueOfAttributeWithDefaults(frameworkToolWrapper, annotationMirror, "value");
    }

    /**
     * Get all mandatory attribute value names
     * @param frameworkToolWrapper used to pass wrapped Elements needed to get the default values
     * @param annotationMirror the annotation mirror to get the mandatory attribute names from
     * @return an array containing all mandatory attribute names
     */
    public static String[] getMandatoryAttributeValueNames(FrameworkToolWrapper frameworkToolWrapper, AnnotationMirror annotationMirror) {

        List<String> result = new ArrayList<String>();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : frameworkToolWrapper.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getDefaultValue() == null ) {
                result.add(entry.getKey().getSimpleName().toString());
            }
        }
        return result.toArray(new String[result.size()]);

    }

    /**
     * Get all optional attribute value names
     * @param frameworkToolWrapper used to pass wrapped Elements needed to get the default values
     * @param annotationMirror the annotation mirror to get the mandatory attribute names from
     * @return an array containing all mandatory attribute names
     */
    public static String[] getOptionalAttributeValueNames(FrameworkToolWrapper frameworkToolWrapper, AnnotationMirror annotationMirror) {

        List<String> result = new ArrayList<String>();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : frameworkToolWrapper.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getDefaultValue() != null ) {
                result.add(entry.getKey().getSimpleName().toString());
            }
        }
        return result.toArray(new String[result.size()]);

    }

    /**
     * Gets the AnnotationValue for the passed key.
     * Also implicitly set default values will be found.
     *
     * @param frameworkToolWrapper used to pass wrapped Elements needed to get the default values
     * @param annotationMirror     the annotation mirror to get the value from
     * @param key                  the attribute key to search for
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttributeWithDefaults(FrameworkToolWrapper frameworkToolWrapper, AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : frameworkToolWrapper.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * @param element
     * @param annotationType
     * @return
     */
    public static String getClassAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType) {
        return getClassAttributeFromAnnotationAsFqn(element, annotationType, "value");
    }

    public static String getClassAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        TypeMirror typeMirror = getClassAttributeFromAnnotationAsTypeMirror(element, annotationType, attributeName);

        return typeMirror != null ? typeMirror.toString() : null;
    }

    public static TypeMirror getClassAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType) {
        return getClassAttributeFromAnnotationAsTypeMirror(element, annotationType, "value");
    }

    public static TypeMirror getClassAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        if (annotationMirror == null) {
            return null;
        }
        AnnotationValue annotationAttributeValue = getAnnotationValueOfAttribute(annotationMirror, attributeName);
        if (annotationAttributeValue == null) {
            return null;
        } else {
            return (TypeMirror) annotationAttributeValue.getValue();
        }

    }

    /**
     * Gets the AnnotationMirror for a passed annotation type from the passed element.
     *
     * @param element the element to get the AnnotationMirror from.
     * @param clazz   the annotations type to get
     * @return the AnnotationMirror or null if it can't be found.
     */
    public static AnnotationMirror getAnnotationMirror(Element element, Class<? extends Annotation> clazz) {
        return getAnnotationMirror(element, clazz.getName());
    }

    /**
     * Gets the AnnotationMirror for a passed annotation type from the passed element.
     *
     * @param element     the element to get the AnnotationMirror from.
     * @param fqClassName the annotations full qualified class name to get
     * @return the AnnotationMirror or null if it can't be found.
     */
    public static AnnotationMirror getAnnotationMirror(Element element, String fqClassName) {
        for (AnnotationMirror m : element.getAnnotationMirrors()) {
            if (m.getAnnotationType().toString().equals(fqClassName)) {
                return m;
            }
        }
        return null;
    }


    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType) {


        return getClassArrayAttributeFromAnnotationAsFqn(element, annotationType, "value");
    }

    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        TypeMirror[] typeMirrorArray = getClassArrayAttributeFromAnnotationAsTypeMirror(element, annotationType, attributeName);
        String[] result = null;


        if (typeMirrorArray != null) {

            result = new String[typeMirrorArray.length];

            for (int i = 0; i < typeMirrorArray.length; i++) {
                result[i] = typeMirrorArray[i].toString();
            }

        }

        return result;
    }

    public static TypeMirror[] getClassArrayAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType) {
        return getClassArrayAttributeFromAnnotationAsTypeMirror(element, annotationType, "value");
    }

    public static TypeMirror[] getClassArrayAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        if (annotationMirror == null) {
            return null;
        }

        AnnotationValue annotationAttributeValue = getAnnotationValueOfAttribute(annotationMirror, attributeName);
        if (annotationAttributeValue == null) {
            return new TypeMirror[0];
        } else {
            return AnnotationValueUtils.getTypeAttributeValueArray(annotationAttributeValue);
        }

    }


}
