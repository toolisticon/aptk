package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * Operand that represents a null value.
 * Internal Java Type is null.
 */
public class NullValueOperand extends Operand<Object> {

    public NullValueOperand(String expressionString) {
        super(OperandType.NULL_VALUE, expressionString);

    }


    @Override
    public Class<Object> getOperandsJavaType() {
        return null;
    }

    @Override
    public Object value() {
        return null;
    }
}