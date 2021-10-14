package io.toolisticon.aptk.templating.expressions.operands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public enum OperandType {
    DOUBLE("([-]?\\d+[.]\\d+)"),
    LONG("([-]?\\d+)"),
    STRING("['](.*?)(?<!(?:[\\\\]{2}){0,500}[\\\\])[']"),
    BOOLEAN("((?:true)|(?:false))"),
    NULL_VALUE("null"),
    DYNAMIC_VALUE("(\\w+(?:[.]\\w+)*)"),
    OPERATION_RESULT(null),
    EXPRESSION(null);


    private final String regExpr;

    private OperandType(String regExpr) {
        this.regExpr = regExpr != null ? "[ ]*" + regExpr + "[ ]*" : null;
    }

    public Pattern getOperandPattern() {
        return regExpr != null ? Pattern.compile(regExpr) : null;
    }

    public static OperandType getOperandType(String operandString) {

        if (operandString == null) {
            throw new IllegalArgumentException("Passed operandString must not be null");
        }

        for (OperandType operandType : values()) {

            if (operandType.getOperandPattern() != null && operandType.getOperandPattern().matcher(operandString).matches()) {
                return operandType;
            }

        }

        throw new IllegalArgumentException("Cannot determine operand type for '" + operandString + "'");
    }

    public static OperandType[] getPatternBasedOperandTypes() {
        List<OperandType> resultList = new ArrayList<OperandType>();

        for (OperandType operandType : values()) {
            if (operandType.getOperandPattern() != null) {
                resultList.add(operandType);
            }
        }

        return resultList.toArray(new OperandType[resultList.size()]);
    }

}
