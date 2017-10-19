package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * String based operand.
 */
public class LongOperand extends Operand <Long>{

    public LongOperand(OperandType operandType, String expressionString, boolean negate) {
        super(operandType, expressionString, negate);

    }

    @Override
    public Class<Long> getOperandsJavaType() {
        return Long.class;
    }

    @Override
    public Long value() {
        return Long.valueOf(getExpressionString());
    }

}