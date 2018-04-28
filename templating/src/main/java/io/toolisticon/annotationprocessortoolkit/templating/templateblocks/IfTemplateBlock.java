package io.toolisticon.annotationprocessortoolkit.templating.templateblocks;

import io.toolisticon.annotationprocessortoolkit.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.Expression;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.ExpressionParser;
import io.toolisticon.annotationprocessortoolkit.templating.expressions.operands.Operand;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If template block.
 */
public class IfTemplateBlock implements TemplateBlock {



    private final String accessPath;
    private final String templateString;

    private TemplateBlockBinder binder;


    public IfTemplateBlock(String attributeString, String templateString) {

        if (attributeString == null || attributeString.trim().isEmpty()) {
            throw new IllegalArgumentException("if command has no attribute string.");
        }

        this.accessPath = attributeString.trim();
        this.templateString = templateString;


        binder = new TemplateBlockBinder(templateString);

    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.IF;
    }

    @Override
    public String getContent(Map<String, Object> outerVariables) {

        Expression expression = ExpressionParser.parseExpression(accessPath, outerVariables);
        Operand result = expression.evaluateExpression();

        if (!Boolean.class.equals(result.getOperandsJavaType())) {
            throw new InvalidExpressionResult("If statements expression '" + accessPath + "' must evaluate to Boolean" + (result.getOperandsJavaType() != null ? ", but is of type " + result.getOperandsJavaType().getCanonicalName() : ""));
        }

        if ((Boolean) result.value()) {
            return binder.getContent(outerVariables).toString();
        } else {
            return "";
        }

    }


    public TemplateBlockBinder getBinder() {
        return binder;
    }

    public void setBinder(TemplateBlockBinder binder) {
        this.binder = binder;
    }


    public String getAccessPath() {
        return accessPath;
    }

    public String getTemplateString() {
        return templateString;
    }


}
