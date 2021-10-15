package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.aptk.templating.exceptions.InvalidPathException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Unit Test for {@link IfTemplateBlock}.
 */
public class IfTemplateBlockTest {

    @Test
    public void test_getTemplateBlockType() {
        MatcherAssert.assertThat(new IfTemplateBlock("abc", "").getTemplateBlockType(), Matchers.is(TemplateBlockType.IF));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_nullSafety() {

        new IfTemplateBlock(null, "abc");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_emptyStringSafety() {

        new IfTemplateBlock("", "abc");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_attributeString_trimmedEmptyStringSafety() {

        new IfTemplateBlock("    ", "abc");

    }


    @Test
    public void testConstructor_attributeString_validString() {
        IfTemplateBlock unit = new IfTemplateBlock("def.hij.klm", "templateString");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));

        MatcherAssert.assertThat(unit.getBinder(), Matchers.notNullValue());
        MatcherAssert.assertThat(unit.getAccessPath(), Matchers.equalTo("def.hij.klm"));
        MatcherAssert.assertThat(unit.getTemplateString(), Matchers.equalTo("templateString"));

    }


    @Test(expected = InvalidPathException.class)
    public void test_getContent_invalidPath() {
        IfTemplateBlock unit = new IfTemplateBlock(" def ", "ABC");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));

        Map<String, Object> model = new HashMap<String, Object>();

        unit.getContent(model);

    }

    @Test(expected = InvalidExpressionResult.class)
    public void test_getContent_invalidType() {
        IfTemplateBlock unit = new IfTemplateBlock(" def ", "${abc}");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", "NOPE");

        unit.getContent(model);

    }


    @Test(expected = InvalidExpressionResult.class)
    public void test_getContent_nullValueInModel_mustReturnEmptyString() {
        IfTemplateBlock unit = new IfTemplateBlock(" def ", "ABC");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", null);

        unit.getContent(model);

    }

    @Test
    public void test_getContent_trueValueInModel() {
        IfTemplateBlock unit = new IfTemplateBlock("def", "AB");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", true);

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("AB"));

    }

    @Test
    public void test_getContent_falseValueInModel() {
        IfTemplateBlock unit = new IfTemplateBlock(" def ", "AB");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));


        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", false);

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo(""));

    }

    @Test
    public void test_getContent_expressionValueInModel() {
        IfTemplateBlock unit = new IfTemplateBlock(" def == 'YEP' ", "AB");

        // Must add template block
        unit.getBinder().addTemplateBlock(new PlainTextTemplateBlock("AB"));

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("def", "YEP");

        MatcherAssert.assertThat(unit.getContent(model), Matchers.equalTo("AB"));

    }


}
