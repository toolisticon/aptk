package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.corematcher.ExclusiveCriteriaCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ExclusiveCriteriaElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ImplicitCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ImplicitElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.InclusiveCharacteristicElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.InclusiveCriteriaCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.IsCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.IsElementBasedCoreMatcher;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import javax.lang.model.element.Element;

/**
 * Factory for some core matchers for testers.
 */
public class TestCoreMatcherFactory {


    private static ValidationMessage createValidationMessage(final String code) {
        return new ValidationMessage() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return code;
            }
        };
    }


    public static <ELEMENTTYPE extends Element> ImplicitCoreMatcher<ELEMENTTYPE> createImplicitCoreMatcher(Class<ELEMENTTYPE> elementType, String code, boolean... results) {

        if (elementType == null || Element.class.equals(elementType)) {
            throw new IllegalArgumentException("Passed elementType must not be null or Element");
        }

        return new ImplicitCoreMatcher<ELEMENTTYPE>(TestMatchers.createImplicitMatcher(elementType, results), createValidationMessage(code));
    }

    public static ImplicitElementBasedCoreMatcher createElementBasedImplicitCoreMatcher(String code, boolean... results) {
        return new ImplicitElementBasedCoreMatcher(TestMatchers.createImplicitMatcher(Element.class, results), createValidationMessage(code));
    }

    public static <ELEMENTTYPE extends Element, TARGETTYPE extends Element> IsCoreMatcher<ELEMENTTYPE, TARGETTYPE> createIsCoreMatcher(Class<ELEMENTTYPE> elementType, Class<TARGETTYPE> targetType, String code, boolean... results) {

        if (elementType == null || Element.class.equals(elementType)) {
            throw new IllegalArgumentException("Passed elementType must not be null or Element");
        }

        return new IsCoreMatcher<ELEMENTTYPE, TARGETTYPE>(TestMatchers.createImplicitMatcher(elementType, results), createValidationMessage(code));
    }

    public static <TARGETTYPE extends Element> IsElementBasedCoreMatcher<TARGETTYPE> createElementBasedIsCoreMatcher(Class<TARGETTYPE> targetType, String code, boolean... results) {
        return new IsElementBasedCoreMatcher<TARGETTYPE>(TestMatchers.createImplicitMatcher(Element.class, results), createValidationMessage(code));
    }


    public static <ELEMENTTYPE extends Element, CRITERIA> InclusiveCriteriaCoreMatcher<ELEMENTTYPE, CRITERIA> createInclusiveCriteriaCoreMatcher(Class<ELEMENTTYPE> elementType, Class<CRITERIA> criteraType, String code, boolean... results) {

        if (elementType == null || Element.class.equals(elementType)) {
            throw new IllegalArgumentException("Passed elementType must not be null or Element");
        }

        return new InclusiveCriteriaCoreMatcher<ELEMENTTYPE, CRITERIA>(TestMatchers.createCriteriaMatcher(elementType, criteraType, results), createValidationMessage(code));
    }

    public static <CRITERIA> InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA> createElementBasedInclusiveCriteriaCoreMatcher(Class<CRITERIA> criteraType, String code, boolean... results) {
        return new InclusiveCharacteristicElementBasedCoreMatcher<CRITERIA>(TestMatchers.createCriteriaMatcher(Element.class, criteraType, results), createValidationMessage(code));
    }

    public static <ELEMENTTYPE extends Element, CRITERIA> ExclusiveCriteriaCoreMatcher<ELEMENTTYPE, CRITERIA> createExclusiveCriteriaCoreMatcher(Class<ELEMENTTYPE> elementType, Class<CRITERIA> criteraType, String code, boolean... results) {

        if (elementType == null || Element.class.equals(elementType)) {
            throw new IllegalArgumentException("Passed elementType must not be null or Element");
        }

        return new ExclusiveCriteriaCoreMatcher<ELEMENTTYPE, CRITERIA>(TestMatchers.createCriteriaMatcher(elementType, criteraType, results), createValidationMessage(code));
    }

    public static <CRITERIA> ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA> createElementBasedExclusiveCriteriaCoreMatcher(Class<CRITERIA> criteraType, String code, boolean... results) {
        return new ExclusiveCriteriaElementBasedCoreMatcher<CRITERIA>(TestMatchers.createCriteriaMatcher(Element.class, criteraType, results), createValidationMessage(code));
    }

}
