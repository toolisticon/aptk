package io.toolisticon.annotationprocessortoolkit.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Unit test for {@link ElementUtils.CheckModifierOfElement}.
 */
public class ElementUtils_CheckModifierOfElementTest {

    @Test
    public void testHasPublicModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PUBLIC));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasPublicModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasPublicModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasPublicModifier(null));


    }

    @Test
    public void testHasProtectedModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PROTECTED));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasProtectedModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(null));

    }

    @Test
    public void testHasPrivateModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasPrivateModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PUBLIC));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(null));

    }

    @Test
    public void testHasAbstractModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.ABSTRACT));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasAbstractModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(null));

    }

    @Test
    public void testHasStaticModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.STATIC));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasStaticModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasStaticModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasStaticModifier(null));

    }


    @Test
    public void testHasFinalModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.FINAL));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasFinalModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasFinalModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasFinalModifier(null));

    }

    @Test
    public void testHasTransientModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.TRANSIENT));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasTransientModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasTransientModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasTransientModifier(null));

    }

    @Test
    public void testHasVolatileModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.VOLATILE));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasVolatileModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasVolatileModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasVolatileModifier(null));

    }

    @Test
    public void testHasSynchronizedModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.SYNCHRONIZED));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(null));

    }


    @Test
    public void testHasNativeModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.NATIVE));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasNativeModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasNativeModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasNativeModifier(null));

    }

    @Test
    public void testHasStrictfpModifier() {

        Element e = Mockito.mock(Element.class);
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.STRICTFP));
        MatcherAssert.assertThat("Modifier should be found", ElementUtils.CheckModifierOfElement.hasStrictfpModifier(e));

        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat("Modifier should not be found", !ElementUtils.CheckModifierOfElement.hasStrictfpModifier(e));

        // check null valued element
        MatcherAssert.assertThat("Modifier should not be found for null valued elements", !ElementUtils.CheckModifierOfElement.hasStrictfpModifier(null));

    }

    @Test
    public void testGetVisibilityModifier() {

        Element e = Mockito.mock(Element.class);

        // PUBLIC
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PUBLIC));
        MatcherAssert.assertThat(ElementUtils.CheckModifierOfElement.getVisibilityModifier(e), Matchers.is(Modifier.PUBLIC));

        // PROTECTED
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PROTECTED));
        MatcherAssert.assertThat(ElementUtils.CheckModifierOfElement.getVisibilityModifier(e), Matchers.is(Modifier.PROTECTED));


        // PRIVATE
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.PRIVATE));
        MatcherAssert.assertThat(ElementUtils.CheckModifierOfElement.getVisibilityModifier(e), Matchers.is(Modifier.PRIVATE));


        // PACKAGE PRIVATE
        Mockito.when(e.getModifiers()).thenReturn(Utilities.convertVarargsToSet(Modifier.ABSTRACT));
        MatcherAssert.assertThat(ElementUtils.CheckModifierOfElement.getVisibilityModifier(e), Matchers.nullValue());



    }


}
