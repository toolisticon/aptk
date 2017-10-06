package de.holisticon.annotationprocessortoolkit.templating.expressions;


import java.util.Map;

public class Operand {

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

    public Class getOperandsJavaType(Map<String, Object> model) {
        return null;
    }
}
