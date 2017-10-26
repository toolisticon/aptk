package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

/**
 * Created by tobiasstamann on 26.10.17.
 */
public class UnaryOperationWrapperOperand extends Operand<Object> {

    private final Operand operand;
    private final OperationType unaryOperationType;

    private Operand resultOperand = null;

    public UnaryOperationWrapperOperand(Operand operand, OperationType unaryOperationType) {
        super(OperandType.OPERATION_RESULT, null);

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

    private void calculateResultOperand() {


        resultOperand = unaryOperationType.doOperation(operand);

    }
}
