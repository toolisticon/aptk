package io.toolisticon.annotationprocessortoolkit.templating;

import io.toolisticon.annotationprocessortoolkit.templating.testclasses.TestClass2;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

        final String EXPECTED_RESULT = "ABC" + System.lineSeparator() +
                "DEF" + System.lineSeparator() +
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
        values.put("xyz", true);


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }


    @Test
    public void parseString_templateWithDifferentIfStatements_Test() throws Exception {


        final String TEMPLATE_STRING = ParseUtilities.readResourceToString("/TestTemplateWithDifferentIfStatments.tpl");
        final String EXPECTED_RESULT = ParseUtilities.readResourceToString("/TestTemplateWithDifferentIfStatements.expectedResult");

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("xyz", true);


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }


    @Test
    public void parseString_templateWithDifferentVariableTextBlocks_Test() throws Exception {


        final String TEMPLATE_STRING = ParseUtilities.readResourceToString("/TestTemplateWithDifferentVariableTextBlocks.tpl");
        final String EXPECTED_RESULT = ParseUtilities.readResourceToString("/TestTemplateWithDifferentVariableTextBlocks.expectedResult");


        Map<String, Object> values = new HashMap<String, Object>();
        values.put("test", new TestClass2());


        MatcherAssert.assertThat(ParseUtilities.parseString(TEMPLATE_STRING).getContent(values), Matchers.is(EXPECTED_RESULT));

    }

    @Test
    public void trimContentString_trimContentString_Test() {
        MatcherAssert.assertThat(ParseUtilities.trimContentString("    \nabc"), Matchers.is("abc"));
        MatcherAssert.assertThat(ParseUtilities.trimContentString("    \n   abc"), Matchers.is("   abc"));
        MatcherAssert.assertThat(ParseUtilities.trimContentString("    \n   \nabc"), Matchers.is("   \nabc"));
        MatcherAssert.assertThat(ParseUtilities.trimContentString("\nabc"), Matchers.is("abc"));
        MatcherAssert.assertThat(ParseUtilities.trimContentString("    \nabc\n  "), Matchers.is("abc\n"));
        MatcherAssert.assertThat(ParseUtilities.trimContentString("    \nabc\n"), Matchers.is("abc\n"));

    }

    @Test
    public void parseNamedAttributes_parseAttributes() {
        String stringToParse = " abc : '/val1' , def : 'v.a.l2',hij:'val 3' ";

        Map<String, String> map = ParseUtilities.parseNamedAttributes(stringToParse);

        MatcherAssert.assertThat(map.get("abc"), Matchers.is("/val1"));
        MatcherAssert.assertThat(map.get("def"), Matchers.is("v.a.l2"));
        MatcherAssert.assertThat(map.get("hij"), Matchers.is("val 3"));

    }

}
