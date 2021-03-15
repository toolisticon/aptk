package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Unit test for {@link TypeMirrorWrapper}.
 */
public class TypeMirrorWrapperTest {

    /*-
    @Test
    public void test_() {

    }
    */

    @Test(expected = IllegalArgumentException.class)
    public void test_nullValuedWrapper() {
        TypeMirrorWrapper.wrap(null);
    }

    @Test
    public void test_isWildcardType() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).isWildcardType());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isWildcardType());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isWildcardType(mockTypeCheck(null)));
    }

    @Test
    public void test_getWildcardType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(WildcardType.class, TypeKind.WILDCARD);
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(typeMirror).getWildcardType(), Matchers.is(typeMirror));

        // expect null for non matching type kind
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).getWildcardType(), Matchers.nullValue());

    }

    @Test
    public void test_isDeclardType() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isDeclared());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ARRAY)).isDeclared());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isDeclared(null));
    }

    @Test
    public void test_getDeclaredType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(DeclaredType.class, TypeKind.DECLARED);
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(typeMirror).getDeclaredType(), Matchers.is(typeMirror));

        // expect null for non matching type kind
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getDeclaredType(), Matchers.nullValue());

    }

    @Test
    public void test_isArrayType() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ARRAY)).isArray());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isArray());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isArray(null));
    }

    @Test
    public void test_getArrayType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(ArrayType.class, TypeKind.ARRAY);
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(typeMirror).getArrayType(), Matchers.is(typeMirror));

        // expect null for non matching type kind
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getArrayType(), Matchers.nullValue());

    }

    @Test
    public void test_isPrimitiveType() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.LONG)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.SHORT)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DOUBLE)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.FLOAT)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.BYTE)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.BOOLEAN)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.INT)).isPrimitive());
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.CHAR)).isPrimitive());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isPrimitive());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isPrimitive(null));
    }

    @Test
    public void test_getPrimitiveType() {
        // Expect cast instance of TypeMirror
        testGetPrimitiveType(TypeKind.INT);
        testGetPrimitiveType(TypeKind.LONG);
        testGetPrimitiveType(TypeKind.FLOAT);
        testGetPrimitiveType(TypeKind.DOUBLE);
        testGetPrimitiveType(TypeKind.SHORT);
        testGetPrimitiveType(TypeKind.BYTE);
        testGetPrimitiveType(TypeKind.BOOLEAN);
        testGetPrimitiveType(TypeKind.CHAR);

        // expect null for non matching type kind
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getPrimitiveType(), Matchers.nullValue());

    }

    private void testGetPrimitiveType(TypeKind typeKind) {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(PrimitiveType.class, typeKind);
        MatcherAssert.assertThat(TypeMirrorWrapper.wrap(typeMirror).getPrimitiveType(), Matchers.is(typeMirror));
    }

    private <T extends TypeMirror> TypeMirror mockTypeCheck(Class<T> type, TypeKind typeKind) {
        TypeMirror typeMirror = Mockito.mock(type);
        Mockito.when(typeMirror.getKind()).thenReturn(typeKind);
        return typeMirror;
    }

    private TypeMirror mockTypeCheck(TypeKind typeKind) {
        return mockTypeCheck(TypeMirror.class, typeKind);
    }


    public static class ImportTest {

        @PassIn
        Map<? super Long, List<? extends Serializable>[]> field;

    }

    @Test
    public void test_getImports() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                Set<String> imports = TypeMirrorWrapper.wrap(element.asType()).getImports();

                MatcherAssert.assertThat(imports, Matchers.containsInAnyOrder(List.class.getCanonicalName(), Map.class.getCanonicalName(), Serializable.class.getCanonicalName()));

            }
        }).executeTest();
    }

    @Test
    public void test_getTypeDeclaration() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                MatcherAssert.assertThat(typeDeclaration, Matchers.is("Map<? super Long, List<? extends Serializable>[]>"));

            }
        }).executeTest();
    }

    public static class TypeArgumentsTest {
        @PassIn
        Map<String, Long> field;
    }

    @Test
    public void test_hasTypeArguments() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(TypeArgumentsTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                // with type arguments
                MatcherAssert.assertThat("expect true for Map", TypeMirrorWrapper.wrap(element.asType()).hasTypeArguments());

                // No Type arguments
                MatcherAssert.assertThat("expect false for String", !TypeMirrorWrapper.wrap(processingEnvironment.getElementUtils().getTypeElement(String.class.getCanonicalName()).asType()).hasTypeArguments());

            }
        }).executeTest();
    }


}
