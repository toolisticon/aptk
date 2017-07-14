import de.holisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@TestAnnotation
public class GenericsTestClass {

    // used for exact match, super (with HashMap) and non matching TypeParameter (HashMap inbetween), pure wildcard
    public void isAssignable_testCase1(Map<String, Map<String, String>> parameter) {

    }

    // used to test extends clause
    public void isAssignable_testCase2(Map<String, HashMap<String, String>> parameter) {

    }

    // used to test extends clause with pure and extends wildcard, generic type()
    public void isAssignable_testCase3(Map<String, ? extends HashMap<String, String>> parameter) {

    }

    // used to test super clause with pure and super wildcard, generic type()
    public void isAssignable_testCase4(Map<String, ? super Map<String, String>> parameter) {

    }

    public void isAssignable_testCase5(Map<String, ?> parameter) {

    }

    public void isAssignable_testCase6(Comparator<String>[] comparators1, Comparator<HashMap>[] comparators2, Comparator<? extends HashMap>[] comparators3) {

    }

}