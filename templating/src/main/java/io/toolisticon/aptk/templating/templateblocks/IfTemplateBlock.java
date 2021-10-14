package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.ParseUtilities;
import io.toolisticon.aptk.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.aptk.templating.expressions.Expression;
import io.toolisticon.aptk.templating.expressions.ExpressionParser;
import io.toolisticon.aptk.templating.expressions.operands.Operand;

import java.util.Map;

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
        this.templateString = ParseUtilities.trimContentString(templateString);


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

        if (!Boolean.class.equals(result.getOperandsJavaType()) && !boolean.class.equals(result.getOperandsJavaType())) {
            throw new InvalidExpressionResult("If statements expression '" + accessPath + "' must evaluate to Boolean or boolean " + (result.getOperandsJavaType() != null ? ", but is of type " + result.getOperandsJavaType().getCanonicalName() : ""));
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
