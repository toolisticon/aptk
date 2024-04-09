package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModuleElementWrapper extends ElementWrapper<Element> {

    private ModuleElementWrapper(Element moduleElement) {
        super(moduleElement);
    }

    public String getQualifiedName() {
        // default value not needed
        return invokeParameterlessMethodOfElement("getQualifiedName", null).toString();
    }

    public boolean hasQualifiedName(String name) {
        return name != null && this.getQualifiedName().equals(name);
    }

    public boolean isOpen() {
        // default value not needed
        return this.<Boolean>invokeParameterlessMethodOfElement("isOpen", false);
    }

    public boolean isUnnamed() {
        // default value not needed
        return this.<Boolean>invokeParameterlessMethodOfElement("isUnnamed", false);
    }

    /*-
    public List<? extends ModuleElement.Directive> getDirectives() {
        return this.element.getDirectives();
    }
    */

    /**
     * Converts an ElementWrapper to a ModuleElementWrapper id the wrapped element is a ModuleElement
     *
     * @param element the element to convert
     * @return a ModuleElementWrapper wrapping passed ElementWrapper wraps a ModuleElement, otherwise null
     */
    public static ModuleElementWrapper toModuleElement(ElementWrapper<?> element) {
        return ModuleElementWrapper.wrap(element.unwrap());
    }

    /**
     * Wraps the passed element in ModuleElementWrapper if it is a ModuleElement.
     *
     * @param element the element to wrap
     * @return a ModuleElementWrapper wrapping passed element if it is a ModuleElement, otherwise null
     */
    public static ModuleElementWrapper wrap(Element element) {
        return (element == null || !"MODULE".equals(element.getKind().name())) ? null : new ModuleElementWrapper(element);
    }

    /**
     * Wraps a List of ModuleElements.
     * Unfortunately because of backward compatibility it is not possible to limit lists component type to ModuleElement.
     * So this method will try to wrap all Elements of the passed list. It will drop those not being ModuleElements.
     *
     * @param moduleElements A List of ModuleElements.
     * @return A list of ModuleElementWrapper of elements representing modules that could successfully wrapped, or an empty list.
     */
    public static List<ModuleElementWrapper> wrapList(List<Element> moduleElements) {
        return moduleElements.stream().map(ModuleElementWrapper::new).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
