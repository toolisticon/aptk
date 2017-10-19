package de.holisticon.annotationprocessortoolkit.templating.expressions;

import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.OperandFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Enum that declares the supported operation types.
 */
public enum OperationType {

    AND("[&]{2}", 50, OperationMode.BINARY, OperandMode.BOOLEAN) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {

            doBaseChecks(this, operand1, operand2, true, true);
            return OperandFactory.createOperationResult(Boolean.class, (Boolean) operand1.value() && (Boolean) operand2.value());

        }
    },
    OR("[|]{2}", 60, OperationMode.BINARY, OperandMode.BOOLEAN) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {

            doBaseChecks(this, operand1, operand2, true, true);
            return OperandFactory.createOperationResult(Boolean.class, (Boolean) operand1.value() || (Boolean) operand2.value());

        }

    },
    EQUAL("[=]{2}", 80, OperationMode.BINARY) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {

            doBaseChecks(this, operand1, operand2, false, false);

            boolean result = false;

            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);


            if (!operand1.getOperandsJavaType().equals(operand2.getOperandsJavaType()) && !operandMode1.equals(operandMode2)) {
                throw new IllegalArgumentException("Incompatible operand types in '==' operation");
            }


            if (operand1.value() == null && operand2.value() == null) {
                result = true;
            } else if ((operand1.value() == null && operand2.value() != null) || (operand1.value() != null && operand2.value() == null)) {
                result = false;
            } else {

                if (OperandMode.FLOAT.equals(operandMode1) && OperandMode.FLOAT.equals(operandMode2)) {

                    Double operand1Value = convertToDouble(operand1);
                    Double operand2Value = convertToDouble(operand2);

                    result = operand1Value.equals(operand2Value);

                } else if (OperandMode.DECIMAL.equals(operandMode1) && OperandMode.DECIMAL.equals(operandMode2)) {

                    // do decimal based operation
                    Long operand1Value = convertToLong(operand1);
                    Long operand2Value = convertToLong(operand2);

                    result = operand1Value.equals(operand2Value);

                } else {
                    result = operand1.value().equals(operand2.value());
                }

            }


            return OperandFactory.createOperationResult(Boolean.class, result);

        }
    },
    NOT_EQUAL("[!][=]", 80, OperationMode.BINARY) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, false, false);

            boolean result = false;

            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);


            if (!operand1.getOperandsJavaType().equals(operand2.getOperandsJavaType()) && !operandMode1.equals(operandMode2)) {
                throw new IllegalArgumentException("Incompatible operand types in '==' operation");
            }


            if (operand1.value() == null && operand2.value() == null) {
                result = true;
            } else if ((operand1.value() == null && operand2.value() != null) || (operand1.value() != null && operand2.value() == null)) {
                result = false;
            } else {

                if (OperandMode.FLOAT.equals(operandMode1) && OperandMode.FLOAT.equals(operandMode2)) {

                    Double operand1Value = convertToDouble(operand1);
                    Double operand2Value = convertToDouble(operand2);

                    result = operand1Value.equals(operand2Value);

                } else if (OperandMode.DECIMAL.equals(operandMode1) && OperandMode.DECIMAL.equals(operandMode2)) {

                    // do decimal based operation
                    Long operand1Value = convertToLong(operand1);
                    Long operand2Value = convertToLong(operand2);

                    result = operand1Value.equals(operand2Value);

                } else {
                    result = operand1.value().equals(operand2.value());
                }
            }


            return OperandFactory.createOperationResult(Boolean.class, !result);
        }
    },
    LESS_OR_EQUAL_THAN("[<][=]", 80, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {

            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value <= operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value <= operand2Value);

            }

        }
    },
    GREATER_OR_EQUAL_THAN("[>][=]", 80, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value >= operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value >= operand2Value);

            }


        }
    },
    LESS_THAN("[<]", 81, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value < operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value < operand2Value);

            }

        }
    },
    GREATER_THAN("[>]", 81, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value > operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value > operand2Value);

            }

        }
    },
    ADDITION("[+]", 50, OperationMode.BINARY, OperandMode.STRING, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.STRING.equals(operandMode1) || OperandMode.STRING.equals(operandMode2)) {

                // do floating point based operation
                String operand1Value = convertToString(operand1);
                String operand2Value = convertToString(operand2);

                return OperandFactory.createOperationResult(String.class, operand1Value + operand2Value);

            } else if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value + operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value + operand2Value);

            }

        }
    },
    SUBTRACTION("[-]", 50, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value - operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value - operand2Value);

            }

        }
    },
    MULTIPLICATION("[*]", 40, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value * operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value * operand2Value);

            }

        }
    },
    DIVISION("[/]", 40, OperationMode.BINARY, OperandMode.DECIMAL, OperandMode.FLOAT) {
        @Override
        public Operand doOperation(Operand operand1, Operand operand2) {
            doBaseChecks(this, operand1, operand2, true, true);

            // Must find common type
            OperandMode operandMode1 = OperandMode.getOperationModeForOperand(operand1);
            OperandMode operandMode2 = OperandMode.getOperationModeForOperand(operand2);

            if (OperandMode.FLOAT.equals(operandMode1) || OperandMode.FLOAT.equals(operandMode2)) {

                // do floating point based operation
                Double operand1Value = convertToDouble(operand1);
                Double operand2Value = convertToDouble(operand2);

                return OperandFactory.createOperationResult(Double.class, operand1Value / operand2Value);

            } else {

                // do decimal based operation
                Long operand1Value = convertToLong(operand1);
                Long operand2Value = convertToLong(operand2);

                return OperandFactory.createOperationResult(Long.class, operand1Value / operand2Value);

            }

        }
    };


    protected enum OperandMode {
        DECIMAL(Short.class, short.class, Integer.class, int.class, Long.class, long.class),
        FLOAT(Float.class, float.class, Double.class, double.class),
        BOOLEAN(Boolean.class, boolean.class),
        STRING(String.class),
        OBJECT;

        private final Class[] supportedTypes;

        OperandMode(Class... supportedTypes) {
            this.supportedTypes = supportedTypes;
        }

        public Class[] getSupportedTypes() {
            return this.supportedTypes;
        }

        public boolean isSupportedType(Class type) {

            if (type == null) {
                return false;
            }

            return getSupportedOperandModeClasses(this).contains(type);
        }

        public static Set<Class> getSupportedOperandModeClasses(OperandMode... operandModes) {
            Set<Class> set = new HashSet<Class>();

            if (operandModes != null) {
                for (OperandMode operandMode : operandModes) {
                    set.addAll(Arrays.asList(operandMode.getSupportedTypes()));
                }
            }

            return set;
        }

        /**
         * Gets the operand mode for a java type.
         *
         * @param operand
         * @return
         */
        public static OperandMode getOperationModeForOperand(Operand operand) {

            if (operand != null) {
                for (OperandMode operandMode : OperandMode.values()) {
                    if (operandMode.isSupportedType(operand.getOperandsJavaType())) {
                        return operandMode;
                    }
                }
            }

            return OBJECT;
        }

    }

    private final Pattern operationPattern;
    private final int operationExecutionOrder;
    private final OperationMode operationMode;
    private final Set<Class> supportedTypes = new HashSet<Class>();

    private OperationType(String operationPatternString, int operationOrder, OperationMode operationMode, OperandMode... supportedOperandModes) {
        this.operationPattern = Pattern.compile("[ ]*" + operationPatternString + "[ ]*");
        this.operationExecutionOrder = operationOrder;
        this.operationMode = operationMode;
        this.supportedTypes.addAll(OperandMode.getSupportedOperandModeClasses(supportedOperandModes));
    }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public Pattern getOperationPattern() {
        return operationPattern;
    }

    public abstract Operand doOperation(Operand operand1, Operand operand2);

    protected void doBaseChecks(OperationType operationType, Operand operand1, Operand operand2, boolean doNullCheck, boolean doSupportedTypesCheck) {

        if (operationType == null) {
            throw new IllegalArgumentException("passed operationType must not be null");
        }

        if ((operand1 == null || operand2 == null)) {
            throw new IllegalArgumentException("passed operands must not be null");
        }

        if (doNullCheck && (operand1.value() == null || operand2.value() == null)) {
            throw new IllegalArgumentException("passed operand values must not be null");
        }

        if (doSupportedTypesCheck && (!supportedTypes.contains(operand1.getOperandsJavaType()) || !supportedTypes.contains(operand2.getOperandsJavaType()))) {
            throw new IllegalArgumentException("At least one operand type isn't supported by the operation");
        }


    }

    /**
     * Converts an operand to a String value by using it's toString method.
     *
     * @param operand
     * @return
     */
    protected String convertToString(Operand operand) {

        if (operand == null || operand.value() == null) {
            return null;
        }

        return operand.value().toString();
    }

    /**
     * Used to convert decimal and floating point numbers to a Double value.
     *
     * @param operand
     * @return
     */
    protected Double convertToDouble(Operand operand) {

        if (operand == null || operand.getOperandsJavaType() == null) {
            return null;
        }

        // convert floating point values
        if (Float.class.equals(operand.getOperandsJavaType()) || float.class.equals(operand.getOperandsJavaType())) {
            return ((Float) operand.value()).doubleValue();
        }
        if (Double.class.equals(operand.getOperandsJavaType()) || Double.class.equals(operand.getOperandsJavaType())) {
            return (Double) operand.value();
        }

        // convert decimal
        Long longValue = convertToLong(operand);
        if (longValue != null) {
            return longValue.doubleValue();
        }

        return null;
    }

    /**
     * used to convert decimal numbers to a Long value.
     *
     * @param operand
     * @return
     */
    protected Long convertToLong(Operand operand) {

        if (operand == null || operand.getOperandsJavaType() == null) {
            return null;
        }

        if (Short.class.equals(operand.getOperandsJavaType()) || short.class.equals(operand.getOperandsJavaType())) {
            return ((Short) operand.value()).longValue();
        }
        if (Integer.class.equals(operand.getOperandsJavaType()) || int.class.equals(operand.getOperandsJavaType())) {
            return ((Integer) operand.value()).longValue();
        }
        if (Long.class.equals(operand.getOperandsJavaType()) || Long.class.equals(operand.getOperandsJavaType())) {
            return (Long) operand.value();
        }


        return null;
    }


}
