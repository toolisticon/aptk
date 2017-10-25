package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

/**
 * String based operand.
 */
public class LongOperand extends Operand <Long>{

    public LongOperand(OperandType operandType, String expressionString,  OperationType[] unaryOperationsToBeApplied) {
        super(operandType, expressionString, unaryOperationsToBeApplied);

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