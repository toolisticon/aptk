package io.toolisticon.annotationprocessortoolkit.templating.expressions.operations;


/**
 * Defines the operation type mode.
 * Helps to determine the number of operands
 */
public enum OperationTypeMode {
    UNARY(1),
    BINARY(2);

    private final int numberOfOperands;

    OperationTypeMode(int numberOfOperands) {
        this.numberOfOperands = numberOfOperands;
    }

    public int getNumberOfOperands() {
        return numberOfOperands;
    }

}
