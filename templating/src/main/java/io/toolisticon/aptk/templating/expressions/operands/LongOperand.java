package io.toolisticon.aptk.templating.expressions.operands;

/**
 * String based operand.
 */
public class LongOperand extends ParsedOperand<Long> {

    public LongOperand(String expressionString) {
        super(expressionString);

    }

    @Override
    public Class<Long> getOperandsJavaType() {
        return Long.class;
    }

    @Override
    public Long value() {
        return Long.valueOf(getExpressionString());
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.LONG;
    }
}