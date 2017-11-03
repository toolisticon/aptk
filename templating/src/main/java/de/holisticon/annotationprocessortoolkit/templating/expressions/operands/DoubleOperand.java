package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;


/**
 * Floating point based operand.
 *
 * Uses Double.valueOf to get value from expression.
 */
public class DoubleOperand extends ParsedOperand<Double> {

    public DoubleOperand( String expressionString) {
        super( expressionString);

    }

    @Override
    public Class<Double> getOperandsJavaType() {
        return Double.class;
    }

    @Override
    public Double value() {
        return Double.valueOf(getExpressionString());
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.DOUBLE;
    }
}