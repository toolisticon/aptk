package de.holisticon.annotationprocessortoolkit.templating.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public enum OperandType {
    DOUBLE("([-]?\\d+[.]\\d+)"),
    LONG("([-]?\\d+)"),
    STRING("'(.*)'"),
    BOOLEAN("((?:true)|(?:false))"),
    DYNAMIC_VALUE("\\w+(?:[.]\\w+)*"),
    NULL_VALUE("null"),
    OPERATION_RESULT(null),
    EXPRESSION(null);


    private final Pattern operandPattern;

    private OperandType(String regExpr) {
        this.operandPattern = regExpr != null ? Pattern.compile(regExpr) : null;
    }

    public Pattern getOperandPattern() {
        return operandPattern;
    }

    public static OperandType getOperandType(String operandString) {

        if (operandString == null) {
            throw new IllegalArgumentException("Passed operandString must not be null");
        }

        for (OperandType operandType : values()) {

            if (operandType.operandPattern.matcher(operandString).matches()) {
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
