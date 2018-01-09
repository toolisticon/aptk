package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import io.toolisticon.annotationprocessortoolkit.templating.expressions.Expression;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.operations.OperationType;

/**
 * Created by tobiasstamann on 06.10.17.
 */
public class OperandFactory {

    /**
     * Hidden constructor.
     */
    private OperandFactory() {

    }

    /**
     * Factory class for the creation of Operands.
     *
     * @param operandType      the detected operand type
     * @param expressionString the operands string representation parsed from expressions string
     * @param expression       Just used for OperandType EXPRESSION
     * @return
     */
    public static Operand createOperand(OperandType operandType, String expressionString, OperationType[] unaryOperationsToBeApplied, Expression expression) {

        if ((operandType == null) || (expressionString == null)) {
            throw new IllegalArgumentException("operandType and expressionString must not be null");
        }

        Operand operand = null;

        switch (operandType) {
            case BOOLEAN: {
                operand = new BooleanOperand(expressionString);
                break;
            }
            case LONG: {
                operand = new LongOperand(expressionString);
                break;
            }
            case DOUBLE: {
                operand = new DoubleOperand(expressionString);
                break;
            }
            case STRING: {
                operand = new StringOperand(expressionString);
                break;
            }
            case DYNAMIC_VALUE: {
                operand = new DynamicOperand(expressionString);
                break;
            }
            case EXPRESSION: {
                operand = new ExpressionOperand(expressionString, expression);
                break;
            }
            case NULL_VALUE: {
                operand = new NullValueOperand(expressionString);
                break;
            }
            default:
                throw new IllegalArgumentException("operandType " + operandType + " currently not implemented");
        }

        if (unaryOperationsToBeApplied != null && unaryOperationsToBeApplied.length >= 1) {
            for (int i = unaryOperationsToBeApplied.length - 1; i >= 0; i--) {

                operand = OperandFactory.createUnaryOperand(operand, unaryOperationsToBeApplied[i]);

            }
        }

        return operand;
    }


    public static OperationResultOperand createOperationResult(Class type, Object value) {
        return new OperationResultOperand(type, value);
    }

    public static UnaryOperationWrapperOperand createUnaryOperand(Operand operand, OperationType operationType) {
        return new UnaryOperationWrapperOperand(operand, operationType);
    }

}
