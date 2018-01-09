package io.toolisticon.annotationprocessortoolkit.templating.expressions.operands;

import io.toolisticon.annotationprocessortoolkit.templating.ModelPathResolver;


/**
 * Dynamic operand queried from models.
 */
public class DynamicOperand extends ParsedOperand<Object> {

    private Object value;

    public DynamicOperand(String expressionString) {
        super( expressionString);

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

    @Override
    public OperandType getOperandType() {
        return OperandType.DYNAMIC_VALUE;
    }
}