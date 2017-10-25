package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

/**
 * Used to hold an Operation result
 */
public class OperationResultOperand extends Operand<Object> {

    private final Class<Object> type;
    private final Object value;

    public OperationResultOperand(Class<Object> type, Object value) {
        super(OperandType.OPERATION_RESULT, null, new OperationType[0]);

        this.type = type;
        this.value = value;

    }

    @Override
    public Class<Object> getOperandsJavaType() {
        return type;
    }

    @Override
    public Object value() {
        return value;
    }
}
