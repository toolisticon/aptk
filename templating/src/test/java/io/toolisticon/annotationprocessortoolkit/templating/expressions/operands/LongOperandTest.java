package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link LongOperand}.
 */
public class LongOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_passedNullValue() {
        new LongOperand(null).value();
    }


    @Test
    public void test_getOperandsJavaType() {

        MatcherAssert.assertThat(new LongOperand("6").getOperandsJavaType(), Matchers.equalTo((Class) Long.class));

    }

    @Test
    public void test_createLongOperand() {

        MatcherAssert.assertThat((Long) new LongOperand("6").value(), Matchers.is(6L));
        MatcherAssert.assertThat((Long) new LongOperand("-6").value(), Matchers.is(-6L));

    }
}
