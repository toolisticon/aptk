package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper for TypeParameterElement to provide convenience functions.
 */
public class TypeParameterElementWrapper extends ElementWrapper<TypeParameterElement> {

    private TypeParameterElementWrapper(TypeParameterElement typeParameterElement) {
        super(typeParameterElement);
    }

    /**
     * Returns the generic class, interface, method, or constructor that is parameterized by this type parameter.
     *
     * @return the generic class, interface, method, or constructor that is parameterized by this type parameter
     */
    public ElementWrapper<Element> getGenericElement() {
        return ElementWrapper.wrap(this.element.getGenericElement());
    }

    /**
     * Returns the bounds of this type parameter. These are the types given by the extends clause used to declare this type parameter. If no explicit extends clause was used, then java.lang.Object is considered to be the sole bound.
     *
     * @return the bounds of this type parameter, or an empty list if there are none
     */
    public List<TypeMirrorWrapper> getBounds() {
        return this.element.getBounds().stream().map(TypeMirrorWrapper::wrap).collect(Collectors.toList());
    }

    public String toString() {
        return this.element.toString();
    }

    public String toStringWithExtendsAndBounds() {
        List<TypeMirrorWrapper> extendedTypes = getBounds();
        return toString() + (extendedTypes.size() == 0 ? "" : extendedTypes.stream().map(e -> e.getTypeDeclaration()).collect(Collectors.joining(" & ", " extends ","")));
    }
    /**
     * Wraps a TypeParameterElement.
     * Will throw IllegalArgumentException if passed element is null.
     * @param typeParameterElement the element to wrap
     * @return a wrapper instance
     */
    public static TypeParameterElementWrapper wrap(TypeParameterElement typeParameterElement) {
        return new TypeParameterElementWrapper(typeParameterElement);
    }

}
