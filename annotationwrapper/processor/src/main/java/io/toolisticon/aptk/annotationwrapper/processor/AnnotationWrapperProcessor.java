package io.toolisticon.aptk.annotationwrapper.processor;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.FilerUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.generators.SimpleJavaWriter;
import io.toolisticon.aptk.tools.wrapper.AnnotationMirrorWrapper;
import io.toolisticon.aptk.tools.wrapper.AnnotationValueWrapper;
import io.toolisticon.aptk.tools.wrapper.ElementWrapper;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.PackageElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeParameterElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;
import io.toolisticon.spiap.api.Service;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Annotation processor to generate wrapper class that eases reading of annotations attributes.
 * This circumvents the need to read attribute values by AnnotationMirror/Value api.
 */
@Service(Processor.class)
public class AnnotationWrapperProcessor extends AbstractAnnotationProcessor {

    /**
     * The supported annotation types.
     */
    private final static Set<String> SUPPORTED_ANNOTATIONS = createSupportedAnnotationSet(AnnotationWrapper.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATIONS;
    }

    /**
     * Stores processing state.
     */
    public static class State {

        final static String WRAPPER_SUFFIX = "Wrapper";

        final PackageElementWrapper packageElement;
        final ElementWrapper<Element> annotatedElement;
        final Set<String> annotationsToBeWrapped = new HashSet<>();
        final List<String> customCodeClasses;
        final Map<String, List<TypeMirrorWrapper>> customInterfaces;
        final Map<String, AnnotationWrapperCustomCode> annotationWrapperCustomCode = new HashMap<>();
        final Map<String, List<CustomCodeClassMethod>> customCodeClassMethodMappings = new HashMap<>();
        final Map<String, String> annotationNameToWrapperSimpleNameMap = new HashMap<>();
        final Map<String, String> wrapperSimpleNameToFqn = new HashMap<>();
        final Map<String, String> wrapperAnnotationFqnToAnnotationFqnNameMap = new HashMap<>();
        final Map<String, String> annotationFqnToWrapperAnnotationFqnNameMap = new HashMap<>();
        final boolean usePublicVisibility;
        final boolean automaticallyWrapEmbeddedAnnotations;


        /**
         * Constructor.
         *
         * @param annotatedElement the base package name
         */
        State(Element annotatedElement) {

            this.annotatedElement = ElementWrapper.wrap(annotatedElement);
            this.packageElement = this.annotatedElement.isPackage() ? ElementWrapper.toPackageElement(this.annotatedElement) : PackageElementWrapper.wrap(ElementUtils.AccessEnclosingElements.<PackageElement>getFirstEnclosingElementOfKind(this.annotatedElement.unwrap(), ElementKind.PACKAGE));

            Collections.addAll(this.annotationsToBeWrapped, AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(annotatedElement, AnnotationWrapper.class));

            // Safe to do so, since tghis is the annotation which is processed by this processor
            AnnotationMirrorWrapper annotationMirror = AnnotationMirrorWrapper.get(annotatedElement, AnnotationWrapper.class).get();
            usePublicVisibility = annotationMirror.getAttributeWithDefault("usePublicVisibility").getBooleanValue();
            automaticallyWrapEmbeddedAnnotations = annotationMirror.getAttributeWithDefault("automaticallyWrapEmbeddedAnnotations").getBooleanValue();
            customCodeClasses = annotationMirror.getAttributeWithDefault("bindCustomCode").getArrayValue().stream().map(e -> e.getClassValue().getQualifiedName()).collect(Collectors.toList());
            customInterfaces = getCustomInterfaces(this.annotatedElement, annotationMirror);


            // now recursively add all embedded annotations
            if (automaticallyWrapEmbeddedAnnotations) {
                for (String annotationFqn : new ArrayList<>(annotationsToBeWrapped)) {
                    recursivelyAddAnnotations(annotationFqn);
                }
            }

            // now create the wrapper names look up maps
            for (String annotationToBeWrapped : annotationsToBeWrapped) {
                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(annotationToBeWrapped);
                String annotationWrapperSimpleName = typeElement.getSimpleName().toString() + WRAPPER_SUFFIX;
                String annotationWrapperFqn = typeElement.getQualifiedName().toString() + WRAPPER_SUFFIX;

                annotationNameToWrapperSimpleNameMap.put(annotationToBeWrapped, annotationWrapperSimpleName);
                wrapperAnnotationFqnToAnnotationFqnNameMap.put(annotationWrapperFqn, annotationToBeWrapped);
                annotationFqnToWrapperAnnotationFqnNameMap.put(annotationToBeWrapped, annotationWrapperFqn);
                wrapperSimpleNameToFqn.put(annotationWrapperSimpleName, annotationWrapperFqn);
                annotationWrapperCustomCode.put(annotationWrapperFqn, new AnnotationWrapperCustomCode(annotationWrapperFqn));
            }

            // first scan for all CustomCodeClasses in Package
            List<ExecutableElement> customCodeMethods = FluentElementFilter.createFluentElementFilter(ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(packageElement.unwrap(), false)).applyFilter(AptkCoreMatchers.IS_METHOD).applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(CustomCodeMethod.class).getResult();

            for (ExecutableElement customClassMethod : customCodeMethods) {
                addCustomCodeMethod(ExecutableElementWrapper.wrap(customClassMethod));
            }


            // handle custom code classes set via the annotation attribute
            for (String customCodeClass : customCodeClasses) {

                // Need to filter out all classes in package which have already been picked up
                if (packageElement.getQualifiedName().equals(TypeMirrorWrapper.wrap(customCodeClass).getPackage())) {
                    // is in package so skip
                    continue;
                }

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(customCodeClass);
                List<ExecutableElementWrapper> ccMethods = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                        .applyFilter(AptkCoreMatchers.IS_METHOD)
                        .applyFilter(AptkCoreMatchers.BY_ANNOTATION).filterByAllOf(CustomCodeMethod.class)
                        .getResult().stream().map(ExecutableElementWrapper::wrap).collect(Collectors.toList());
                for (ExecutableElementWrapper customClassMethod : ccMethods) {
                    addCustomCodeMethod(customClassMethod);
                }

            }


        }

        /**
         * Recursively determines all embedded annotation types
         *
         * @param annotationFqn The annotation to scan
         */
        private void recursivelyAddAnnotations(String annotationFqn) {

            Optional<TypeElementWrapper> element = TypeElementWrapper.getByFqn(annotationFqn);

            if (element.isPresent()) {
                for (ExecutableElementWrapper executableElementWrapper : element.get().getEnclosedElements().stream()
                        .filter(ElementWrapper::isMethod)
                        .map(ElementWrapper::toExecutableElementWrapper)
                        .collect(Collectors.toList())) {
                    String returnTypeFqn = executableElementWrapper.getReturnType().getQualifiedName();
                    if (executableElementWrapper.getReturnType().isDeclared()
                            && executableElementWrapper.getReturnType().getTypeElement().get().isAnnotation()
                            && !this.annotationsToBeWrapped.contains(returnTypeFqn)) {
                        this.annotationsToBeWrapped.add(returnTypeFqn);
                        recursivelyAddAnnotations(returnTypeFqn);
                    }

                }
            }

        }

        private void addCustomCodeMethod(ExecutableElementWrapper customClassMethod) {

            // validate
            if (!customClassMethod.validateWithFluentElementValidator()
                    .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.STATIC)
                    .applyValidator(AptkCoreMatchers.BY_MODIFIER).hasNoneOf(Modifier.PRIVATE)
                    .applyInvertedValidator(AptkCoreMatchers.HAS_NO_PARAMETERS).validateAndIssueMessages()) {
                return;
            }

            // get type annotation type as fqn
            AnnotationMirrorWrapper customCodeMethodAnnotationMirror = customClassMethod.getAnnotationMirror(CustomCodeMethod.class).get();
            TypeMirrorWrapper typeMirrorWrapper = customCodeMethodAnnotationMirror.getAttributeWithDefault().getClassValue();
            String expectedTypeMirrorWrapperClassName = typeMirrorWrapper.getSimpleName() + WRAPPER_SUFFIX;

            VariableElementWrapper firstParameter = customClassMethod.getParameters().get(0);

            if (!firstParameter.asType().toString().equals(expectedTypeMirrorWrapperClassName) && !firstParameter.asType().toString().endsWith("." + expectedTypeMirrorWrapperClassName)) {
                firstParameter.compilerMessage().asError().write(AnnotationWrapperProcessorMessages.ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE, firstParameter.asType().toString(), expectedTypeMirrorWrapperClassName);
            }



            /*-
            TypeMirror typeMirror = firstParameter.asType();

            if (typeMirror.getKind().equals(TypeKind.DECLARED)){
                if (!wrapperSimpleNameToFqn.containsKey(TypeUtils.TypeRetrieval.getTypeElement(typeMirror).getSimpleName().toString())) {
                    MessagerUtils.error(firstParameter, AnnotationWrapperProcessorMessages.ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE, TypeUtils.TypeRetrieval.getTypeElement(typeMirror).getQualifiedName().toString(), TypeUtils.TypeRetrieval.getTypeElement(typeMirror).getQualifiedName().toString());
                    return;
                }
            } else {
                String ccmWrapperSimpleOrQualifiedName = firstParameter.asType().toString();

                if (!wrapperSimpleNameToFqn.containsKey(ccmWrapperSimpleOrQualifiedName) && !wrapperAnnotationFqnToAnnotationFqnNameMap.containsKey(ccmWrapperSimpleOrQualifiedName)) {
                    MessagerUtils.error(firstParameter, AnnotationWrapperProcessorMessages.ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE, ccmWrapperSimpleOrQualifiedName);
                    return;
                }
            }
             */


            // Must find first parameter
            String annotation = customCodeMethodAnnotationMirror.getAttributeWithDefault().getClassValue().getQualifiedName();
            String wrapperFqn = annotationFqnToWrapperAnnotationFqnNameMap.get(annotation);

            if (wrapperFqn != null) {
                annotationWrapperCustomCode.get(wrapperFqn).addCustomCodeMethod(customClassMethod);
            }

        }

        Map<String, List<TypeMirrorWrapper>> getCustomInterfaces(ElementWrapper element, AnnotationMirrorWrapper annotationMirror) {
            Map<String, List<TypeMirrorWrapper>> result = new HashMap<>();

            // custom interfaces has empty array as default value so it's safe to open the stream
            AnnotationValueWrapper annotationValue = annotationMirror.getAttributeWithDefault("customInterfaces");
            List<AnnotationMirrorWrapper> annotationMirrors = annotationValue.getArrayValue().stream().map(AnnotationValueWrapper::getAnnotationValue).collect(Collectors.toList());

            for (AnnotationMirrorWrapper customInterfaceAnnotationMirror : annotationMirrors) {
                TypeMirrorWrapper annotationType = customInterfaceAnnotationMirror.getAttributeWithDefault("annotationToWrap").getClassValue();
                List<TypeMirrorWrapper> interfaceTypes = customInterfaceAnnotationMirror.getAttributeWithDefault("interfacesToApply").getArrayValue().stream().map(AnnotationValueWrapper::getClassValue).collect(Collectors.toList());


                List<TypeMirrorWrapper> customInterfacesList = result.get(annotationType.getQualifiedName());
                if (customInterfacesList == null) {
                    customInterfacesList = new ArrayList<>();
                    result.put(annotationType.getQualifiedName(), customInterfacesList);
                }

                for (TypeMirrorWrapper customInterfaceType : interfaceTypes) {

                    // get is safe because it is interface
                    if (!customInterfaceType.getTypeElement().get().validateWithFluentElementValidator()
                            .is(AptkCoreMatchers.IS_INTERFACE)
                            .justValidate()) {
                        element.compilerMessage(annotationMirror.unwrap()).asError().write("Class in interfacesToApply attribute array must be an interface!");
                        continue;
                    }

                    customInterfacesList.add(customInterfaceType);
                }

            }
            return result;
        }

        /**
         * Gets the annotated element used during wrapper generation
         *
         * @return The annotated element
         */
        public ElementWrapper<Element> getAnnotatedElement() {
            return this.annotatedElement;
        }

        /**
         * Gets the package name used during wrapper generation
         *
         * @return The package name
         */
        public String getPackageName() {
            return this.packageElement.getQualifiedName();
        }

        public Set<String> getAnnotationToBeWrapped() {
            return annotationsToBeWrapped;
        }

        public AnnotationWrapperCustomCode getAnnotationWrapperCustomCode(String annotationName) {
            return annotationWrapperCustomCode.get(annotationFqnToWrapperAnnotationFqnNameMap.get(annotationName));
        }

        public boolean containsAnnotationToBeWrapped(String annotationFqn) {
            return annotationsToBeWrapped.contains(annotationFqn);
        }

        public String getWrapperName(String annotationFqn) {
            return annotationNameToWrapperSimpleNameMap.get(annotationFqn);
        }

        public String getFQWrapperName(String annotationFqn) {
            return wrapperAnnotationFqnToAnnotationFqnNameMap.get(annotationFqn);
        }

        public boolean isUsePublicVisibility() {
            return usePublicVisibility;
        }

        public String getVisibilityModifier() {
            return usePublicVisibility ? "public " : "";
        }

        public List<TypeMirrorWrapper> getCustomInterfacesForAnnotation(String annotationFqn) {
            return this.customInterfaces.get(annotationFqn);
        }

    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        // process Services annotation
        for (Element element : roundEnv.getElementsAnnotatedWith(AnnotationWrapper.class)) {

            State state = new State(element);

            // Now iterate over the annotation types and
            for (String annotationToBeWrapped : state.getAnnotationToBeWrapped()) {
                processAnnotationToBeWrapped(state, annotationToBeWrapped);
            }


        }

        return false;

    }

    /**
     * Stores the configuration done in the {@link AnnotationToWrap} annotation.
     * <p>
     * In fact this is exactly a good example reason why this processor is needed.
     */
    public static class AnnotationToWrap {

        /**
         * the fully qualified name of the annotation
         */
        final String annotationFqn;

        /**
         * The attributes of the annotation
         */
        final List<AnnotationAttribute> attributes;

        /**
         * Custom code classes. Used to extend api.
         */
        final AnnotationWrapperCustomCode annotationWrapperCustomCode;

        /**
         * Custom interfaces for wrapper.
         */
        final List<TypeMirrorWrapper> customInterfaces;

        /**
         * The constructor
         *
         * @param annotationFqn               the fully qualified name of the annotation
         * @param attributes                  The attributes of the annotation
         * @param annotationWrapperCustomCode custom code methods to extend the wrappers api
         */
        AnnotationToWrap(String annotationFqn, List<AnnotationAttribute> attributes, AnnotationWrapperCustomCode annotationWrapperCustomCode, List<TypeMirrorWrapper> customInterfaces) {
            this.annotationFqn = annotationFqn;
            this.attributes = attributes;
            this.annotationWrapperCustomCode = annotationWrapperCustomCode;
            this.customInterfaces = customInterfaces;
        }

        /**
         * Gets the attributes of the annotation
         *
         * @return
         */
        public List<AnnotationAttribute> getAttributes() {
            return this.attributes;
        }

        /**
         * Gets custom code.
         *
         * @return
         */
        public AnnotationWrapperCustomCode getAnnotationWrapperCustomCode() {
            return annotationWrapperCustomCode;
        }

        /**
         * The simple name of the annotation
         *
         * @return
         */
        public String getSimpleName() {
            return TypeUtils.TypeRetrieval.getTypeElement(annotationFqn).getSimpleName().toString();
        }

        /**
         * The fully qualified name of the annotation.
         *
         * @return the fully qualified name
         */
        public String getQualifiedName() {
            return TypeUtils.TypeRetrieval.getTypeElement(annotationFqn).getQualifiedName().toString();
        }

        /**
         * Gets all imports needed by the annotation.
         *
         * @return the import stings
         */
        public List<String> getImports() {
            List<String> imports = new ArrayList<>();

            // first add the annotation itself
            imports.add(getQualifiedName());

            // now add all return types not starting with java.lang
            for (AnnotationAttribute attribute : this.attributes) {
                String importString = attribute.getImport();
                if (importString != null && !importString.startsWith("java.lang")) {
                    imports.add(importString);
                }
            }

            // now add all imports of wrappers
            for (String importString : annotationWrapperCustomCode.getImports()) {
                if (importString != null && !importString.startsWith("java.lang")) {
                    imports.add(importString);
                }
            }

            return imports;
        }

        public List<TypeMirrorWrapper> getCustomInterfaces() {
            return customInterfaces;
        }

        public String getImplementsString() {

            if (getCustomInterfaces() != null && getCustomInterfaces().size() > 0) {

                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (TypeMirrorWrapper wrapper : getCustomInterfaces()) {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(wrapper.getSimpleName().toString());
                }

                return sb.toString();
            }


            return "";
        }
    }

    /**
     * Class to store information about an annotation attribute.
     */
    public static class AnnotationAttribute {

        final ExecutableElementWrapper attribute;
        final State state;

        /**
         * Constructor
         *
         * @param state     the processing state
         * @param attribute The ExecutableElement that represents the attribute
         */
        AnnotationAttribute(State state, ExecutableElementWrapper attribute) {
            this.state = state;
            this.attribute = attribute;
        }

        /**
         * Gets the attributes name
         *
         * @return the attribute name
         */
        public String getName() {
            return attribute.getSimpleName().toString();
        }

        public boolean isArray() {
            return attribute.getReturnType().isArray();
        }

        /**
         * Checks whether attribute is a primitive type or a String or not
         *
         * @return true if attribute is a primitive or String (array) type, otherwise false
         */
        public boolean isPrimitiveOrString() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (returnType.isArray()) {
                returnType = returnType.getWrappedComponentType();
            }

            return returnType.isPrimitive() || String.class.getCanonicalName().equals(returnType.getQualifiedName());

        }

        /**
         * Checks whether attribute is a primitive Array
         *
         * @return true if attribute is a primitive, otherwise false
         */
        public boolean isPrimitiveArrayType() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (!returnType.isArray()) {
                return false;
            }

            return returnType.getWrappedComponentType().isPrimitive();

        }

        /**
         * Checks whether attribute is a primitive Array and returns the primitives simple boxed type name.
         *
         * @return the primitives boxed type simple name, otherwise null
         */
        public String getBoxedType() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (returnType.isArray()) {
                returnType = returnType.getWrappedComponentType();
            }

            return returnType.isPrimitive() ? TypeUtils.getTypes().boxedClass(returnType.getPrimitiveType()).getSimpleName().toString(): null;
        }

        /**
         * Checks whether attribute is a primitive Array
         *
         * @return true if attribute is a primitive, otherwise false
         */
        public boolean isStringArrayType() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (!returnType.isArray()) {
                return false;
            }

            returnType = returnType.getWrappedComponentType();

            return returnType.isDeclared() && String.class.getCanonicalName().equals(returnType.toString());

        }

        /**
         * Checks whether attribute is enum type or not
         *
         * @return true if attribute is of type enum or enum array, otherwise false
         */
        public boolean isEnum() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (returnType.isArray()) {
                returnType = returnType.getWrappedComponentType();
            }

            return returnType.isDeclared() && returnType.getTypeElement().get().isEnum();
        }

        /**
         * Checks whether attribute is of type Class or not
         *
         * @return true if attribute is of type Class or Class array, otherwise false
         */
        public boolean isClass() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (returnType.isArray()) {
                returnType = returnType.getWrappedComponentType();
            }

            return returnType.isDeclared() && TypeUtils.TypeComparison.isTypeEqual(returnType.unwrap(), Class.class);
        }

        /**
         * Checks whether attribute is an annotation type or not
         *
         * @return true if attribute is annotation type (array), otherwise false
         */
        public boolean isAnnotationType() {

            TypeMirrorWrapper returnType = attribute.getReturnType();
            if (returnType.isArray()) {
                returnType = returnType.getWrappedComponentType();
            }

            return returnType.isDeclared() && ElementKind.ANNOTATION_TYPE.equals(returnType.getTypeElement().get().getKind());
        }

        /**
         * Checks if annotation type has an annotation wrapper implementation
         *
         * @return true if attribute type can be represented by an annotation wrapper type
         */
        public boolean isWrappedAnnotationType() {
            return isAnnotationType() && state.containsAnnotationToBeWrapped(this.attribute.getReturnType().getQualifiedName());
        }

        /**
         * Gets the wrapped annotation name
         *
         * @return the AnnotationWrapper type fqn for attribute
         */
        public String getTargetWrapperAnnotationName() {
            return state.getWrapperName(this.attribute.getReturnType().getQualifiedName());
        }

        /**
         * Checks if attribute is optional
         *
         * @return true if attribute is optional, otherwise false
         */
        public boolean isOptional() {
            return this.attribute.getDefaultValue().isPresent();
        }

        /**
         * Gets the import needed by this attribute
         *
         * @return will return the fqn of the attribute type. Will return null for primitive types and types residing in implicit java.lang package
         */
        public String getImport() {

            TypeMirrorWrapper typeMirror = attribute.getReturnType();

            if (typeMirror.isArray()) {
                typeMirror = typeMirror.getWrappedComponentType();
            }

            if (typeMirror.getKind().isPrimitive()) {
                return null;
            }

            // return null for String
            String qualifiedName = typeMirror.getQualifiedName();
            return qualifiedName.startsWith("java.lang") ? null : qualifiedName;
        }

        /**
         * The attributes type as a simple name
         *
         * @return The attributes type as a simple name. Will return the component types simple name if attribute type is an array
         */
        public String getAttributeType() {
            return attribute.getReturnType().getSimpleName();
        }

        /**
         * Gets the component type of an array or collection
         *
         * @return the component type in case the attribute is an array or a collection, otherwise null
         */
        public String getComponentAttributeType() {

            TypeMirrorWrapper componentTypeMirrorWrapper = attribute.getReturnType().getWrappedComponentType();
            return componentTypeMirrorWrapper != null ? componentTypeMirrorWrapper.getSimpleName() : null;

        }

        /**
         * Gets the attributes wrapped TypeMirror
         *
         * @return The wrapped TypeMirror
         */
        public TypeMirrorWrapper getWrappedTypeMirror() {
            return attribute.getReturnType();
        }


    }

    /**
     * Class to weave custom code into annotation wrapper.
     */
    public static class AnnotationWrapperCustomCode {

        private final String annotationWrapperFQN;
        private final List<CustomCodeClassMethod> customMethods = new ArrayList<>();

        /**
         * The constructor.
         *
         * @param annotationWrapperFQN the annotations fqn
         */
        public AnnotationWrapperCustomCode(String annotationWrapperFQN) {

            this.annotationWrapperFQN = annotationWrapperFQN;

        }

        /**
         * Get the annotation wrapper fqn.
         *
         * @return the Type element
         */
        public String getAnnotationWrapperFqn() {
            return this.annotationWrapperFQN;
        }

        public Set<String> getImports() {

            Set<String> imports = new HashSet<>();

            for (CustomCodeClassMethod method : customMethods) {
                imports.addAll(method.getImports());
            }

            return imports;

        }

        public void addCustomCodeMethod(ExecutableElementWrapper customCodeClassMethodExecutableElement) {
            this.customMethods.add(new CustomCodeClassMethod(customCodeClassMethodExecutableElement));
        }

        public List<CustomCodeClassMethod> getCustomMethods() {
            return customMethods;
        }
    }

    /**
     * Class representing custom code methods
     */
    public static class CustomCodeClassMethod {

        private final TypeElementWrapper customCodeClassTypeElement;
        private final ExecutableElementWrapper customCodeMethodExecutableElement;

        public CustomCodeClassMethod(ExecutableElementWrapper customCodeMethodExecutableElement) {
            this.customCodeClassTypeElement = customCodeMethodExecutableElement.<TypeElementWrapper>getFirstEnclosingElementWithKind(ElementKind.CLASS).get();
            this.customCodeMethodExecutableElement = customCodeMethodExecutableElement;
        }

        public String getMethodDeclarationString() {
            StringBuilder result = new StringBuilder();

            // First add the type parameters
            if (this.customCodeMethodExecutableElement.getTypeParameters().size() > 0) {
                result.append("<");

                // TODO: has to be changed with Java 8
                boolean first = true;
                for (TypeParameterElementWrapper typeParameterElement : this.customCodeMethodExecutableElement.getTypeParameters()) {

                    if (first) {
                        first = false;
                    } else {
                        result.append(", ");
                    }

                    result.append(typeParameterElement);
                }

                result.append(">");
            }

            // Now add return type
            result.append(customCodeMethodExecutableElement.getReturnType().getTypeDeclaration());
            result.append(" ");

            // add method name
            result.append(customCodeMethodExecutableElement.getSimpleName());

            result.append("(");


            List<VariableElementWrapper> variableElementSubList = customCodeMethodExecutableElement.getParameters().subList(1, customCodeMethodExecutableElement.getParameters().size());
            boolean first = true;
            for (VariableElementWrapper variableElement : variableElementSubList) {

                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }

                // add type
                result.append(variableElement.asType().getTypeDeclaration());
                //result.append(variableElement.asType().toString());

                result.append(" ");
                // add name
                result.append(variableElement.getSimpleName().toString());
            }


            result.append(")");

            return result.toString();

        }

        public String getForwardCall() {
            StringBuilder result = new StringBuilder();

            if (this.customCodeMethodExecutableElement.getReturnType().getKind() != TypeKind.VOID) {
                result.append("return ");
            }

            // First add TypeName
            result.append(this.customCodeMethodExecutableElement.getEnclosingElement().get().getSimpleName());
            result.append(".");

            // add method name
            result.append(customCodeMethodExecutableElement.getSimpleName());

            result.append("(");
            result.append("this");

            if (customCodeMethodExecutableElement.getParameters().size() > 1) {
                List<VariableElementWrapper> variableElementSubList = customCodeMethodExecutableElement.getParameters().subList(1, customCodeMethodExecutableElement.getParameters().size());

                // TODO: has to be changed with Java 8
                for (VariableElementWrapper parameter : variableElementSubList) {

                    result.append(", ");
                    result.append(parameter.getSimpleName());

                }

            }

            result.append(")");

            return result.toString();
        }

        public Set<String> getImports() {
            Set<String> imports = new HashSet<>();

            // Must add the custom code class
            imports.add(customCodeClassTypeElement.getQualifiedName().toString());

            // Must add return type
            imports.addAll(this.customCodeMethodExecutableElement.getReturnType().getImports());

            // Must add parameter types
            boolean firstParameter = true;
            for (VariableElementWrapper parameter : customCodeMethodExecutableElement.getParameters()) {
                if (firstParameter) {
                    firstParameter = false;
                    continue;
                }

                imports.addAll(parameter.asType().getImports());
            }

            return imports;
        }


    }

    /**
     * Processes one annotation.
     *
     * @param state                        the processing state
     * @param annotationToCreateWrapperFor The annotation to create a wrapper for as fqn
     */
    void processAnnotationToBeWrapped(State state, String annotationToCreateWrapperFor) {
        // get annotation type first
        TypeElementWrapper typeElement = TypeElementWrapper.getByFqn(annotationToCreateWrapperFor).get();

        // Now process all attributes
        List<ExecutableElementWrapper> attributes = typeElement.getEnclosedElements().stream()
                .filter(ElementWrapper::isMethod)
                .map(ElementWrapper::toExecutableElementWrapper)
                .collect(Collectors.toList());

        List<AnnotationAttribute> annotationAttributes = new ArrayList<>();
        for (ExecutableElementWrapper attribute : attributes) {

            // Annotations may have different Attribute Types
            // We have to distinct cases
            // - primitive types and String (require no imports)
            // - Enums (require imports)
            // - Classes (require imports and extraction as fqn or TypeMirror)
            // - Annotations (Must be replaced with wrappers, Need imports)
            // - Arrays (of above)
            annotationAttributes.add(new AnnotationAttribute(state, attribute));

        }

        AnnotationToWrap annotationToWrap = new AnnotationToWrap(annotationToCreateWrapperFor, annotationAttributes, state.getAnnotationWrapperCustomCode(annotationToCreateWrapperFor), state.getCustomInterfacesForAnnotation(annotationToCreateWrapperFor));

        createClass(state, annotationToWrap);

    }

    /**
     * Generates a wrapper class for one annotation.
     * <p>
     * Example how to use the templating engine.
     * <p>
     */

    private void createClass(State state, AnnotationToWrap annotationToWrap) {

        // Now create class

        // Fill Model
        Map<String, Object> model = new HashMap<>();
        model.put("state", state);
        model.put("atw", annotationToWrap);


        // create the class
        String filePath = state.getPackageName() + "." + annotationToWrap.getSimpleName() + "Wrapper";
        try {
            SimpleJavaWriter javaWriter = FilerUtils.createSourceFile(filePath, state.getAnnotatedElement().unwrap());
            javaWriter.writeTemplate("/AnnotationWrapper.tpl", model);
            javaWriter.close();
        } catch (IOException e) {
            state.getAnnotatedElement().compilerMessage().asError().write(AnnotationWrapperProcessorMessages.ERROR_CANT_CREATE_WRAPPER, annotationToWrap.getQualifiedName());
        }
    }

}