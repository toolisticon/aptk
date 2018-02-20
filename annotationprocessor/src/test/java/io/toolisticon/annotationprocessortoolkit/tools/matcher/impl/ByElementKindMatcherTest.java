package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;


/**
 * Unit test for {@link ByElementKindMatcher}.
 */
public class ByElementKindMatcherTest {

    private ByElementKindMatcher unit = new ByElementKindMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return cannonical class name of annotation class", unit.getStringRepresentationOfPassedCharacteristic(ElementKind.ENUM).equals(ElementKind.ENUM.name()));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, ElementKind.CLASS));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch() {

        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);

        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, ElementKind.INTERFACE));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedElement() {

        MatcherAssert.assertThat("Should return false in case of null valued element", !unit.checkForMatchingCharacteristic(null, ElementKind.CLASS));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedElementKind() {
        Element element = Mockito.mock(Element.class);
        MatcherAssert.assertThat("Should return false in case of null valued annotation", !unit.checkForMatchingCharacteristic(element, null));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedParameters() {
        MatcherAssert.assertThat("Should return false in case of null valued parameters", !unit.checkForMatchingCharacteristic(null, null));

    }

}