package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit Test for {@link VariableTextTemplateBlock}.
 */
public class VariableTextTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new VariableTextTemplateBlock("abc").getTemplateBlockType(), Matchers.is(TemplateBlockType.DYNAMIC_TEXT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_nullValuedAccessPath() {

        VariableTextTemplateBlock unit = new VariableTextTemplateBlock(null);

    }

    @Test
    public void test_constructor_ValidAccessPath() {

        VariableTextTemplateBlock unit = new VariableTextTemplateBlock("abc");

        MatcherAssert.assertThat(unit.getAccessPath(), Matchers.equalTo("abc"));

    }

    @Test(expected = InvalidPathException.class)
    public void test_getContent_invalidPath() {

        VariableTextTemplateBlock unit = new VariableTextTemplateBlock("abc");

        Map<String,Object> model = new HashMap<String, Object>();

        unit.getContent(model);

    }

    @Test
    public void test_getContent_nullValuedValue() {

        VariableTextTemplateBlock unit = new VariableTextTemplateBlock("abc");

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("abc", null);

        MatcherAssert.assertThat(unit.getContent(model), Matchers.nullValue());

    }

    @Test
    public void test_getContent_nonNullValuedValue() {

        VariableTextTemplateBlock unit = new VariableTextTemplateBlock("abc");

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("abc", "ABC");

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("ABC"));

    }

}
