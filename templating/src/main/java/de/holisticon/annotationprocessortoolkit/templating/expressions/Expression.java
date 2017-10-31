package de.holisticon.annotationprocessortoolkit.templating.expressions;

import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.ExpressionOperand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This implementation is not threadsafe.
 */
public class Expression {

    private Operand[] operands;
    private OperationType[] operationTypes;

    public Expression(Operand[] operands, OperationType[] operationTypes) {
        this.operands = operands;
        this.operationTypes = operationTypes;
    }

    public Operand[] getOperands() {
        return operands;
    }

    public OperationType[] getOperationTypes() {
        return operationTypes;
    }

    public Operand evaluateExpression() {

        // in case of one operand return it
        if (operands.length == 1 && operationTypes.length == 0) {
            return operands[0];
        }

        // first resolve sub expressions
        for (int i = 0; i < operands.length; i++) {

            // evaluate sub expressions and replace operand by result
            if (operands[i].getOperandType().equals(OperandType.EXPRESSION)) {
                operands[i] = ((ExpressionOperand) operands[i]).getExpression().evaluateExpression();
            }

        }

        // goto binary expressions by execution order (execution order is from left to right)
        for (OperationType binaryOperationType : OperationType.getOperationsByOperationTypeMode(OperationTypeMode.BINARY)) {

            boolean foundOperation = true;
            while (foundOperation) {

                foundOperation = false;

                for (int operationIndex = 0; operationIndex < operationTypes.length; operationIndex++) {

                    if (binaryOperationType.equals(operationTypes[operationIndex])) {
                        // apply operation
                        applyOperation(operationIndex);
                        foundOperation = true;
                        break;
                    }

                }
            }
        }


        return operands[0];
    }

    private void applyOperation(int operationIndex) {

        if (operationIndex >= 0 && operationIndex < operationTypes.length) {

            // resolve one operand
            List<Operand> newOperands = new ArrayList<Operand>(Arrays.asList(operands));
            List<OperationType> newOperationTypes = new ArrayList<OperationType>(Arrays.asList(operationTypes));

            // getOperands and operationType
            Operand operand1 = newOperands.get(operationIndex);
            Operand operand2 = newOperands.get(operationIndex + 1);

            OperationType operationtype = newOperationTypes.get(operationIndex);


            // do operation and update expressions
            newOperands.remove(operationIndex + 1);
            newOperands.set(operationIndex, operationtype.doOperation(operand1, operand2));
            newOperationTypes.remove(operationIndex);

            operands = newOperands.toArray(new Operand[newOperands.size()]);
            operationTypes = newOperationTypes.toArray(new OperationType[newOperationTypes.size()]);

        }

    }

}
