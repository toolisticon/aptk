package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides some convenience functions to work with Elements
 */
public class ElementWrapper<E extends Element> {

    protected final E element;

    protected ElementWrapper(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Passed Element to wrap must not be null.");
        }
        this.element = element;
    }

    /**
     * Gets the wrapped Element.
     *
     * @return the wrapped Element
     */
    public E unwrap() {
        return this.element;
    }


    /**
     * Gets the PackageElement of the enclosing package.
     *
     * @return the PackageElement of the enclosing Package
     */
    public PackageElementWrapper getPackage() {
        return PackageElementWrapper.wrap(ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(element, ElementKind.PACKAGE));
    }

    /**
     * Gets the fully qualified name of the package
     *
     * @return the fully qualified name of the package
     */
    public String getPackageName() {
        return getPackage().getQualifiedName().toString();
    }

    /**
     * Gets the Simple Name of the package.
     *
     * @return the simple name of the package
     */
    public String getSimplePackageName() {
        return getPackage().getSimpleName().toString();
    }

    /**
     * Checks if wrapped elements package name equals passed name.
     *
     * @param name the qualified name of the package
     * @return true if package names match, otherwise false
     */
    public boolean hasPackageName(String name) {
        return name != null && this.getPackageName().equals(name);
    }

    /**
     * Checks if wrapped elements simple package name equals passed name.
     *
     * @param name the qualified name of the package
     * @return true if package names match, otherwise false
     */
    public boolean hasSimplePackageName(String name) {
        return name != null && this.getSimplePackageName().equals(name);
    }


    /**
     * Gets the simple name of the element.
     *
     * @return the simple name of the element
     */
    public String getSimpleName() {
        return element.getSimpleName().toString();
    }


    /**
     * Checks if simple name of element matches passed name.
     *
     * @param name the name to check for
     * @return true if elements name and passed name match, otherwise false
     */
    public boolean hasSimpleName(String name) {
        return name != null && this.getSimpleName().equals(name);
    }

    /**
     * Checks if element has all passed modifiers are present.
     *
     * @param modifiersToCheck the modifiers to check
     * @return true, if all passed modifiers are present, otherwise false
     */
    public boolean hasModifiers(Modifier... modifiersToCheck) {
        if (modifiersToCheck == null) {
            return false;
        }

        for (Modifier modifier : modifiersToCheck) {
            if (!element.getModifiers().contains(modifier)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Starts a validation of wrapped element based on FluentElementValidator.
     * <p>
     * The FluentElementValidator API is pretty powerful if you want to automatically write compiler messages if validations fail.
     *
     * @return a FluentElementValidator instance
     */
    public FluentElementValidator<E> validateWithFluentElementValidator() {
        return FluentElementValidator.createFluentElementValidator(this.unwrap());
    }

    /**
     * Starts a validation based on a lambda style api.
     * <p>
     * The validator is pretty flexible. The downside is that it's not possible to provide automatic compiler messages in case of failing validations.
     *
     * @return The validator api instance
     */
    public ElementWrapperValidatorInterface.FirstValidation<ElementWrapper<E>> validate() {
        return ElementWrapperValidator.startValidation(this);
    }

    /**
     * Gets the annotation
     *
     * @param annotationFqn the annotation to get
     * @return the AnnotationMirror wrapped by an AnnotationWrapper instance,
     */
    public Optional<AnnotationMirrorWrapper> getAnnotation(String annotationFqn) {
        return AnnotationMirrorWrapper.get(element, annotationFqn);
    }

    /**
     * Returns the type defined by this element.
     * A generic element defines a family of types, not just one. If this is a generic element, a prototypical type is returned. This is the element's invocation on the type variables corresponding to its own formal type parameters. For example, for the generic class element C , the parameterized type C  is returned. The Types utility interface has more general methods for obtaining the full range of types defined by an element.
     *
     * @return the type defined by this element
     */
    public TypeMirrorWrapper asType() {
        return TypeMirrorWrapper.wrap(this.element.asType());
    }

    /**
     * Returns the kind of this element.
     *
     * @return the kind of this element
     */
    public ElementKind getKind() {
        return this.element.getKind();
    }

    /**
     * Returns the modifiers of this element, excluding annotations. Implicit modifiers, such as the public and static modifiers of interface members, are included.
     *
     * @return the modifiers of this element, or an empty set if there are none
     */
    public Set<Modifier> getModifiers() {
        return this.element.getModifiers();
    }

    /**
     * Returns the enclosing element.
     *
     * @return an Optional containing the enclosing element, or an empty Optional if it doesn't exist
     */
    public Optional<ElementWrapper<Element>> getEnclosingElement() {
        Element enclosingElement = element.getEnclosingElement();
        return enclosingElement != null ? Optional.of(ElementWrapper.wrap(enclosingElement)) : Optional.empty();
    }

    /**
     * Returns the hierarchy of enclosing element. (bottom up)
     * Doesn't include the wrapped element.
     *
     * @return a list containing the hierarchy of enclosing elements, or empty list
     */
    public List<ElementWrapper<Element>> getAllEnclosingElements() {
        return getAllEnclosingElements(false);
    }

    /**
     * Returns the hierarchy of enclosing element. (bottom up)
     *
     * @param includeWrappedElement flag whether wrapped element should be included or not
     * @return a list containing the hierarchy of enclosing elements, or empty list
     */
    public List<ElementWrapper<Element>> getAllEnclosingElements(boolean includeWrappedElement) {
        return ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(element, includeWrappedElement)
                .stream().map(ElementWrapper::wrap).collect(Collectors.toList());
    }


    /**
     * Returns the wrapped enclosed elements as a List.
     *
     * @return a List containing the wrapped enclosed elements or an empty list
     */
    public List<ElementWrapper<? extends Element>> getEnclosedElements() {
        return element.getEnclosedElements().stream().map(ElementWrapper::wrap).collect(Collectors.toList());
    }

    /**
     * Returns the flattened enclosed element tree as a list.
     *
     * @return a list of all elements of the enclosed element tree
     */
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree() {
        return getFlattenedEnclosedElementTree(false);
    }

    /**
     * Returns the flattened enclosed element tree as a list.
     *
     * @return a list of all elements of the enclosed element tree plus element itself if includeSelf flag is set
     */
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf) {
        return getFlattenedEnclosedElementTree(false, Integer.MAX_VALUE);
    }

    /**
     * Returns the flattened enclosed element tree as a list. All elements up to a max depth will be included in the result.
     *
     * @return a list of all elements up to the max depth of the enclosed element tree plus element itself if includeSelf flag is set
     */
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf, int maxDepth) {
        return ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(this.unwrap(), includeSelf, maxDepth)
                .stream().map(ExecutableElementWrapper::wrap).collect(Collectors.toList());
    }

    /**
     * Gets all wrapped AnnotationMirrors related with the Element as a List.
     *
     * @return all wrapped AnnotationMirrors related with the Element as a List or an empty List
     */
    public List<AnnotationMirrorWrapper> getAnnotationMirrors() {
        return this.element.getAnnotationMirrors().stream().map(AnnotationMirrorWrapper::wrap).collect(Collectors.toList());
    }

    /**
     * Returns an annotation of a specific type.
     *
     * @param annotationType the type to search
     * @return the annotation or null if Element isn't annotated with annotation
     */
    public Optional<AnnotationMirrorWrapper> getAnnotationMirror(Class<? extends Annotation> annotationType) {
        AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, annotationType);
        return annotationMirror != null ? Optional.of(AnnotationMirrorWrapper.wrap(annotationMirror)) : Optional.empty();
    }

    /**
     * Returns an Optional of AnnotationMirror of a specific type.
     *
     * @param annotationTypeFqn the type to search
     * @return the annotation or null if Element isn't annotated with annotation
     */
    public Optional<AnnotationMirrorWrapper> getAnnotationMirror(String annotationTypeFqn) {
        AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, annotationTypeFqn);
        return annotationMirror != null ? Optional.of(AnnotationMirrorWrapper.wrap(annotationMirror)) : Optional.empty();
    }

    /**
     * Returns an annotation of a specific type.
     * This should only be used if annotation doesn't have Class based attributes, since Classes might not be compiled already.
     *
     * @param annotationType the type to search
     * @param <A>            The annotation type
     * @return the annotation or null if Element isn't annotated with annotation
     */
    public <A extends Annotation> Optional<A> getAnnotation(Class<A> annotationType) {
        return Optional.ofNullable(element.getAnnotation(annotationType));
    }

    /**
     * Applies a visitor to this element.
     *
     * @param v the visitor operating on this element
     * @param p additional parameter to the visitor
     * @return a visitor-specified result
     */
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
        return element.accept(v, p);
    }

    /**
     * Gets annotations by type
     *
     * @param annotationType the Class object corresponding to the annotation type
     * @param <A>            the annotation type
     * @return this construct's annotations for the specified annotation type if present on this construct, else an empty array
     */
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
        return element.getAnnotationsByType(annotationType);
    }


    @Override
    public int hashCode() {
        return element.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        return element.equals(obj);
    }


    // --------------------------------------------------------------------------
    // Central wrappers
    // --------------------------------------------------------------------------


    /**
     * Wraps an Element.
     *
     * @param element the Element to wrap
     * @return an ElementWrapper instance
     */
    public static <T extends Element> ElementWrapper<T> wrap(T element) {
        return new ElementWrapper<>(element);
    }

    /**
     * Wraps list of Elements.
     *
     * @param elements The list of Elements to wrap
     * @return a List of Wrapped Elements, or an empty list
     */
    public static <T extends Element> List<ElementWrapper<T>> wrap(List<T> elements) {
        return elements != null ? elements.stream().map(ElementWrapper::wrap).collect(Collectors.toList()) : new ArrayList<>();
    }

    // --------------------------------------------------------------------------
    // Helper functions for usage with stream api
    // --------------------------------------------------------------------------

    /**
     * Checks if passed element represents a module.
     *
     * @param element the element to check
     * @return true if passed element represents a module, otherwise false
     */
    public static boolean isModule(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isModule(element.unwrap());
    }

    /**
     * Checks if passed element represents a package.
     *
     * @param element the element to check
     * @return true if passed element represents a package, otherwise false
     */
    public static boolean isPackage(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isPackage(element.unwrap());
    }

    /**
     * Checks if passed element represents a class.
     *
     * @param element the element to check
     * @return true if passed element represents a class, otherwise false
     */
    public static boolean isClass(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isClass(element.unwrap());
    }

    /**
     * Checks if passed element represents an interface.
     *
     * @param element the element to check
     * @return true if passed element represents an interface, otherwise false
     */
    public static boolean isInterface(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isInterface(element.unwrap());
    }

    /**
     * Checks if passed element represents an enum.
     *
     * @param element the element to check
     * @return true if passed element represents an enum, otherwise false
     */
    public static boolean isEnum(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isEnum(element.unwrap());
    }

    /**
     * Checks if passed element represents an annotation.
     *
     * @param element the element to check
     * @return true if passed element represents an annotation, otherwise false
     */
    public static boolean isAnnotation(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isAnnotation(element.unwrap());
    }

    /**
     * Checks if passed element represents a constructor.
     *
     * @param element the element to check
     * @return true if passed element represents a constructor, otherwise false
     */
    public static boolean isConstructor(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isConstructor(element.unwrap());
    }

    /**
     * Checks if passed element represents a method.
     *
     * @param element the element to check
     * @return true if passed element represents a method, otherwise false
     */
    public static boolean isMethod(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isMethod(element.unwrap());
    }

    /**
     * Checks if passed element represents a field.
     *
     * @param element the element to check
     * @return true if passed element represents a field, otherwise false
     */
    public static boolean isField(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isField(element.unwrap());
    }

    /**
     * Checks if passed element represents an annotation attribute.
     *
     * @param element the element to check
     * @return true if passed element represents an annotation attribute, otherwise false
     */
    public static boolean isAnnotationAttribute(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isAnnotationAttribute(element.unwrap());
    }

    /**
     * Checks if passed element represents a constructor parameter.
     *
     * @param element the element to check
     * @return true if passed element represents a constructor parameter, otherwise false
     */
    public static boolean isConstructorParameter(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isConstructorParameter(element.unwrap());
    }

    /**
     * Checks if passed element represents a method parameter.
     *
     * @param element the element to check
     * @return true if passed element represents a method parameter, otherwise false
     */
    public static boolean isMethodParameter(ElementWrapper<? extends Element> element) {
        return ElementUtils.CheckKindOfElement.isMethodParameter(element.unwrap());
    }

    /**
     * Checks if wrapped element is a PackageElement.
     *
     * @param element the element to check
     * @return true if passed element is a PackageElement, otherwise false
     */
    public static boolean isPackageElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isPackageElement(element.unwrap());
    }

    /**
     * Checks if wrapped element is a TypeElement.
     *
     * @param element the element to check
     * @return true if passed element is a TypeElement, otherwise false
     */
    public static boolean isTypeElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isTypeElement(element.unwrap());
    }

    /**
     * Checks if wrapped element is a ExecutableElement.
     *
     * @param element the element to check
     * @return true if passed element is a ExecutableElement, otherwise false
     */
    public static boolean isExecutableElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isExecutableElement(element.unwrap());
    }

    /**
     * Checks if wrapped element is a VariableElement.
     *
     * @param element the element to check
     * @return true if passed element is a VariableElement, otherwise false
     */
    public static boolean isVariableElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isVariableElement(element.unwrap());
    }

    /**
     * Checks if wrapped element is a TypeParameterElement.
     *
     * @param element the element to check
     * @return true if passed element is a TypeParameterElement, otherwise false
     */
    public static boolean isTypeParameterElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isTypeParameterElement(element.unwrap());
    }

    /**
     * Checks if wrapped element is a ModuleElement.
     *
     * @param element the element to check
     * @return true if passed element is a ModuleElement, otherwise false
     */
    public static boolean isModuleElement(ElementWrapper<? extends Element> element) {
        return ElementUtils.CastElement.isModuleElement(element.unwrap());
    }

    /**
     * Converts wrapper to a PackageElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a PackageElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be casted to target Element type
     */
    public static PackageElementWrapper toPackageElement(ElementWrapper<? extends Element> wrapper) {
        return PackageElementWrapper.wrap(ElementUtils.CastElement.castToPackageElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a TypeElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a TypeElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be casted to target Element type
     */
    public static TypeElementWrapper toTypeElement(ElementWrapper<? extends Element> wrapper) {
        return TypeElementWrapper.wrap(ElementUtils.CastElement.castToTypeElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a TypeParameterElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a TypeParameterElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be casted to target Element type
     */
    public static TypeParameterElementWrapper toTypeParameterElementWrapper(ElementWrapper<? extends Element> wrapper) {
        return TypeParameterElementWrapper.wrap(ElementUtils.CastElement.castToTypeParameterElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a VariableElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a VariableElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be casted to target Element type
     */
    public static VariableElementWrapper toVariableElementWrapper(ElementWrapper<? extends Element> wrapper) {
        return VariableElementWrapper.wrap(ElementUtils.CastElement.castToVariableElement(wrapper.unwrap()));
    }


}
