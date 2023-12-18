package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit tests for {@link TypeElementWrapper}.
 */
public class TypeElementWrapperTest {

    public interface WhatsUp {

    }

    @PassIn
    private static class EmbeddedTestClass implements Cloneable, Serializable {

    }

    @PassIn
    private static class ChildTestClass extends EmbeddedTestClass implements WhatsUp {

    }

    @Test
    public void test_wrapAndUnwrap() {
        TypeElement element = Mockito.mock(TypeElement.class);
        TypeElementWrapper unit = TypeElementWrapper.wrap(element);
        MatcherAssert.assertThat(unit.unwrap(), Matchers.is(element));
    }

    @Test
    public void test_getNestingKind() {
        TypeElement element = Mockito.mock(TypeElement.class);
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.MEMBER);
        TypeElementWrapper unit = TypeElementWrapper.wrap(element);
        MatcherAssert.assertThat(unit.getNestingKind(), Matchers.is(NestingKind.MEMBER));
    }

    @Test
    public void test_hasNestingKind() {
        TypeElement element = Mockito.mock(TypeElement.class);
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.MEMBER);
        TypeElementWrapper unit = TypeElementWrapper.wrap(element);
        MatcherAssert.assertThat("Shoud have nesting kind", unit.hasNestingKind(NestingKind.MEMBER));
        MatcherAssert.assertThat("Shoud not have found nesting kind", !unit.hasNestingKind(NestingKind.TOP_LEVEL));
    }

    @Test
    public void test_isNested() {
        TypeElement element = Mockito.mock(TypeElement.class);
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.MEMBER);
        TypeElementWrapper unit = TypeElementWrapper.wrap(element);
        MatcherAssert.assertThat("Shoud have nesting kind", unit.isNested());
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.TOP_LEVEL);
        MatcherAssert.assertThat("Shoud not have found nesting kind", !unit.isNested());
    }

    @Test
    public void test_getQualifiedName_and_hasQualifiedName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(EmbeddedTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getQualifiedName(), Matchers.is(EmbeddedTestClass.class.getCanonicalName()));

                    MatcherAssert.assertThat("Should have found matching name", unit.hasQualifiedName(EmbeddedTestClass.class.getCanonicalName()));
                    MatcherAssert.assertThat("Should not have found matching name", !unit.hasQualifiedName("TestTest"));

                })
                .executeTest();
    }

    @Test
    public void test_getSuperclass_withParentClass() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(ChildTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getSuperclass().getQualifiedName(), Matchers.is(EmbeddedTestClass.class.getCanonicalName()));

                })
                .executeTest();
    }

    @Test
    public void test_getSuperclass_withoutParentClass() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(EmbeddedTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getSuperclass().getQualifiedName(), Matchers.is(Object.class.getCanonicalName()));

                })
                .executeTest();
    }

    @Test
    public void test_getInterfaces_direct() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(EmbeddedTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getInterfaces().stream().map(e -> e.getQualifiedName()).collect(Collectors.toList()), Matchers.containsInAnyOrder(Serializable.class.getCanonicalName(), Cloneable.class.getCanonicalName()));

                })
                .executeTest();
    }

    @Test
    public void test_getInterfaces_withoutSuperclass() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(ChildTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getInterfaces().stream().map(e -> e.getQualifiedName()).collect(Collectors.toList()), Matchers.containsInAnyOrder(WhatsUp.class.getCanonicalName()));

                })
                .executeTest();
    }

    @Test
    public void test_getAllInterfaces_indirect() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(ChildTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getAllInterfaces().stream().map(e -> e.getQualifiedName()).collect(Collectors.toList()), Matchers.containsInAnyOrder(WhatsUp.class.getCanonicalName(), Serializable.class.getCanonicalName(), Cloneable.class.getCanonicalName()));

                })
                .executeTest();
    }

    @PassIn
    public static class TypeParameterTest<T extends Serializable> {

        List<T> list;

    }

    @Test
    public void test_getTypeParameters_noTypeParameters() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(ChildTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getTypeParameters(), Matchers.hasSize(0));

                })
                .executeTest();
    }

    @Test
    public void test_getTypeParameters_withTypeParameters() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TypeParameterTest.class, PassIn.class, (processingEnvironment, element) -> {

                    TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                    MatcherAssert.assertThat(unit.getTypeParameters(), Matchers.hasSize(1));
                    MatcherAssert.assertThat(unit.getTypeParameters().get(0).getBounds().get(0).getQualifiedName(), Matchers.is(Serializable.class.getCanonicalName()));
                })
                .executeTest();
    }

    @PassIn
    private static class GetMethodTestClass {

        public GetMethodTestClass() {

        }

        private GetMethodTestClass(String a) {

        }

        private static class staticInnerClass {

        }

        private class innerClass {

        }

        private static String staticField;

        public final String finalField = "";

        private String privateField;

        static void staticMethod() {

        }

        private void privateMethod() {

        }

        private void methodWithParameters(String a, Integer b, int c, Object... d) {

        }

    }


    @Test
    public void test_getMethod() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);
                        MatcherAssert.assertThat(unit.getMethod("staticMethod").get().getSimpleName(), Matchers.is("staticMethod"));
                        MatcherAssert.assertThat(unit.getMethod("methodWithParameters", String.class, Integer.class, int.class, Object[].class).get().getSimpleName(), Matchers.is("methodWithParameters"));
                        MatcherAssert.assertThat("Must not find method", !unit.getMethod("methodWithParameters", String.class, Integer.class, Object[].class).isPresent());


                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getMethods() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getMethods().stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticMethod", "privateMethod", "methodWithParameters"));
                        MatcherAssert.assertThat(unit.getMethods(Modifier.STATIC).stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticMethod"));

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getFields() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getFields().stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticField", "privateField", "finalField"));
                        MatcherAssert.assertThat(unit.getFields(Modifier.STATIC).stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticField"));
                        MatcherAssert.assertThat(unit.getFields(Modifier.STATIC, Modifier.PRIVATE).stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticField"));

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getFieldByName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getFieldByName("staticField").get().getSimpleName(), Matchers.is("staticField"));
                        MatcherAssert.assertThat(unit.getFieldByName("privateField").get().getSimpleName(), Matchers.is("privateField"));
                        MatcherAssert.assertThat("Should not find field", !unit.getFieldByName("NOT_EXISTENT").isPresent());


                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }


    @Test
    public void test_getConstructors() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getConstructors(), Matchers.hasSize(2));
                        MatcherAssert.assertThat(unit.getConstructors(Modifier.PRIVATE), Matchers.hasSize(1));
                        MatcherAssert.assertThat(unit.getConstructors(Modifier.PUBLIC), Matchers.hasSize(1));
                        MatcherAssert.assertThat(unit.getConstructors(Modifier.STATIC), Matchers.hasSize(0));

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getInnerTypes() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetMethodTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getInnerTypes().stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("innerClass", "staticInnerClass"));
                        MatcherAssert.assertThat(unit.getInnerTypes(Modifier.PRIVATE).stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("innerClass", "staticInnerClass"));
                        MatcherAssert.assertThat(unit.getInnerTypes(Modifier.PRIVATE, Modifier.STATIC).stream().map(ElementWrapper::getSimpleName).collect(Collectors.toList()), Matchers.containsInAnyOrder("staticInnerClass"));

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    private static class GetOuterTypeTestClass {

        class MidInnerClass {

            @PassIn
            private  class innerMostClass {

            }

        }

    }

    @Test
    public void test_getOuterType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetOuterTypeTestClass.class, PassIn.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        TypeElementWrapper unit = TypeElementWrapper.wrap(element);

                        MatcherAssert.assertThat(unit.getOuterType().get().getQualifiedName(), Matchers.is(GetOuterTypeTestClass.MidInnerClass.class.getCanonicalName()));
                        MatcherAssert.assertThat(unit.getOuterTopLevelType().get().getQualifiedName(), Matchers.is(TypeElementWrapperTest.class.getCanonicalName()));

                        MatcherAssert.assertThat("Should return empty Optional for Top Level Class", !unit.getOuterTopLevelType().get().getOuterType().isPresent());
                        MatcherAssert.assertThat("Should return empty Optional for Top Level Class", !unit.getOuterTopLevelType().get().getOuterTopLevelType().isPresent());

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }


    @Test
    public void test_getByFqn() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest( (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat(TypeElementWrapper.getByFqn(String.class.getCanonicalName()).get().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                        MatcherAssert.assertThat("Should get empty Optional for String array", !TypeElementWrapper.getByFqn(String[].class.getCanonicalName()).isPresent());
                        MatcherAssert.assertThat("Should get empty Optional for atomic type", !TypeElementWrapper.getByFqn(int.class.getCanonicalName()).isPresent());

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getByClass() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest( (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat(TypeElementWrapper.getByClass(String.class).get().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                        MatcherAssert.assertThat("Should get empty Optional for String array", !TypeElementWrapper.getByClass(String[].class).isPresent());
                        MatcherAssert.assertThat("Should get empty Optional for atomic type", !TypeElementWrapper.getByClass(int.class).isPresent());

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

    @Test
    public void test_getByTypeMirror() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest( (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat(TypeElementWrapper.getByTypeMirror(TypeUtils.TypeRetrieval.getTypeMirror(String.class)).get().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                        MatcherAssert.assertThat("Should get empty Optional for String array", !TypeElementWrapper.getByTypeMirror(TypeUtils.TypeRetrieval.getTypeMirror(String[].class)).isPresent());
                        MatcherAssert.assertThat("Should get empty Optional for atomic type", !TypeElementWrapper.getByTypeMirror(TypeUtils.TypeRetrieval.getTypeMirror(int.class)).isPresent());

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

/*-
    public static Optional<TypeElementWrapper> getByFqn (String fqn)
    public static Optional<TypeElementWrapper> getByClass (Class<?> clazz)
    public static Optional<TypeElementWrapper> getTypeMirror (TypeMirror typeMirror)
  */

    @PassIn
    public enum TestEnum {
        ABC,
        DEF,
        HIJ_KLM;
    }

    @Test
    public void test_getEnumValues() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestEnum.class, (processingEnvironment, element) -> {

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        // happy path
                        MatcherAssert.assertThat(TypeElementWrapper.wrap(element).getEnumValues().stream().map(e -> e.getSimpleName()).collect(Collectors.toList()), Matchers.containsInAnyOrder("ABC","DEF", "HIJ_KLM"));

                        // no enum ! => null
                        MatcherAssert.assertThat(TypeElementWrapper.getByClass(TypeElementWrapperTest.class).get().getEnumValues(), Matchers.nullValue());

                    } finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .executeTest();
    }

}
