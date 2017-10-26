package de.holisticon.annotationprocessortoolkit.templating.expressions;


public abstract class Operand<T> {

    private final OperandType operandType;
    private final String expressionString;


    public Operand(OperandType operandType, String expressionString) {

        this.operandType = operandType;
        this.expressionString = expressionString;

    }


    public String getExpressionString() {
        return expressionString;
    }

    public OperandType getOperandType() {
        return operandType;
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
