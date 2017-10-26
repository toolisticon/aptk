package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;


/**
 * Floating point based operand.
 */
public class DoubleOperand extends Operand<Double> {

    public DoubleOperand(OperandType operandType, String expressionString) {
        super(operandType, expressionString);

    }

    @Override
    public Class<Double> getOperandsJavaType() {
        return Double.class;
    }

    @Override
    public Double value() {
        return Double.valueOf(getExpressionString());
    }

}