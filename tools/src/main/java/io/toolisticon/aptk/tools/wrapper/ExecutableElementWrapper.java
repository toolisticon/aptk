package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.TypeMirrorWrapper;

import javax.lang.model.element.ExecutableElement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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


    /**
     * Returns all Type Parameters of the wrapped Executable Element.
     * @return a list containing all  wrapped TypeParameters, or an empty list if none are present.
     */
    public List<TypeParameterElementWrapper> getTypeParameters() {
        return this.element.getTypeParameters().stream().map(TypeParameterElementWrapper::wrap).collect(Collectors.toList());
    }


    /**
     * Returns the return type of the Executable Element.
     * Wraps a NoType with kind VOID if this executable is not a method, or is a method that does not return a value.
     * @return a TypeMirrorWrapper instance
     */
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

    /**
     * Returns the receiver type of this executable, or NoType with kind NONE if the executable has no receiver type. An executable which is an instance method, or a constructor of an inner class, has a receiver type derived from the declaring type. An executable which is a static method, or a constructor of a non-inner class, or an initializer (static or instance), has no receiver type.
     *
     * @return the receiver type of this executable
     */
    public TypeMirrorWrapper getReceiverType() {
        return TypeMirrorWrapper.wrap(this.element.getReceiverType());
    }

    /**
     * Returns if wrapped ExecutableElement has a vararg parameter
     * @return
     */
    public boolean isVarArgs() {
        return this.element.isVarArgs();
    }

    /**
     * Returns if wrapped ExecutableElement is a default implementation.
     * @return true if wrapped ExecutableElement is a default implementation, otherwise false
     */
    public boolean isDefault() {
        return this.element.isDefault();
    }


    /**
     * Returns the exceptions and other throwables listed in this method or constructor's throws clause in declaration order.
     * @return the exceptions and other throwables listed in the throws clause, or an empty list if there are none
     */
    public List<TypeMirrorWrapper> getThrownTypes() {
        return this.element.getThrownTypes().stream().map(TypeMirrorWrapper::wrap).collect(Collectors.toList());
    }

    /**
     * Returns an Optional containing the default value if this executable is an annotation type element and if a default value is present.
     * @return the Optional containing the default value if present
     */
    public Optional<AnnotationValueWrapper> getDefaultValue() {
        return this.element.getDefaultValue() != null ? Optional.of(AnnotationValueWrapper.wrap(element.getDefaultValue())) : Optional.empty();
    }

    /**
     * Wraps an ExecutableElement.
     * Throws IllegalArgumentException if passed executableElement is null
     * @param executableElement the element to wrap, must not be null
     * @return the wrapper instance
     */
    public static ExecutableElementWrapper wrap(ExecutableElement executableElement) {
        return new ExecutableElementWrapper(executableElement);
    }

}
