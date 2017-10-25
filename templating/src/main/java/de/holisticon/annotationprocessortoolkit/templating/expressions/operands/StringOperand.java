package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

/**
 * String based operand.
 */
public class StringOperand extends Operand<String> {

    public StringOperand(OperandType operandType, String expressionString,  OperationType[] unaryOperationsToBeApplied) {
        super(operandType, expressionString, unaryOperationsToBeApplied);

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
