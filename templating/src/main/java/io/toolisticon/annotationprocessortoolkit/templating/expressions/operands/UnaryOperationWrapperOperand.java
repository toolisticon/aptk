package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import io.toolisticon.annotationprocessortoolkit.templating.expressions.operations.OperationType;

/**
 * Created by tobiasstamann on 26.10.17.
 */
public class UnaryOperationWrapperOperand extends Operand<Object> {

    private final Operand operand;
    private final OperationType unaryOperationType;

    private Operand resultOperand = null;

    public UnaryOperationWrapperOperand(Operand operand, OperationType unaryOperationType) {
        super();

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
