package io.toolisticon.annotationprocessortoolkit.tools.matcher;

/**
 * Wrapper class for matchers.
 */
public class Matcher<MT extends BaseMatcher> {

    /**
     * The wrapped matcher instance.
     */
    private final MT matcher;

    /**
     * Constructor that allows passing in of the {@link Matcher} to be wrapped.
     *
     * @param matcher the matcher to be wrapped within
     */
    public Matcher(MT matcher) {
        this.matcher = matcher;
    }

    /**
     * Gets the wrapped Matcher.
     *
     * @return the wrapped matcher
     */
    public MT getMatcher() {
        return this.matcher;
    }

}
