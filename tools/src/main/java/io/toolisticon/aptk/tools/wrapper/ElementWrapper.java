package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentvalidator.FluentElementValidator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides some convenience functions to work with Elements
 */
public class ElementWrapper<E extends Element> {

    protected final E element;

    protected ElementWrapper(E element) {
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
    public PackageElement getPackage() {
        return ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(element, ElementKind.PACKAGE);
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

    public boolean hasPackageName(String name) {
        return name != null && this.getPackageName().equals(name);
    }

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
        for (Modifier modifier : modifiersToCheck) {
            if (!element.getModifiers().contains(modifier)) {
                return false;
            }
        }
        return true;
    }

    public FluentElementValidator<E> validate() {
        return FluentElementValidator.createFluentElementValidator(this.unwrap());
    }

    /**
     * Gets the annotation
     *
     * @param annotationFqn the annotation to get
     * @return the AnnotationMirror wrapped by an AnnotationWrapper instance,
     */
    public AnnotationMirrorWrapper getAnnotation(String annotationFqn) {
        return AnnotationMirrorWrapper.get(element, annotationFqn);
    }

    /**
     * Checks if wrapped instance is null
     *
     * @return true, if wrapped element is null, otherwise false.
     */
    public boolean isWrappedElementNull() {
        return this.element == null;
    }

    /**
     * Checks if wrapped instance is null
     *
     * @return true, if wrapped element is not null, otherwise false.
     */
    public boolean isWrappedElementNotNull() {
        return !isWrappedElementNull();
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
     * @return the enclosing element, or null if it doesn't exist
     */
    public ElementWrapper<Element> getEnclosingElement() {
        return ElementWrapper.wrap(element.getEnclosingElement());
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
     * Returns all enclosed Elements e
     *
     * @return
     */
    public List<ElementWrapper<Element>> getAllEnclosedElements() {
        return getAllEnclosedElements(false);
    }

    /**
     * Returns all enclosed Elements e
     *
     * @return
     */
    public List<ElementWrapper<Element>> getAllEnclosedElements(boolean includeSelf) {
        return ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(this.unwrap(), includeSelf)
                .stream().map(ExecutableElementWrapper::wrap).collect(Collectors.toList());
    }

    /**
     * Returns all enclosed Elements e
     *
     * @return
     */
    public List<ElementWrapper<Element>> getAllEnclosedElements(boolean includeSelf, int maxDepth) {
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
     * @param <A>            The annotation type
     * @return the annotation or null if Element isn't annotated with annotation
     */
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return element.getAnnotation(annotationType);
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

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
        return element.getAnnotationsByType(annotationType);
    }

    // --------------------------------------------------------------------------
    // Central wrappers
    // --------------------------------------------------------------------------


    public static PackageElementWrapper wrap(PackageElement element) {
        return PackageElementWrapper.wrap(element);
    }

    public static ExecutableElementWrapper wrap(ExecutableElement element) {
        return ExecutableElementWrapper.wrap(element);
    }

    public static TypeElementWrapper wrap(TypeElement element) {
        return TypeElementWrapper.wrap(element);
    }

    public static VariableElementWrapper wrap(VariableElement element) {
        return VariableElementWrapper.wrap(element);
    }

    public static TypeParameterElementWrapper wrap(TypeParameterElement element) {
        return TypeParameterElementWrapper.wrap(element);
    }

    /**
     * Wraps an Element.
     *
     * @param element the Element to wrap
     * @return an ElementWrapper instance
     */
    public static ElementWrapper<Element> wrap(Element element) {
        return new ElementWrapper<>(element);
    }

    /**
     * Wraps list of Elements.
     *
     * @param elements The list of Elements to wrap
     * @return a List of Wrapped Elements, or an empty list
     */
    public static List<ElementWrapper<Element>> wrap(List<Element> elements) {
        return elements != null ? elements.stream().map(ElementWrapper::wrap).collect(Collectors.toList()) : new ArrayList<>();
    }

    // --------------------------------------------------------------------------
    // Helper functions for usage with stream api
    // --------------------------------------------------------------------------

    public static boolean isModule(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isModule(element.unwrap());
    }

    public static boolean isPackage(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isPackage(element.unwrap());
    }

    public static boolean isClass(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isClass(element.unwrap());
    }

    public static boolean isInterface(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isInterface(element.unwrap());
    }

    public static boolean isEnum(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isEnum(element.unwrap());
    }

    public static boolean isAnnotation(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isAnnotation(element.unwrap());
    }

    public static boolean isConstructor(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isConstructor(element.unwrap());
    }

    public static boolean isMethod(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isMethod(element.unwrap());
    }

    public static boolean isField(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isField(element.unwrap());
    }

    public static boolean isAnnotationAttribute(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isAnnotationAttribute(element.unwrap());
    }

    public static boolean isConstructorParameter(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isConstructorParameter(element.unwrap());
    }

    public static boolean isMethodParameter(ElementWrapper<Element> element) {
        return ElementUtils.CheckKindOfElement.isMethodParameter(element.unwrap());
    }

    public static boolean isPackageElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isPackageElement(element.unwrap());
    }

    public static boolean isTypeElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isTypeElement(element.unwrap());
    }

    public static boolean isExecutableElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isExecutableElement(element.unwrap());
    }

    public static boolean isVariableElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isVariableElement(element.unwrap());
    }

    public static boolean isTypeParameterElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isTypeParameterElement(element.unwrap());
    }

    public static boolean isModuleElement(ElementWrapper<Element> element) {
        return ElementUtils.CastElement.isModuleElement(element.unwrap());
    }

    public static PackageElementWrapper toPackageElement(ElementWrapper<Element> element) {
        return ElementWrapper.wrap(ElementUtils.CastElement.castToPackageElement(element.unwrap()));
    }

    public static TypeElementWrapper toTypeElement(ElementWrapper<Element> element) {
        return ElementWrapper.wrap(ElementUtils.CastElement.castToTypeElement(element.unwrap()));
    }


    public static void main(String[] args) {
        List<ElementWrapper> testList = new ArrayList<>();

        List<TypeElementWrapper> teList = testList.stream()
                .filter(ElementWrapper::isWrappedElementNotNull)
                .filter(ElementWrapper::isTypeElement)
                .map(ElementWrapper::toTypeElement)
                .filter(e -> e.hasModifiers(Modifier.STATIC, Modifier.PUBLIC))
                .filter(e -> e.hasQualifiedName("WHOOP"))
                .filter(ElementWrapper::isWrappedElementNotNull)
                .collect(Collectors.toList());
        PackageElementWrapper tew = ElementWrapper.wrap((PackageElement) null);
        tew.validate()
                .is(AptkCoreMatchers.IS_FIELD)
                .applyValidator(AptkCoreMatchers.BY_NAME).hasOneOf("abc")
                .validateAndIssueMessages();
    }


}
