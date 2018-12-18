package io.toolisticon.annotationprocessortoolkit.tools.matcher;


import io.toolisticon.annotationprocessortoolkit.tools.matcher.impl.ByAnnotationMatcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link Matcher}.
 */
public class MatcherTest {

    public static class UnitTestMatcher implements BaseMatcher {

    }

    private final static UnitTestMatcher MATCHER = new UnitTestMatcher();

    @Test
    public void testMatcher() {

        Matcher<UnitTestMatcher> matcher = new Matcher<UnitTestMatcher>(MATCHER);
        MatcherAssert.assertThat(matcher.getMatcher(), Matchers.is(MATCHER));

    }

}
