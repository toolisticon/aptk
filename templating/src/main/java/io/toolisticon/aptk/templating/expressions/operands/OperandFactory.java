package io.toolisticon.aptk.templating.expressions.operands;

import io.toolisticon.aptk.templating.expressions.Expression;
import io.toolisticon.aptk.templating.expressions.operations.OperationType;

/**
 * A factory class for creating Operands.
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
     * @param operandType                the detected operand type
     * @param expressionString           the operands string representation parsed from expressions string
     * @param unaryOperationsToBeApplied The unary operations to be applied
     * @param expression                 Just used for OperandType EXPRESSION
     * @return the operand
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


    /**
     * Creates an Operation result.
     *
     * @param type  The type
     * @param value The value
     * @return the operation result
     */
    public static OperationResultOperand createOperationResult(Class type, Object value) {
        return new OperationResultOperand(type, value);
    }

    /**
     * Creates a unary wrapper operand
     *
     * @param operand       The operand
     * @param operationType the unary operation type
     * @return the wrapped unary operation operand
     */
    public static UnaryOperationWrapperOperand createUnaryOperand(Operand operand, OperationType operationType) {
        return new UnaryOperationWrapperOperand(operand, operationType);
    }

}
