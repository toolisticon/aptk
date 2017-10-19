package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * Boolean based operand.
 */
public class BooleanOperand extends Operand<Boolean> {

    public BooleanOperand(OperandType operandType, String expressionString, boolean negate) {
        super(operandType, expressionString, negate);

    }


    @Override
    public Class<Boolean> getOperandsJavaType() {
        return Boolean.class;
    }

    @Override
    public Boolean value() {
        return Boolean.valueOf(getExpressionString());
    }
}
