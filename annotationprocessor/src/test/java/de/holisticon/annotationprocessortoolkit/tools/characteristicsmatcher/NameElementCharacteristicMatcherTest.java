package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;

/**
 * Unit test for {@link NameMatcher}.
 */
public class NameElementCharacteristicMatcherTest {

    private final static String NAME = "NAME";


    private NameMatcher unit = new NameMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return enum name", unit.getStringRepresentationOfPassedCharacteristic(NAME).equals(NAME));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match() {


        Element element = Mockito.mock(Element.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn(NAME);

        Mockito.when(element.getSimpleName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch() {

        Element element = Mockito.mock(Element.class);


        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn("XXX");

        Mockito.when(element.getSimpleName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_nullValuedElement() {

        MatcherAssert.assertThat("Should return false in case of null valued element", !unit.checkForMatchingCharacteristic(null, NAME));

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
