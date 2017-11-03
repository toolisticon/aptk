package de.holisticon.annotationprocessortoolkit.templating.templateblocks;

import de.holisticon.annotationprocessortoolkit.templating.ModelPathResolver;
import de.holisticon.annotationprocessortoolkit.templating.ParseUtilities;
import de.holisticon.annotationprocessortoolkit.templating.expressions.Expression;
import de.holisticon.annotationprocessortoolkit.templating.expressions.ExpressionParser;
import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.Operand;

import java.util.Map;

/**
 *
 */
public class VariableTextTemplateBlock implements TemplateBlock {


    private final String accessPath;

    public VariableTextTemplateBlock(String accessPath) {
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

        return result.value() != null  ? result.value().toString() : null;

    }
}

