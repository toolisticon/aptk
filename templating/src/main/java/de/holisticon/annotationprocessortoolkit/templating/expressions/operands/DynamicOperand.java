package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;


/**
 * Dynamic operand queried from models.
 */
public class DynamicOperand extends Operand<Object> {

    public DynamicOperand(OperandType operandType, String expressionString, boolean negate) {
        super(operandType, expressionString, negate);

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