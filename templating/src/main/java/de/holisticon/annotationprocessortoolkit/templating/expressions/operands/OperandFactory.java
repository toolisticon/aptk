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
                operand = new BooleanOperand(operandType, expressionString, unaryOperationsToBeApplied);
                break;
            }
            case LONG: {
                operand = new LongOperand(operandType, expressionString, unaryOperationsToBeApplied);
                break;
            }
            case DOUBLE: {
                operand = new DoubleOperand(operandType, expressionString, unaryOperationsToBeApplied);
                break;
            }
            case STRING: {
                operand = new StringOperand(operandType, expressionString, unaryOperationsToBeApplied);
                break;
            }
            case DYNAMIC_VALUE: {
                operand = new DynamicOperand(operandType, expressionString, unaryOperationsToBeApplied);
                break;
            }
            case EXPRESSION: {
                operand = new ExpressionOperand(operandType, expressionString, unaryOperationsToBeApplied, expression);
                break;
            }
            default:
                throw new IllegalArgumentException("operandType " + operandType + " currently not implemented");
        }


        return operand;
    }


    public static OperationResultOperand createOperationResult(Class type, Object value) {
        return new OperationResultOperand(type, value);
    }

}
