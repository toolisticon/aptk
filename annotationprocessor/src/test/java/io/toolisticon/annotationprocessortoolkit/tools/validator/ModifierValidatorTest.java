package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByModifierMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Unit and integration test of generic element validator with {@link ByModifierMatcher}.
 */
public class ModifierValidatorTest {


    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_allOf_match() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_allOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have not found FilterTestAnnotation2 and return false", !unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");

        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAllOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_nullValuedElement() {


        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAllOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_allOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAllOf(element));


    }

    // --------------------------------------------------------
    // -- at least one of tests -------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasAtLeastOneOf_match() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasAtLeastOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");

        MatcherAssert.assertThat("Should have found TestAnnotation and return true", unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");

        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAtLeastOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_nullValuedElement() {


        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAtLeastOneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasAtLeastOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);
        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
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

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have found all annotation and return false since only one match is allowed", !unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasOneOf_oneMissingMatch() {


        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have just found TestAnnotation and return true", unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasOneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_nullValuedElement() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasOneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
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


        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have found all annotation and return false", !unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));

    }

    @Test
    public void test_hasNoneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(true);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have found TestAnnotation and return false", !unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);


        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should have not found any match and return true", unit.hasNoneOf(element, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_nullValuedElement() {


        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasNoneOf(null, Modifier.FINAL, Modifier.PUBLIC));


    }

    @Test
    public void test_hasNoneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByModifierMatcher byModifierMatcher = Mockito.mock(ByModifierMatcher.class);


        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.FINAL)).thenReturn(false);
        Mockito.when(byModifierMatcher.checkForMatchingCharacteristic(element, Modifier.PUBLIC)).thenReturn(false);

        InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>> unit = new InclusiveCriteriaElementValidator<Element, Modifier, CriteriaMatcher<Element, Modifier>>(byModifierMatcher,"");

        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasNoneOf(element));


    }

}
