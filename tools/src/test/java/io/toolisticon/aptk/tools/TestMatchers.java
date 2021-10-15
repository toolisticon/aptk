package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.matcher.CriteriaMatcher;
import io.toolisticon.aptk.tools.matcher.ImplicitMatcher;

import javax.lang.model.element.Element;

/**
 * Some test matchers.
 */
public class TestMatchers {

    private static class RoundRobinResults {

        private final boolean[] resultArray;
        private int nextIndex = 0;

        public RoundRobinResults(boolean... resultArray) {
            this.resultArray = resultArray;
        }

        public boolean nextResult() {
            if (resultArray == null || resultArray.length == 0) {
                return true;
            }

            boolean returnValue = resultArray[nextIndex];
            nextIndex++;
            if (nextIndex >= resultArray.length) {
                nextIndex = 0;
            }
            return returnValue;


        }

    }


    public static <ELEMENTTYPE extends Element> ImplicitMatcher<ELEMENTTYPE> createImplicitMatcher(Class<ELEMENTTYPE> elementType, boolean... results) {

        final RoundRobinResults roundRobinResults = new RoundRobinResults(results);

        return new ImplicitMatcher<ELEMENTTYPE>() {

            RoundRobinResults results = roundRobinResults;

            @Override
            public boolean check(ELEMENTTYPE element) {
                return results.nextResult();
            }
        };
    }

    public static <ELEMENTTYPE extends Element, CRITERIA> CriteriaMatcher<ELEMENTTYPE, CRITERIA> createCriteriaMatcher(Class<ELEMENTTYPE> elementType, Class<CRITERIA> criteraType, boolean... results) {

        final RoundRobinResults roundRobinResults = new RoundRobinResults(results);

        return new CriteriaMatcher<ELEMENTTYPE, CRITERIA>() {

            RoundRobinResults results = roundRobinResults;

            @Override
            public boolean checkForMatchingCharacteristic(ELEMENTTYPE element, CRITERIA toCheckFor) {
                return results.nextResult();
            }

            @Override
            public String getStringRepresentationOfPassedCharacteristic(CRITERIA toGetStringRepresentationFor) {
                return toGetStringRepresentationFor != null ? toGetStringRepresentationFor.toString() : "<NULL>";
            }
        };
    }

}
