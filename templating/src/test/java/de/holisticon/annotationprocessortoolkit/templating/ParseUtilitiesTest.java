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

    @Test
    public void getGetter_checkNullSafety() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(null, "abc"), Matchers.nullValue());
        MatcherAssert.assertThat(ParseUtilities.getGetter(this, null), Matchers.nullValue());
        MatcherAssert.assertThat(ParseUtilities.getGetter(null, null), Matchers.nullValue());

    }

    public static class GetGetterTestClass {

        public void isIsGetter() {

        }

        public void hasHasGetter() {

        }

        public void getGetGetter() {

        }

        public void methodWithParameter(String test) {

        }

        public void getMethodWithParameter() {

        }

        public void someMethod() {

        }

        private void nonAccessibleMethod() {

        }

    }

    @Test
    public void getGetter_nonexistingFieldNameShouldReturnNull() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "xxx"), Matchers.nullValue());

    }

    @Test
    public void getGetter_testExistingMethodName() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "someMethod"), Matchers.is("someMethod"));

    }

    @Test
    public void getGetter_testNonAccessibleExistingMethodName() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "nonAccessibleMethod"), Matchers.nullValue());

    }

    @Test
    public void getGetter_testDifferentKindOfGetterPrefixes() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "isGetter"), Matchers.is("isIsGetter"));
        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "getGetter"), Matchers.is("getGetGetter"));
        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "hasGetter"), Matchers.is("hasHasGetter"));

    }


    @Test
    public void getGetter_testMethodWithParameter() {

        MatcherAssert.assertThat(ParseUtilities.getGetter(new GetGetterTestClass(), "methodWithParameter"), Matchers.is("getMethodWithParameter"));

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
