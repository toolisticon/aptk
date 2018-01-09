package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

/**
 * Wrapper class for matchers.
 */
public class Matcher<T> {

    /**
     * The wrapped matcher instance.
     */
    private final GenericMatcher<T> matcher;

    /**
     * Constructor that allows passing in of the {@link Matcher} to be wrapped.
     *
     * @param matcher
     */
    public Matcher(GenericMatcher<T> matcher) {
        this.matcher = matcher;
    }

    /**
     * Hidden constructor.
     */
    private Matcher() {
        this.matcher = null;
    }

    /**
     * Gets the wrapped Matcher.
     *
     * @return the wrapped matcher
     */
    public GenericMatcher<T> getMatcher() {
        return this.matcher;
    }


}
