package de.holisticon.annotationprocessortoolkit.tools;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
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
        AnnotationValue annotationAttributeValue = getAnnotationValue(annotationMirror, attributeName);
        if (annotationAttributeValue == null) {
            return null;
        } else {
            return (TypeMirror) annotationAttributeValue.getValue();
        }

    }


    protected static AnnotationMirror getAnnotationMirror(Element element, Class<? extends Annotation> clazz) {
        String clazzName = clazz.getName();
        for (AnnotationMirror m : element.getAnnotationMirrors()) {
            if (m.getAnnotationType().toString().equals(clazzName)) {
                return m;
            }
        }
        return null;
    }

    protected static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }


    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType) {



        return getClassArrayAttributeFromAnnotationAsFqn(element, annotationType, "value");
    }

    public static String[] getClassArrayAttributeFromAnnotationAsFqn(Element element, Class<? extends Annotation> annotationType, String attributeName) {

        TypeMirror[] typeMirrorArray = getClassArrayAttributeFromAnnotationAsTypeMirror(element, annotationType, attributeName);
        String [] result = null;


        if (typeMirrorArray != null) {

            result = new String[typeMirrorArray.length];

            for (int i=0 ; i < typeMirrorArray.length ; i++) {
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
        AnnotationValue annotationAttributeValue = getAnnotationValue(annotationMirror, attributeName);
        if (annotationAttributeValue == null) {
            return new TypeMirror[0];
        } else {
            List<Attribute> typeMirrorList = (List<Attribute>) annotationAttributeValue.getValue();

            TypeMirror[] result = new TypeMirror[typeMirrorList.size()];

            for(int i=0 ; i < typeMirrorList.size() ; i++) {
                result[i] = (TypeMirror) typeMirrorList.get(i).type.getTypeArguments().get(0);
            }

            return result;
        }

    }


}
