package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation2;
import de.holisticon.annotationprocessortoolkit.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.AnnotationElementCharacteristicMatcher;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher.GenericElementCharacteristicValidator;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;

/**
 * Unit and integration test of {@link GenericElementCharacteristicValidator} with {@link AnnotationElementCharacteristicMatcher}.
 */
public class AnnotationElementCharacteristicValidatorTest {


    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_allOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_allOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have not found FilterTestAnnotation2 and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAllOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_nullValuedElement() {


        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAllOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_allOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasAllOf(element));


    }

    // --------------------------------------------------------
    // -- at least one of tests -------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasAtLeastOneOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found all annotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasAtLeastOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found TestAnnotation and return true", unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasAtLeastOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_nullValuedElement() {


        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasAtLeastOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasAtLeastOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return false for non existing characteristics", !unit.hasAtLeastOneOf(element));


    }

    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasOneOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found all annotation and return false since only one match is allowed", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasOneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have just found TestAnnotation and return true", unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have not found any match and return false", !unit.hasOneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_nullValuedElement() {


        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasOneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasOneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return false for non existing characteristics", !unit.hasOneOf(element));


    }

    // --------------------------------------------------------
    // -- all of tests ----------------------------------------
    // --------------------------------------------------------


    @Test
    public void test_hasNoneOf_match() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(true);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found all annotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));

    }

    @Test
    public void test_hasNoneOf_oneMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(true);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have found TestAnnotation and return false", !unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nonMissingMatch() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should have not found any match and return true", unit.hasNoneOf(element, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_nullValuedElement() {


        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);

        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return null for null valued element", !unit.hasNoneOf(null, TestAnnotation.class, FilterTestAnnotation2.class));


    }

    @Test
    public void test_hasNoneOf_missingAnnotationParameters() {

        Element element = Mockito.mock(Element.class);

        AnnotationElementCharacteristicMatcher annotationMatcher = Mockito.mock(AnnotationElementCharacteristicMatcher.class);


        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, TestAnnotation.class)).thenReturn(false);
        Mockito.when(annotationMatcher.checkForMatchingCharacteristic(element, FilterTestAnnotation2.class)).thenReturn(false);


        GenericElementCharacteristicValidator<Class<? extends Annotation>> unit = new GenericElementCharacteristicValidator<Class<? extends Annotation>>(annotationMatcher);
        MatcherAssert.assertThat("Should always return true for non existing characteristics", unit.hasNoneOf(element));


    }

}
