package io.toolisticon.aptk.tools.wrapper;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModuleElementWrapper extends ElementWrapper<Element> {

    private final static String MODULE_ELEMENT_CLASS_NAME = "javax.lang.model.element.ModuleElement";
    private final static String DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$Directive";
    private final static String EXPORTS_DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$ExportsDirective";

    private final static String PROVIDES_DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$ProvidesDirective";

    private final static String USES_DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$UsesDirective";


    private final static String OPENS_DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$OpensDirective";

    private final static String REQUIRES_DIRECTIVE_CLASS_NAME = MODULE_ELEMENT_CLASS_NAME + "$RequiresDirective";


    public abstract static class Directive {

        protected final Object wrappedDirective;

        private Directive(Object wrappedDirective) {
            this.wrappedDirective = wrappedDirective;
        }

        public boolean isExportDirective() {
            return isExportDirective(wrappedDirective);
        }

        private static boolean isExportDirective(Object instance) {
            return "EXPORTS".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, DIRECTIVE_CLASS_NAME, "getKind").get().toString());
        }

        public ExportsDirective toExportsDirective() {
            return (ExportsDirective) this;
        }


        public boolean isOpensDirective() {
            return isOpensDirective(wrappedDirective);
        }

        private static boolean isOpensDirective(Object instance) {
            return "OPENS".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, DIRECTIVE_CLASS_NAME, "getKind").get().toString());
        }

        public OpensDirective toOpensDirective() {
            return (OpensDirective) this;
        }

        public boolean isProvidesDirective() {
            return isProvidesDirective(wrappedDirective);
        }

        private static boolean isProvidesDirective(Object instance) {
            return "PROVIDES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, DIRECTIVE_CLASS_NAME, "getKind").get().toString());
        }

        public ProvidesDirective toProvidesDirective() {
            return (ProvidesDirective) this;
        }

        public boolean isRequiresDirective() {
            return isRequiresDirective(wrappedDirective);
        }

        private static boolean isRequiresDirective(Object instance) {
            return "REQUIRES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, DIRECTIVE_CLASS_NAME, "getKind").get().toString());
        }

        public RequiresDirective toRequiresDirective() {
            return (RequiresDirective) this;
        }

        public boolean isUsesDirective() {
            return isUsesDirective(wrappedDirective);
        }

        private static boolean isUsesDirective(Object instance) {
            return "USES".equals(ElementWrapper.invokeParameterlessMethodOfElement(instance, DIRECTIVE_CLASS_NAME, "getKind").get().toString());
        }


        public UsesDirective toUsesDirective() {
            return (UsesDirective) this;
        }


    }


    public interface ExportsDirective {

        /**
         * Returns the package being exported.
         *
         * @return the package being exported
         */
        PackageElementWrapper getPackage();

        /**
         * Returns the specific modules to which the package is being exported, or null, if the package is exported to all modules which have readability to this module.
         *
         * @return the specific modules to which the package is being exported
         */
        List<ModuleElementWrapper> getTargetModules();

    }

    static class ExportsDirectiveImpl extends Directive implements ExportsDirective {


        private ExportsDirectiveImpl(Object wrappedDirective) {
            super(wrappedDirective);
        }

        @Override
        public PackageElementWrapper getPackage() {
            Optional<PackageElement> result = ElementWrapper.<PackageElement>invokeParameterlessMethodOfElement(wrappedDirective, EXPORTS_DIRECTIVE_CLASS_NAME, "getPackage");
            return PackageElementWrapper.wrap(result.get());
        }

        @Override
        public List<ModuleElementWrapper> getTargetModules() {
            return ModuleElementWrapper.wrapList(ElementWrapper.<List<Element>>invokeParameterlessMethodOfElement(wrappedDirective, EXPORTS_DIRECTIVE_CLASS_NAME, "getTargetModules").get());
        }


    }

    public interface OpensDirective {


        /**
         * Returns the package being opened.
         *
         * @return the package being opened
         */
        PackageElementWrapper getPackage();

        /**
         * Returns the specific modules to which the package is being open or null, if the package is open all modules which have readability to this module.
         *
         * @return the specific modules to which the package is being opened
         */
        List<ModuleElementWrapper> getTargetModules();

    }

    static class OpensDirectiveImpl extends Directive implements OpensDirective {


        private OpensDirectiveImpl(Object wrappedDirective) {
            super(wrappedDirective);
        }

        @Override
        public PackageElementWrapper getPackage() {
            return PackageElementWrapper.wrap(ElementWrapper.<PackageElement>invokeParameterlessMethodOfElement(wrappedDirective, OPENS_DIRECTIVE_CLASS_NAME, "getPackage").get());
        }

        @Override
        public List<ModuleElementWrapper> getTargetModules() {
            return ModuleElementWrapper.wrapList(ElementWrapper.<List<Element>>invokeParameterlessMethodOfElement(wrappedDirective, OPENS_DIRECTIVE_CLASS_NAME, "getTargetModules").get());
        }

    }

    public interface ProvidesDirective {

        /**
         * Returns the service being provided
         *
         * @return the service being provided
         */
        TypeElementWrapper getService();

        /**
         * Returns the implementations of the service being provided.
         *
         * @return the implementations of the service being provided
         */
        List<TypeElementWrapper> getImplementations();

    }

    static class ProvidesDirectiveImpl extends Directive implements ProvidesDirective {


        private ProvidesDirectiveImpl(Object wrappedDirective) {
            super(wrappedDirective);
        }

        @Override
        public TypeElementWrapper getService() {
            return TypeElementWrapper.wrap(ElementWrapper.<TypeElement>invokeParameterlessMethodOfElement(wrappedDirective, PROVIDES_DIRECTIVE_CLASS_NAME, "getService").get());
        }

        @Override
        public List<TypeElementWrapper> getImplementations() {
            List<TypeElement> implementations = (ElementWrapper.<List<TypeElement>>invokeParameterlessMethodOfElement(wrappedDirective, PROVIDES_DIRECTIVE_CLASS_NAME, "getImplementations").get());
            return implementations.stream().map(TypeElementWrapper::wrap).collect(Collectors.toList());
        }

    }

    public interface RequiresDirective {

        /**
         * Returns the module that is required
         *
         * @return the module that is required
         */
        ModuleElementWrapper getDependency();


        /**
         * Returns whether or not this is a static dependency.
         *
         * @return whether or not this is a static dependency.
         */
        boolean isStatic();

        /**
         * Returns whether or not this is a transitive dependency.
         *
         * @return whether or not this is a transitive dependency.
         */
        boolean isTransitive();

    }

    static class RequiresDirectiveImpl extends Directive implements RequiresDirective {


        private RequiresDirectiveImpl(Object wrappedDirective) {
            super(wrappedDirective);
        }

        @Override
        public ModuleElementWrapper getDependency() {
            return ModuleElementWrapper.wrap(ElementWrapper.<Element>invokeParameterlessMethodOfElement(wrappedDirective, REQUIRES_DIRECTIVE_CLASS_NAME, "getDependency").get());
        }


        @Override
        public boolean isStatic() {
            return ElementWrapper.<Boolean>invokeParameterlessMethodOfElement(wrappedDirective, REQUIRES_DIRECTIVE_CLASS_NAME, "isStatic").get();
        }

        @Override
        public boolean isTransitive() {
            return ElementWrapper.<Boolean>invokeParameterlessMethodOfElement(wrappedDirective, REQUIRES_DIRECTIVE_CLASS_NAME, "isTransitive").get();
        }

    }

    public interface UsesDirective {

        /**
         * Returns the service being used
         *
         * @return the service being used
         */
        TypeElementWrapper getService();


    }

    public static class UsesDirectiveImpl extends Directive implements UsesDirective {

        private UsesDirectiveImpl(Object wrappedDirective) {
            super(wrappedDirective);
        }

        @Override
        public TypeElementWrapper getService() {
            return TypeElementWrapper.wrap(ElementWrapper.<TypeElement>invokeParameterlessMethodOfElement(wrappedDirective, USES_DIRECTIVE_CLASS_NAME, "getService").get());
        }


    }

    private static Directive createDirective(Object instance) {
        if (instance == null) {
            return null;
        }

        if (Directive.isExportDirective(instance)) {
            return new ExportsDirectiveImpl(instance);
        } else if (Directive.isOpensDirective(instance)) {
            return new OpensDirectiveImpl(instance);
        } else if (Directive.isProvidesDirective(instance)) {
            return new ProvidesDirectiveImpl(instance);
        } else if (Directive.isRequiresDirective(instance)) {
            return new RequiresDirectiveImpl(instance);
        } else if (Directive.isUsesDirective(instance)) {
            return new UsesDirectiveImpl(instance);
        }

        return null;
    }

    private static List<Directive> createDirectives(List<?> directives) {

        return directives.stream().map(e -> createDirective(e)).filter(Objects::nonNull).collect(Collectors.toList());

    }

    private ModuleElementWrapper(Element moduleElement) {
        super(moduleElement);
    }

    public String getQualifiedName() {
        Optional<Name> result = this.<Name>invokeParameterlessMethodOfElement(MODULE_ELEMENT_CLASS_NAME, "getQualifiedName");
        return result.isPresent() ? result.get().toString() : "";
    }

    public boolean hasQualifiedName(String name) {
        return name != null && this.getQualifiedName().equals(name);
    }

    public boolean isOpen() {
        Optional<Boolean> result = this.<Boolean>invokeParameterlessMethodOfElement(MODULE_ELEMENT_CLASS_NAME, "isOpen");
        return result.orElse(false);
    }

    public boolean isUnnamed() {
        Optional<Boolean> result = this.<Boolean>invokeParameterlessMethodOfElement(MODULE_ELEMENT_CLASS_NAME, "isUnnamed");
        return result.orElse(false);
    }


    public List<Directive> getDirectives() {
        Optional<List<?>> result = this.<List<?>>invokeParameterlessMethodOfElement(MODULE_ELEMENT_CLASS_NAME, "getDirectives");
        return createDirectives(result.isPresent() ? result.get() : Collections.EMPTY_LIST);
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
     * @return A list of ModuleElementWrapper of elements representing modules that could successfully be wrapped, or an empty list.
     */
    public static List<ModuleElementWrapper> wrapList(List<Element> moduleElements) {
        return moduleElements.stream().map(ModuleElementWrapper::wrap).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
