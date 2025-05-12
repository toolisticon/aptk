package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
     * Get DeclaredType of wrapped annotation.
     *
     * @return the annotations declared type
     */
    public DeclaredType getAnnotationType() {
        return this.annotationMirror.getAnnotationType();
    }

    /**
     * Get wrapped DeclaredType of wrapped annotation.
     *
     * @return the annotations declared type
     */
    public TypeMirrorWrapper getAnnotationTypeAsWrappedTypeMirror() {
        return TypeMirrorWrapper.wrap(getAnnotationType());
    }

    /**
     * Get wrapped DeclaredType's TypeElement of wrapped annotation.
     *
     * @return the annotations declared type as wrapped TypeElement
     */
    public TypeElementWrapper getAnnotationTypeAsWrappedTypeElement() {
        return getAnnotationTypeAsWrappedTypeMirror().getTypeElement().get();
    }


    /**
     * Gets the wrapped type mirror of the 'value' annotation attribute.
     *
     * @return an optional containing the wrapped type mirror of the annotation attribute or an empty Optional if the attribute doesn't exist.
     */
    public Optional<TypeMirrorWrapper> getAttributeTypeMirror() {
        return getAttributeTypeMirror("value");
    }

    /**
     * Gets the wrapped type mirror of an annotation attribute.
     *
     * @param attributeName the name of the attribute
     * @return an optional containing the wrapped type mirror of the annotation attribute or an empty Optional if the attribute doesn't exist.
     */
    public Optional<TypeMirrorWrapper> getAttributeTypeMirror(String attributeName) {
        Optional<ExecutableElementWrapper> annotationAttribute = this.getAnnotationTypeAsWrappedTypeElement().getMethod(attributeName);
        return annotationAttribute.map(ExecutableElementWrapper::getReturnType);
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
     * Returns the annotations attribute marked with the passed annotation.
     *
     * @param annotationType the annotation type to search the attribute for
     * @return an Optional of AnnotationValueWrapper
     * @throws IllegalArgumentException if no attribute annotated with the passed annotation type can be found in the annotation type.
     */
    public Optional<AnnotationValueWrapper> getAttributeByAnnotation(Class<? extends Annotation> annotationType) {

        // throw exception if name is null
        if (annotationType == null) {
            throw new IllegalArgumentException("passed annotation type must not be null");
        }
        
        List<ExecutableElement> result = getAnnotationTypeAsWrappedTypeElement().filterEnclosedElements()
        	.applyFilter(AptkCoreMatchers.IS_METHOD)
        	.applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(annotationType)
        	.getResult();

        if (result.size() != 1) {
        	throw new IllegalArgumentException("There must be exactly one attribute annotated with the '" + annotationType.getCanonicalName() + "' annotation.");
        }

        String name = result.get(0).getSimpleName().toString();

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
     * Returns the annotations attribute annotated with the passed annotation with its default value if not set explicitly.
     *
     * @param annotationType the annotation type to search the attribute for
     * @return an AnnotationValueWrapper instance
     * @throws IllegalArgumentException if no attribute annotated with the passed annotation type can be found in the annotation type.
     */
    public AnnotationValueWrapper getAttributeWithDefaultByAnnotation(Class<? extends Annotation> annotationType) {

        // throw exception if name is null
        if (annotationType == null) {
            throw new IllegalArgumentException("passed annotation type must not be null");
        }

        
        List<ExecutableElement> result = getAnnotationTypeAsWrappedTypeElement().filterEnclosedElements()
            	.applyFilter(AptkCoreMatchers.IS_METHOD)
            	.applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(annotationType)
            	.getResult();

        if (result.size() != 1) {
        	throw new IllegalArgumentException("There must be exactly one attribute annotated with the '" + annotationType.getCanonicalName() + "' annotation.");
        }

        String name = result.get(0).getSimpleName().toString();



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
     * Get all attribute names of the annotation.
     *
     * @return a list containing all attribute names in declaration order
     */
    public List<String> getAttributeNamesInDeclarationOrder() {
        return this.annotationMirror.getAnnotationType().asElement().getEnclosedElements().stream().filter(e -> e.getKind() == ElementKind.METHOD).map(e -> e.getSimpleName().toString()).collect(Collectors.toList());
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
     * Returns the wrapped TypeMirror of the AnnotationMirrors annotation type.
     *
     * @return the wrapped TypeMirror corresponding to the wrapped TypeMirror
     */
    public TypeMirrorWrapper asTypeMirror() {
        return getAnnotationTypeAsWrappedTypeMirror();
    }

    /**
     * Returns the wrapped TypeElement of the AnnotationMirrors annotation type.
     *
     * @return the wrapped TypeElement corresponding to the wrapped TypeMirror
     */
    public TypeElementWrapper asElement() {
        return this.getAnnotationTypeAsWrappedTypeElement();
    }

    /**
     * Gets a string representation of the annotation.
     *
     * @return the annotations string representation
     */
    public String getStringRepresentation() {

        String stringBuilder = "@" + this.asElement().getSimpleName() +
                getAttributeNamesInDeclarationOrder().stream()
                        .map(e -> {
                            Optional<AnnotationValueWrapper> optionalAnnotationValueWrapper = getAttribute(e);
                            return optionalAnnotationValueWrapper.map(annotationValueWrapper -> e + " = " + getAnnotationAttributeValueStringRepresentation(annotationValueWrapper)).orElse(null);
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(", ", "(", ")"));

        return stringBuilder.replaceAll("\"", "\\\\\"");
    }

    /**
     * Gets a string representation of the annotation.
     *
     * @return the annotations string representation
     */
    public String getStringRepresentationWithDefaults() {

        String stringBuilder = "@" + this.asElement().getSimpleName() +
                getAttributeNamesInDeclarationOrder().stream()
                        .map(e -> e + " = " + getAnnotationAttributeValueStringRepresentation(getAttributeWithDefault(e)))
                        .collect(Collectors.joining(", ", "(", ")"));

        return stringBuilder.replaceAll("\"", "\\\\\"");
    }

    String getAnnotationAttributeValueStringRepresentation(AnnotationValueWrapper annotationValueWrapper) {
        if (annotationValueWrapper.isArray()) {
            return annotationValueWrapper.getArrayValue().stream().map(this::getAnnotationAttributeValueStringRepresentation).collect(Collectors.joining(", ", "{", "}"));
        } else if (annotationValueWrapper.isEnum()) {
            return annotationValueWrapper.getEnumValue().asType().getSimpleName() + "." + annotationValueWrapper.getEnumValue().getSimpleName();
        } else if (annotationValueWrapper.isClass()) {
            return annotationValueWrapper.getClassValue().getSimpleName() + ".class";
        } else {
            return annotationValueWrapper.unwrap().toString();
        }
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
        return annotationMirrors != null ? Arrays.stream(annotationMirrors).map(AnnotationMirrorWrapper::wrap).toArray(AnnotationMirrorWrapper[]::new) : null;
    }
}
