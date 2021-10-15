package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.expressions.Expression;
import io.toolisticon.aptk.templating.expressions.ExpressionParser;
import io.toolisticon.aptk.templating.expressions.operands.Operand;

import java.util.Map;

/**
 *
 */
public class VariableTextTemplateBlock implements TemplateBlock {


    private final String accessPath;

    public VariableTextTemplateBlock(String accessPath) {
        if (accessPath == null || accessPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Passed accessPath must not be null or empty");
        }
        this.accessPath = accessPath;
    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.DYNAMIC_TEXT;
    }

    @Override
    public String getContent(Map<String, Object> variables) {


        Expression expression = ExpressionParser.parseExpression(accessPath, variables);
        Operand result = expression.evaluateExpression();

        return result.value() != null ? result.value().toString() : null;

    }

    protected String getAccessPath() {
        return this.accessPath;
    }
}

