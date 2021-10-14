package io.toolisticon.aptk.templating.expressions.operands;

/**
 * Operand that represents a null value.
 * Internal Java Type is null.
 */
public class NullValueOperand extends ParsedOperand<Object> {

    public NullValueOperand(String expressionString) {
        super(expressionString);

    }


    @Override
    public Class<Object> getOperandsJavaType() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.NULL_VALUE;
    }


}