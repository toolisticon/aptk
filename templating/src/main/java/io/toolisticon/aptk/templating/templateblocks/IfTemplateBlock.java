package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.ParseUtilities;
import io.toolisticon.aptk.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.aptk.templating.exceptions.MultipleElseCasesException;
import io.toolisticon.aptk.templating.expressions.Expression;
import io.toolisticon.aptk.templating.expressions.ExpressionParser;
import io.toolisticon.aptk.templating.expressions.operands.Operand;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If template block.
 */
public class IfTemplateBlock implements TemplateBlock {

    private static final String ELSE_REGEX = "[!][{]else[}]";
    private static final Pattern ELSE_PATTERN = Pattern.compile(ELSE_REGEX);

    private final String accessPath;
    private final String templateString;

    private TemplateBlockBinder binder;
    private TemplateBlockBinder elseBinder;


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
            return elseBinder != null ? elseBinder.getContent(outerVariables) : "";
        }

    }


    public TemplateBlockBinder getBinder() {
        return binder;
    }

    public void setBinder(TemplateBlockBinder binder) {


        // check if the passed binder contains an else case
        if (!hasElseStatement(binder)) {

            this.binder = binder;

        } else {

            this.binder = new TemplateBlockBinder(null);
            this.elseBinder = new TemplateBlockBinder(null);

            boolean foundElse = false;
            for (TemplateBlock templateBlock : binder.getTemplateBlocks()) {

                if (!foundElse) {
                    if (!hasElseStatement(templateBlock)) {
                        // templateBlock belongs to if
                        this.binder.addTemplateBlock(templateBlock);
                    } else {
                        foundElse = true;

                        // handle else template block
                        String[] result = templateBlock.getContent(null).split(ELSE_REGEX);

                        this.binder.addTemplateBlock(new PlainTextTemplateBlock(result[0]));
                        this.elseBinder.addTemplateBlock(new PlainTextTemplateBlock(result[1]));
                    }

                } else {
                    // put rest of template
                    this.elseBinder.addTemplateBlock(templateBlock);
                }

            }

        }


    }


    public String getAccessPath() {
        return accessPath;
    }

    public String getTemplateString() {
        return templateString;
    }

    private boolean hasElseStatement(TemplateBlockBinder binder) {
        int elseCount = 0;
        for (TemplateBlock templateBlock : binder.getTemplateBlocks()) {

            elseCount += (hasElseStatement(templateBlock) ? 1 : 0);

        }

        if (elseCount > 1) {
            throwMultipleElseStatementError();
        }

        return elseCount == 1;
    }

    /**
     * Checks wether passed templateBlock contains one else command block.
     * @param templateBlock the template block to scan
     * @return true, if template block contains one else command block, otherwise false.
     * @throws MultipleElseCasesException id more than one else command block is present
     */
    private boolean hasElseStatement(TemplateBlock templateBlock) {

        if (templateBlock.getTemplateBlockType() == TemplateBlockType.PLAIN_TEXT) {

            PlainTextTemplateBlock plainTextTemplateBlock = (PlainTextTemplateBlock) templateBlock;

            Matcher matcher = ELSE_PATTERN.matcher(plainTextTemplateBlock.getContent(null));

            if (matcher.find()) {


                if (matcher.find()) {
                    // must throw error
                    throwMultipleElseStatementError();
                }

                return true;
            }


        }

        return false;

    }

    /**
     * Throws a MultipleElseCasesException exception if there is more than one else command block.
     */
    private void throwMultipleElseStatementError() {
        throw new MultipleElseCasesException("If command block has multiple else cases.");
    }


}
