package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Convenience class for accessing annotation values.
 */
public class AnnotationMirrorWrapper {

    private final AnnotationMirror annotationMirror;

    private AnnotationMirrorWrapper(AnnotationMirror annotationMirror) {

        if (annotationMirror == null) {
            throw new IllegalArgumentException("Passed annotationMirror must not be null");
        }

        this.annotationMirror = annotationMirror;
    }

    /**
     * Returns the wrapped AnnotationMirror.
     *
     * @return the wrapped AnnotationMirror instance
     */
    public AnnotationMirror unwrap() {
        return annotationMirror;
    }

    /**
     * Returns the "value" attribute
     *
     * @return an Optional of AnnotationValueWrapper
     */
    public Optional<AnnotationValueWrapper> getAttribute() {
        return getAttribute("value");
    }

    /**
     * Returns the annotations attribute of a specific name.
     *
     * @param name the name of the attribute
     * @return an Optional of AnnotationValueWrapper
     */
    public Optional<AnnotationValueWrapper> getAttribute(String name) {

        // throw exception if name is null
        if (name == null) {
            throw new IllegalArgumentException("passed attribute name must not be null");
        }

        // throw exception if attribute name is not existent
        if (!hasAttribute(name)) {
            throw new IllegalArgumentException("Passed attribute name is not valid. Annotation " + this.annotationMirror.getAnnotationType().asElement().getSimpleName() + " has the following attributes : " + getAttributeNames());
        }

        AnnotationValue annotationValue = AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror, name);
        return annotationValue != null ? Optional.of(AnnotationValueWrapper.wrap(annotationValue)) : Optional.empty();
    }

    /**
     * Returns the "value" attribute.
     * Will return its default value if not set explicitly.
     *
     * @return an Optional of AnnotationValueWrapper
     */
    public AnnotationValueWrapper getAttributeWithDefault() {
        return getAttributeWithDefault("value");
    }

    /**
     * Returns the annotations attribute of a specific name with its default value if not set explicitly.
     *
     * @param name the name of the attribute
     * @return an Optional of AnnotationValueWrapper
     */
    public AnnotationValueWrapper getAttributeWithDefault(String name) {

        // throw exception if name is null
        if (name == null) {
            throw new IllegalArgumentException("passed attribute name must not be null");
        }

        // throw exception if attribute name is not existent
        if (!hasAttribute(name)) {
            throw new IllegalArgumentException("Passed attribute name is not valid. Annotation " + this.annotationMirror.getAnnotationType().asElement().getSimpleName() + " has the following attributes : " + getAttributeNames());
        }


        AnnotationValue annotationValue = AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, name);
        return AnnotationValueWrapper.wrap(annotationValue);
    }

    /**
     * Tries to get annotation from element by using the annotation Class.
     *
     * @param element    the element to read the annotation from
     * @param annotation the annotation class
     * @return an Optional of AnnotationMirrorWrapper
     */
    public static Optional<AnnotationMirrorWrapper> get(Element element, Class<? extends Annotation> annotation) {

        if (element == null || annotation == null) {
            return Optional.empty();
        }

        AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, annotation);
        return annotationMirror != null ? Optional.of(new AnnotationMirrorWrapper(annotationMirror)) : Optional.empty();
    }

    /**
     * Tries to get annotation from element by using the annotation fully qualified name.
     *
     * @param element       the element to read the annotation from
     * @param annotationFqn the annotations fully qualified name
     * @return an Optional of AnnotationMirrorWrapper
     */
    public static Optional<AnnotationMirrorWrapper> get(Element element, String annotationFqn) {

        if (element == null || annotationFqn == null) {
            return Optional.empty();
        }

        AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, annotationFqn);
        return annotationMirror != null ? Optional.of(new AnnotationMirrorWrapper(annotationMirror)) : Optional.empty();
    }

    /**
     * Get all attribute names of the annotation.
     *
     * @return a set containing all attribute names
     */
    public Set<String> getAttributeNames() {
        return this.annotationMirror.getAnnotationType().asElement().getEnclosedElements().stream().filter(e -> e.getKind() == ElementKind.METHOD).map(e -> e.getSimpleName().toString()).collect(Collectors.toSet());
    }

    /**
     * Check if AnnotationMirror has an attribute with passed name.
     *
     * @param name The attribute name to check
     * @return true if attribute with passed name was found, otherwise false
     */
    public boolean hasAttribute(String name) {
        return getAttributeNames().contains(name);
    }


    /**
     * Returns the element corresponding to the wrapped AnnotationMirror.
     *
     * @return the TypeElement corresponding to this type
     */
    public TypeMirrorWrapper asElement() {
        return TypeMirrorWrapper.wrap(this.annotationMirror.getAnnotationType().asElement().asType());
    }

    /**
     * Wraps a TypeMirror instance
     *
     * @param annotationMirror the AnnotationMirror to set
     * @return The wrapper instance
     */
    public static AnnotationMirrorWrapper wrap(AnnotationMirror annotationMirror) {
        return new AnnotationMirrorWrapper(annotationMirror);
    }

    /**
     * Wraps an array of AnnotationMirror instances.
     *
     * @param annotationMirrors the annotation mirror instance array to wrap
     * @return an array that contains the wrapped instances
     */
    public static AnnotationMirrorWrapper[] wrap(AnnotationMirror[] annotationMirrors) {
        return annotationMirrors != null ? Arrays.stream(annotationMirrors).map(e -> AnnotationMirrorWrapper.wrap(e)).toArray(AnnotationMirrorWrapper[]::new): null;
    }
}
