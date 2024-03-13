package io.toolisticon.aptk.tools.validator;

import io.toolisticon.aptk.tools.FilterTestAnnotation2;
import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.matcher.impl.ByAnnotationMatcher;
import io.toolisticon.aptk.tools.DummyValidatorMessage;
import io.toolisticon.cute.TestAnnotation;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Unit and integration test of {@link InclusiveCriteriaElementValidator} with {@link ByAnnotationMatcher}.
 */
public class AnnotationValidatorTest {


    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_allOf_match() {

        Element element = Mockito.mock(Element.class);
        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);

        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_allOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have not found FilterTestAnnotation2 and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);

        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nullValuedElement() {


        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);

        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAllOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAllOf(element));


    }

    // --------------------------------------------------------
    // -- at least one of tests -------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasAtLeastOneOf_match() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasAtLeastOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have found TestAnnotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nullValuedElement() {


        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);

        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAtLeastOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAtLeastOneOf(element));
        MatcherAssert.assertThat("Should always return true for single null valued characteristics", unit.hasAtLeastOneOf(element, null));
        MatcherAssert.assertThat("Should always return true for multiple nulll valued characteristics", unit.hasAtLeastOneOf(element, null, null));


    }

    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasOneOf_match() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have found all annotation and return false since only one match is allowed", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have just found TestAnnotation and return true", unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nullValuedElement() {


        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);

        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasOneOf(element));
        MatcherAssert.assertThat("Should always return true for single null valued characteristics", unit.hasOneOf(element, null));
        MatcherAssert.assertThat("Should always return true for multiple nulll valued characteristics", unit.hasOneOf(element, null, null));


    }

    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasNoneOf_match() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have found all annotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasNoneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have found TestAnnotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should have not found any match and return true", unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nullValuedElement() {


        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);

        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasNoneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        ByAnnotationMatcher byAnnotationMatcher = Mockito.mock(ByAnnotationMatcher.class);


        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(byAnnotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>> unit = new InclusiveCriteriaElementValidator<Element, Class<? extends Annotation>, CriteriaMatcher<Element, Class<? extends Annotation>>>(byAnnotationMatcher, DummyValidatorMessage.DUMMY_MESSAGE);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasNoneOf(element));


    }

}
