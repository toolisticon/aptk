package de.holisticon.annotationprocessortoolkit.templating.expressions;

import java.util.regex.Pattern;

/**
 * Created by tobiasstamann on 30.09.17.
 */
public enum OperationType {

    AND("[&]{2}", 50, OperationMode.BINARY, Boolean.class, boolean.class),
    OR("[|]{2}", 60, OperationMode.BINARY, Boolean.class, boolean.class),
    EQUAL("[=]{2}", 80, OperationMode.BINARY),
    NOT_EQUAL("[!][=]", 80, OperationMode.BINARY),
    LESS_OR_EQUAL_THAN("[<][=]", 80, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    GREATER_OR_EQUAL_THAN("[>][=]", 80, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    LESS_THAN("[<]", 81, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    GREATER_THAN("[>]", 81, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    ADDITION("[+]", 50, OperationMode.BINARY, String.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    SUBTRACTION("[-]", 50, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    MULTIPLICATION("[*]", 40, OperationMode.BINARY, Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class),
    DIVISION("[/]", 40, OperationMode.BINARY,  Short.class, short.class, Integer.class, int.class, Long.class, long.class, Float.class, float.class, Double.class, double.class);


    private final Pattern operationPattern;
    private final int operationExecutionOrder;
    private final OperationMode operationMode;
    private Class[] supportedTypes;

    private OperationType(String operationPatternString, int operationOrder, OperationMode operationMode, Class... supportedTypes) {
        this.operationPattern = Pattern.compile("[ ]*" + operationPatternString + "[ ]*");
        this.operationExecutionOrder = operationOrder;
        this.operationMode = operationMode;
        this.supportedTypes = supportedTypes;
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public Pattern getOperationPattern() {
        return operationPattern;
    }
}
