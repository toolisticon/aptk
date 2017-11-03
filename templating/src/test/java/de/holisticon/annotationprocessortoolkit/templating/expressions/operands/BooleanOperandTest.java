package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link BooleanOperand}.
 */
public class BooleanOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_passedNullValue() {
        new BooleanOperand(null).value();
    }


    @Test
    public void test_getOperandsJavaType() {

        MatcherAssert.assertThat(new BooleanOperand("true").getOperandsJavaType(), Matchers.equalTo((Class) Boolean.class));

    }

    @Test
    public void test_createBooleanOperand() {

        MatcherAssert.assertThat((Boolean) new BooleanOperand("true").value(), Matchers.is(true));
        MatcherAssert.assertThat((Boolean) new BooleanOperand("false").value(), Matchers.is(false));

    }


    @Test
    public void test_nonBooleanValue() {
        MatcherAssert.assertThat((Boolean) new BooleanOperand("XYZ").value(), Matchers.is(false));
    }

}
