package io.toolisticon.aptk.templating.expressions.operands;

import io.toolisticon.aptk.templating.expressions.operations.OperationType;
import io.toolisticon.aptk.templating.expressions.operations.OperationTypeMode;

/**
 * A wrapper for unary operations.
 */
public class UnaryOperationWrapperOperand extends Operand<Object> {

    private final Operand operand;
    private final OperationType unaryOperationType;

    private Operand resultOperand = null;

    public UnaryOperationWrapperOperand(Operand operand, OperationType unaryOperationType) {
        super();

        if (unaryOperationType == null) {
            throw new IllegalArgumentException("unaryOperationType must not be null");
        }
        if (unaryOperationType.getOperationTypeMode() != OperationTypeMode.UNARY) {
            throw new IllegalArgumentException("unaryOperationType must be a unary operation type");
        }

        this.operand = operand;
        this.unaryOperationType = unaryOperationType;

    }


    @Override
    public Class<Object> getOperandsJavaType() {

        if (resultOperand == null) {
            calculateResultOperand();
        }

        return resultOperand.getOperandsJavaType();
    }

    @Override
    public Object value() {

        if (resultOperand == null) {
            calculateResultOperand();
        }

        return resultOperand.value();
    }

    @Override
    public OperandType getOperandType() {
        return OperandType.OPERATION_RESULT;
    }

    private void calculateResultOperand() {


        resultOperand = unaryOperationType.doOperation(operand);

    }
}
