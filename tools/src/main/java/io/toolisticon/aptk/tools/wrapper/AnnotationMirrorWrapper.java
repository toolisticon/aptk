package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationUtils;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Convenience class for accessing annotation values.
 */
public class AnnotationMirrorWrapper {

    private final AnnotationMirror annotationMirror;

    private AnnotationMirrorWrapper(AnnotationMirror annotationMirror) {
        this.annotationMirror = annotationMirror;
    }

    public boolean isPresent () {
        return annotationMirror != null;
    }

    public AnnotationMirror unwrap(){
        return annotationMirror;
    }

    public AnnotationValueWrapper getAttribute() {
        return getAttribute("value");
    }

    public AnnotationValueWrapper getAttribute(String key) {
        return AnnotationValueWrapper.wrap(AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, key));
    }

    public AnnotationValueWrapper getAttributeWithDefault() {
        return getAttributeWithDefault("value");
    }

    public AnnotationValueWrapper getAttributeWithDefault(String key) {
        return AnnotationValueWrapper.wrap(AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, key));
    }

    public static <AT extends Annotation> AnnotationMirrorWrapper get(Element element, Class<AT> annotation) {
        return new AnnotationMirrorWrapper(AnnotationUtils.getAnnotationMirror(element, annotation));
    }

    public static AnnotationMirrorWrapper get(Element element, String annotation) {
        return new AnnotationMirrorWrapper(AnnotationUtils.getAnnotationMirror(element, annotation));
    }

    public static AnnotationMirrorWrapper wrap(AnnotationMirror annotationMirror) {
        return new AnnotationMirrorWrapper(annotationMirror);
    }
}
