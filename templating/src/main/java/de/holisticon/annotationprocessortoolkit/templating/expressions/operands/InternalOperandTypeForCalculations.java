package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Operands need to be normalized to be able to execute them.
 * <p/>
 * For example we need to normalize all operands to Long for decimal addition operation.
 */
public enum InternalOperandTypeForCalculations {

    DECIMAL(Short.class, short.class, Integer.class, int.class, Long.class, long.class),
    FLOAT(Float.class, float.class, Double.class, double.class),
    BOOLEAN(Boolean.class, boolean.class),
    STRING(String.class),
    OBJECT;

    private final Class[] supportedTypes;

    InternalOperandTypeForCalculations(Class... supportedTypes) {
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

    public static Set<Class> getSupportedOperandModeClasses(InternalOperandTypeForCalculations... internalOperandTypeForCalculationses) {
        Set<Class> set = new HashSet<Class>();

        if (internalOperandTypeForCalculationses != null) {
            for (InternalOperandTypeForCalculations internalOperandTypeForCalculations : internalOperandTypeForCalculationses) {
                set.addAll(Arrays.asList(internalOperandTypeForCalculations.getSupportedTypes()));
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
