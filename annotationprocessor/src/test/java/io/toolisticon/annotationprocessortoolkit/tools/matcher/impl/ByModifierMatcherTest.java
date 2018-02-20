package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit test for {@link ByModifierMatcher}.
 */
public class ByModifierMatcherTest {


    private ByModifierMatcher unit = new ByModifierMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return enum name", unit.getStringRepresentationOfPassedCharacteristic(Modifier.FINAL).equals(Modifier.FINAL.name()));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match() {


        Element element = Mockito.mock(Element.class);

        Set<Modifier> modifierSet = new HashSet<Modifier>();
        modifierSet.add(Modifier.FINAL);

        Mockito.when(element.getModifiers()).thenReturn(modifierSet);

        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, Modifier.FINAL));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch() {

        Element element = Mockito.mock(Element.class);


        Set<Modifier> modifierSet = new HashSet<Modifier>();
        modifierSet.add(Modifier.FINAL);

        Mockito.when(element.getModifiers()).thenReturn(modifierSet);
        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, Modifier.PUBLIC));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedElement() {

        MatcherAssert.assertThat("Should return false in case of null valued element", !unit.checkForMatchingCharacteristic(null, Modifier.ABSTRACT));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedAnnotationType() {
        Element element = Mockito.mock(Element.class);
        MatcherAssert.assertThat("Should retrun false in case of null valued annotation", !unit.checkForMatchingCharacteristic(element, null));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedParameters() {
        MatcherAssert.assertThat("Should retrun false in case of null valued parameters", !unit.checkForMatchingCharacteristic(null, null));

    }

}
