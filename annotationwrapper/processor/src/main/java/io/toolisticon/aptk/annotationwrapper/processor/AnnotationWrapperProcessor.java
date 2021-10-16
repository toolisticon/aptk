package io.toolisticon.aptk.annotationwrapper.processor;

import io.toolisticon.aptk.annotationwrapper.api.AnnotationWrapper;
import io.toolisticon.aptk.tools.AbstractAnnotationProcessor;
import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.FilerUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.generators.SimpleJavaWriter;
import io.toolisticon.spiap.api.Service;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        final PackageElement packageElement;
        final Set<String> annotationsToBeWrapped = new HashSet<>();
        final Map<String, List<CustomCodeClass>> customCodeClassMappings = new HashMap<>();
        final boolean usePublicVisibility;
        final boolean automaticallyWrapEmbeddedAnnotations;


        /**
         * Constructor
         *
         * @param packageElement the base package name
         */
        State(PackageElement packageElement) {
            this.packageElement = packageElement;

            Collections.addAll(this.annotationsToBeWrapped, AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(packageElement, AnnotationWrapper.class));

            AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(packageElement, AnnotationWrapper.class);
            usePublicVisibility = (Boolean) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "usePublicVisibility").getValue();
            automaticallyWrapEmbeddedAnnotations = (Boolean) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "automaticallyWrapEmbeddedAnnotations").getValue();

            // handle custom code classes
            List<AnnotationMirror> bindCustomCodeAnnotationMirrors = (List<AnnotationMirror>) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror, "bindCustomCode").getValue();
            for (AnnotationMirror bindCustomCodeAnnotationMirror : bindCustomCodeAnnotationMirrors) {
                String bindCustomCodeAnnotationFqn = AnnotationUtils.getClassAttributeFromAnnotationAsFqn(bindCustomCodeAnnotationMirror, "annotation");
                this.annotationsToBeWrapped.add(bindCustomCodeAnnotationFqn);
                List<CustomCodeClass> customCodeClasses = new ArrayList<>();
                for (TypeMirror typeMirror : AnnotationUtils.getClassArrayAttributeFromAnnotationAsTypeMirror(bindCustomCodeAnnotationMirror, "customCodeClass")) {
                    if (TypeUtils.CheckTypeKind.isDeclared(typeMirror)) {
                        customCodeClasses.add(new CustomCodeClass(TypeUtils.TypeRetrieval.getTypeElement(typeMirror)));
                    }
                }
                customCodeClassMappings.put(bindCustomCodeAnnotationFqn, customCodeClasses);
            }

            // now recursively add all embedded annotations
            if (automaticallyWrapEmbeddedAnnotations) {
                for (String annotationFqn : new ArrayList<>(annotationsToBeWrapped)) {
                    recursivelyAddAnnotations(annotationFqn);
                }
            }


        }

        /**
         * Recursively determines all embedded annotation types
         *
         * @param annotationFqn The annotation to scan
         */
        private void recursivelyAddAnnotations(String annotationFqn) {

            TypeElement element = TypeUtils.TypeRetrieval.getTypeElement(annotationFqn);

            for (ExecutableElement executableElement : FluentElementFilter.createFluentElementFilter(element.getEnclosedElements()).applyFilter(CoreMatchers.IS_METHOD).getResult()) {

                TypeMirror returnTypeMirror = executableElement.getReturnType();
                String returnTypeFqn = TypeMirrorWrapper.wrap(returnTypeMirror).getQualifiedName();
                if (
                        TypeKind.DECLARED.equals(returnTypeMirror.getKind())
                                && ElementKind.ANNOTATION_TYPE.equals(TypeUtils.TypeRetrieval.getTypeElement(returnTypeMirror).getKind())
                                && !this.annotationsToBeWrapped.contains(returnTypeFqn)
                ) {
                    this.annotationsToBeWrapped.add(returnTypeFqn);
                    recursivelyAddAnnotations(returnTypeFqn);
                }

            }

        }

        /**
         * Gets the PackageElement used during wrapper generation
         *
         * @return The PackageElement
         */
        public PackageElement getPackageElement() {
            return this.packageElement;
        }

        /**
         * Gets the package name used during wrapper generation
         *
         * @return The package name
         */
        public String getPackageName() {
            return this.packageElement.getQualifiedName().toString();
        }

        public Set<String> getAnnotationToBeWrapped() {
            return annotationsToBeWrapped;
        }

        public boolean containsAnnotationToBeWrapped(String annotationFqn) {
            return annotationsToBeWrapped.contains(annotationFqn);
        }

        public String getWrapperName(String annotationFqn) {
            return TypeMirrorWrapper.wrap(TypeUtils.TypeRetrieval.getTypeMirror(annotationFqn)).getSimpleName() + "Wrapper";
        }

        public boolean isUsePublicVisibility() {
            return usePublicVisibility;
        }

    }

    @Override
    public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        // process Services annotation
        for (Element element : roundEnv.getElementsAnnotatedWith(AnnotationWrapper.class)) {


            PackageElement packageElement = (PackageElement) element;
            State state = new State(packageElement);

            // Now iterate over the annotation types and
            for (String annotationToBeWrapped : state.getAnnotationToBeWrapped()) {
                processAnnotationToBeWrapped(state, annotationToBeWrapped);
            }


        }

        return false;

    }

    /**
     * Stores the configuration done in the {@see AnnotationToWrap} annotation.
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
        final List<CustomCodeClass> customCodeClasses;

        /**
         * The constructor
         *
         * @param annotationFqn     the fully qualified name of the annotation
         * @param attributes        The attributes of the annotation
         * @param customCodeClasses custom code classes to extend the wrappers api
         */
        AnnotationToWrap(String annotationFqn, List<AnnotationAttribute> attributes, List<CustomCodeClass> customCodeClasses) {
            this.annotationFqn = annotationFqn;
            this.attributes = attributes;
            this.customCodeClasses = customCodeClasses != null ? customCodeClasses : Collections.EMPTY_LIST;
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
         * Gets all custom code classes
         *
         * @return
         */
        public List<CustomCodeClass> getCustomCodeClasses() {
            return customCodeClasses;
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
         * @return
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
            for (CustomCodeClass customCodeClass : this.customCodeClasses) {
                for (String importString : customCodeClass.getImports()) {
                    if (importString != null && !importString.startsWith("java.lang")) {
                        imports.add(importString);
                    }
                }
            }

            return imports;
        }

    }

    /**
     * Class to store information about an annotation attribute.
     */
    public static class AnnotationAttribute {

        final ExecutableElement attribute;
        final State state;

        /**
         * Constructor
         *
         * @param state     the processing state
         * @param attribute The ExecutableElement that represents the attribute
         */
        AnnotationAttribute(State state, ExecutableElement attribute) {
            this.state = state;
            this.attribute = attribute;
        }

        /**
         * Gets the attributes name
         *
         * @return
         */
        public String getName() {
            return attribute.getSimpleName().toString();
        }

        public boolean isArray() {
            return TypeKind.ARRAY == attribute.getReturnType().getKind();
        }

        /**
         * Checks whether attribute is a primitive type or a String or not
         *
         * @return
         */
        public boolean isPrimitiveOrString() {

            TypeMirror returnType = attribute.getReturnType();
            if (isArray()) {
                returnType = ((ArrayType) returnType).getComponentType();
            }

            // check for primitives
            switch (returnType.getKind()) {
                case INT:
                case BYTE:
                case LONG:
                case CHAR:
                case FLOAT:
                case DOUBLE:
                case BOOLEAN:
                case SHORT:
                    return true;
                case DECLARED: {
                    return (String.class.getCanonicalName().equals(returnType.toString()));
                }

            }

            return false;

        }

        /**
         * Checks whether attribute is enum type or not
         *
         * @return
         */
        public boolean isEnum() {

            TypeMirror returnType = attribute.getReturnType();
            if (isArray()) {
                returnType = ((ArrayType) returnType).getComponentType();
            }

            return TypeKind.DECLARED.equals(returnType.getKind()) && ElementKind.ENUM.equals(TypeUtils.TypeRetrieval.getTypeElement(returnType).getKind());
        }

        /**
         * Checks whether attribute is of type Class or not
         *
         * @return
         */
        public boolean isClass() {

            TypeMirror returnType = attribute.getReturnType();
            if (isArray()) {
                returnType = ((ArrayType) returnType).getComponentType();
            }

            return TypeKind.DECLARED.equals(returnType.getKind()) && TypeUtils.TypeComparison.isTypeEqual(returnType, Class.class);
        }

        /**
         * Checks whether attribute is an annotation type or not
         *
         * @return
         */
        public boolean isAnnotationType() {

            TypeMirror returnType = attribute.getReturnType();
            if (isArray()) {
                returnType = ((ArrayType) returnType).getComponentType();
            }

            return TypeKind.DECLARED.equals(returnType.getKind()) && ElementKind.ANNOTATION_TYPE.equals(TypeUtils.TypeRetrieval.getTypeElement(returnType).getKind());
        }

        /**
         * Checks if annotation type has an annotation wrapper implementation
         *
         * @return
         */
        public boolean isWrappedAnnotationType() {
            return isAnnotationType() && state.containsAnnotationToBeWrapped(TypeMirrorWrapper.wrap(this.attribute.getReturnType()).getQualifiedName());
        }

        /**
         * Gets the wrapped annotation name
         *
         * @return
         */
        public String getTargetWrapperAnnotationName() {
            return state.getWrapperName(TypeMirrorWrapper.wrap(this.attribute.getReturnType()).getQualifiedName());
        }

        /**
         * Checks if attribute is optional
         *
         * @return
         */
        public boolean isOptional() {
            return this.attribute.getDefaultValue() != null;
        }

        /**
         * Gets the import needed by this attribute
         *
         * @return will return the fqn of the attribute type. Will return null for primitive types and types residing in implicit java.lang package
         */
        public String getImport() {

            TypeMirror typeMirror = attribute.getReturnType();

            if (isArray()) {
                typeMirror = ((ArrayType) attribute.getReturnType()).getComponentType();
            }

            if (typeMirror.getKind().isPrimitive()) {
                return null;
            }

            // return null for String
            String qualifiedName = TypeMirrorWrapper.wrap(typeMirror).getQualifiedName();
            return qualifiedName.startsWith("java.lang") ? null : qualifiedName;
        }

        /**
         * The attributes type as a simple name
         *
         * @return The attributes type as a simple name. Will return the component types simple name if attribute type is an array
         */
        public String getAttributeType() {
            return TypeMirrorWrapper.wrap(attribute.getReturnType()).getSimpleName();
        }

        /**
         * Gets the component type of an array
         *
         * @return the component type in case the attribute is an array, otherwise null
         */
        public String getComponentAttributeType() {
            return TypeMirrorWrapper.wrap(attribute.getReturnType()).getWrappedComponentType().getSimpleName();
        }

        /**
         * Gets the attributes wrapped TypeMirror
         *
         * @return The wrapped TypeMirror
         */
        public TypeMirrorWrapper getWrappedTypeMirror() {
            return TypeMirrorWrapper.wrap(attribute.getReturnType());
        }


    }

    public static class CustomCodeClass {

        final List<CustomCodeClassMethod> customMethods = new ArrayList<>();

        public CustomCodeClass(TypeElement typeElement) {

            List<ExecutableElement> methods = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                    .applyFilter(CoreMatchers.IS_METHOD)
                    .applyFilter(CoreMatchers.BY_MODIFIER).filterByAllOf(Modifier.STATIC)
                    .applyInvertedFilter(CoreMatchers.HAS_NO_PARAMETERS).getResult();

            for (ExecutableElement method : methods) {

                // Check if firstParameter matches Wrapper type - skip for now

                // check if method is visible (Enclosing type and method)

                customMethods.add(new CustomCodeClassMethod(method));

            }

        }

        public Set<String> getImports() {

            Set<String> imports = new HashSet<>();

            for (CustomCodeClassMethod method : customMethods) {
                imports.addAll(method.getImports());
            }

            return imports;

        }

        public List<CustomCodeClassMethod> getCustomMethods() {
            return customMethods;
        }
    }

    public static class CustomCodeClassMethod {

        private final ExecutableElement executableElement;

        public CustomCodeClassMethod(ExecutableElement executableElement) {
            this.executableElement = executableElement;
        }

        public String getMethodDeclarationString() {

            StringBuilder result = new StringBuilder();

            // First add the type parameters
            if (this.executableElement.getTypeParameters().size() > 0) {
                result.append("<");

                // TODO: has to be changed with Java 8
                boolean first = true;
                for (TypeParameterElement typeParameterElement : this.executableElement.getTypeParameters()) {

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
            result.append(TypeMirrorWrapper.wrap(this.executableElement.getReturnType()).getTypeDeclaration());
            result.append(" ");

            // add method name
            result.append(executableElement.getSimpleName());

            result.append("(");


            List<? extends VariableElement> variableElementSubList = executableElement.getParameters().subList(1, executableElement.getParameters().size());
            boolean first = true;
            for (VariableElement variableElement : variableElementSubList) {

                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }

                // add type
                result.append(TypeMirrorWrapper.wrap(variableElement.asType()).getTypeDeclaration());
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

            // First add TypeName
            result.append(this.executableElement.getEnclosingElement().getSimpleName().toString());
            result.append(".");

            // add method name
            result.append(executableElement.getSimpleName());

            result.append("(");

            if (executableElement.getParameters().size() > 1) {

                List<? extends VariableElement> variableElementSubList = executableElement.getParameters().subList(1, executableElement.getParameters().size());

                result.append("this");

                // TODO: has to be changed with Java 8
                for (VariableElement parameter : variableElementSubList) {

                    result.append(", ");
                    result.append(parameter.getSimpleName());

                }

            }

            result.append(")");

            return result.toString();
        }

        public Set<String> getImports() {
            Set<String> imports = new HashSet<>();

            // Must add return type
            imports.addAll(TypeMirrorWrapper.getImports(this.executableElement.getReturnType()));

            // Must add parameter types
            boolean firstParameter = true;
            for (VariableElement parameter : executableElement.getParameters()) {
                if (firstParameter) {
                    firstParameter = false;
                    continue;
                }

                imports.addAll(TypeMirrorWrapper.getImports(parameter.asType()));
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
        TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(annotationToCreateWrapperFor);

        // Now process all attributes
        List<ExecutableElement> attributes = FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements()).applyFilter(CoreMatchers.IS_METHOD).getResult();

        List<AnnotationAttribute> annotationAttributes = new ArrayList<>();
        for (ExecutableElement attribute : attributes) {

            // Annotations may have different Attribute Types
            // We have to distinct cases
            // - primitive types and String (require no imports)
            // - Enums (require imports)
            // - Classes (require imports and extraction as fqn or TypeMirror)
            // - Annotations (Must be replaced with wrappers, Need imports)
            // - Arrays (of above)
            annotationAttributes.add(new AnnotationAttribute(state, attribute));

        }

        AnnotationToWrap annotationToWrap = new AnnotationToWrap(annotationToCreateWrapperFor, annotationAttributes, state.customCodeClassMappings.get(annotationToCreateWrapperFor));

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
            SimpleJavaWriter javaWriter = FilerUtils.createSourceFile(filePath, state.getPackageElement());
            javaWriter.writeTemplate("/AnnotationWrapper.tpl", model);
            javaWriter.close();
        } catch (IOException e) {
            MessagerUtils.error(state.getPackageElement(), AnnotationWrapperProcessorMessages.ERROR_CANT_CREATE_WRAPPER, annotationToWrap.getQualifiedName());
        }
    }

}