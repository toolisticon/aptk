package io.toolisticon.aptk.tools;


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

    // ---------------------------------------------
    // -- isModule tests
    // ---------------------------------------------

    @Test
    public void isModule_shouldMatch() {

        ElementKind elementKind;
        boolean expectedResult=true;
        try {
            elementKind = ElementKind.valueOf("MODULE");
            Element element = Mockito.mock(Element.class);
            Mockito.when(element.getKind()).thenReturn(elementKind);

            MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isModule(element));

        } catch (IllegalArgumentException e) {
            // < java 9 ignore it
            return;
        }

    }

    @Test
    public void isModule_shouldntMatch() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isModule(element));

    }

    @Test
    public void isModule_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isModule(null));

    }

    // ---------------------------------------------
    // -- Convenience element checks
    // ---------------------------------------------

    // ---------------------------------------------
    // -- isAnnotationAttribute tests
    // ---------------------------------------------


    @Test
    public void isAnnotationAttribute_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.ANNOTATION_TYPE);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isAnnotationAttribute(element));

    }

    @Test
    public void isAnnotationAttribute_shouldntMatch_noMethod() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isAnnotationAttribute(element));

    }

    @Test
    public void isAnnotationAttribute_shouldntMatch_methodOnClass() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isAnnotationAttribute(element));

    }

    @Test
    public void isAnnotationAttribute_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isAnnotationAttribute(null));

    }

    // ---------------------------------------------
    // -- isAnnotationAttribute tests
    // ---------------------------------------------


    @Test
    public void isMethodParameter_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.METHOD);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isMethodParameter(element));

    }

    @Test
    public void isMethodParameter_shouldntMatch_noParameter() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isMethodParameter(element));

    }

    @Test
    public void isMethodParameter_shouldntMatch_paraeterOnConstructor() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.CONSTRUCTOR);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isMethodParameter(element));

    }

    @Test
    public void isMethodParameter_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isMethodParameter(null));

    }

    // ---------------------------------------------
    // -- isAnnotationAttribute tests
    // ---------------------------------------------


    @Test
    public void isConstructorParameter_shouldMatch() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.CONSTRUCTOR);

        MatcherAssert.assertThat("Should match", ElementUtils.CheckKindOfElement.isConstructorParameter(element));

    }

    @Test
    public void isConstructorParameter_shouldntMatch_noMethod() {

        Element element = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isConstructorParameter(element));

    }

    @Test
    public void isConstructorParameter_shouldntMatch_parameterOnMethod() {

        Element element = Mockito.mock(Element.class);
        Element enclosingElement = Mockito.mock(Element.class);
        Mockito.when(element.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(element.getEnclosingElement()).thenReturn(enclosingElement);
        Mockito.when(enclosingElement.getKind()).thenReturn(ElementKind.METHOD);

        MatcherAssert.assertThat("Shouldn't match", !ElementUtils.CheckKindOfElement.isConstructorParameter(element));

    }

    @Test
    public void isConstructorParameter_shouldReturnFalseForNullValue() {

        MatcherAssert.assertThat("Should return false with null valued parameter", !ElementUtils.CheckKindOfElement.isConstructorParameter(null));

    }
}
