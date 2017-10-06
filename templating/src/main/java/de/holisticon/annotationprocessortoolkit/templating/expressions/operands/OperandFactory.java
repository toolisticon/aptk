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
    public static Operand createOperand(OperandType operandType, String expressionString, Expression expression) {

        if ((operandType == null) || (expressionString == null)) {
            throw new IllegalArgumentException("operandType and expressionString must not be null");
        }

        switch (operandType) {
            case BOOLEAN: {

                break;
            }
            case LONG: {

                break;
            }
            case DOUBLE: {

                break;
            }
            case STRING: {

                break;
            }
            case DYNAMIC_VALUE: {

                break;
            }
            case EXPRESSION: {

                break;
            }
        }


        return null;
    }

}
