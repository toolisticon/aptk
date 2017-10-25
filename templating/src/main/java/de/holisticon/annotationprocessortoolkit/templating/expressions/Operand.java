package de.holisticon.annotationprocessortoolkit.templating.expressions;


public abstract class Operand<T> {

    private final OperandType operandType;
    private final String expressionString;
    private final OperationType[] unaryOperationsToBeApplied;


    public Operand(OperandType operandType, String expressionString, OperationType[] unaryOperationsToBeApplied) {

        this.operandType = operandType;
        this.expressionString = expressionString;
        this.unaryOperationsToBeApplied = unaryOperationsToBeApplied;

    }


    public String getExpressionString() {
        return expressionString;
    }

    public OperandType getOperandType() {
        return operandType;
    }

    protected OperationType[] getUnaryOperationsToBeApplied() {
        return unaryOperationsToBeApplied;
    }

    /**
     * Determines the java type after unary operations.
     *
     * @return the java type after the execution of all unary operations
     * @throws IllegalArgumentException if at least one unary operation can't be applied.
     */
    protected Class determineJavaTypeAfterUnaryOperations() {

        if (unaryOperationsToBeApplied.length > 0) {

            Class startType = getOperandsJavaType();

            // use reverse order for evaluation
            for (int i = unaryOperationsToBeApplied.length - 1; i >= 0; i--) {

                OperationType unaryOperation = unaryOperationsToBeApplied[i];

                //if (unaryOperation.) {

                //}

            }
        }
        return null;
    }

    /**
     * Can be used to get the java type of the operand.
     *
     * @return the java type of the operand
     */
    public abstract Class<? extends T> getOperandsJavaType();


    /**
     * Gets the value of the operand
     *
     * @return
     */
    public abstract T value();

}
