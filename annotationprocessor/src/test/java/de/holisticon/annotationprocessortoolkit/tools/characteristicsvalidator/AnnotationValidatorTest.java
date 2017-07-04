package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation2;
import de.holisticon.annotationprocessortoolkit.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.AnnotationMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Unit and integration test of {@link GenericElementCharacteristicValidator} with {@link AnnotationMatcher}.
 */
public class AnnotationValidatorTest {


    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_allOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_allOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have not found FilterTestAnnotation2 and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);

        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nullValuedElement() {


        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAllOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAllOf(element));


    }

    // --------------------------------------------------------
    // -- at least one of tests -------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasAtLeastOneOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasAtLeastOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found TestAnnotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nullValuedElement() {


        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAtLeastOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
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

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return false since only one match is allowed", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have just found TestAnnotation and return true", unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nullValuedElement() {


        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
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

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found all annotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasNoneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have found TestAnnotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should have not found any match and return true", unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nullValuedElement() {


        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasNoneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationMatcher annotationMatcher = Mockito.mock(AnnotationMatcher.class);
        Matcher<Class<? extends Annotation>> matcher = Mockito.mock(Matcher.class);
        Mockito.when(matcher.getMatcher()).thenReturn(annotationMatcher);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(matcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasNoneOf(element));


    }

}
