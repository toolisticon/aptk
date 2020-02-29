package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidPathException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit Test for {@link IncludeTemplateBlock}.
 */
public class IncludeTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new IncludeTemplateBlock("resource : '/IncludeTemplateBlockTest.tpl'").getTemplateBlockType(), Matchers.is(TemplateBlockType.INCLUDE));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_nullSafety() {

        new IncludeTemplateBlock(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_emptyStringSafety() {

        new IncludeTemplateBlock("");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_trimmedEmptyStringSafety() {

        new IncludeTemplateBlock("    ");

    }


    @Test
    public void testConstructor_attributeString_validString_justResource() {
        IncludeTemplateBlock unit = new IncludeTemplateBlock("resource : '/IncludeTemplateBlockTest.tpl'");


        MatcherAssert.assertThat(unit.getTemplateResource(), Matchers.equalTo("/IncludeTemplateBlockTest.tpl"));
        MatcherAssert.assertThat(unit.getModelAccessPath(), Matchers.nullValue());

        MatcherAssert.assertThat(unit.getTemplateString(), Matchers.equalTo("test : ${model.value}"));

    }

    @Test
    public void testConstructor_attributeString_validString_resourceAndModel() {
        IncludeTemplateBlock unit = new IncludeTemplateBlock("resource : '/IncludeTemplateBlockTest.tpl', model : 'model'");


        MatcherAssert.assertThat(unit.getTemplateResource(), Matchers.equalTo("/IncludeTemplateBlockTest.tpl"));
        MatcherAssert.assertThat(unit.getModelAccessPath(), Matchers.equalTo("model"));

        MatcherAssert.assertThat(unit.getTemplateString(), Matchers.equalTo("test : ${model.value}"));

    }


    @Test
    public void test_getContent_successfully() {
        IncludeTemplateBlock unit = new IncludeTemplateBlock("resource : '/IncludeTemplateBlockTest.tpl'");

        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, Object> subModel = new HashMap<String, Object>();
        subModel.put("value", "test");
        model.put("model", subModel);

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("test : test"));

    }

    @Test(expected = InvalidPathException.class)
    public void test_getContent_missingModelValue() {
        IncludeTemplateBlock unit = new IncludeTemplateBlock("resource: '/IncludeTemplateBlockTest.tpl'");

        Map<String, Object> model = new HashMap<String, Object>();

        unit.getContent(model);

    }

}
