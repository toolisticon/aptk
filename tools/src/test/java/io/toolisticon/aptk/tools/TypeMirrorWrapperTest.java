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
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    @Test(expected = IllegalArgumentException.class)
    public void test_nullValuedWrapper_class() {
        TypeMirrorWrapper.wrap((Class<?>) null);
    }

    @Test
    public void test_wrap() {

        CompileTestBuilder.unitTest().<Element>defineTest((processingEnvironment, element) -> {

            ToolingProvider.setTooling(processingEnvironment);
            try {

                assertThat(TypeMirrorWrapper.wrap(TypeMirrorWrapperTest.class).getQualifiedName(), is(TypeMirrorWrapperTest.class.getCanonicalName()));
                assertThat(TypeMirrorWrapper.wrap(TypeMirrorWrapperTest.class.getCanonicalName()).getQualifiedName(), is(TypeMirrorWrapperTest.class.getCanonicalName()));


            } finally {
                ToolingProvider.clearTooling();
            }
        }).executeTest();

    }


    @Test
    public void test_isVoid() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.VOID)).isVoidType());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isVoidType());
        assertThat("Expected false for null", !TypeMirrorWrapper.isVoidType(mockTypeCheck(null)));
    }

    @Test
    public void test_isWildcardType() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).isWildcardType());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isWildcardType());
        assertThat("Expected false for null", !TypeMirrorWrapper.isWildcardType(mockTypeCheck(null)));
    }

    @Test
    public void test_getWildcardType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(WildcardType.class, TypeKind.WILDCARD);
        assertThat(TypeMirrorWrapper.wrap(typeMirror).getWildcardType(), is(typeMirror));

        // expect null for non matching type kind
        assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).getWildcardType(), Matchers.nullValue());

    }


    public static class ClassType {

    }


    public static enum EnumType {

    }


    public interface InterfaceType {

    }

    @Test
    public void test_isClassType() {
        CompileTestBuilder.unitTest().defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                ToolingProvider.setTooling(processingEnvironment);

                assertThat("must return true for Class", TypeMirrorWrapper.wrap(ClassType.class).isClass());
                assertThat("must return false for Enum", !TypeMirrorWrapper.wrap(EnumType.class).isClass());
                assertThat("must return false for Interface", !TypeMirrorWrapper.wrap(InterfaceType.class).isClass());
                assertThat("must return false for annotation", !TypeMirrorWrapper.wrap(Target.class).isClass());

            }
        }).executeTest();
    }

    @Test
    public void test_isEnumType() {
        CompileTestBuilder.unitTest().defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                ToolingProvider.setTooling(processingEnvironment);

                assertThat("must return false for Class", !TypeMirrorWrapper.wrap(ClassType.class).isEnum());
                assertThat("must return true for Enum", TypeMirrorWrapper.wrap(EnumType.class).isEnum());
                assertThat("must return false for Interface", !TypeMirrorWrapper.wrap(InterfaceType.class).isEnum());
                assertThat("must return false for annotation", !TypeMirrorWrapper.wrap(Target.class).isEnum());

            }
        }).executeTest();
    }

    @Test
    public void test_isInterfaceType() {
        CompileTestBuilder.unitTest().defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                ToolingProvider.setTooling(processingEnvironment);

                assertThat("must return false for Class", !TypeMirrorWrapper.wrap(ClassType.class).isInterface());
                assertThat("must return false for Enum", !TypeMirrorWrapper.wrap(EnumType.class).isInterface());
                assertThat("must return true for Interface", TypeMirrorWrapper.wrap(InterfaceType.class).isInterface());
                assertThat("must return false for annotation", !TypeMirrorWrapper.wrap(Target.class).isInterface());


            }
        }).executeTest();
    }

    @Test
    public void test_isAnnotationType() {
        CompileTestBuilder.unitTest().defineTest(new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                ToolingProvider.setTooling(processingEnvironment);

                assertThat("must return false for Class", !TypeMirrorWrapper.wrap(ClassType.class).isAnnotation());
                assertThat("must return false for Enum", !TypeMirrorWrapper.wrap(EnumType.class).isAnnotation());
                assertThat("must return false for Interface", !TypeMirrorWrapper.wrap(InterfaceType.class).isAnnotation());
                assertThat("must return true for annotation", TypeMirrorWrapper.wrap(Target.class).isAnnotation());


            }
        }).executeTest();
    }

    @Test
    public void test_isErrorType() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ERROR)).isErrorType());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isErrorType());
        assertThat("Expected false for null", !TypeMirrorWrapper.isErrorType(mockTypeCheck(null)));
    }


    @Test
    public void test_isDeclaredType() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isDeclared());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ARRAY)).isDeclared());
        assertThat("Expected false for null", !TypeMirrorWrapper.isDeclared(null));
    }

    @Test
    public void test_getDeclaredType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(DeclaredType.class, TypeKind.DECLARED);
        assertThat(TypeMirrorWrapper.wrap(typeMirror).getDeclaredType(), is(typeMirror));

        // expect null for non matching type kind
        assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getDeclaredType(), Matchers.nullValue());

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

    public static class IterableCheck_Iterable {
        @PassIn
        Iterable<String> iterableField;
    }

    public static class IterableCheck_IterableWithoutComponentType {
        @PassIn
        Iterable collectionField;
    }

    public static class IterableCheck_NonIterable {
        @PassIn
        Map<String, String> nonIterableField;
    }

    private void executeTest(Class<?> clazz, Consumer<VariableElement> test){
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(clazz,
                (processingEnvironment, element) -> {
                    ToolingProvider.setTooling(processingEnvironment);
                    try {
                        test.accept(element);
                    } finally {
                        ToolingProvider.clearTooling();
                    }
                }).executeTest();
    }

    @Test
    public void test_isIterable_Iterable()
    {
        executeTest(IterableCheck_Iterable.class,(element) ->
                assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(element.asType()).isIterable())
                );
    }
    @Test
    public void test_isCollection_List()
    {
        executeTest(CollectionCheck_List.class, (element) ->
                assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(element.asType()).isCollection())
        );
    }

    @Test
    public void test_isCollection_Set()
    {
        executeTest(CollectionCheck_Set.class, (element) ->
                assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(element.asType()).isCollection())
        );
    }

    @Test
    public void test_isCollection_noCollection()
    {
        executeTest(CollectionCheck_NoCollection.class, (element) ->
                assertThat("Expected false for no collection type", !TypeMirrorWrapper.wrap(element.asType()).isCollection())
        );
    }

    @Test
    public void test_isIterable_nonIterable()
    {
        executeTest(IterableCheck_NonIterable.class, (element) ->
                assertThat("Expected false for non iterable type", !TypeMirrorWrapper.wrap(element.asType()).isIterable())
        );
    }


    @Test
    public void test_getComponentType_forList() {
        executeTest(CollectionCheck_Set.class, (element) -> {
                    TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
                    assertThat("Should be true", typeMirrorWrapper.hasComponentType());
                    assertThat(typeMirrorWrapper.getComponentType().toString(), is(String.class.getCanonicalName()));
        });
    }

    @Test
    public void test_getComponentType_forIterable() {
        executeTest(IterableCheck_Iterable.class, (element) -> {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
            assertThat("Should be true", typeMirrorWrapper.hasComponentType());
            assertThat(typeMirrorWrapper.getComponentType().toString(), is(String.class.getCanonicalName()));
        });
    }

    @Test
    public void test_getComponentType_forListWithoutComponentType()
    {
        executeTest(CollectionCheck_ListWithoutComponentType.class, (element) -> {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
            assertThat("Should be true", typeMirrorWrapper.hasComponentType());
            assertThat(typeMirrorWrapper.getWrappedComponentType().getQualifiedName(),
                    is(Object.class.getCanonicalName()));
        });
    }

    @Test
    public void test_getComponentType_forIterableWithoutComponentType()
    {
        executeTest(IterableCheck_IterableWithoutComponentType.class, (element) -> {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
            assertThat("Should be true", typeMirrorWrapper.hasComponentType());
            assertThat(typeMirrorWrapper.getWrappedComponentType().getQualifiedName(),
                    is(Object.class.getCanonicalName()));
        });
    }

    @Test
    public void test_getComponentType_forSet()
    {
        executeTest(CollectionCheck_Set.class, (element) -> {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
            assertThat("Should be true", typeMirrorWrapper.hasComponentType());
            assertThat(typeMirrorWrapper.getComponentType().toString(), is(String.class.getCanonicalName()));
        });
    }

    @Test
    public void test_getComponentType_nonCollection()
    {
        executeTest(CollectionCheck_NoCollection.class, (element) -> {
            TypeMirrorWrapper typeMirrorWrapper = TypeMirrorWrapper.wrap(element.asType());
            assertThat("Should be false", !typeMirrorWrapper.hasComponentType());
            assertThat(TypeMirrorWrapper.wrap(element.asType()).getComponentType(), Matchers.nullValue());
        });
    }

    @Test
    public void test_isArrayType() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.ARRAY)).isArray());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isArray());
        assertThat("Expected false for null", !TypeMirrorWrapper.isArray(null));
    }

    @Test
    public void test_getArrayType() {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(ArrayType.class, TypeKind.ARRAY);
        assertThat(TypeMirrorWrapper.wrap(typeMirror).getArrayType(), is(typeMirror));

        // expect null for non matching type kind
        assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getArrayType(), Matchers.nullValue());

    }

    @Test
    public void test_isPrimitiveType() {
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.LONG)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.SHORT)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DOUBLE)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.FLOAT)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.BYTE)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.BOOLEAN)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.INT)).isPrimitive());
        assertThat("Expected true for matching kind", TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.CHAR)).isPrimitive());
        assertThat("Expected false for non matching kind", !TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.DECLARED)).isPrimitive());
        assertThat("Expected false for null", !TypeMirrorWrapper.isPrimitive(null));
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
        assertThat(TypeMirrorWrapper.wrap(mockTypeCheck(TypeKind.WILDCARD)).getPrimitiveType(), Matchers.nullValue());

    }

    private void testGetPrimitiveType(TypeKind typeKind) {
        // Expect cast instance of TypeMirror
        TypeMirror typeMirror = mockTypeCheck(PrimitiveType.class, typeKind);
        assertThat(TypeMirrorWrapper.wrap(typeMirror).getPrimitiveType(), is(typeMirror));
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

                assertThat(imports, Matchers.containsInAnyOrder(List.class.getCanonicalName(), Map.class.getCanonicalName(), Serializable.class.getCanonicalName()));

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

                assertThat(typeDeclaration, is("Map<? super Long, List<? extends Serializable>[]>"));

            }
        }).executeTest();
    }

    @Test
    public void test_getTypeDeclaration_withoutTypeParameters() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest_NonGeneric.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                assertThat(typeDeclaration, is("String"));

            }
        }).executeTest();
    }

    @Test
    public void test_getTypeDeclaration_withPrimitive() {
        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(ImportTest_Primitive.class, new UnitTest<VariableElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, VariableElement element) {

                String typeDeclaration = TypeMirrorWrapper.wrap(element.asType()).getTypeDeclaration();

                assertThat(typeDeclaration, is("long"));

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
                assertThat("expect true for Map", TypeMirrorWrapper.wrap(element.asType()).hasTypeArguments());

                // No Type arguments
                assertThat("expect false for String", !TypeMirrorWrapper.wrap(processingEnvironment.getElementUtils().getTypeElement(String.class.getCanonicalName()).asType()).hasTypeArguments());

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
                    assertThat(typeArgumentTypeMirrors, Matchers.hasSize(2));
                    assertThat(TypeUtils.TypeConversion.convertToFqn(typeArgumentTypeMirrors.get(0)), is(String.class.getCanonicalName()));
                    assertThat(TypeUtils.TypeConversion.convertToFqn(typeArgumentTypeMirrors.get(1)), is(Long.class.getCanonicalName()));

                    // check for non typeArgument class
                    assertThat(TypeMirrorWrapper.wrap(element.getEnclosingElement().asType()).getTypeArguments(), Matchers.nullValue());

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
                    assertThat(typeArgumentTypeMirrorWrappers, Matchers.hasSize(2));
                    assertThat(typeArgumentTypeMirrorWrappers.get(0).getQualifiedName(), is(String.class.getCanonicalName()));
                    assertThat(typeArgumentTypeMirrorWrappers.get(1).getQualifiedName(), is(Long.class.getCanonicalName()));

                    // check for non typeArgument class
                    assertThat(TypeMirrorWrapper.wrap(element.getEnclosingElement().asType()).getWrappedTypeArguments(), Matchers.nullValue());

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), is(PackageTest.class.getPackage().getName()));

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), is(PackageTest.class.getPackage().getName()));

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getPackage(), Matchers.nullValue());

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getTypeElement().get().getQualifiedName().toString(), is(GetTypeElement.class.getCanonicalName()));

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

                assertThat("Must return empty optional", !TypeMirrorWrapper.wrap(element.asType()).getTypeElement().isPresent());

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), is(GetName.class.getSimpleName()));

            }
        }).executeTest();
    }

    @Test
    public void test_getSimpleName_ofPrimitive() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofPrimitive.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), is("long"));

            }
        }).executeTest();
    }

    @Test
    public void test_getSimpleName_ofArray() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofArray.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getSimpleName(), is(GetName.class.getSimpleName()));

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

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), is(GetName.class.getCanonicalName()));

            }
        }).executeTest();
    }

    @Test
    public void test_getQualifiedName_ofPrimitive() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofPrimitive.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), is("long"));

            }
        }).executeTest();
    }

    @Test
    public void test_getQualifiedName_ofArray() {
        CompileTestBuilder.unitTest().<Element>defineTestWithPassedInElement(GetName_ofArray.class, new UnitTest<Element>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, Element element) {

                assertThat(TypeMirrorWrapper.wrap(element.asType()).getQualifiedName(), is(GetName.class.getCanonicalName()));

            }
        }).executeTest();
    }

    // ---------------------------------------------------------------
    // -- Assignability
    // ---------------------------------------------------------------

    interface MyInterface {

    }

    static class MySuperClass implements MyInterface {

    }

    static class MyChildClass extends MySuperClass {

    }

    @Test
    public void test_assignability() {
        CompileTestBuilder.unitTest().<Element>defineTest((processingEnvironment, element) -> {
            try {
                ToolingProvider.setTooling(processingEnvironment);

                TypeMirrorWrapper myInterface = TypeMirrorWrapper.wrap(MyInterface.class);
                TypeMirrorWrapper superClass = TypeMirrorWrapper.wrap(MySuperClass.class);
                TypeMirrorWrapper childClass = TypeMirrorWrapper.wrap(MyChildClass.class);
                TypeMirrorWrapper nonMatching = TypeMirrorWrapper.wrap(String.class);

                // Check interface
                assertThat("Should be assignable", myInterface.isAssignableFrom(superClass));
                assertThat("Should be assignable", myInterface.isAssignableFrom(childClass));
                assertThat("Shouldn't be assignable", !myInterface.isAssignableFrom(nonMatching));
                assertThat("Should be assignable", !myInterface.isAssignableTo(superClass));
                assertThat("Should be assignable", !myInterface.isAssignableTo(childClass));
                assertThat("Shouldn't be assignable", !myInterface.isAssignableTo(nonMatching));

                // Check superclass
                assertThat("Should be assignable", superClass.isAssignableFrom(childClass));
                assertThat("Shouldn't be assignable", !superClass.isAssignableFrom(myInterface));
                assertThat("Shouldn't be assignable", !superClass.isAssignableFrom(nonMatching));
                assertThat("Should be assignable", superClass.isAssignableTo(myInterface));
                assertThat("Shouldn't be assignable", !superClass.isAssignableTo(childClass));
                assertThat("Shouldn't be assignable", !superClass.isAssignableTo(nonMatching));

                // Check childclass
                assertThat("Shouldn't be assignable", !childClass.isAssignableFrom(superClass));
                assertThat("Shouldn't be assignable", !childClass.isAssignableFrom(myInterface));
                assertThat("Shouldn't be assignable", !childClass.isAssignableFrom(nonMatching));
                assertThat("Should be assignable", childClass.isAssignableTo(myInterface));
                assertThat("Should be assignable", childClass.isAssignableTo(superClass));
                assertThat("Shouldn't be assignable", !childClass.isAssignableTo(nonMatching));

            } finally {
                ToolingProvider.clearTooling();
            }
        }).executeTest();
    }
}
