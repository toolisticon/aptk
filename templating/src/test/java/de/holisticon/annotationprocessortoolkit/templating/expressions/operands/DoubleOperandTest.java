package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link DoubleOperand}.
 */
public class DoubleOperandTest {


    @Test
    public void test_createDoubleOperand() {

        MatcherAssert.assertThat((Double) new DoubleOperand("6.0").value(), Matchers.is(6.0));
        MatcherAssert.assertThat((Double) new DoubleOperand("-6.0").value(), Matchers.is(-6.0));

    }

}
