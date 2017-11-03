package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

/**
 * Used to hold an Operation result
 */
public class OperationResultOperand extends Operand<Object> {

    private final Class<Object> type;
    private final Object value;

    public OperationResultOperand(Class<Object> type, Object value) {
        super();

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

    @Override
    public OperandType getOperandType() {
        return OperandType.OPERATION_RESULT;
    }
}
