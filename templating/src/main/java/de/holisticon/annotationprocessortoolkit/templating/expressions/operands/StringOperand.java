package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * String based operand.
 */
public class StringOperand extends Operand<String> {

    public StringOperand(OperandType operandType, String expressionString, boolean negate) {
        super(operandType, expressionString, negate);

    }

    @Override
    public Class<String> getOperandsJavaType() {
        return String.class;
    }

    @Override
    public String value() {
        return getExpressionString();
    }


}
