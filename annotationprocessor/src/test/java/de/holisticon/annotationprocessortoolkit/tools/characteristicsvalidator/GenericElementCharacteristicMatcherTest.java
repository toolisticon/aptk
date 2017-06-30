package de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Unit test of {@link GenericElementCharacteristicValidator}.
 */
public class GenericElementCharacteristicMatcherTest {


    @Test
    public void testHasOf_OneOf() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        Element element = Mockito.mock(Element.class);
        spy.hasOf(ValidatorKind.ONE_OF, element, Modifier.ABSTRACT);

        Mockito.verify(spy).hasOneOf(element, Modifier.ABSTRACT);

    }

    @Test
    public void testHasOf_AtLeastOneOf() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        Element element = Mockito.mock(Element.class);
        spy.hasOf(ValidatorKind.AT_LEAST_ONE_OF, element, Modifier.ABSTRACT);

        Mockito.verify(spy).hasAtLeastOneOf(element, Modifier.ABSTRACT);

    }

    @Test
    public void testHasOf_AllOf() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        Element element = Mockito.mock(Element.class);
        spy.hasOf(ValidatorKind.ALL_OF, element, Modifier.ABSTRACT);

        Mockito.verify(spy).hasAllOf(element, Modifier.ABSTRACT);

    }

    @Test
    public void testHasOf_NoneOf() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        Element element = Mockito.mock(Element.class);
        spy.hasOf(ValidatorKind.NONE_OF, element, Modifier.ABSTRACT);

        Mockito.verify(spy).hasNoneOf(element, Modifier.ABSTRACT);

    }

    @Test
    public void testHasOf_nullValuedKind() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        Element element = Mockito.mock(Element.class);
        MatcherAssert.assertThat(spy.hasOf(null, element, Modifier.ABSTRACT), Matchers.is(false));

        Mockito.verify(spy, Mockito.never()).hasNoneOf(element, Modifier.ABSTRACT);
        Mockito.verify(spy, Mockito.never()).hasOneOf(element, Modifier.ABSTRACT);
        Mockito.verify(spy, Mockito.never()).hasAtLeastOneOf(element, Modifier.ABSTRACT);
        Mockito.verify(spy, Mockito.never()).hasAllOf(element, Modifier.ABSTRACT);


    }

    @Test
    public void testHasOf_oneOf_nullValuedElement() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        MatcherAssert.assertThat(spy.hasOf(ValidatorKind.ONE_OF, null, Modifier.ABSTRACT), Matchers.is(false));

        Mockito.verify(spy).hasOneOf(null, Modifier.ABSTRACT);


    }

    @Test
    public void testHasOf_atLeastOneOf_nullValuedElement() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        MatcherAssert.assertThat(spy.hasOf(ValidatorKind.AT_LEAST_ONE_OF, null, Modifier.ABSTRACT), Matchers.is(false));

        Mockito.verify(spy).hasAtLeastOneOf(null, Modifier.ABSTRACT);


    }

    @Test
    public void testHasOf_allOf_nullValuedElement() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        MatcherAssert.assertThat(spy.hasOf(ValidatorKind.ALL_OF, null, Modifier.ABSTRACT), Matchers.is(false));

        Mockito.verify(spy).hasAllOf(null, Modifier.ABSTRACT);


    }

    @Test
    public void testHasOf_noneOf_nullValuedElement() {

        GenericElementCharacteristicValidator<Modifier> spy = Mockito.spy((GenericElementCharacteristicValidator<Modifier>) Validator.getModifierValidator());

        MatcherAssert.assertThat(spy.hasOf(ValidatorKind.NONE_OF, null, Modifier.ABSTRACT), Matchers.is(false));

        Mockito.verify(spy).hasNoneOf(null, Modifier.ABSTRACT);


    }


}
