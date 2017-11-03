package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.OperandType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link Operand}.
 */
public class OperandTest {

    @Test(expected = IllegalArgumentException.class)
    public void OperandType_getOperand_testNullValue() {
        OperandType.getOperandType(null);
    }

    @Test
    public void OperandType_getOperand_testLong() {
        MatcherAssert.assertThat(OperandType.getOperandType("1234"), Matchers.is(OperandType.LONG));
    }

    @Test
    public void OperandType_getOperand_testDouble() {
        MatcherAssert.assertThat(OperandType.getOperandType("12.34"), Matchers.is(OperandType.DOUBLE));
    }

    @Test
    public void OperandType_getOperand_testString() {
        MatcherAssert.assertThat(OperandType.getOperandType("'1234'"), Matchers.is(OperandType.STRING));
    }

    @Test
    public void OperandType_getOperand_testBoolean_true() {
        MatcherAssert.assertThat(OperandType.getOperandType("true"), Matchers.is(OperandType.BOOLEAN));
    }

    @Test
    public void OperandType_getOperand_testBoolean_false() {
        MatcherAssert.assertThat(OperandType.getOperandType("false"), Matchers.is(OperandType.BOOLEAN));
    }

    @Test
    public void OperandType_getOperand_testExpression() {
        MatcherAssert.assertThat(OperandType.getOperandType("abc.def.hij"), Matchers.is(OperandType.DYNAMIC_VALUE));
        MatcherAssert.assertThat(OperandType.getOperandType("abc.def"), Matchers.is(OperandType.DYNAMIC_VALUE));
        MatcherAssert.assertThat(OperandType.getOperandType("abc"), Matchers.is(OperandType.DYNAMIC_VALUE));

    }

}
