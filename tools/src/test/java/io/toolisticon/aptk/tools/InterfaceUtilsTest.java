package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.aptk.tools.wrapper.TypeElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InterfaceUtilsTest {


    @PassIn
    interface ToImplement<X, Y extends Collection<X>> extends ParentInterface<X> {

        Y doSomethingElse();

    }

    interface ParentInterface<X> {

        X doSomething();

    }


/*-

    @Test
    public void testGenerics(){

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(ToImplement.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                InterfaceUtils.getMethodsToBeImplemented(TypeElementWrapper.wrap(element));

                System.out.println("X");

            }
        })
                .compilationShouldSucceed()
                .executeTest();

    }
*/


    static class TypeVarReplacement<T, F> {

        @PassIn
        public Map<T, List<F>> map;

    }


    @Test
    public void getStringRepresentationWithReplaceTypeVars() {

        CompileTestBuilder.unitTest().defineTestWithPassedInElement(TypeVarReplacement.class, new UnitTest<VariableElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                        ToolingProvider.setTooling(processingEnvironment);
                        try {

                            Map<String, TypeMirrorWrapper> map = new HashMap<>();
                            map.put("T", TypeMirrorWrapper.wrap(String.class));
                            map.put("F", TypeMirrorWrapper.wrap(Integer.class));

                            TypeMirrorWrapper typeMirrorWrapper = new InterfaceUtils.TVTypeMirrorWrapper(element.asType(), map);

                            MatcherAssert.assertThat(typeMirrorWrapper.getTypeDeclaration(), Matchers.is("Map<String, List<Integer>>"));
                        } finally {
                            ToolingProvider.clearTooling();
                        }

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }


    @PassIn
    static class TypeVarMappingTest<T, F> {


        public Map<T, List<F>> map;

    }

    @Test
    public void mapTypeVars_withPassedInTypes() {
        CompileTestBuilder.unitTest().defineTestWithPassedInElement(TypeVarMappingTest.class, new UnitTest<TypeElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                        ToolingProvider.setTooling(processingEnvironment);
                        try {

                            Map<String, TypeMirrorWrapper> result = InterfaceUtils.mapTypeVars(TypeElementWrapper.wrap(element), TypeMirrorWrapper.wrap(String.class), TypeMirrorWrapper.wrap(Integer.class));

                            MatcherAssert.assertThat(result.keySet(), Matchers.containsInAnyOrder("F", "T"));
                            MatcherAssert.assertThat(result.values().stream().map(TypeMirrorWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder(String.class.getSimpleName(), Integer.class.getSimpleName()));


                        } finally {
                            ToolingProvider.clearTooling();
                        }

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void mapTypeVars_withoutPassedInTypes() {
        CompileTestBuilder.unitTest().defineTestWithPassedInElement(TypeVarMappingTest.class, new UnitTest<TypeElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                        ToolingProvider.setTooling(processingEnvironment);
                        try {

                            Map<String, TypeMirrorWrapper> result = InterfaceUtils.mapTypeVars(TypeElementWrapper.wrap(element));

                            MatcherAssert.assertThat(result.keySet(), Matchers.containsInAnyOrder("F", "T"));
                            MatcherAssert.assertThat(result.values().stream().map(TypeMirrorWrapper::toString).collect(Collectors.toList()), Matchers.containsInAnyOrder("F", "T"));


                        } finally {
                            ToolingProvider.clearTooling();
                        }

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    @PassIn
    interface MethodsToImplement_Base<T, F> extends MethodsToImplement_Mid<String, Integer> {

        T groundLevel(F baseParam);

    }

    interface MethodsToImplement_Mid<T, F> extends MethodsToImplement_Top<T, String> {

        T midLevel(F midParam);

        void doubledMethod(String parameter);

    }

    interface MethodsToImplement_Top<T, F> {

        T topLevel(F topParam);

        void withGenerics(List<? extends F> genParam);

        <K extends Collection<F>> void withMethodTypeVar(K param);

        F withMethodTypeVar() throws IOException;

        void doubledMethod(F parameter);

    }

    @Test
    public void getMethodsToImplement() {
        CompileTestBuilder.unitTest().defineTestWithPassedInElement(MethodsToImplement_Base.class, new UnitTest<TypeElement>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                        ToolingProvider.setTooling(processingEnvironment);
                        try {

                            Set<ExecutableElementWrapper> methods = InterfaceUtils.getMethodsToImplement(TypeElementWrapper.wrap(element));

                            MatcherAssert.assertThat(methods.stream().map(e -> e.getMethodSignature()).collect(Collectors.toList()), Matchers.containsInAnyOrder(
                                    "T groundLevel(F baseParam)",
                                    "String midLevel(Integer midParam)",
                                    "String topLevel(String topParam)",
                                    "void withGenerics(List<? extends String> genParam)",
                                    "<K extends Collection<String>> void withMethodTypeVar(K param)",
                                    "String withMethodTypeVar() throws IOException",
                                    "void doubledMethod(String parameter)"));


                        } finally {
                            ToolingProvider.clearTooling();
                        }

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


}