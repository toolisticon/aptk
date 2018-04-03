package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import io.toolisticon.annotationprocessortoolkit.templating.expressions.operations.OperationType;
import org.junit.Test;

/**
 * Unit test for {@link OperandFactory}.
 */
public class OperandFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void createOperand_nullValued_operandType() {
        OperandFactory.createOperand(null, "ABC", new OperationType[0], null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createOperand_nullValued_expressionString() {
        OperandFactory.createOperand(OperandType.BOOLEAN, null, new OperationType[0], null);
    }


}
