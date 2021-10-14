package io.toolisticon.aptk.tools.matcher.impl;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Unit test for {@link ByQualifiedNameMatcher}.
 */
public class ByQualifiedNameMatcherTest {

    private final static String NAME = "NAME";


    private ByQualifiedNameMatcher unit = new ByQualifiedNameMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return enum name", unit.getStringRepresentationOfPassedCharacteristic(NAME).equals(NAME));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match_class() {

        TypeElement element = Mockito.mock(TypeElement.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn(NAME);

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(element.getQualifiedName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_match_package() {

        PackageElement element = Mockito.mock(PackageElement.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn(NAME);

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        Mockito.when(element.getQualifiedName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_match_method() {

        Element element = Mockito.mock(Element.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn(NAME);

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(element.getSimpleName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch_class() {

        TypeElement element = Mockito.mock(TypeElement.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn("XXX");

        Mockito.when(element.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(element.getQualifiedName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch_package() {

        PackageElement element = Mockito.mock(PackageElement.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn("XXX");

        Mockito.when(element.getKind()).thenReturn(ElementKind.PACKAGE);
        Mockito.when(element.getQualifiedName()).thenReturn(nameOfElement);
        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, NAME));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch_method() {

        Element element = Mockito.mock(Element.class);

        Name nameOfElement = Mockito.mock(Name.class);
        Mockito.when(nameOfElement.toString()).thenReturn("XXX");

        Mockito.when(element.getKind()).thenReturn(ElementKind.METHOD);
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
        MatcherAssert.assertThat("Should return false in case of null valued parameters", !unit.checkForMatchingCharacteristic(null, null));

    }

}
