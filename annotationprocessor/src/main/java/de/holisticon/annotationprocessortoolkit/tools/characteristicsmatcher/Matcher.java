package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

/**
 * Wrapper class for matchers.
 */
public class Matcher<T> {

    private final GenericMatcher<T> matcher;

    public Matcher(GenericMatcher<T> matcher) {
        this.matcher = matcher;
    }

    /**
     * Hidden constructor.
     */
    private Matcher() {
        this.matcher = null;
    }

    public GenericMatcher<T> getMatcher() {
        return this.matcher;
    }


}
