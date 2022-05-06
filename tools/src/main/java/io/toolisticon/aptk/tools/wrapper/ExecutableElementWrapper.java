package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wraps an ExecutableElement to provide some convenience functionality
 */
public class ExecutableElementWrapper extends ElementWrapper<ExecutableElement> {

    
    /**
     * Hidden constructor.
     *
     * @param executableElement the ExecutableElement to wrap
     */
    private ExecutableElementWrapper(ExecutableElement executableElement) {
        super(executableElement);
    }


    /**
     * Gets the method signature String for an ExecutableElement.
     * <p>
     * This is useful for implementing of interfaces in generated classes.
     * <p>
     * This method works perfectly well for ExecutableElements of classes and interfaces in compilation,
     * but will have some limitations for precompiled classes.
     * <p>
     * Keep in mind that javac doesn't store parameter names in bytecode out of the box.
     * So passing in an ExecutableElement of a precompiled class will have arg0,arg1,.. as parameter names.
     * This can be changed if class is compiled with "-parameters" compiler option.
     * <p>
     * Annotations won't be part of the method signature.
     *
     * @return the method signature
     */
    public String getMethodSignature() {

        StringBuilder builder = new StringBuilder();

        // Add throws if present
        if (!this.getTypeParameters().isEmpty()) {
            builder.append("<");
            builder.append(
                    this.getTypeParameters().stream().map(
                            tp -> tp.getSimpleName()
                                    + " extends "
                                    + tp.getBounds().stream().map(tm -> tm.getTypeDeclaration()).collect(Collectors.joining(" & "))
                    ).collect(Collectors.joining(", "))
            );
            builder.append("> ");
        }


        // return type and method name
        builder.append(this.getReturnType().getTypeDeclaration()).append(" ").append(this.getSimpleName());

        // add parameters
        builder.append("(");
        if (this.isVarArgs()) {
            if (this.getParameters().size() > 1) {
                List<VariableElementWrapper> nonVarargParameters = this.getParameters().subList(0, this.getParameters().size() - 1);
                builder.append(nonVarargParameters.stream().map(element -> element.asType().getTypeDeclaration() + " " + element.getSimpleName()).collect(Collectors.joining(", ")));
                builder.append(", ");
            }
            VariableElementWrapper lastVariableElement = this.getParameters().get(this.getParameters().size() - 1);
            builder.append(
                            lastVariableElement.asType().getWrappedComponentType().getTypeDeclaration())
                    .append("... ")
                    .append(lastVariableElement.getSimpleName()
                    );
        } else {

            builder.append(element.getParameters().stream().map(element -> TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration() + " " + element.getSimpleName()).collect(Collectors.joining(", ")));

        }
        builder.append(")");

        // Add throws if present
        if (!this.getThrownTypes().isEmpty()) {
            builder.append(" throws ");
            builder.append(this.getThrownTypes().stream().map(TypeMirrorWrapper::getSimpleName).collect(Collectors.joining(", ")));
        }

        return builder.toString();
    }

    /**
     * Gets imports needed by method signature.
     * Doesn't include annotation types.
     *
     * @return a set containing all imports needed by the method signature
     */
    public Set<String> getImports() {
        Set<String> imports = new HashSet<>();

        // Type Parameters
        imports.addAll(
                this.getTypeParameters().stream()
                        .flatMap(tp -> tp.getBounds().stream())
                        .flatMap(tm -> tm.getImports().stream())
                        .collect(Collectors.toList()));

        // Return value
        imports.addAll(this.getReturnType().getImports());

        // parameters
        for (VariableElementWrapper typeParameterElement : this.getParameters()) {
            imports.addAll(typeParameterElement.asType().getImports());
        }

        // thrown exception types
        for (TypeMirrorWrapper thrownTypes : this.getThrownTypes()) {
            imports.addAll(thrownTypes.getImports());
        }

        return imports;
    }

    static List<String> getImportsNeededByMethodsSignature() {
        return null;
    }

    public List<TypeParameterElementWrapper> getTypeParameters() {
        return this.element.getTypeParameters().stream().map(TypeParameterElementWrapper::wrap).collect(Collectors.toList());
    }


    public TypeMirrorWrapper getReturnType() {
        return TypeMirrorWrapper.wrap(this.element.getReturnType());
    }

    /**
     * Returns all Parameters as a List.
     *
     * @return a List of parameters, or empty list
     */
    public List<VariableElementWrapper> getParameters() {
        return this.element.getParameters().stream().map(VariableElementWrapper::wrap).collect(Collectors.toList());
    }

    public TypeMirrorWrapper getReceiverType() {
        return TypeMirrorWrapper.wrap(this.element.getReceiverType());
    }

    public boolean isVarArgs() {
        return this.element.isVarArgs();
    }

    public boolean isDefault() {
        return this.element.isDefault();
    }

    public List<TypeMirrorWrapper> getThrownTypes() {
        return this.element.getThrownTypes().stream().map(TypeMirrorWrapper::wrap).collect(Collectors.toList());
    }

    public AnnotationValueWrapper getDefaultValue() {
        return this.element.getDefaultValue() != null ? AnnotationValueWrapper.wrap(element.getDefaultValue()) : null;
    }

    public static ExecutableElementWrapper wrap(ExecutableElement executableElement) {
        return new ExecutableElementWrapper(executableElement);
    }

}
