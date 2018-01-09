package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link NullValueOperand}.
 */
public class NullValueOperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_passedNullValue() {
        new BooleanOperand(null).value();
    }


    @Test
    public void test_getOperandsJavaType() {

        MatcherAssert.assertThat(new NullValueOperand("null").getOperandsJavaType(), Matchers.nullValue());

    }

    @Test
    public void test_getValue() {

        MatcherAssert.assertThat(new NullValueOperand("null").value(), Matchers.nullValue());

    }

}
