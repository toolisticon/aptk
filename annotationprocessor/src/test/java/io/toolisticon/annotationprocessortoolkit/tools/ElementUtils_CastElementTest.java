package io.toolisticon.annotationprocessortoolkit.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Unit test for {@link ElementUtils.CastElement}.
 */
public class ElementUtils_CastElementTest {

    @Test
    public void isTypeElement_testIfAllElementKindsWillBeDetectedCorrectly() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat("Should be detected as TypeElement", ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.INTERFACE);
        MatcherAssert.assertThat("Should be detected as TypeElement", ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);
        MatcherAssert.assertThat("Should be detected as TypeElement", ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ANNOTATION_TYPE);
        MatcherAssert.assertThat("Should be detected as TypeElement", ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.FIELD);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.STATIC_INIT);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.LOCAL_VARIABLE);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        MatcherAssert.assertThat("Should not be detected as TypeElement", !ElementUtils.CastElement.isTypeElement(element));

        // test null safety
        MatcherAssert.assertThat("Should return false for null valued parameter", !ElementUtils.CastElement.isTypeElement(null));

    }


    @Test
    public void isVariableElement_testIfAllElementKindsWillBeDetectedCorrectly() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        MatcherAssert.assertThat("Should be detected as VariableElement", ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.FIELD);
        MatcherAssert.assertThat("Should be detected as VariableElement", ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.INTERFACE);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ANNOTATION_TYPE);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.STATIC_INIT);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.LOCAL_VARIABLE);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat("Should not be detected as VariableElement", !ElementUtils.CastElement.isVariableElement(element));

        // test null safety
        MatcherAssert.assertThat("Should return false for null valued parameter", !ElementUtils.CastElement.isVariableElement(null));

    }

    @Test
    public void isExecutableElement_testIfAllElementKindsWillBeDetectedCorrectly() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
        MatcherAssert.assertThat("Should be detected as ExecutableElement", ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        MatcherAssert.assertThat("Should be detected as ExecutableElement", ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.INTERFACE);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ANNOTATION_TYPE);
        MatcherAssert.assertThat("Should be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.FIELD);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.STATIC_INIT);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.LOCAL_VARIABLE);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat("Should not be detected as ExecutableElement", !ElementUtils.CastElement.isExecutableElement(element));

        // test null safety
        MatcherAssert.assertThat("Should return false for null valued parameter", !ElementUtils.CastElement.isExecutableElement(null));

    }

    @Test
    public void isPackageElement_testIfAllElementKindsWillBeDetectedCorrectly() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CONSTRUCTOR);
        MatcherAssert.assertThat("Should be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        MatcherAssert.assertThat("Should be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.INTERFACE);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ANNOTATION_TYPE);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM_CONSTANT);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.FIELD);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        MatcherAssert.assertThat("Should not be detected as PackageElement", ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.STATIC_INIT);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.LOCAL_VARIABLE);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        MatcherAssert.assertThat("Should not be detected as PackageElement", !ElementUtils.CastElement.isPackageElement(element));

        // test null safety
        MatcherAssert.assertThat("Should return false for null valued parameter", !ElementUtils.CastElement.isPackageElement(null));

    }


    // ---------------------------------------
    // castClass -----------------------------
    // ---------------------------------------

    @Test
    public void castClassTest() {

        Element unit = Mockito.mock(TypeElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castClass(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castClassTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castClass(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castClassTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castClass(unit);

    }

    @Test
    public void castClassTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castClass(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // castInterface -------------------------
    // ---------------------------------------

    @Test
    public void castInterfaceTest() {

        Element unit = Mockito.mock(TypeElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castInterface(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castInterfaceTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castInterface(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castInterfaceTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castInterface(unit);

    }

    @Test
    public void castInterfaceTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castInterface(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // castEnum  -----------------------------
    // ---------------------------------------

    @Test
    public void castEnumTest() {

        Element unit = Mockito.mock(TypeElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castEnum(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castEnumTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castEnum(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castEnumTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castEnum(unit);

    }

    @Test
    public void castEnumTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castEnum(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // castParameter  ------------------------
    // ---------------------------------------

    @Test
    public void castParameterTest() {

        Element unit = Mockito.mock(VariableElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castParameter(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castParameterTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castParameter(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castParameterTest_MustThrowErrorForTypeElement() {

        Element unit = Mockito.mock(TypeElement.class);
        ElementUtils.CastElement.castParameter(unit);

    }

    @Test
    public void castParameterTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castParameter(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // castField  ----------------------------
    // ---------------------------------------

    @Test
    public void castFieldTest() {

        Element unit = Mockito.mock(VariableElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castField(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castFieldTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castField(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castFieldTest_MustThrowErrorForTypeElement() {

        Element unit = Mockito.mock(TypeElement.class);
        ElementUtils.CastElement.castField(unit);

    }

    @Test
    public void castFieldTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castField(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // cast Constructor  ---------------------
    // ---------------------------------------

    @Test
    public void castConstructorTest() {

        Element unit = Mockito.mock(ExecutableElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castConstructor(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castConstructorTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castConstructor(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castConstructorTest_MustThrowErrorForTypeElement() {

        Element unit = Mockito.mock(TypeElement.class);
        ElementUtils.CastElement.castConstructor(unit);

    }

    @Test
    public void castConstructorTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castConstructor(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // castMethod  ---------------------------
    // ---------------------------------------

    @Test
    public void castMethodTest() {

        Element unit = Mockito.mock(ExecutableElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castMethod(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castMethodTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castMethod(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castMethodTest_MustThrowErrorForTypeElement() {

        Element unit = Mockito.mock(TypeElement.class);
        ElementUtils.CastElement.castMethod(unit);

    }

    @Test
    public void castMethodTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castMethod(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // cast Annotation Type  -----------------
    // ---------------------------------------

    @Test
    public void castAnnotationTypeTest() {

        Element unit = Mockito.mock(TypeElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castAnnotationType(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castAnnotationTypeTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castAnnotationType(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castAnnotationTypeTest_MustThrowErrorForExecutableElement() {

        Element unit = Mockito.mock(ExecutableElement.class);
        ElementUtils.CastElement.castAnnotationType(unit);

    }

    @Test
    public void castAnnotationTypeTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castAnnotationType(null), Matchers.nullValue());
    }

    // ---------------------------------------
    // cast Annotation Attribute  ------------
    // ---------------------------------------

    @Test
    public void castAnnotationAttributeTest() {

        Element unit = Mockito.mock(ExecutableElement.class);
        MatcherAssert.assertThat(ElementUtils.CastElement.castAnnotationAttribute(unit), Matchers.notNullValue());

    }

    @Test(expected = ClassCastException.class)
    public void castAnnotationAttributeTest_MustThrowErrorForVariableElement() {

        Element unit = Mockito.mock(VariableElement.class);
        ElementUtils.CastElement.castAnnotationAttribute(unit);

    }

    @Test(expected = ClassCastException.class)
    public void castAnnotationAttributeTest_MustThrowErrorForTypeElement() {

        Element unit = Mockito.mock(TypeElement.class);
        ElementUtils.CastElement.castAnnotationAttribute(unit);

    }

    @Test
    public void castAnnotationAttributeTest_shouldHandleNullValuedParameterCorrectly() {
        MatcherAssert.assertThat(ElementUtils.CastElement.castAnnotationAttribute(null), Matchers.nullValue());
    }
}
