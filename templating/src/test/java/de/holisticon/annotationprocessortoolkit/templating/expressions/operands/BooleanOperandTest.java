package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link BooleanOperand}.
 */
public class BooleanOperandTest {

    @Test
    public void test_createBooleanOperand(){

        MatcherAssert.assertThat((Boolean)new BooleanOperand("true").value(), Matchers.is(true));
        MatcherAssert.assertThat((Boolean)new BooleanOperand("false").value(), Matchers.is(false));

    }

    @Test
    public void test_passedNullValue(){
        MatcherAssert.assertThat((Boolean)new BooleanOperand(null).value(), Matchers.is(false));
    }

    @Test
    public void test_nonBooleanValue(){
        MatcherAssert.assertThat((Boolean)new BooleanOperand("XYZ").value(), Matchers.is(false));
    }

}
