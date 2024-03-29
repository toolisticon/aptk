package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeParameterElementWrapper;
import io.toolisticon.aptk.tools.wrapper.VariableElementWrapper;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
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
 * This class contains helper methods to get all methods to implement by a class implementing the interface.
 * Resolves the issue of type var replacement by concrete types.
 */
public class InterfaceUtils {

    /**
     * Method to get all methods which must be implemented by implementing types of the interface.
     * Replaces type variables according to the type hierarchy.
     *
     * @param typeElementWrapper The TypeElement of the interface to be implemented
     * @param typeArgumentsToReplace     The types to apply to type variables
     * @return A set that contains all methods to be implemented
     */

    public static Set<ExecutableElementWrapper> getMethodsToImplement(TypeElementWrapper typeElementWrapper, TypeMirrorWrapper... typeArgumentsToReplace) {

        // skip for non interfaces
        if (!typeElementWrapper.isInterface()) {
            return Collections.emptySet();
        }

        Set<ExecutableElementWrapper> result = new HashSet<>();

        Map<String, TypeMirrorWrapper> typeVarMappings = mapTypeVars(typeElementWrapper, typeArgumentsToReplace);

        // Add all methods on this level
        result.addAll(typeElementWrapper.getMethods().stream().map(e -> new InterfaceUtils.TVExecutableElementWrapper(e.unwrap(), typeVarMappings)).collect(Collectors.toList()));

        // Add all methods of parent interfaces
        for (TypeMirrorWrapper parentInterface : typeElementWrapper.getInterfaces()) {
            result.addAll(getMethodsToImplement(parentInterface.getTypeElement().get(), getTypeArgumentsForParentInterface(parentInterface, typeVarMappings)));
        }

        return result;
    }

    /**
     * Resolves type argument of parent interface or type based on type the type hierarchy and passed in type arguments.
     * @param typeElementWrapper The base type element
     * @param interfaceOrSupertypeToSearch the interface or super type to get the resolved type arguments for
     * @param typeArgumentsToReplace The type arguments to pass in at typeElementWrapper initialization
     * @return The resolved type arguments for interfaceOrSupertypeToSearch
     */
    public static List<TypeMirrorWrapper> getResolvedTypeArgumentOfSuperTypeOrInterface(TypeElementWrapper typeElementWrapper, TypeMirrorWrapper interfaceOrSupertypeToSearch, TypeMirrorWrapper... typeArgumentsToReplace) {

        List<TypeMirrorWrapper> result = new ArrayList<>();

        Map<String, TypeMirrorWrapper> typeVarMappings = mapTypeVars(typeElementWrapper, typeArgumentsToReplace);

        // check super type
        Optional<TypeElementWrapper> superclassTEW = typeElementWrapper.getSuperclass().getTypeElement();
        if (superclassTEW.isPresent()) {

            if (superclassTEW.get().asType().erasure().equals(interfaceOrSupertypeToSearch.erasure())) {
                for (TypeMirrorWrapper typeArgument : typeElementWrapper.getSuperclass().getWrappedTypeArguments()) {
                    if (typeArgument.isTypeVar()) {
                        result.add(typeVarMappings.get(typeArgument.toString()));
                    } else {
                        result.add(typeArgument);
                    }
                }
            } else {
                result.addAll(getResolvedTypeArgumentOfSuperTypeOrInterface(superclassTEW.get(), interfaceOrSupertypeToSearch, getTypeArgumentsForParentInterface(typeElementWrapper.getSuperclass(), typeVarMappings)));
            }
        }

        // Add all methods of parent interfaces
        for (TypeMirrorWrapper parentInterface : typeElementWrapper.getInterfaces()) {

            if (interfaceOrSupertypeToSearch.getQualifiedName().equals(parentInterface.getQualifiedName())) {

                for (TypeMirrorWrapper typeArgument : parentInterface.getWrappedTypeArguments()) {
                    if (typeArgument.isTypeVar()) {
                        result.add(typeVarMappings.get(typeArgument.toString()));
                    } else {
                        result.add(typeArgument);
                    }
                }

            } else {
                result.addAll(getResolvedTypeArgumentOfSuperTypeOrInterface(parentInterface.getTypeElement().get(), interfaceOrSupertypeToSearch, getTypeArgumentsForParentInterface(parentInterface, typeVarMappings)));
            }

        }

        return result;
    }


    static Map<String, TypeMirrorWrapper> mapTypeVars(TypeElementWrapper interfaceTypeElement, TypeMirrorWrapper... interfacesTypeParameterTypes) {

        Map<String, TypeMirrorWrapper> map = new HashMap<>();

        int i = 0;
        for (TypeParameterElementWrapper typeParameterElementWrapper : interfaceTypeElement.getTypeParameters()) {
            map.put(typeParameterElementWrapper.asType().getTypeVar().toString(), i < interfacesTypeParameterTypes.length ? interfacesTypeParameterTypes[i] : typeParameterElementWrapper.asType());
            i++;
        }

        return map;
    }

    static TypeMirrorWrapper[] getTypeArgumentsForParentInterface(TypeMirrorWrapper parentInterface, Map<String, TypeMirrorWrapper> typeVarMappings) {
        List<TypeMirrorWrapper> result = new ArrayList<>();
        if (parentInterface.hasTypeArguments()) {
            for (TypeMirrorWrapper typeMirrorWrapper : parentInterface.getWrappedTypeArguments()) {

                if (typeMirrorWrapper.isTypeVar() && typeVarMappings.containsKey(typeMirrorWrapper.getTypeVar().toString())) {
                    result.add(typeVarMappings.get(typeMirrorWrapper.getTypeVar().toString()));
                } else {
                    result.add(typeMirrorWrapper);
                }

            }
        }

        return result.toArray(new TypeMirrorWrapper[0]);
    }


    public static class TVTypeMirrorWrapper extends TypeMirrorWrapper {

        private final TypeMirrorWrapper originalTypeMirror;
        private final Map<String, TypeMirrorWrapper> typeVarMap;

        TVTypeMirrorWrapper(TypeMirror typeMirror, Map<String, TypeMirrorWrapper> typeVarMap) {
            super(getTypeMirrorWithReplacedTypeVars(typeMirror, typeVarMap).unwrap());
            this.originalTypeMirror = TypeMirrorWrapper.wrap(typeMirror);
            this.typeVarMap = typeVarMap;
        }

        static TypeMirrorWrapper getTypeMirrorWithReplacedTypeVars(TypeMirror typeMirror, Map<String, TypeMirrorWrapper> typeVarMap) {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(typeMirror);
            if (typeMirrorWrapper.isTypeVar() && typeVarMap.containsKey(typeMirrorWrapper.getTypeVar().toString())) {
                return typeVarMap.get(typeMirrorWrapper.getTypeVar().toString());
            } else {
                return typeMirrorWrapper;
            }
        }


        /**
         * Gets the String containing the type declaration of wrapped TypeMirror.
         * Replaces TypeVars.
         *
         * @return the type declaration String
         */
        public String getTypeDeclaration() {


            if (this.getKind() == TypeKind.VOID) {
                return "void";
            } else if (this.isPrimitive()) {
                return this.toString();
            } else if (this.isArray()) {
                return getTypeDeclaration(this.getComponentType()) + "[]";
            } else if (this.isTypeVar()) {
                return this.toString();
            } else if (this.isDeclared()) {

                return this.getSimpleName() + (
                        this.hasTypeArguments() ? "<" + this.getWrappedTypeArguments().stream()
                                .map(e -> new TVTypeMirrorWrapper(e.unwrap(), typeVarMap))
                                .map(e -> e.getTypeDeclaration()).collect(Collectors.joining(", ")) + ">" : ""
                );

            } else if (this.isWildcardType()) {
                WildcardType wildcardType = this.getWildcardType();
                if (wildcardType.getSuperBound() != null) {
                    return "? super " + new TVTypeMirrorWrapper(wildcardType.getSuperBound(), typeVarMap).getTypeDeclaration();
                } else if (wildcardType.getExtendsBound() != null) {
                    return "? extends " + new TVTypeMirrorWrapper(wildcardType.getExtendsBound(), typeVarMap).getTypeDeclaration();
                } else {
                    return "?";
                }
            }

            return this.toString();
        }



    }


    static class TVExecutableElementWrapper extends ExecutableElementWrapper {

        private final Map<String, TypeMirrorWrapper> typeVarMap;

        TVExecutableElementWrapper(ExecutableElement element, Map<String, TypeMirrorWrapper> typeVarMap) {
            super(element);
            this.typeVarMap = typeVarMap;
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
                                        + tp.getBounds().stream().map(tm -> new TVTypeMirrorWrapper(tm.unwrap(), typeVarMap).getTypeDeclaration()).collect(Collectors.joining(" & "))
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
                    builder.append(nonVarargParameters.stream().map(element -> new TVTypeMirrorWrapper(element.asType().unwrap(), typeVarMap).getTypeDeclaration() + " " + element.getSimpleName()).collect(Collectors.joining(", ")));
                    builder.append(", ");
                }
                VariableElementWrapper lastVariableElement = this.getParameters().get(this.getParameters().size() - 1);
                builder.append(
                                lastVariableElement.asType().getWrappedComponentType().getTypeDeclaration())
                        .append("... ")
                        .append(lastVariableElement.getSimpleName()
                        );
            } else {

                builder.append(element.getParameters().stream().map(element -> new TVTypeMirrorWrapper(element.asType(), typeVarMap).getTypeDeclaration() + " " + element.getSimpleName()).collect(Collectors.joining(", ")));

            }
            builder.append(")");

            // Add throws if present
            if (!this.getThrownTypes().isEmpty()) {
                builder.append(" throws ");
                builder.append(this.getThrownTypes().stream().map(TypeMirrorWrapper::getSimpleName).collect(Collectors.joining(", ")));
            }

            return builder.toString();
        }

        @Override
        public Set<String> getImports() {
            return super.getImports();
        }

        /**
         * Gets the return type of the method. Type variables will be replaced by concrete types.
         *
         * @return The return type
         */
        @Override
        public TypeMirrorWrapper getReturnType() {
            return new TVTypeMirrorWrapper(super.getReturnType().unwrap(), typeVarMap);
        }

        @Override
        public List<VariableElementWrapper> getParameters() {

            return super.getParameters().stream().map(e -> new TVVariableElementWrapper(e.unwrap(), typeVarMap)).collect(Collectors.toList());
        }

        @Override
        public int hashCode() {

            int hashCode = 0;

            for (VariableElementWrapper parameter : getParameters()) {
                hashCode += parameter.asType().getTypeDeclaration().hashCode();
            }

            return getSimpleName().hashCode() + hashCode;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof TVExecutableElementWrapper)) {
                return false;
            }
            ExecutableElementWrapper otherObj = (ExecutableElementWrapper) obj;
            if (!this.getSimpleName().equals(otherObj.getSimpleName())) {
                return false;
            }

            if (this.getParameters().size() != otherObj.getParameters().size()) {
                return false;
            }

            for (int i = 0; i < this.getParameters().size(); i++) {
                if (!this.getParameters().get(i).asType().getTypeDeclaration().equals(otherObj.getParameters().get(i).asType().getTypeDeclaration())) {
                    return false;
                }
            }

            return true;

        }

    }


    static class TVVariableElementWrapper extends VariableElementWrapper {

        private final Map<String, TypeMirrorWrapper> typeVarMap;

        TVVariableElementWrapper(VariableElement element, Map<String, TypeMirrorWrapper> typeVarMap) {
            super(element);
            this.typeVarMap = typeVarMap;
        }

        @Override
        public TypeMirrorWrapper asType() {
            return new TVTypeMirrorWrapper(super.asType().unwrap(), typeVarMap);
        }
    }

}
