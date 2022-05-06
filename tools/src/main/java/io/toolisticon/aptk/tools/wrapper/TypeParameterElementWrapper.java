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

    public ElementWrapper<Element> getGenericElement() {
        return ElementWrapper.wrap(this.element.getGenericElement());
    }

    public List<TypeMirrorWrapper> getBounds() {
        return this.element.getBounds().stream().map(TypeMirrorWrapper::wrap).collect(Collectors.toList());
    }

    public static TypeParameterElementWrapper wrap(TypeParameterElement typeParameterElement) {
        return new TypeParameterElementWrapper(typeParameterElement);
    }

}
