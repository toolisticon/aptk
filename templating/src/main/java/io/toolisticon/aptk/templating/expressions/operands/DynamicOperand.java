package io.toolisticon.aptk.templating.expressions.operands;

import io.toolisticon.aptk.templating.ModelPathResolver;


/**
 * Dynamic operand queried from models.
 */
public class DynamicOperand extends ParsedOperand<Object> {

    private Object value;

    public DynamicOperand(String expressionString) {
        super(expressionString);

    }

    @Override
    public Class<Object> getOperandsJavaType() {

        ModelPathResolver.ResolvedModelPathResult result = ModelPathResolver.resolveModelPath(ModelPathResolver.modelMapThreadLocal.get(), getExpressionString());

        // result cannot be null
        return result.getType();

    }

    @Override
    public Object value() {

        ModelPathResolver.ResolvedModelPathResult result = ModelPathResolver.resolveModelPath(ModelPathResolver.modelMapThreadLocal.get(), getExpressionString());

        // result cannot be null
        return result.getValue();

    }

    @Override
    public OperandType getOperandType() {
        return OperandType.DYNAMIC_VALUE;
    }
}