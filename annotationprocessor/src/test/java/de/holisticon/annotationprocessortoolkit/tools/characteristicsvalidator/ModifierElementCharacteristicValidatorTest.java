package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.ModifierElementCharacteristicMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Unit and integration test of {@link GenericElementCharacteristicValidator} with {@link ModifierElementCharacteristicMatcher}.
 */
public class ModifierElementCharacteristicValidatorTest {


    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_allOf_match() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_allOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have not found FilterTestAnnotation2 and return false", !unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);

        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_nullValuedElement() {


        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAllOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAllOf(element));


    }

    // --------------------------------------------------------
    // -- at least one of tests -------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasAtLeastOneOf_match() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasAtLeastOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);

        MatcherAssert.assertThat("Should have found TestAnnotation and return true", unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);

        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_nullValuedElement() {


        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAtLeastOneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);
        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAtLeastOneOf(element));
        MatcherAssert.assertThat("Should always return true for single null valued characteristics", unit.hasAtLeastOneOf(element, null));
        MatcherAssert.assertThat("Should always return true for multiple nulll valued characteristics", unit.hasAtLeastOneOf(element, null, null));


    }

    // --------------------------------------------------------
    // -- one of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasOneOf_match() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return false since only one match is allowed", !unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasOneOf_oneMissingMatch() {


        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have just found TestAnnotation and return true", unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_nullValuedElement() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasOneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasOneOf(element));
        MatcherAssert.assertThat("Should always return true for single null valued characteristics", unit.hasOneOf(element, null));
        MatcherAssert.assertThat("Should always return true for multiple nulll valued characteristics", unit.hasOneOf(element, null, null));


    }

    // --------------------------------------------------------
    // -- none of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasNoneOf_match() {

        Element element = Mockito.mock(Element.class);


        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return false", !unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasNoneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have found TestAnnotation and return false", !unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);


        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);

        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return true", unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_nullValuedElement() {


        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasNoneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ModifierElementCharacteristicMatcher modifierMatcher = Mockito.mock(ModifierElementCharacteristicMatcher.class);


        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(modifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        Matcher<Modifier> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(modifierMatcher);


        GenericElementCharacteristicValidator<Modifier> unit = new GenericElementCharacteristicValidator<Modifier>(matcher);

        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasNoneOf(element));


    }

}
