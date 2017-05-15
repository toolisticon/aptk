package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.TestAnnotation;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;

/**
 * Unit test for {@link AnnotationElementCharacteristicMatcher}.
 */
public class AnnotationElementCharacteristicMatcherTest {

    private AnnotationElementCharacteristicMatcher unit = new AnnotationElementCharacteristicMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return cannonical class name of annotation class", unit.getStringRepresentationOfPassedCharacteristic(TestAnnotation.class).equals(TestAnnotation.class.getCanonicalName()));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match() {

        TestAnnotation annotation = Mockito.mock(TestAnnotation.class);
        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getAnnotation(TestAnnotation.class)).thenReturn(annotation);

        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, TestAnnotation.class));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch() {

        TestAnnotation annotation = Mockito.mock(TestAnnotation.class);
        Element element = Mockito.mock(Element.class);

        Mockito.when(element.getAnnotation(TestAnnotation.class)).thenReturn(null);

        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, TestAnnotation.class));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedElement() {

        MatcherAssert.assertThat("Should retrun false in case of null valued element", !unit.checkForMatchingCharacteristic(null, TestAnnotation.class));

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
