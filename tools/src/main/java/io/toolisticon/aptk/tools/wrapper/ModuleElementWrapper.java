package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModuleElementWrapper extends ElementWrapper<Element> {

    public abstract static class Directive {

        protected final Object wrappedDirective;

        private Directive(Object wrappedDirective) {
            this.wrappedDirective = wrappedDirective;
        }

        public boolean isExportDirective() {
            return isExportDirective(wrappedDirective);
        }

        private static boolean isExportDirective(Object instance) {
            return "EXPORTS".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, "getKind", null).toString());
        }

        public ExportsDirective toExportsDirective() {
            return isExportDirective() ? (ExportsDirective) this : null;
        }


        public boolean isOpensDirective() {
            return isOpensDirective(wrappedDirective);
        }

        private static boolean isOpensDirective(Object instance) {
            return "OPENS".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, "getKind", null).toString());
        }

        public OpensDirective toOpensDirective() {
            return isOpensDirective() ? (OpensDirective) this : null;
        }

        public boolean isProvidesDirective() {
            return isProvidesDirective(wrappedDirective);
        }

        private static boolean isProvidesDirective(Object instance) {
            return "PROVIDES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, "getKind", null).toString());
        }

        public ProvidesDirective toProvidesDirective() {
            return isProvidesDirective() ? (ProvidesDirective) this : null;
        }

        public boolean isRequiresDirective() {
            return isRequiresDirective(wrappedDirective);
        }

        private static  boolean isRequiresDirective(Object instance) {
            return "REQUIRES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, "getKind", null).toString());
        }

        public RequiresDirective toRequiresDirective() {
            return isRequiresDirective() ? (RequiresDirective) this : null;
        }

        public boolean isUsesDirective() {
            return isUsesDirective(wrappedDirective);
        }

        private static boolean isUsesDirective(Object instance) {
            return "USES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, "getKind", null).toString());
        }

        public UsesDirective toUsesDirective() {
            return isRequiresDirective() ? (UsesDirective) this : null;
        }


    }


    public static class ExportsDirective extends Directive {


        private ExportsDirective(Object wrappedDirective) {
            super(wrappedDirective);
        }

        /**
         * Returns the package being exported.
         *
         * @return the package being exported
         */
        public PackageElementWrapper getPackage() {
            return PackageElementWrapper.wrap(ElementWrapper.<PackageElement>invokeParameterlessMethodOfElement(wrappedDirective, "getPackage", null));
        }

        /**
         * Returns the specific modules to which the package is being exported, or null, if the package is exported to all modules which have readability to this module.
         *
         * @return the specific modules to which the package is being exported
         */
        public List<ModuleElementWrapper> getTargetModules() {
            return ModuleElementWrapper.wrapList(ElementWrapper.<List<? extends Element>>invokeParameterlessMethodOfElement(wrappedDirective, "getTargetModules", Collections.EMPTY_LIST));
        }


    }

    public static class OpensDirective extends Directive {


        private OpensDirective(Object wrappedDirective) {
            super(wrappedDirective);
        }

        /**
         * Returns the package being exported.
         *
         * @return the package being exported
         */
        public PackageElementWrapper getPackage() {
            return PackageElementWrapper.wrap(ElementWrapper.<PackageElement>invokeParameterlessMethodOfElement(wrappedDirective, "getPackage", null));
        }

        /**
         * Returns the specific modules to which the package is being exported, or null, if the package is exported to all modules which have readability to this module.
         *
         * @return the specific modules to which the package is being exported
         */
        public List<ModuleElementWrapper> getTargetModules() {
            return ModuleElementWrapper.wrapList(ElementWrapper.<List<? extends Element>>invokeParameterlessMethodOfElement(wrappedDirective, "getTargetModules", Collections.EMPTY_LIST));
        }

    }

    public static class ProvidesDirective extends Directive {


        private ProvidesDirective(Object wrappedDirective) {
            super(wrappedDirective);
        }

        /**
         * Returns the service being provided
         *
         * @return the service being provided
         */
        public TypeElementWrapper getService(){
            return TypeElementWrapper.wrap(ElementWrapper.<TypeElement>invokeParameterlessMethodOfElement(wrappedDirective, "getService", null));
        }

        /**
         * Returns the implementations of the service being provided.
         *
         * @return the implementations of the service being provided
         */
        public List<TypeElementWrapper> getImplementations() {
            List<TypeElement> implementations = (ElementWrapper.<List<TypeElement>>invokeParameterlessMethodOfElement(wrappedDirective, "getTargetModules", Collections.EMPTY_LIST));
            return implementations.stream().map(TypeElementWrapper::wrap).collect(Collectors.toList());
        }

    }

    public static class RequiresDirective extends Directive {


        private RequiresDirective(Object wrappedDirective) {
            super(wrappedDirective);
        }

        /**
         * Returns the module that is required
         *
         * @return the module that is required
         */
        public ModuleElementWrapper getDependency(){
            return ModuleElementWrapper.wrap(ElementWrapper.<Element>invokeParameterlessMethodOfElement(wrappedDirective, "getDependency", null));
        }


        /**
         * Returns whether or not this is a static dependency.
         * @return whether or not this is a static dependency.
         */
        public boolean isStatic() {
            return ElementWrapper.<Boolean>invokeParameterlessMethodOfElement(wrappedDirective,"isStatic",false);
        }

        /**
         * Returns whether or not this is a transitive dependency.
         * @return whether or not this is a transitive dependency.
         */
        boolean isTransitive() {
            return ElementWrapper.<Boolean>invokeParameterlessMethodOfElement(wrappedDirective,"isTransitive",false);
        }

    }

    public static class UsesDirective extends Directive {

        private UsesDirective(Object wrappedDirective) {
            super(wrappedDirective);
        }

        /**
         * Returns the service being used
         *
         * @return the service being used
         */
        public TypeElementWrapper getService(){
            return TypeElementWrapper.wrap(ElementWrapper.<TypeElement>invokeParameterlessMethodOfElement(wrappedDirective, "getService", null));
        }



    }

    private static Directive createDirective(Object instance) {
        if (instance == null) {
            return null;
        }

        if (Directive.isExportDirective(instance)) {
            return new ExportsDirective(instance);
        } else if (Directive.isOpensDirective(instance)) {
            return new OpensDirective(instance);
        } else if (Directive.isProvidesDirective(instance)) {
            return new ProvidesDirective(instance);
        } else if (Directive.isRequiresDirective(instance)) {
            return new RequiresDirective(instance);
        } else if (Directive.isUsesDirective(instance)) {
            return new UsesDirective(instance);
        }

        return null;
    }

    private static List<Directive> createDirectives (List<?> directives) {

        return directives.stream().map(e -> createDirective(e)).filter(Objects::nonNull).collect(Collectors.toList());

    }

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


    public List<Directive> getDirectives() {
        return createDirectives(this.<List<?>>invokeParameterlessMethodOfElement("getDirectives", Collections.EMPTY_LIST));
    }


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
