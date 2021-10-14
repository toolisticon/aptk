package io.toolisticon.aptk.templating.expressions.operands;

import io.toolisticon.aptk.templating.expressions.Expression;

/**
 * Expression Based Operand.
 */
public class ExpressionOperand extends ParsedOperand<Object> {

    private final Expression expression;

    private Operand calculatedExpressionOperand;

    public ExpressionOperand( String expressionString, Expression expression) {
        super( expressionString);

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

    @Override
    public OperandType getOperandType() {
        return OperandType.EXPRESSION;
    }

    private Operand calculateExpression() {
        if (this.calculatedExpressionOperand == null) {
            this.calculatedExpressionOperand = expression.evaluateExpression();
        }
        return calculatedExpressionOperand;
    }

}
