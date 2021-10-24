package io.toolisticon.aptk.templating.expressions.operands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Operands need to be normalized to be able to execute them.
 * <p>
 * For example we need to normalize all operands to Long for decimal addition operation.
 */
public enum InternalOperandTypeForCalculations {

    DECIMAL(Short.class, short.class, Integer.class, int.class, Long.class, long.class),
    FLOAT(Float.class, float.class, Double.class, double.class),
    BOOLEAN(Boolean.class, boolean.class),
    STRING(String.class),
    OBJECT;

    private final Class[] supportedTypes;

    /**
     * Constructor.
     *
     * @param supportedTypes The supported types of the operand
     */
    InternalOperandTypeForCalculations(Class... supportedTypes) {
        this.supportedTypes = supportedTypes;
    }

    /**
     * Returns all supported types.
     *
     * @return the supported types of the operand
     */
    public Class[] getSupportedTypes() {
        return this.supportedTypes;
    }

    /**
     * Checks if passed class is supported by operand
     *
     * @param type the type to check
     * @return true if type is supported, otherwise false
     */
    public boolean isSupportedType(Class type) {

        if (type == null) {
            return false;
        }

        return getSupportedOperandModeClasses(this).contains(type);
    }

    /**
     * Gets the supported operand type
     *
     * @param internalOperandTypeForCalculations the operand types for calculation
     * @return A Set containing all supported Operand
     */
    public static Set<Class> getSupportedOperandModeClasses(InternalOperandTypeForCalculations... internalOperandTypeForCalculations) {
        Set<Class> set = new HashSet<Class>();

        if (internalOperandTypeForCalculations != null) {
            for (InternalOperandTypeForCalculations internalOperandTypeForCalculation : internalOperandTypeForCalculations) {
                set.addAll(Arrays.asList(internalOperandTypeForCalculation.getSupportedTypes()));
            }
        }

        return set;
    }

    /**
     * Gets the operand mode for a java type.
     *
     * @param operand the operand
     * @return The internal operand type for passed operand
     */
    public static InternalOperandTypeForCalculations getOperationModeForOperand(Operand operand) {

        if (operand != null) {
            for (InternalOperandTypeForCalculations internalOperandTypeForCalculations : InternalOperandTypeForCalculations.values()) {
                if (internalOperandTypeForCalculations.isSupportedType(operand.getOperandsJavaType())) {
                    return internalOperandTypeForCalculations;
                }
            }
        }

        return OBJECT;
    }

}
