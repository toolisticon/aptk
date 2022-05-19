package io.toolisticon.aptk.tools.matcher.impl;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

/**
 * Unit test for {@link ByNestingKindMatcher}.
 */
public class ByNestingKindMatcherTest {


    private ByNestingKindMatcher unit = new ByNestingKindMatcher();


    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_happyPath() {

        MatcherAssert.assertThat("Should return NestingKind name as String", unit.getStringRepresentationOfPassedCharacteristic(NestingKind.TOP_LEVEL).equals(NestingKind.TOP_LEVEL.toString()));
        MatcherAssert.assertThat("Should return NestingKind name as String", unit.getStringRepresentationOfPassedCharacteristic(NestingKind.MEMBER).equals(NestingKind.MEMBER.toString()));
        MatcherAssert.assertThat("Should return NestingKind name as String", unit.getStringRepresentationOfPassedCharacteristic(NestingKind.ANONYMOUS).equals(NestingKind.ANONYMOUS.toString()));
        MatcherAssert.assertThat("Should return NestingKind name as String", unit.getStringRepresentationOfPassedCharacteristic(NestingKind.LOCAL).equals(NestingKind.LOCAL.toString()));

    }

    @Test
    public void test_getStringRepresentationOfPassedCharacteristic_passedNullValue() {

        MatcherAssert.assertThat("Should return null for null valued parameter", unit.getStringRepresentationOfPassedCharacteristic(null) == null);

    }

    @Test
    public void test_checkForMatchingCharacteristic_match() {


        TypeElement element = Mockito.mock(TypeElement.class);
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.TOP_LEVEL);

        MatcherAssert.assertThat("Should find match correctly", unit.checkForMatchingCharacteristic(element, NestingKind.TOP_LEVEL));

    }

    @Test
    public void test_checkForMatchingCharacteristic_mismatch() {

        TypeElement element = Mockito.mock(TypeElement.class);
        Mockito.when(element.getNestingKind()).thenReturn(NestingKind.TOP_LEVEL);

        MatcherAssert.assertThat("Should find mismatch correctly", !unit.checkForMatchingCharacteristic(element, NestingKind.MEMBER));


    }


}
