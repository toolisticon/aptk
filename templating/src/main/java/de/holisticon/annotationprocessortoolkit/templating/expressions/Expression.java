package de.holisticon.annotationprocessortoolkit.templating.expressions;

/**
 *
 */
public class Expression {

    private boolean negated = false;

    private final Object[] operands;
    private final OperationType[] operationTypes;

    public Expression(Object[] operands, OperationType[] operationTypes) {
        this.operands = operands;
        this.operationTypes = operationTypes;
    }

    public boolean isNegated() {
        return negated;
    }

    public void setNegated(boolean negated) {
        this.negated = negated;
    }

    public Object[] getOperands() {
        return operands;
    }

    public OperationType[] getOperationTypes() {
        return operationTypes;
    }
}
