package io.toolisticon.aptk.constraints;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.ElementUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;

final class UtilityFunctions {

    /**
     * Hidden.
     */
    private UtilityFunctions() {

    }

    /**
     * Get the annotation value of an annotation attribute.
     * @param annotationMirrorToCheck the annotation to get the attribute from
     * @param constraintAttributeName the name of the attribute to get
     * @return the AnnotationValue
     */
    public static AnnotationValue getAnnotationValueOfAttribute(AnnotationMirror annotationMirrorToCheck, String constraintAttributeName) {
        return AnnotationUtils.getAnnotationValueOfAttribute(annotationMirrorToCheck, constraintAttributeName);
    }

    /**
     * Get the annotation value of an annotation attribute.
     * @param annotationMirrorToCheck the annotation to get the attribute from
     * @param constraintAttributeName the name of the attribute to get
     * @return the AnnotationValue
     */
    public static AnnotationValue getAnnotationValueOfAttributeWithDefaults(AnnotationMirror annotationMirrorToCheck, String constraintAttributeName) {
        return AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirrorToCheck, constraintAttributeName);
    }


    /**
     * Get the constraint annotation from an AnnotationMirror.
     * @param annotationMirrorToCheck the AnnotationMirror to get the constraint annotation from
     * @param constraintAnnotationType the type of the constraint annotation to get
     * @param <T> The Type of the constraint
     * @return the constraint annotation
     */
    public static <T extends Annotation> T getConstraintAnnotationOfAnnotation(AnnotationMirror annotationMirrorToCheck, Class<T> constraintAnnotationType) {
        return annotationMirrorToCheck.getAnnotationType().asElement().getAnnotation(constraintAnnotationType);
    }

    public static <T extends Annotation> T getConstraintAnnotationOfAnnotationAttribute(AnnotationMirror annotationMirrorToCheck, String constraintAttributeName, Class<T> constraintAnnotationType) {
        T constraintAnnotation = null;
        for (ExecutableElement executableElement : annotationMirrorToCheck.getElementValues().keySet()) {

            if (executableElement.getSimpleName().toString().equals(constraintAttributeName)) {
                constraintAnnotation = executableElement.getAnnotation(constraintAnnotationType);
                break;
            }

        }
        return constraintAnnotation;
    }

    public static boolean isPlacedOnAnnotationType(Element element) {
        return ElementUtils.CheckKindOfElement.isAnnotation(element);
    }

    public static boolean isPlacedOnAnnotationAttribute(Element element) {
        return ElementUtils.CheckKindOfElement.isMethod(element) && ElementUtils.CheckKindOfElement.isAnnotation(element.getEnclosingElement());
    }

    public static String getSimpleName (AnnotationMirror annotationMirror) {
        return annotationMirror.getAnnotationType().asElement().getSimpleName().toString();
    }

}
