package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * String based operand.
 */
public class LongOperand extends Operand<Long> {

    public LongOperand(String expressionString) {
        super(OperandType.LONG, expressionString);

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