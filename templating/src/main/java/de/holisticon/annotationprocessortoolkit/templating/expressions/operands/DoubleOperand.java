package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;


/**
 * Floating point based operand.
 *
 * Uses Double.valueOf to get value from expression.
 */
public class DoubleOperand extends Operand<Double> {

    public DoubleOperand( String expressionString) {
        super(OperandType.DOUBLE, expressionString);

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