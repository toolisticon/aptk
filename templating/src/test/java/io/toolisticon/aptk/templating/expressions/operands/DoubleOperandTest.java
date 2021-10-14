package io.toolisticon.aptk.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link DoubleOperand}.
 */
public class DoubleOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_passedNullValue() {
        new DoubleOperand(null).value();
    }



    @Test
    public void test_getOperandsJavaType() {

        MatcherAssert.assertThat(new DoubleOperand("6.0").getOperandsJavaType(), Matchers.equalTo((Class) Double.class));

    }

    @Test
    public void test_createDoubleOperand() {

        MatcherAssert.assertThat((Double) new DoubleOperand("6.0").value(), Matchers.is(6.0));
        MatcherAssert.assertThat((Double) new DoubleOperand("-6.0").value(), Matchers.is(-6.0));

    }

}
