package io.toolisticon.aptk.tools;


import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * @param annotationMirror the annotation mirror to get the value from
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttributeWithDefaults(AnnotationMirror annotationMirror) {
        return getAnnotationValueOfAttributeWithDefaults(annotationMirror, "value");
    }

    /**
     * Get all mandatory attribute value names
     *
     * @param annotationMirror the annotation mirror to get the mandatory attribute names from
     * @return an array containing all mandatory attribute names
     */
    public static String[] getMandatoryAttributeValueNames(AnnotationMirror annotationMirror) {

        List<String> result = new ArrayList<>();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : ProcessingEnvironmentUtils.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getDefaultValue() == null) {
                result.add(entry.getKey().getSimpleName().toString());
            }
        }
        return result.toArray(new String[result.size()]);

    }

    /**
     * Get all optional attribute value names
     *
     * @param annotationMirror the annotation mirror to get the mandatory attribute names from
     * @return an array containing all mandatory attribute names
     */
    public static String[] getOptionalAttributeValueNames(AnnotationMirror annotationMirror) {

        List<String> result = new ArrayList<>();
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : ProcessingEnvironmentUtils.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getDefaultValue() != null) {
                result.add(entry.getKey().getSimpleName().toString());
            }
        }
        return result.toArray(new String[result.size()]);

    }

    /**
     * Gets the AnnotationValue for the passed key.
     * Also implicitly set default values will be found.
     *
     * @param annotationMirror the annotation mirror to get the value from
     * @param key              the attribute key to search for
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static AnnotationValue getAnnotationValueOfAttributeWithDefaults(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : ProcessingEnvironmentUtils.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     * Gets the ExecutableElement of a annotation attribute for the passed key.
     * This can be used to determine the type of an attribute.
     * This is very useful if you want to generate code that uses the annotation.
     *
     * @param annotationMirror the annotation mirror to get the value from
     * @param key              the attribute key to search for
     * @return the AnnotationValue,  or null if the AnnotationValue with passed key cannot be found
     */
    public static ExecutableElement getExecutableElementForAnnotationAttributeName(AnnotationMirror annotationMirror, String key) {

        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : ProcessingEnvironmentUtils.getElements().getElementValuesWithDefaults(annotationMirror).entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getKey();
            }
        }
        return null;

    }

    /**
     * Gets the a FQN of a Class based "value" attribute from annotation.
     *
     * @param element        the element to get the annotation for.
     * @param annotationType the annotation type to get the FQN from
     * @return the FQN of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static String getClassAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType) {
        return getClassAttributeFromAnnotationAsFqn(element, annotationType, "value");
    }

    /**
     * Gets the a FQN of a Class based attribute from annotation.
     *
     * @param element        the element to get the annotation for.
     * @param annotationType the annotation type to get the FQN from
     * @param attributeName  the name of the attribute to get
     * @return the FQN of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static String getClassAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        TypeMirror typeMirror = getClassAttributeFromAnnotationAsTypeMirror(element, annotationType, attributeName);

        // type erasure isn't necessary here since Class based attributes are always not parameterized
        return typeMirror != null ? typeMirror.toString() : null;
    }

    /**
     * Gets the a FQN of a Class based attribute from annotation.
     *
     * @param annotationMirror the AnnotationMirror to get the attribute from.
     * @param attributeName    the name of the attribute to get
     * @return the FQN of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static String getClassAttributeFromAnnotationAsFqn(AnnotationMirror annotationMirror, String attributeName) {

        TypeMirror typeMirror = getClassAttributeFromAnnotationAsTypeMirror(annotationMirror, attributeName);

        // type erasure isn't necessary here since Class based attributes are always not parameterized
        return typeMirror != null ? typeMirror.toString() : null;
    }

    /**
     * Gets the a FQN of a Class based attribute from annotation.
     *
     * @param annotationMirror the AnnotationMirror to get the value attribute from.
     * @return the FQN of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static String getClassAttributeFromAnnotationAsFqn(AnnotationMirror annotationMirror) {

        TypeMirror typeMirror = getClassAttributeFromAnnotationAsTypeMirror(annotationMirror, "value");

        // type erasure isn't necessary here since Class based attributes are always not parameterized
        return typeMirror != null ? typeMirror.toString() : null;
    }

    /**
     * Gets the a TypeMirror of a Class based "value" attribute from annotation.
     *
     * @param element        the element to get the annotation for.
     * @param annotationType the annotation type to get the TypeMirror from
     * @return the TypeMirror of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static TypeMirror getClassAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType) {
        return getClassAttributeFromAnnotationAsTypeMirror(element, annotationType, "value");
    }

    /**
     * Gets the a TypeMirror of a Class based attribute from annotation.
     *
     * @param element        the element to get the annotation for.
     * @param annotationType the annotation type to get the TypeMirror from
     * @param attributeName  the name of the attribute to get
     * @return the TypeMirror of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static TypeMirror getClassAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        return getClassAttributeFromAnnotationAsTypeMirror(annotationMirror, attributeName);

    }

    /**
     * Gets the a TypeMirror of a Class based attribute from annotation.
     *
     * @param annotationMirror the annotation mirror to get the TypeMirror from
     * @param attributeName    the name of the attribute to get
     * @return the TypeMirror of the searched annotation value or null if annotation can't be found or attribute isn't of type class.
     */
    public static TypeMirror getClassAttributeFromAnnotationAsTypeMirror(AnnotationMirror annotationMirror, String attributeName) {

        if (annotationMirror == null) {
            return null;
        }
        AnnotationValue annotationAttributeValue = getAnnotationValueOfAttribute(annotationMirror, attributeName);
        if (annotationAttributeValue == null) {
            return null;
        } else {
            try {
                return (TypeMirror) annotationAttributeValue.getValue();
            } catch (ClassCastException e) {
                return null;
            }
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
        return getAnnotationMirror(element, clazz.getCanonicalName());
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

    /**
     * Gets the element for an annotationMirror
     *
     * @param annotationMirror the annotation mirror
     * @return the Element, or null if passed annotationMirror is null
     */
    public static Element getElementForAnnotationMirror(AnnotationMirror annotationMirror) {
        return (annotationMirror != null ? annotationMirror.getAnnotationType().asElement() : null);
    }

    /**
     * Gets class array attribute from annotations value attribute as an array of full qualified class names.
     *
     * @param element        the annotated element
     * @param annotationType the annotation type
     * @return The full qualified class name array of the attribute
     */
    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType) {

        return getClassArrayAttributeFromAnnotationAsFqn(element, annotationType, "value");
    }

    /**
     * Gets class array attribute from annotations attributeName attribute as an array of full qualified class names.
     *
     * @param element        the annotated element
     * @param annotationType the annotation type
     * @param attributeName  the name of the attribute
     * @return The full qualified class name array of the attribute
     */
    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        return getClassArrayAttributeFromAnnotationAsFqn(annotationMirror, attributeName);

    }

    /**
     * Gets class array attribute from annotations attributeName attribute as an array of full qualified class names.
     *
     * @param annotationMirror the annotation type
     * @param attributeName    the name of the attribute
     * @return The full qualified class name array of the attribute
     */
    public static String[] getClassArrayAttributeFromAnnotationAsFqn(AnnotationMirror annotationMirror, String attributeName) {

        TypeMirror[] typeMirrorArray = getClassArrayAttributeFromAnnotationAsTypeMirror(annotationMirror, attributeName);
        String[] result = null;

        if (typeMirrorArray != null) {

            result = new String[typeMirrorArray.length];

            for (int i = 0; i < typeMirrorArray.length; i++) {
                result[i] = typeMirrorArray[i].toString();
            }

        }

        return result;
    }

    /**
     * Gets class array attribute from annotations value attribute as an array of TypeMirrors.
     *
     * @param element        the annotated element
     * @param annotationType the annotation type
     * @return The full qualified class name array of the attribute
     */
    public static TypeMirror[] getClassArrayAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType) {
        return getClassArrayAttributeFromAnnotationAsTypeMirror(element, annotationType, "value");
    }

    /**
     * Gets class array attribute from annotations attributeName attribute as an array of TypeMirrors.
     *
     * @param element        the annotated element
     * @param annotationType the annotation type
     * @param attributeName  the name of the attribute
     * @return The full qualified class name array of the attribute
     */
    public static TypeMirror[] getClassArrayAttributeFromAnnotationAsTypeMirror(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        AnnotationMirror annotationMirror = getAnnotationMirror(element, annotationType);
        return getClassArrayAttributeFromAnnotationAsTypeMirror(annotationMirror, attributeName);

    }

    /**
     * Gets class array attribute from annotations attributeName attribute as an array of TypeMirrors.
     *
     * @param annotationMirror the annotation mirror
     * @param attributeName    the name of the attribute
     * @return The full qualified class name array of the attribute
     */
    public static TypeMirror[] getClassArrayAttributeFromAnnotationAsTypeMirror(AnnotationMirror annotationMirror, String attributeName) {

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

    /**
     * Checks if passed annotation is repeatable.
     * (annotataed with Repeatable annotation)
     * @param annotation the annotation to check
     * @return true if the annotation is repeatable, otherwise false
     */
    public static boolean isRepeatableAnnotation(Class<? extends Annotation> annotation) {
        return annotation != null && TypeUtils.TypeRetrieval.getTypeElement(annotation).getAnnotation(Repeatable.class) != null;
    }

    /**
     * Gets the repeatable wrapper type of annotation
     * @param annotation the annotation to get the repeatable annotation wrapper type for
     * @return an optional containing the repeatable wrapper type or an empty optional if passed annotation is null or annotation is no repeatable.
     */
    public static Optional<Class<? extends Annotation>> getRepeatableAnnotationWrapperClass(Class<? extends Annotation> annotation) {
        if(isRepeatableAnnotation(annotation)) {

            TypeElement annotationTypeElement = TypeUtils.TypeRetrieval.getTypeElement(annotation);
            AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(annotationTypeElement, Repeatable.class);

            TypeMirror typeMirror = getClassAttributeFromAnnotationAsTypeMirror(annotationMirror, "value");

            try {
                return Optional.of((Class<? extends Annotation>)Class.forName(typeMirror.toString()));
            } catch (ClassNotFoundException e) {
              // ignore - shouldn't happen since
            }

        }
        return Optional.empty();
    }

    public static Optional<List<AnnotationMirror>> getRepeatableAnnotation(Element element, Class<? extends Annotation> annotation) {

        if (annotation == null || element == null || !isRepeatableAnnotation(annotation)) {
            return Optional.empty();
        }

        Class<? extends Annotation> repeatableAnnotationWrapperType = getRepeatableAnnotationWrapperClass(annotation).get();

        List<AnnotationMirror> result = new ArrayList<>();

        // 1. single annotation use
        AnnotationMirror singleUse = getAnnotationMirror(element, annotation);
        if (singleUse != null) {
            result.add(singleUse);
        }

        // 2. via repeatable annotation
        AnnotationMirror repeatableAnnotationMirror = getAnnotationMirror(element, repeatableAnnotationWrapperType);
        if (repeatableAnnotationMirror != null) {
            AnnotationValue annotationValue = getAnnotationValueOfAttributeWithDefaults(getAnnotationMirror(element, repeatableAnnotationWrapperType));
            result.addAll(Arrays.asList(AnnotationValueUtils.getAnnotationValueArray(annotationValue)));
        }

        return Optional.of(result);
    }



}
