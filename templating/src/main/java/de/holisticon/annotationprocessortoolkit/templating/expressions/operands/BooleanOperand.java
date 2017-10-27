package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

/**
 * Boolean based operand.
 */
public class BooleanOperand extends Operand<Boolean> {

    public BooleanOperand( String expressionString) {
        super(OperandType.BOOLEAN, expressionString);

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
