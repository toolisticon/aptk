package de.holisticon.annotationprocessortoolkit.templating.expressions;

import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.OperandFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Enum that declares the supported operation types.
 */
public enum OperationType {

    NEGATE("[ ]*[!]", 0, OperationTypeMode.UNARY, InternalOperandTypeForCalculations.BOOLEAN) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand = operands[0];

            return OperandFactory.createOperationResult(Boolean.class, !((Boolean) operand.value()));

        }
    },
    AND("[&]{2}", 80, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.BOOLEAN) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            return OperandFactory.createOperationResult(Boolean.class, (Boolean) operand1.value() && (Boolean) operand2.value());

        }
    },
    OR("[|]{2}", 85, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.BOOLEAN) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            return OperandFactory.createOperationResult(Boolean.class, (Boolean) operand1.value() || (Boolean) operand2.value());

        }

    },
    EQUAL("[=]{2}", 70, OperationTypeMode.BINARY) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, false, false);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            boolean result = false;

            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            // check for Decimals and floating points => make it comparable
            if (
                    (
                            InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1)
                                    && InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations2)
                    ) || (
                            InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)
                                    && InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations1)
                    )
                    ) {

                operand1 = OperandFactory.createOperationResult(Double.class, convertToDouble(operand1));
                operand2 = OperandFactory.createOperationResult(Double.class, convertToDouble(operand2));
                internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
                internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            }


            if (!operand1.getOperandsJavaType().equals(operand2.getOperandsJavaType()) && !internalOperandTypeForCalculations1.equals(internalOperandTypeForCalculations2)) {
                throw new IllegalArgumentException("Incompatible operand types in '==' operation");
            }


            if (operand1.value() == null && operand2.value() == null) {
                result = true;
            } else if ((operand1.value() == null && operand2.value() != null) || (operand1.value() != null && operand2.value() == null)) {
                result = false;
            } else {

                if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) && InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

                    Double operand1Value = convertToDouble(operand1);
                    Double operand2Value = convertToDouble(operand2);

                    result = operand1Value.equals(operand2Value);

                } else if (InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations1) && InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations2)) {

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
    NOT_EQUAL("[!][=]", 70, OperationTypeMode.BINARY) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, false, false);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            boolean result = false;

            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);


            if (!operand1.getOperandsJavaType().equals(operand2.getOperandsJavaType()) && !internalOperandTypeForCalculations1.equals(internalOperandTypeForCalculations2)) {
                throw new IllegalArgumentException("Incompatible operand types in '==' operation");
            }


            if (operand1.value() == null && operand2.value() == null) {
                result = true;
            } else if ((operand1.value() == null && operand2.value() != null) || (operand1.value() != null && operand2.value() == null)) {
                result = false;
            } else {

                if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) && InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

                    Double operand1Value = convertToDouble(operand1);
                    Double operand2Value = convertToDouble(operand2);

                    result = operand1Value.equals(operand2Value);

                } else if (InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations1) && InternalOperandTypeForCalculations.DECIMAL.equals(internalOperandTypeForCalculations2)) {

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
    LESS_OR_EQUAL_THAN("[<][=]", 60, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    GREATER_OR_EQUAL_THAN("[>][=]", 60, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    LESS_THAN("[<]", 60, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    GREATER_THAN("[>]", 60, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    ADDITION("[+]", 50, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.STRING, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.STRING.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.STRING.equals(internalOperandTypeForCalculations2)) {

                // do floating point based operation
                String operand1Value = convertToString(operand1);
                String operand2Value = convertToString(operand2);

                return OperandFactory.createOperationResult(String.class, operand1Value + operand2Value);

            } else if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    SUBTRACTION("[-]", 50, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    MULTIPLICATION("[*]", 40, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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
    DIVISION("[/]", 40, OperationTypeMode.BINARY, InternalOperandTypeForCalculations.DECIMAL, InternalOperandTypeForCalculations.FLOAT) {
        @Override
        public Operand doOperation(Operand... operands) {

            // do initial checks for constraints
            doBaseChecks(this, operands, true, true);

            // must be done after the base check !
            Operand operand1 = operands[0];
            Operand operand2 = operands[1];

            // Must find common type
            InternalOperandTypeForCalculations internalOperandTypeForCalculations1 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand1);
            InternalOperandTypeForCalculations internalOperandTypeForCalculations2 = InternalOperandTypeForCalculations.getOperationModeForOperand(operand2);

            if (InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations1) || InternalOperandTypeForCalculations.FLOAT.equals(internalOperandTypeForCalculations2)) {

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

    private final Pattern operationPattern;
    private final int operationExecutionOrder;
    private final OperationTypeMode operationTypeMode;
    private final Set<Class> supportedTypes = new HashSet<Class>();

    /**
     * Constructor for OperationType.
     *
     * @param operationPatternString                        the operation types pattern String
     * @param operationOrder                                the execution order of the operation
     * @param operationTypeMode                             the operation type mode (f.e. UNARY or BINARY, defines number of parameters)
     * @param supportedInternalOperandTypeForCalculationses the supported operand modes (==java types) of this operation type
     */
    OperationType(
            String operationPatternString,
            int operationOrder,
            OperationTypeMode operationTypeMode,
            InternalOperandTypeForCalculations... supportedInternalOperandTypeForCalculationses) {

        this.operationPattern = Pattern.compile("[ ]*" + operationPatternString + "[ ]*");
        this.operationExecutionOrder = operationOrder;
        this.operationTypeMode = operationTypeMode;
        this.supportedTypes.addAll(InternalOperandTypeForCalculations.getSupportedOperandModeClasses(supportedInternalOperandTypeForCalculationses));

    }

    /**
     * Gets the operations type mode.
     *
     * @return the operations type mode
     */
    public OperationTypeMode getOperationTypeMode() {
        return operationTypeMode;
    }

    /**
     * Gets the operations pattern.
     *
     * @return the operations pattern
     */
    public Pattern getOperationPattern() {
        return operationPattern;
    }


    /**
     * Gets the operations execution order weight.
     *
     * @return the execution order weight
     */
    public int getOperationExecutionOrder() {
        return operationExecutionOrder;
    }

    /**
     * Gets the operations by OperationTypeMode.
     *
     * @param operationTypeMode the OperationType
     * @return An array for passed operationTypeMode, an empty array for passed null value
     */
    public static OperationType[] getOperationsByOperationTypeMode(OperationTypeMode operationTypeMode) {

        List<OperationType> result = new ArrayList<OperationType>();

        for (OperationType operationType : values()) {
            if (operationType.getOperationTypeMode().equals(operationTypeMode)) {
                result.add(operationType);
            }
        }

        result.sort(new ExecutionOrderComparator());

        return result.toArray(new OperationType[result.size()]);
    }

    /**
     * Abstract base method to execute an operation.
     *
     * @param operands The number of operands depends on the OperationType
     * @return The result wrapped in an Operand
     * @throws IllegalArgumentException if base checks for OperationType fail (like number of operands, null value checks,...)
     */
    public abstract Operand doOperation(Operand... operands);

    /**
     * This method is used to do some base checks that must be done before most operations.
     * <p/>
     * This includes checks for number of operands(mandatory) and supportedTypeCheck(optional) and null checks
     * for operation type, operands (mandatory) and operand values (optional).
     *
     * @param operationType         the operation type to check for
     * @param operands              the operands of the operation
     * @param doNullCheck           Defines if null checks for operand values should be done
     * @param doSupportedTypesCheck Defines if operand types should be checked for conformity with operation
     * @throws IllegalArgumentException if a constraint is violated
     */
    protected void doBaseChecks(OperationType operationType, Operand[] operands, boolean doNullCheck, boolean doSupportedTypesCheck) {

        if (operationType == null) {
            throw new IllegalArgumentException("passed operationType must not be null");
        }

        // check if there are the correct number of operands
        if (operationType.getOperationTypeMode().getNumberOfOperands() != operands.length) {
            throw new IllegalArgumentException("Got invalid number of operands for operation type " + operationType.name() + " - needed " + operationType.getOperationTypeMode().getNumberOfOperands() + " operands, but got " + operands.length);
        }

        // do null check
        for (int i = 0; i < operationType.getOperationTypeMode().getNumberOfOperands(); i++) {
            if (operands[i] == null) {
                throw new IllegalArgumentException("passed operands must not be null");
            }
        }

        // check operand values
        if (doNullCheck) {
            for (int i = 0; i < operationType.getOperationTypeMode().getNumberOfOperands(); i++) {
                if (operands[i].value() == null) {
                    throw new IllegalArgumentException("passed operand values must not be null");
                }
            }
        }

        // check if types are supported
        if (doSupportedTypesCheck) {
            for (int i = 0; i < operationType.getOperationTypeMode().getNumberOfOperands(); i++) {
                if (!supportedTypes.contains(operands[i].getOperandsJavaType())) {
                    throw new IllegalArgumentException("At least one operand type (" + Operand.getStringRepresentationOfOperandsJavaTypes(operands) + ") isn't supported by the operation " + operationType.name());
                }
            }
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
     * Used to convert decimal numbers to a Long value.
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

    private static class ExecutionOrderComparator implements Comparator<OperationType> {
        @Override
        public int compare(OperationType o1, OperationType o2) {

            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 != null && o2 == null) {
                return -1;
            } else if (o1 == null && o2 != null) {
                return 1;
            } else {

                if (o1.getOperationExecutionOrder() == o2.getOperationExecutionOrder()) {
                    return 0;
                } else if (o1.getOperationExecutionOrder()< o2.getOperationExecutionOrder()) {
                    return -1;
                } else {
                    return 1;
                }

            }
        }
    }

}
