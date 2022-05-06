package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleElementWrapper extends ElementWrapper<ModuleElement> {

    private ModuleElementWrapper(ModuleElement moduleElement) {
        super(moduleElement);
    }

    public String getQualifiedName() {
        return this.element.getQualifiedName().toString();
    }

    public boolean hasQualifiedName(String name) {
        return name != null && this.getQualifiedName().equals(name);
    }

    public boolean isOpen() {
        return this.element.isOpen();
    }

    public boolean isUnnamed() {
        return this.element.isUnnamed();
    }

    public List<? extends ModuleElement.Directive> getDirectives() {
        return this.element.getDirectives();
    }

    public static boolean isModuleElement(ElementWrapper<Element> element) {
        return element != null & element.getKind().equals(ElementKind.MODULE);
    }

    public static ModuleElementWrapper toModuleElement(Element element) {
        return ModuleElementWrapper.wrap((ModuleElement) element);
    }

    public static ModuleElementWrapper wrap(ModuleElement moduleElement) {
        return new ModuleElementWrapper(moduleElement);
    }

    public static List<ModuleElementWrapper> wrapList(List<ModuleElement> moduleElements) {
        return moduleElements.stream().map(ModuleElementWrapper::new).collect(Collectors.toList());
    }

}
