package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

import de.holisticon.annotationprocessortoolkit.templating.ModelPathResolver;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Operand;
import de.holisticon.annotationprocessortoolkit.templating.expressions.OperandType;


/**
 * Dynamic operand queried from models.
 */
public class DynamicOperand extends Operand<Object> {

    private Object value;

    public DynamicOperand(String expressionString) {
        super(OperandType.DYNAMIC_VALUE, expressionString);

    }

    @Override
    public Class<Object> getOperandsJavaType() {

        ModelPathResolver.ResolvedModelPathResult result = ModelPathResolver.resolveModelPath(ModelPathResolver.modelMapThreadLocal.get(), getExpressionString());
        return result != null ? result.getType() : null;

    }

    @Override
    public Object value() {

        ModelPathResolver.ResolvedModelPathResult result = ModelPathResolver.resolveModelPath(ModelPathResolver.modelMapThreadLocal.get(), getExpressionString());
        return result != null ? result.getValue() : null;

    }

}