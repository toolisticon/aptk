package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

/**
 * Boolean based operand.
 * Uses Boolean.valueOf to determine value of passed expression string.
 */
public class BooleanOperand extends ParsedOperand<Boolean> {

    public BooleanOperand(String expressionString) {
        super(expressionString);
    }


    @Override
    public Class<Boolean> getOperandsJavaType() {
        return Boolean.class;
    }

    @Override
    public Boolean value() {
        return Boolean.valueOf(getExpressionString());
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.BOOLEAN;
    }
}
