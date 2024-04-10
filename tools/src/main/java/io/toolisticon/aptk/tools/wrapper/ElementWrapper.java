package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.InvocationTargetException;
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
        return getPackage().getQualifiedName();
    }

    /**
     * Gets the Simple Name of the package.
     *
     * @return the simple name of the package
     */
    public String getSimplePackageName() {
        return getPackage().getSimpleName();
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
     * Gets the module of the wrapped element
     * @return an optional containing the wrapped module element
     */
    public Optional<ModuleElementWrapper> getModule() {
        try {
            Element element = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(this.element, ElementKind.valueOf("MODULE"));
            return element != null ? Optional.of(ModuleElementWrapper.wrap(element)) : Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
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


    public FluentElementFilter<Element> filterEnclosedElements() {
        return FluentElementFilter.createFluentElementFilter(getEnclosedElements().stream().map(ElementWrapper::unwrap).collect(Collectors.toList()));
    }

    public FluentElementFilter<Element> filterFlattenedEnclosedElementTree() {
        return filterFlattenedEnclosedElementTree(false);
    }

    public FluentElementFilter<Element> filterFlattenedEnclosedElementTree(boolean includeSelf) {
        return filterFlattenedEnclosedElementTree(includeSelf, Integer.MAX_VALUE);
    }

    public FluentElementFilter<Element> filterFlattenedEnclosedElementTree(boolean includeSelf, int maxDepth) {
        return FluentElementFilter.createFluentElementFilter(getFlattenedEnclosedElementTree(includeSelf, maxDepth).stream().map(ElementWrapper::unwrap).collect(Collectors.toList()));
    }

    /**
     * Gets all wrapped annotations of element.
     *
     * @return a list containing all annotations of the element
     */
    public List<AnnotationMirrorWrapper> getAnnotations() {
        return this.element.getAnnotationMirrors().stream().map(AnnotationMirrorWrapper::wrap).collect(Collectors.toList());
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

    public <EW extends ElementWrapper<?>> Optional<EW> getFirstEnclosingElementWithKind(ElementKind kind) {
        Element element = ElementUtils.AccessEnclosingElements.<TypeElement>getFirstEnclosingElementOfKind(this.element, kind);

        if (element == null) {
            return Optional.empty();
        }

        ElementWrapper<Element> elementWrapper = ElementWrapper.wrap(element);

        if (elementWrapper.isTypeElement()) {
            return (Optional<EW>) Optional.of(ElementWrapper.toTypeElement(elementWrapper));
        } else if (elementWrapper.isExecutableElement()) {
            return (Optional<EW>) Optional.of(ElementWrapper.toExecutableElementWrapper(elementWrapper));
        } else if (elementWrapper.isPackageElement()) {
            return (Optional<EW>) Optional.of(ElementWrapper.toPackageElement(elementWrapper));
        } else if (elementWrapper.isVariableElement()) {
            return (Optional<EW>) Optional.of(ElementWrapper.toVariableElementWrapper(elementWrapper));
        } else if (elementWrapper.isTypeParameterElement()) {
            return (Optional<EW>) Optional.of(ElementWrapper.toTypeParameterElementWrapper(elementWrapper));
        }

        return (Optional<EW>) Optional.of(elementWrapper);
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
     * @param includeSelf flag to configure if wrapped element should be included in result or not
     * @return a list of all elements of the enclosed element tree plus element itself if includeSelf flag is set
     */
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf) {
        return getFlattenedEnclosedElementTree(includeSelf, Integer.MAX_VALUE);
    }

    /**
     * Returns the flattened enclosed element tree as a list. All elements up to a max depth will be included in the result.
     *
     * @param includeSelf flag to configure if wrapped element should be included in result or not
     * @param maxDepth    The max tree depth to be included in result
     * @return a list of all elements up to the max depth of the enclosed element tree plus element itself if includeSelf flag is set
     */
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf, int maxDepth) {
        return ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(this.unwrap(), includeSelf, maxDepth)
                .stream().map(ElementWrapper::wrap).collect(Collectors.toList());
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
     * Checks if passed annotation is present.
     *
     * @param annotation the annotation tom check
     * @return true if passed annotation is present, otherwise false
     */
    public boolean hasAnnotation(Class<? extends Annotation> annotation) {
        return getAnnotationMirror(annotation).isPresent();
    }

    /**
     * Checks if passed annotation is present.
     *
     * @param annotationFqn the annotation tom check
     * @return true if passed annotation is present, otherwise false
     */
    public boolean hasAnnotation(String annotationFqn) {
        return getAnnotationMirror(annotationFqn).isPresent();
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

    public Optional<List<AnnotationMirrorWrapper>> getRepeatableAnnotation(Class<? extends Annotation> annotationType) {

        List<AnnotationMirrorWrapper> result = new ArrayList<>();

        /*-
        // handle repeatable annotation
        TypeElementWrapper annotationTypeElement = TypeElementWrapper.getByClass(annotationType).get();
         repeatableAnnotations = annotationTypeElement.getAnnotation(this.ge)

        if(annotationTypeElement.isRepeatableAnnotation() && this.this.getRepeatableAnnotation(annotationType)) {

        }

        if ()
            */
        return Optional.of(result);
    }

    /**
     * Applies a visitor to this element.
     *
     * @param v   the visitor operating on this element
     * @param p   additional parameter to the visitor
     * @param <P> the type of the additional parameter to the visitor's methods
     * @param <R> the return type of the visitor's methods
     * @return a visitor-specified result
     */
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
        return element.accept(v, p);
    }

    public CompileMessageWriter.CompileMessageWriterStart compilerMessage() {
        return CompileMessageWriter.at(this.element);
    }

    public CompileMessageWriter.CompileMessageWriterStart compilerMessage(AnnotationMirror annotationMirror) {
        return CompileMessageWriter.at(this.element, annotationMirror);
    }

    public CompileMessageWriter.CompileMessageWriterStart compilerMessage(AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        return CompileMessageWriter.at(this.element, annotationMirror, annotationValue);
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
     * @param <T>     The ElementWrappers wrapped Element type
     * @return an ElementWrapper instance
     */
    public static <T extends Element> ElementWrapper<T> wrap(T element) {
        return new ElementWrapper<>(element);
    }

    /**
     * Wraps list of Elements.
     *
     * @param elements The list of Elements to wrap
     * @param <T>      The ElementWrappers wrapped Element type
     * @return a List of Wrapped Elements, or an empty list
     */
    public static <T extends Element> List<ElementWrapper<T>> wrap(List<T> elements) {
        return elements != null ? elements.stream().map(ElementWrapper::wrap).collect(Collectors.toList()) : new ArrayList<>();
    }

    // --------------------------------------------------------------------------
    // Helper functions for usage with stream api
    // --------------------------------------------------------------------------

    /**
     * Checks if wrapped element represents a module.
     *
     * @return true if wrapped element represents a module, otherwise false
     */
    public boolean isModule() {
        return ElementUtils.CheckKindOfElement.isModule(this.element);
    }

    /**
     * Checks if wrapped element represents a package.
     *
     * @return true if wrapped element represents a package, otherwise false
     */
    public boolean isPackage() {
        return ElementUtils.CheckKindOfElement.isPackage(this.element);
    }

    /**
     * Checks if wrapped element represents a class.
     *
     * @return true if wrapped element represents a class, otherwise false
     */
    public boolean isClass() {
        return ElementUtils.CheckKindOfElement.isClass(this.element);
    }


    /**
     * Checks if wrapped element represents an interface.
     *
     * @return true if wrapped element represents an interface, otherwise false
     */
    public boolean isInterface() {
        return ElementUtils.CheckKindOfElement.isInterface(this.element);
    }


    /**
     * Checks if wrapped element represents an enum.
     *
     * @return true if wrapped element represents an enum, otherwise false
     */
    public boolean isEnum() {
        return ElementUtils.CheckKindOfElement.isEnum(this.element);
    }

    /**
     * Checks if wrapped element represents a record.
     *
     * @return true if wrapped element represents a record, otherwise false
     */
    public boolean isRecord() {
        return ElementUtils.CheckKindOfElement.isRecord(this.element);
    }

    /**
     * Checks if wrapped element represents a record.
     *
     * @return true if wrapped element represents a record, otherwise false
     */
    public boolean isRecordComponent() {
        return ElementUtils.CheckKindOfElement.isRecordComponent(this.element);
    }

    /**
     * Checks if wrapped element represents an annotation.
     *
     * @return true if wrapped element represents an annotation, otherwise false
     */
    public boolean isAnnotation() {
        return ElementUtils.CheckKindOfElement.isAnnotation(this.element);
    }


    /**
     * Checks if wrapped element is a repeatable annotation.
     *
     * @return if wrapped element represents a repeatable annotation, otherwise false
     */
    public boolean isRepeatableAnnotation() {
        return isAnnotation() && hasAnnotation(Repeatable.class);
    }

    /**
     * Gets an Optional containing the wrapped repeatable TypeMirror, if the wrapped represents a repeatable annotation.
     *
     * @return The wrapped repeatable annotation Type Mirror or an empty Optional if it doesn't exist
     */
    public Optional<TypeMirrorWrapper> getRepeatableWrapperType() {

        if (isRepeatableAnnotation()) {
            return Optional.of(getAnnotationMirror(Repeatable.class).get().getAttribute().get().getClassValue());
        }

        return Optional.empty();
    }

    /**
     * Checks if wrapped element represents a constructor.
     *
     * @return true if wrapped element represents a constructor, otherwise false
     */
    public boolean isConstructor() {
        return ElementUtils.CheckKindOfElement.isConstructor(this.element);
    }

    /**
     * Checks if wrapped element represents a method.
     *
     * @return true if wrapped element represents a method, otherwise false
     */
    public boolean isMethod() {
        return ElementUtils.CheckKindOfElement.isMethod(this.element);
    }

    /**
     * Checks if wrapped element represents a field.
     *
     * @return true if wrapped element represents a field, otherwise false
     */
    public boolean isField() {
        return ElementUtils.CheckKindOfElement.isField(element);
    }


    /**
     * Checks if wrapped element represents an annotation attribute.
     *
     * @return true if wrapped element represents an annotation attribute, otherwise false
     */
    public boolean isAnnotationAttribute() {
        return ElementUtils.CheckKindOfElement.isAnnotationAttribute(element);
    }

    /**
     * Checks if wrapped element represents a constructor parameter.
     *
     * @return true if wrapped element represents a constructor parameter, otherwise false
     */
    public boolean isConstructorParameter() {
        return ElementUtils.CheckKindOfElement.isConstructorParameter(element);
    }


    /**
     * Checks if wrapped element represents a method parameter.
     *
     * @return true if wrapped element represents a method parameter, otherwise false
     */
    public boolean isMethodParameter() {
        return ElementUtils.CheckKindOfElement.isMethodParameter(element);
    }


    /**
     * Checks if wrapped element is a PackageElement.
     *
     * @return true if wrapped element is a PackageElement, otherwise false
     */
    public boolean isPackageElement() {
        return ElementUtils.CastElement.isPackageElement(element);
    }


    /**
     * Checks if wrapped element is a TypeElement.
     *
     * @return true if wrapped element is a TypeElement, otherwise false
     */
    public boolean isTypeElement() {
        return ElementUtils.CastElement.isTypeElement(element);
    }

    /**
     * Checks if wrapped element is a RecordComponentElement.
     *
     * @return true if wrapped element is a RecordComponentElement, otherwise false
     */
    public boolean isRecordComponentElement() {
        return ElementUtils.CastElement.isRecordComponentElement(element);
    }

    /**
     * Checks if wrapped element is a ExecutableElement.
     *
     * @return true if wrapped element is a ExecutableElement, otherwise false
     */
    public boolean isExecutableElement() {
        return ElementUtils.CastElement.isExecutableElement(element);
    }


    /**
     * Checks if wrapped element is a VariableElement.
     *
     * @return true if wrapped element is a VariableElement, otherwise false
     */
    public boolean isVariableElement() {
        return ElementUtils.CastElement.isVariableElement(element);
    }

    /**
     * Checks if wrapped element is a TypeParameterElement.
     *
     * @return true if wrapped element is a TypeParameterElement, otherwise false
     */
    public boolean isTypeParameterElement() {
        return ElementUtils.CastElement.isTypeParameterElement(element);
    }

    /**
     * Checks if wrapped element is a ModuleElement.
     *
     * @return true if wrapped element is a ModuleElement, otherwise false
     */
    public boolean isModuleElement() {
        return ElementUtils.CastElement.isModuleElement(this.element);
    }

    /**
     * Converts wrapper to a PackageElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a PackageElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be cast to target Element type
     */
    public static PackageElementWrapper toPackageElement(ElementWrapper<? extends Element> wrapper) {
        return PackageElementWrapper.wrap(ElementUtils.CastElement.castToPackageElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a TypeElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a TypeElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be cast to target Element type
     */
    public static TypeElementWrapper toTypeElement(ElementWrapper<? extends Element> wrapper) {
        return TypeElementWrapper.wrap(ElementUtils.CastElement.castToTypeElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a TypeParameterElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a TypeParameterElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be cast to target Element type
     */
    public static TypeParameterElementWrapper toTypeParameterElementWrapper(ElementWrapper<? extends Element> wrapper) {
        return TypeParameterElementWrapper.wrap(ElementUtils.CastElement.castToTypeParameterElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a VariableElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a VariableElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be cast to target Element type
     */
    public static VariableElementWrapper toVariableElementWrapper(ElementWrapper<? extends Element> wrapper) {
        return VariableElementWrapper.wrap(ElementUtils.CastElement.castToVariableElement(wrapper.unwrap()));
    }

    /**
     * Converts wrapper to a ExecutableElementWrapper by casting and re-wrapping wrapped element.
     *
     * @param wrapper the wrapper to convert
     * @return a ExecutableElementWrapper instance
     * @throws ClassCastException if wrapped Element cannot be cast to target Element type
     */
    public static ExecutableElementWrapper toExecutableElementWrapper(ElementWrapper<? extends Element> wrapper) {
        return ExecutableElementWrapper.wrap(ElementUtils.CastElement.castToExecutableElement(wrapper.unwrap()));
    }

    protected <TARGET_TYPE> Optional<TARGET_TYPE> invokeParameterlessMethodOfElement(String interfaceName, String methodName) {
        return ElementWrapper.<TARGET_TYPE>invokeParameterlessMethodOfElement(element, interfaceName, methodName);
    }

    protected static <TARGET_TYPE> Optional<TARGET_TYPE> invokeParameterlessMethodOfElement(Object instance, String interfaceName, String methodName) {
        try {
            Class<?> interfaceClass = Class.forName(interfaceName);

            return Optional.ofNullable((TARGET_TYPE) interfaceClass.getMethod(methodName).invoke(instance));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
