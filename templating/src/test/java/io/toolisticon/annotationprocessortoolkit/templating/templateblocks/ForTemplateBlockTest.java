package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Unit Test for {@link ForTemplateBlock}.
 */
public class ForTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new ForTemplateBlock("test:abc.def", "DEF").getTemplateBlockType(), Matchers.is(TemplateBlockType.FOR));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_nullSafety() {

        new ForTemplateBlock(null, "abc");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_emptyStringSafety() {

        new ForTemplateBlock("", "abc");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_trimmedEmptyStringSafety() {

        new ForTemplateBlock("    ", "abc");

    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_invalidString() {
        new ForTemplateBlock(" sadsa   ", "abc");
    }

    @Test
    public void testConstructor_attributeString_validString() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def.hij.klm ", "templateString");

        MatcherAssert.assertThat(unit.getBinder(), Matchers.notNullValue());
        MatcherAssert.assertThat(unit.getLoopVariableName(), Matchers.equalTo("abc"));
        MatcherAssert.assertThat(unit.getAccessPath(), Matchers.equalTo("def.hij.klm"));
        MatcherAssert.assertThat(unit.getTemplateString(), Matchers.equalTo("templateString"));

    }

    @Test(expected = InvalidPathException.class)
    public void test_getContent_invalidPath() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();

        unit.getContent(model);

    }

    @Test(expected = InvalidPathException.class)
    public void test_getContent_invalidType() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", "NOPE");

        unit.getContent(model);

    }


    @Test(expected = InvalidExpressionResult.class)
    public void test_getContent_nullValueInModel_mustReturnEmptyString() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", null);

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo(""));

    }

    @Test
    public void test_getContent_arrayValueInModel() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();
        String[] value = {"A","B"};
        model.put("def", value);

        // Must add template block
        unit.getBinder().addTemplateBlock(new VariableTextTemplateBlock("abc"));

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("AB"));

    }

    @Test
    public void test_getContent_listValueInModel() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();
        List<String> value = Arrays.asList("A","B");
        model.put("def", value);

        // Must add template block
        unit.getBinder().addTemplateBlock(new VariableTextTemplateBlock("abc"));

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("AB"));

    }

    @Test
    public void test_getContent_setValueInModel() {
        ForTemplateBlock unit = new ForTemplateBlock(" abc : def ", "${abc}");

        Map<String, Object> model = new HashMap<String, Object>();
        Set<String> value = new HashSet<String>(Arrays.asList("A","B"));
        model.put("def", value);

        // Must add template block
        unit.getBinder().addTemplateBlock(new VariableTextTemplateBlock("abc"));

        MatcherAssert.assertThat(unit.getContent(model), Matchers.isOneOf("AB","BA"));

    }

}
