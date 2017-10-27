package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;

/**
 * Expression Based Operand.
 */
public class ExpressionOperand extends Operand<Object> {

    private final Expression expression;

    private Operand calculatedExpressionOperand;

    public ExpressionOperand( String expressionString, Expression expression) {
        super(OperandType.EXPRESSION, expressionString);

        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Class<Object> getOperandsJavaType() {
        return calculateExpression().getOperandsJavaType();
    }

    @Override
    public Object value() {
        return calculateExpression().value();
    }

    private Operand calculateExpression() {
        if (this.calculatedExpressionOperand == null) {
            this.calculatedExpressionOperand = expression.evaluateExpression();
        }
        return calculatedExpressionOperand;
    }

}
