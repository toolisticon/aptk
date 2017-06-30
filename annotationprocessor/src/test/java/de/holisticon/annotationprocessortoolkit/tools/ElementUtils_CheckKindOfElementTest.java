package de.holisticon.annotationprocessortoolkit.tools;


import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public class ElementUtils_CheckKindOfElementTest {


    // ---------------------------------------------
    // -- isEnum tests
    // ---------------------------------------------

    @Test
    public void isEnum_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isEnum(element));

    }

    @Test
    public void isEnum_shouldNotMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isEnum(element));

    }

    @Test
    public void isEnum_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isEnum(null));

    }

    // ---------------------------------------------
    // -- isClass tests
    // ---------------------------------------------

    @Test
    public void isClass_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isClass(element));

    }

    @Test
    public void isClass_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.ENUM);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isClass(element));

    }

    @Test
    public void isClass_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isClass(null));

    }

    // ---------------------------------------------
    // -- isInterface tests
    // ---------------------------------------------

    @Test
    public void isInterface_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.INTERFACE);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isInterface(element));

    }

    @Test
    public void isInterface_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isInterface(element));

    }

    @Test
    public void isInterface_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isInterface(null));

    }

    // ---------------------------------------------
    // -- isMethod tests
    // ---------------------------------------------

    @Test
    public void isMethod_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isMethod(element));

    }

    @Test
    public void isMethod_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isMethod(element));

    }

    @Test
    public void isMethod_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isMethod(null));

    }

    // ---------------------------------------------
    // -- isParameter tests
    // ---------------------------------------------

    @Test
    public void isParameter_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isParameter(element));

    }

    @Test
    public void isParameter_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isParameter(element));

    }

    @Test
    public void isParameter_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isParameter(null));

    }

    // ---------------------------------------------
    // -- isConstructor tests
    // ---------------------------------------------

    @Test
    public void isConstructor_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CONSTRUCTOR);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isConstructor(element));

    }

    @Test
    public void isConstructor_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isConstructor(element));

    }

    @Test
    public void isConstructor_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isConstructor(null));

    }

    // ---------------------------------------------
    // -- isField tests
    // ---------------------------------------------

    @Test
    public void isField_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.FIELD);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isField(element));

    }

    @Test
    public void isField_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isField(element));

    }

    @Test
    public void isField_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isField(null));

    }



}
