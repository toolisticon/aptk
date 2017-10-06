package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * Expression Based Operand
 */
public class ExpressionOperand extends Operand {

    private final Expression expression;

    public ExpressionOperand(OperandType operandType, String expressionString, Expression expression) {
        super(operandType, expressionString);

        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
