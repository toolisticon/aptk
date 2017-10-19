package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

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
    public static Operand createOperand(OperandType operandType, String expressionString, boolean negate, Expression expression) {

        if ((operandType == null) || (expressionString == null)) {
            throw new IllegalArgumentException("operandType and expressionString must not be null");
        }

        Operand operand = null;

        switch (operandType) {
            case BOOLEAN: {
                operand = new BooleanOperand(operandType, expressionString, negate);
                break;
            }
            case LONG: {
                operand = new LongOperand(operandType, expressionString, negate);
                break;
            }
            case DOUBLE: {
                operand = new DoubleOperand(operandType, expressionString, negate);
                break;
            }
            case STRING: {
                operand = new StringOperand(operandType, expressionString, negate);
                break;
            }
            case DYNAMIC_VALUE: {
                operand = new DynamicOperand(operandType, expressionString, negate);
                break;
            }
            case EXPRESSION: {
                operand = new ExpressionOperand(operandType, expressionString, negate, expression);
                break;
            }
            default:
                throw new IllegalArgumentException("operandType " + operandType + " currently not implemented");
        }


        return operand;
    }


    public static OperationResultOperand createOperationResult(Class type, Object value) {
        return new OperationResultOperand(type,value);
    }

}
