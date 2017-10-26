package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperationType;

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
     * @param expressionString the operands string representation parsed from expression string
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
                operand = new BooleanOperand(operandType, expressionString);
                break;
            }
            case LONG: {
                operand = new LongOperand(operandType, expressionString);
                break;
            }
            case DOUBLE: {
                operand = new DoubleOperand(operandType, expressionString);
                break;
            }
            case STRING: {
                operand = new StringOperand(operandType, expressionString);
                break;
            }
            case DYNAMIC_VALUE: {
                operand = new DynamicOperand(operandType, expressionString);
                break;
            }
            case EXPRESSION: {
                operand = new ExpressionOperand(operandType, expressionString, expression);
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
