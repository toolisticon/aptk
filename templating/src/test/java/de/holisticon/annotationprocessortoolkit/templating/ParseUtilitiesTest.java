package de.holisticon.annotationprocessortoolkit.templating;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link ParseUtilities}.
 */
public class ParseUtilitiesTest {


    @Test
    public void readResourceToStringTest() throws Exception {

        final String EXPECTED_RESULT = "ABC\n" +
                "DEF\n" +
                "HIJ";

        MatcherAssert.assertThat(ParseUtilities.readResourceToString("/ReadResourceToStringTest.txt"), Matchers.is(EXPECTED_RESULT));

    }




    public static class ParseStringTestPojo {
        private final String respect = "respect";
        private final String life = "life";

        public String getRespect() {
            return respect;
        }

        public String getLife() {
            return life;
        }
    }


    @Test
    public void parseString_JustVariableFieldsNoControlBlocks() throws Exception {


        final String TEMPLATE_STRING = ParseUtilities.readResourceToString("/MultipleVariableTextWithoutControlBocks.tpl");
        final String EXPECTED_RESULT = ParseUtilities.readResourceToString("/MultipleVariableTextWithoutControlBlock.expectedResult");

        Map<String, Object> values = new HashMap<String, Object>();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nobler", "nobler");
        model.put("wished", "wished");
        values.put("model", model);
        model.put("pojo", new ParseStringTestPojo());


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }

    public static class ComplexTemplateWithControlBlockPojo {
        private final int value;

        public ComplexTemplateWithControlBlockPojo(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }


    }

    @Test
    public void parseString_ComplexTemplateWithControlBlock() throws Exception {


        final String TEMPLATE_STRING = ParseUtilities.readResourceToString("/ComplexTemplateWithControlBlock.tpl");
        final String EXPECTED_RESULT = ParseUtilities.readResourceToString("/ComplexTemplateWithControlBlock.expectedResult");

        Map<String, Object> values = new HashMap<String, Object>();

        Map<String, Object> model = new HashMap<String, Object>();

        List<ComplexTemplateWithControlBlockPojo> loopValues = new ArrayList<ComplexTemplateWithControlBlockPojo>();
        loopValues.add(new ComplexTemplateWithControlBlockPojo(1));
        loopValues.add(new ComplexTemplateWithControlBlockPojo(2));
        loopValues.add(new ComplexTemplateWithControlBlockPojo(3));

        model.put("loopValues", loopValues);
        values.put("model", model);


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }

    @Test
    @Ignore
    public void parseString_ComplexTemplateWithAllControlBlocks() throws Exception {


        final String TEMPLATE_STRING = ParseUtilities.readResourceToString("/ComplexTemplateWithAllBlockTypes.tpl");
        final String EXPECTED_RESULT = ParseUtilities.readResourceToString("/ComplexTemplateWithAllBlockTypes.expectedResult");

        Map<String, Object> values = new HashMap<String, Object>();

        Map<String, Object> model = new HashMap<String, Object>();

        List<ComplexTemplateWithControlBlockPojo> loopValues = new ArrayList<ComplexTemplateWithControlBlockPojo>();
        loopValues.add(new ComplexTemplateWithControlBlockPojo(1));
        loopValues.add(new ComplexTemplateWithControlBlockPojo(2));
        loopValues.add(new ComplexTemplateWithControlBlockPojo(3));

        model.put("loopValues", loopValues);
        values.put("model", model);


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }


}
