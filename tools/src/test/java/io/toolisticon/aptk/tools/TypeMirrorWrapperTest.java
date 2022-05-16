package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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
    public void test_nullValuedWrapper_TypeMirror() {
        TypeMirrorWrapper.wrap((TypeMirror) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_nullValuedWrapper_fqn() {
        TypeMirrorWrapper.wrap((String) null);
    }

    @Test
    public void test_isVoid() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.VOID)).isVoidType());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isVoidType());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isVoidType(mockTypeCheck(null)));
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
    public void test_isErrorType() {
        MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ERROR)).isErrorType());
        MatcherAssert.assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isErrorType());
        MatcherAssert.assertThat("Expected false for null", !TypeMirrorWrapper.isErrorType(mockTypeCheck(null)));
    }


    @Test
    public void test_isDeclaredType() {
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

    public static class CollectionCheck_List {
        @PassIn
        List<String> collectionField;
    }

    public static class CollectionCheck_ListWithoutComponentType {
        @PassIn
        List collectionField;
    }

    public static class CollectionCheck_Set {
        @PassIn
        Set<String> collectionField;
    }

    public static class CollectionCheck_NoCollection {
        @PassIn
        Map<String, String> noCollectionField;
    }

    @Test
    public void test_isCollection_List() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_List.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(element.asType()).isCollection());
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    @Test
    public void test_isCollection_Set() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_Set.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    MatcherAssert.assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(element.asType()).isCollection());
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    @Test
    public void test_isCollection_noCollection() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_NoCollection.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    MatcherAssert.assertThat("Expected false for no collection type", !TypeMirrorWrapper.wrap(element.asType()).isCollection());
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }


    @Test
    public void test_getComponentType_forList() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_Set.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
                    MatcherAssert.assertThat("Should be true", typeMirrorWrapper.hasComponentType());
                    MatcherAssert.assertThat(typeMirrorWrapper.getComponentType().toString(), Matchers.is(String.class.getCanonicalName()));
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    @Test
    public void test_getComponentType_forListWithoutComponentType() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_ListWithoutComponentType.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
                    MatcherAssert.assertThat("Should be true", typeMirrorWrapper.hasComponentType());
                    MatcherAssert.assertThat(typeMirrorWrapper.getWrappedComponentType().getQualifiedName(), Matchers.is(Object.class.getCanonicalName()));
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    @Test
    public void test_getComponentType_forSet() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_Set.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
                    MatcherAssert.assertThat("Should be true", typeMirrorWrapper.hasComponentType());
                    MatcherAssert.assertThat(typeMirrorWrapper.getComponentType().toString(), Matchers.is(String.class.getCanonicalName()));
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    @Test
    public void test_getComponentType_nonCollection() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(CollectionCheck_NoCollection.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                ToolingProvider.setTooling(processingEnvironment);
                try {
                    TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
                    MatcherAssert.assertThat("Should be false", !typeMirrorWrapper.hasComponentType());
                    MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getComponentType(), Matchers.nullValue());
                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
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

    // ---------------------------------------------------------------
    // -- getImports
    // ---------------------------------------------------------------

    public static class ImportTest {

        @PassIn
        Map<? super Long, List<? extends Serializable>[]> field;

    }

    public static class ImportTest_NonGeneric {

        @PassIn
        String field;

    }

    public static class ImportTest_Primitive {

        @PassIn
        long field;

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

    // ---------------------------------------------------------------
    // -- getTypeDeclaration
    // ---------------------------------------------------------------

    @Test
    public void test_getTypeDeclaration_withTypeParameters() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                MatcherAssert.assertThat(typeDeclaration, Matchers.is("Map<? super Long, List<? extends Serializable>[]>"));

            }
        }).executeTest();
    }

    @Test
    public void test_getTypeDeclaration_withoutTypeParameters() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest_NonGeneric.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                MatcherAssert.assertThat(typeDeclaration, Matchers.is("String"));

            }
        }).executeTest();
    }

    @Test
    public void test_getTypeDeclaration_withPrimitive() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest_Primitive.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                MatcherAssert.assertThat(typeDeclaration, Matchers.is("long"));

            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- hasTypeArguments
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // -- getTypeArguments
    // ---------------------------------------------------------------

    @Test
    public void test_getTypeArguments() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(TypeArgumentsTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                try {
                    ToolingProvider.setTooling(processingEnvironment);

                    List<? extends TypeMirror> typeArgumentTypeMirrors = TypeMirrorWrapper.wrap(element.asType()).getTypeArguments();
                    MatcherAssert.assertThat(typeArgumentTypeMirrors, Matchers.hasSize(2));
                    MatcherAssert.assertThat(TypeUtils.TypeConversion.convertToFqn(typeArgumentTypeMirrors.get(0)), Matchers.is(String.class.getCanonicalName()));
                    MatcherAssert.assertThat(TypeUtils.TypeConversion.convertToFqn(typeArgumentTypeMirrors.get(1)), Matchers.is(Long.class.getCanonicalName()));

                    // check for non typeArgument class
                    MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.getEnclosingElement().asType()).getTypeArguments(), Matchers.nullValue());

                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- getWrappedTypeArguments
    // ---------------------------------------------------------------

    @Test
    public void test_getWrappedTypeArguments() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(TypeArgumentsTest.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                try {
                    ToolingProvider.setTooling(processingEnvironment);

                    List<? extends TypeMirrorWrapper> typeArgumentTypeMirrorWrappers = TypeMirrorWrapper.wrap(element.asType()).getWrappedTypeArguments();
                    MatcherAssert.assertThat(typeArgumentTypeMirrorWrappers, Matchers.hasSize(2));
                    MatcherAssert.assertThat(typeArgumentTypeMirrorWrappers.get(0).getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                    MatcherAssert.assertThat(typeArgumentTypeMirrorWrappers.get(1).getQualifiedName(), Matchers.is(Long.class.getCanonicalName()));

                    // check for non typeArgument class
                    MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.getEnclosingElement().asType()).getWrappedTypeArguments(), Matchers.nullValue());

                } finally {
                    ToolingProvider.clearTooling();
                }
            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- getPackage
    // ---------------------------------------------------------------

    @PassIn
    public static class PackageTest {

    }

    @Test
    public void test_getPackage() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(PackageTest.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), Matchers.is(PackageTest.class.getPackage().getName()));

            }
        }).executeTest();
    }


    public static class PackageTestOfArray {
        @PassIn
        PackageTest[] array;

    }

    @Test
    public void test_getPackageOfArray() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(PackageTestOfArray.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), Matchers.is(PackageTest.class.getPackage().getName()));

            }
        }).executeTest();
    }

    public static class PackageTestOfPrimitive {
        @PassIn
        long[] array;

    }

    @Test
    public void test_getPackageOfPrimitiveArray() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(PackageTestOfPrimitive.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), Matchers.nullValue());

            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- getTypeElement
    // ---------------------------------------------------------------


    @PassIn
    public static class GetTypeElement {

    }

    @Test
    public void test_getTypeElement() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(GetTypeElement.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getTypeElement().get().getQualifiedName().toString(), Matchers.is(GetTypeElement.class.getCanonicalName()));

            }
        }).executeTest();
    }


    public static class GetTypeElement_ofPrimitive {
        @PassIn
        long primitive;
    }

    @Test
    public void test_getTypeElement_ofPrimitive() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(GetTypeElement_ofPrimitive.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                MatcherAssert.assertThat("Must return empty optional",!TypeMirrorWrapper.wrap(element.asType()).getTypeElement().isPresent());

            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- getSimpleName
    // ---------------------------------------------------------------

    @PassIn
    public static class GetName {
    }

    public static class GetName_ofPrimitive {
        @PassIn
        long primitive;
    }

    public static class GetName_ofArray {
        @PassIn
        GetName[] array;
    }

    @Test
    public void test_getSimpleName() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), Matchers.is(GetName.class.getSimpleName()));

            }
        }).executeTest();
    }

    @Test
    public void test_getSimpleName_ofPrimitive() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofPrimitive.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), Matchers.is("long"));

            }
        }).executeTest();
    }

    @Test
    public void test_getSimpleName_ofArray() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofArray.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), Matchers.is(GetName.class.getSimpleName()));

            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- getQualifiedName
    // ---------------------------------------------------------------

    @Test
    public void test_getQualifiedName() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), Matchers.is(GetName.class.getCanonicalName()));

            }
        }).executeTest();
    }

    @Test
    public void test_getQualifiedName_ofPrimitive() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofPrimitive.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), Matchers.is("long"));

            }
        }).executeTest();
    }

    @Test
    public void test_getQualifiedName_ofArray() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofArray.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                MatcherAssert.assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), Matchers.is(GetName.class.getCanonicalName()));

            }
        }).executeTest();
    }


}
