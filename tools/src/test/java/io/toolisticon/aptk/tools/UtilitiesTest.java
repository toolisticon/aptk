package io.toolisticon.aptk.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for {@link Utilities}.
 */
public class UtilitiesTest {

    // -----------------------------------------------
    // convertArrayToSet
    // -----------------------------------------------


    @Test
    public void convertArrayToSet_nullValue() {

        MatcherAssert.assertThat(Utilities.convertArrayToSet(null), Matchers.nullValue());

    }

    @Test
    public void convertArrayToSet_nonNullValue() {

        String[] array = {"A","B","C"};
        MatcherAssert.assertThat(Utilities.convertArrayToSet(array), Matchers.containsInAnyOrder("A","B","C"));

    }

    // -----------------------------------------------
    // convertArrayToSet
    // -----------------------------------------------

    @Test
    public void convertVarargsToSet_noValues_shouldReturnEmptySet() {

        MatcherAssert.assertThat(Utilities.convertVarargsToSet(), Matchers.empty());

    }

    @Test
    public void convertVarargsToSet_singleNullValue_shouldReturnEmptySet() {

        MatcherAssert.assertThat(Utilities.convertVarargsToSet(null), Matchers.empty());

    }

    @Test
    public void convertArrayToSet_withValues() {


        MatcherAssert.assertThat(Utilities.convertVarargsToSet("A","B", (String)null, "C"), Matchers.containsInAnyOrder("A","B","C", (String)null));

    }

    // -----------------------------------------------
    // convertVarargsToList
    // -----------------------------------------------

    @Test
    public void convertVarargsToList_noValues_shouldReturnEmptySet() {

        MatcherAssert.assertThat(Utilities.convertVarargsToList(), Matchers.empty());

    }

    @Test
    public void convertVarargsToList_singleNullValue_shouldReturnEmptySet() {

        MatcherAssert.assertThat(Utilities.convertVarargsToList(null), Matchers.empty());

    }

    @Test
    public void convertVarargsToList_withValues() {


        MatcherAssert.assertThat(Utilities.convertVarargsToList("A","B", (String)null, "C"), Matchers.contains("A","B", (String)null,"C"));

    }

    // -----------------------------------------------
    // convertVarargsToList
    // -----------------------------------------------

    @Test
    public void convertVarargsToArray_noValues_shouldReturnEmptySet() {

        MatcherAssert.assertThat(Arrays.asList(Utilities.convertVarargsToArray()), Matchers.empty());

    }

    @Test
    public void convertVarargsToArray_singleNullValue_shouldReturnEmptyArrayg() {

        MatcherAssert.assertThat(Arrays.asList(Utilities.convertVarargsToArray(null)), Matchers.empty());

    }

    @Test
    public void convertVarargsToArray_withValues() {


        MatcherAssert.assertThat(Arrays.asList(Utilities.convertVarargsToArray("A","B", (String)null, "C")), Matchers.contains("A","B", (String)null,"C"));

    }

}
