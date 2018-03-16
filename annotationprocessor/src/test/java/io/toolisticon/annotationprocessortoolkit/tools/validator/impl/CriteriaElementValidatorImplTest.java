package io.toolisticon.annotationprocessortoolkit.tools.validator.impl;

import io.toolisticon.annotationprocessortoolkit.tools.matcher.CriteriaMatcher;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;

/**
 * Unit Test for {@link CriteriaElementValidatorImpl}.
 */

public class CriteriaElementValidatorImplTest {

    public static class TestCriteriaMatcher implements CriteriaMatcher<Element, String> {

        private final boolean fixResult;

        public TestCriteriaMatcher(boolean fixResult) {
            this.fixResult = fixResult;
        }

        @Override
        public boolean checkForMatchingCharacteristic(Element element, String toCheckFor) {
            return fixResult;
        }

        @Override
        public String getStringRepresentationOfPassedCharacteristic(String toGetStringRepresentationFor) {
            return toGetStringRepresentationFor;
        }
    }

    private static final TestCriteriaMatcher SUCCEEDING_MATCHER = new TestCriteriaMatcher(true);
    private static final TestCriteriaMatcher FAILING_MATCHER = new TestCriteriaMatcher(false);
    private Element element;

    private CriteriaElementValidatorImpl unit;

    @Before
    public void init() {
        element = Mockito.mock(Element.class);

        unit = Mockito.spy(CriteriaElementValidatorImpl.class);
    }

    @Test
    public void validateByValidatorKind_testDelegateToHasMethods() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        // one of
        unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, matcherSpy, element, "TEST");

        Mockito.verify(unit, Mockito.times(1)).hasOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasNoneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAtLeastOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAllOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));

        Mockito.reset(unit);


        // none of
        unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcherSpy, element, "TEST");

        Mockito.verify(unit, Mockito.never()).hasOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.times(1)).hasNoneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAtLeastOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAllOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));

        Mockito.reset(unit);

        // at least one of
        unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcherSpy, element, "TEST");

        Mockito.verify(unit, Mockito.never()).hasOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasNoneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.times(1)).hasAtLeastOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAllOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));

        Mockito.reset(unit);

        // all of
        unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcherSpy, element, "TEST");

        Mockito.verify(unit, Mockito.never()).hasOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasNoneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.never()).hasAtLeastOneOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));
        Mockito.verify(unit, Mockito.times(1)).hasAllOf(Mockito.any(CriteriaMatcher.class), Mockito.any(Element.class), Mockito.any(String.class));

        Mockito.reset(unit);

    }


    @Test
    public void validateByValidatorKind_null_safety_test() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        MatcherAssert.assertThat("Should return false if validator kind is null", !unit.validateByValidatorKind(null, matcherSpy, element, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


    }

    @Test
    public void validateByValidatorKind_oneOf_test() {

        MatcherAssert.assertThat("Should be validated as true", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, SUCCEEDING_MATCHER, element, "TEST"));
        MatcherAssert.assertThat("Should be validated as false", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, FAILING_MATCHER, element, "TEST"));


    }

    @Test
    public void validateByValidatorKind_oneOf_nullSafety_test() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        // matcher == null
        MatcherAssert.assertThat("Should return false if matcher is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, null, element, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, SUCCEEDING_MATCHER, null, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // matcher == null && element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, null, null, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


        // empty criteria
        MatcherAssert.assertThat("Should return true if no criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, SUCCEEDING_MATCHER, element));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if single null criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, SUCCEEDING_MATCHER, element, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if two null valued criteria are used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ONE_OF, SUCCEEDING_MATCHER, element, null, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


    }

    @Test
    public void validateByValidatorKind_noneOf_test() {

        MatcherAssert.assertThat("Should be validated as false if Matcher matches", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, SUCCEEDING_MATCHER, element, "TEST"));
        MatcherAssert.assertThat("Should be validated as true if Matcher doesn't match", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, FAILING_MATCHER, element, "TEST"));


    }

    @Test
    public void validateByValidatorKind_noneOf_nullSafety_test() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        // matcher == null
        MatcherAssert.assertThat("Should return false if matcher is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, null, element, "TEST"));

        // element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcherSpy, null, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // matcher == null && element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, null, null, "TEST"));


        // empty criteria
        MatcherAssert.assertThat("Should return true if no criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcherSpy, element));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if single null criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcherSpy, element, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if two null valued criteria are used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_NONE_OF, matcherSpy, element, null, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


    }

    @Test
    public void validateByValidatorKind_atLeastOneOf_test() {

        MatcherAssert.assertThat("Should be validated as true", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, SUCCEEDING_MATCHER, element, "TEST"));
        MatcherAssert.assertThat("Should be validated as false", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, FAILING_MATCHER, element, "TEST"));


    }

    @Test
    public void validateByValidatorKind_atLeastOneOf_nullSafety_test() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        // matcher == null
        MatcherAssert.assertThat("Should return false if matcher is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, null, element, "TEST"));

        // element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcherSpy, null, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // matcher == null && element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, null, null, "TEST"));


        // empty criteria
        MatcherAssert.assertThat("Should return true if no criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcherSpy, element));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if single null criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcherSpy, element, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if two null valued criteria are used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_AT_LEAST_ONE_OF, matcherSpy, element, null, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


    }

    @Test
    public void validateByValidatorKind_allOf_test() {

        MatcherAssert.assertThat("Should be validated as true", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, SUCCEEDING_MATCHER, element, "TEST"));
        MatcherAssert.assertThat("Should be validated as false", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, FAILING_MATCHER, element, "TEST"));


    }

    @Test
    public void validateByValidatorKind_allOf_nullSafety_test() {

        TestCriteriaMatcher matcherSpy = Mockito.spy(SUCCEEDING_MATCHER);

        // matcher == null
        MatcherAssert.assertThat("Should return false if matcher is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, null, element, "TEST"));

        // element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcherSpy, null, "TEST"));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // matcher == null && element == null
        MatcherAssert.assertThat("Should return false if element is null", !unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, null, null, "TEST"));


        // empty criteria
        MatcherAssert.assertThat("Should return true if no criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcherSpy, element));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if single null criteria is used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcherSpy, element, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));

        // pass null as criteria
        MatcherAssert.assertThat("Should return true if two null valued criteria are used", unit.validateByValidatorKind(CriteriaElementValidatorImpl.ValidatorKind.HAS_ALL_OF, matcherSpy, element, null, null));
        Mockito.verify(matcherSpy, Mockito.never()).checkForMatchingCharacteristic(Mockito.any(Element.class), Mockito.any(String.class));


    }

}
