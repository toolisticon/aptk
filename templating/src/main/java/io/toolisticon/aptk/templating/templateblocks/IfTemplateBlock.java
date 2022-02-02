package io.toolisticon.aptk.templating.templateblocks;

import io.toolisticon.aptk.templating.ParseUtilities;
import io.toolisticon.aptk.templating.exceptions.InvalidElseIfException;
import io.toolisticon.aptk.templating.exceptions.InvalidExpressionResult;
import io.toolisticon.aptk.templating.expressions.Expression;
import io.toolisticon.aptk.templating.expressions.ExpressionParser;
import io.toolisticon.aptk.templating.expressions.operands.Operand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * If template block.
 */
public class IfTemplateBlock implements TemplateBlock {

    /**
     * Class to store If and ElseIf statements.
     */
    static class IfStatement {

        /** The if statements expression - must resolve to boolean value. */
        private final String accessPath;
        /** The template block binder that belongs to the if statement. */
        private final TemplateBlockBinder binder;

        /**
         * Constructor.
         * @param accessPath the if statements expression
         * @param binder
         */
        IfStatement(String accessPath, TemplateBlockBinder binder) {
            this.accessPath = accessPath;
            this.binder = binder;
        }

        String getAccessPath() {
            return accessPath;
        }

        TemplateBlockBinder getBinder() {
            return binder;
        }
    }

    private static final String ELSE_OR_ELSEIF_DETECTION_REGEX = "[!][{](?:[ ]*?)(else(?:if){0,1})([ ]+(.*?)){0,1}(?:[ ]*?)[}]";
    private static final Pattern ELSE_OR_ELSEIF_DETECTION_PATTERN = Pattern.compile(ELSE_OR_ELSEIF_DETECTION_REGEX);

    private static final String ELSE_REGEX = "[!][{](?:[ ]*?)else(?:[ ]*?)[}]";
    private static final Pattern ELSE_PATTERN = Pattern.compile(ELSE_REGEX);

    private static final String ELSEIF_REGEX = "[!][{]elseif[ ]+(.*?)[ ]*[}]";
    private static final Pattern ELSEIF_PATTERN = Pattern.compile(ELSEIF_REGEX);

    /** The initial if statement expression - must resolve to boolean value.*/
    private final String accessPath;
    /** The templateString representing the complete if statement.*/
    private final String templateString;

    private TemplateBlockBinder elseBinder;

    private final List<IfStatement> ifStatements = new ArrayList<>();


    public IfTemplateBlock(String attributeString, String templateString) {

        if (attributeString == null || attributeString.trim().isEmpty()) {
            throw new IllegalArgumentException("if command has no attribute string.");
        }

        this.accessPath = attributeString.trim();
        this.templateString = ParseUtilities.trimContentString(templateString);

    }


    @Override
    public TemplateBlockType getTemplateBlockType() {
        return TemplateBlockType.IF;
    }

    @Override
    public String getContent(Map<String, Object> outerVariables) {

        for (IfStatement ifStatement : ifStatements) {

            Expression expression = ExpressionParser.parseExpression(ifStatement.getAccessPath(), outerVariables);
            Operand result = expression.evaluateExpression();

            if (!Boolean.class.equals(result.getOperandsJavaType()) && !boolean.class.equals(result.getOperandsJavaType())) {
                throw new InvalidExpressionResult("If statements expression '" + ifStatement.getAccessPath() + "' must evaluate to Boolean or boolean " + (result.getOperandsJavaType() != null ? ", but is of type " + result.getOperandsJavaType().getCanonicalName() : ""));
            }

            if ((Boolean) result.value()) {
                return ifStatement.getBinder().getContent(outerVariables).toString();
            }

        }

        return elseBinder != null ? elseBinder.getContent(outerVariables) : "";

    }

    private enum Phase {
        ELSEIF("elseif"),
        ELSE("else");

        private final String commandString;

        Phase(String commandString) {
            this.commandString = commandString;
        }

        String getCommandString() {
            return this.commandString;
        }
    }

    public void setBinder(TemplateBlockBinder binder) {

        this.elseBinder = new TemplateBlockBinder(null);

        // access path and binder for current if
        TemplateBlockBinder currentIfBinder = new TemplateBlockBinder(null);
        ifStatements.add(new IfStatement(this.accessPath, currentIfBinder));


        // phase 1: elseifs, phase2: no more elseif and else
        Phase phase = Phase.ELSEIF;
        for (TemplateBlock templateBlock : binder.getTemplateBlocks()) {
            if (phase == Phase.ELSEIF) {

                // Just add template block for non plaintext blocks
                if (templateBlock.getTemplateBlockType() != TemplateBlockType.PLAIN_TEXT) {
                    currentIfBinder.addTemplateBlock(templateBlock);
                    continue;
                }

                // Check for elseif or else
                Matcher detectElseOrElseIfMatcher = ELSE_OR_ELSEIF_DETECTION_PATTERN.matcher(templateBlock.getContent(null));
                boolean foundElseOrElseIf = detectElseOrElseIfMatcher.find();

                // add block if it contains no elseif or else
                if (!foundElseOrElseIf) {
                    currentIfBinder.addTemplateBlock(templateBlock);
                    continue;
                }

                // This is the most complex part, a plain text block can contain multiple elseif statements and a final else statement
                String contentString = templateBlock.getContent(null);
                int lastBeginIndex = 0;
                while (foundElseOrElseIf) {

                    if (phase == Phase.ELSEIF) {

                        // add segment to current if or elseif and finish it
                        PlainTextTemplateBlock currentSegmentTemplateBlock = new PlainTextTemplateBlock(contentString.substring(lastBeginIndex, detectElseOrElseIfMatcher.start()));
                        currentIfBinder.addTemplateBlock(currentSegmentTemplateBlock);

                    } else {

                        // detected another else or elseif in else phase -> error
                        String statementString = detectElseOrElseIfMatcher.group();
                        throw new InvalidElseIfException("Detected '" + statementString + "' after finishing else case which is syntactical incorrect");

                    }


                    // Must now detect if it's an elseif or command which was found
                    String commandString = detectElseOrElseIfMatcher.group();
                    String command = detectElseOrElseIfMatcher.group(1);

                    if (Phase.ELSEIF.getCommandString().equals(command)) {

                        // Check if else was used syntactically correct
                        Matcher elsifMatcher = ELSEIF_PATTERN.matcher(commandString);
                        if (!elsifMatcher.matches()) {
                            throw new InvalidElseIfException("Detected syntactical wrong elseif command : '" + commandString + "'");
                        }

                        // get expression
                        String elseifAccessPath = elsifMatcher.group(1);

                        // now create new binder
                        currentIfBinder = new TemplateBlockBinder(null);
                        ifStatements.add(new IfStatement(elseifAccessPath, currentIfBinder));

                    } else {

                        // Check if else was used syntactically correct
                        if (!ELSE_PATTERN.matcher(commandString).matches()) {
                            throw new InvalidElseIfException("Detected syntactical wrong else command : '" + commandString + "'");
                        }

                        // Switch to else stage
                        phase = Phase.ELSE;

                    }


                    // do next check in plain text block
                    lastBeginIndex = detectElseOrElseIfMatcher.end();
                    foundElseOrElseIf = detectElseOrElseIfMatcher.find();
                }

                // must add last block to either elseif or else case
                // add segment to current if or elseif and finish it
                PlainTextTemplateBlock currentSegmentTemplateBlock = new PlainTextTemplateBlock(contentString.substring(lastBeginIndex));
                if (phase == Phase.ELSEIF) {
                    currentIfBinder.addTemplateBlock(currentSegmentTemplateBlock);
                } else {
                    this.elseBinder.addTemplateBlock(currentSegmentTemplateBlock);
                }

            } else {
                // In ELSE phase

                // Just add template block for non plaintext blocks
                if (templateBlock.getTemplateBlockType() != TemplateBlockType.PLAIN_TEXT) {
                    this.elseBinder.addTemplateBlock(templateBlock);
                    continue;
                }

                // must not contain any more else or elseif cases
                // Check for elseif or else
                Matcher detectElseOrElseIfMatcher = ELSE_OR_ELSEIF_DETECTION_PATTERN.matcher(templateBlock.getContent(null));
                if (detectElseOrElseIfMatcher.find()) {
                    String statementString = detectElseOrElseIfMatcher.group();
                    throw new InvalidElseIfException("Detected '" + statementString + "' after finishing else case which is syntactical incorrect");
                }

                this.elseBinder.addTemplateBlock(templateBlock);

            }

        }


    }


    public String getAccessPath() {
        return accessPath;
    }

    public String getTemplateString() {
        return templateString;
    }

    public TemplateBlockBinder getElseBinder() {
        return elseBinder;
    }

    public List<IfStatement> getIfStatements() {
        return ifStatements;
    }
}
