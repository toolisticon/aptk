package de.holisticon.annotationprocessortoolkit.internal;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Set;

/**
 * Unit test class for {@link Utilities}.
 */
public class UtilitiesTest {

    @Test
    public void test_ConvertArrayToSet_nullValuedParameter() {

        MatcherAssert.assertThat(Utilities.convertArrayToSet(null), Matchers.nullValue());

    }

    @Test
    public void test_ConvertArrayToSet_emptyArrayParameter() {

        MatcherAssert.assertThat(Utilities.convertArrayToSet(new String[0]), Matchers.hasSize(0));

    }

    @Test
    public void test_ConvertArrayToSet_happyPath() {

        String[] testArray = {"a", "b", "c"};

        MatcherAssert.assertThat(Utilities.convertArrayToSet(testArray), Matchers.containsInAnyOrder("a", "b", "c"));


    }

    @Test
    public void test_ConvertArrayToSet_withDoubledElements() {

        String[] testArray = {"a", "b", "c", "b", "a"};

        Set<String> result = Utilities.convertArrayToSet(testArray);
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder("a", "b", "c"));
        MatcherAssert.assertThat(result, Matchers.hasSize(3));

    }


}
