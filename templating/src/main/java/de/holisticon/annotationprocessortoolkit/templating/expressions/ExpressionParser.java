package de.holisticon.annotationprocessortoolkit.templating.expressions;

import de.holisticon.annotationprocessortoolkit.templating.expressions.operands.ExpressionOperand;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser to paarse an expression String.
 */
public class ExpressionParser {

    private static class ExpressionParseResult {

        Expression expression;
        String restString;

        public ExpressionParseResult(Expression expression, String restString) {
            this.expression = expression;
            this.restString = restString;
        }

        public Expression getExpression() {
            return expression;
        }

        public String getRestString() {
            return restString;
        }
    }


    public static Expression parseExpression(String expressionString) {

        return parseExpressionRecursively(expressionString, false).getExpression();

    }

    public static ExpressionParseResult parseExpressionRecursively(String expressionString, boolean usedBrackets) {

        final Pattern negatePattern = Pattern.compile("[ ]*[!]");
        final Pattern openingBracePattern = Pattern.compile("[ ]*[(][ ]*");
        final Pattern closingBracePattern = Pattern.compile("[ ]*[)][ ]*");

        List<Object> operands = new ArrayList<Object>();
        List<OperationType> operations = new ArrayList<OperationType>();

        int index = 0;
        boolean closingBraceDetected = false;
        boolean firstOperand = true;

        while (index < expressionString.length()) {

            if (firstOperand) {
                firstOperand = false;
            } else {
                OperationTypeSearchResult operationTypeSearchResult = getOperationType(expressionString, index);
                operations.add(operationTypeSearchResult.getValue());
                index = operationTypeSearchResult.getEndIndex();
            }


            boolean negated = false;

            // check if next operand / expression is negated
            Matcher negateMatcher = negatePattern.matcher(expressionString);
            if (negateMatcher.find(index) && negateMatcher.start() == index) {

                index = negateMatcher.end();
                negated = true;

            }


            // check if brace is opened => do subexpression
            Matcher openingBraceMatcher = openingBracePattern.matcher(expressionString);
            if (openingBraceMatcher.find(index) && openingBraceMatcher.start() == index) {

                index = openingBraceMatcher.end();

                // parse subexpression
                ExpressionParseResult subExpressionResult = parseExpressionRecursively(expressionString.substring(index), true);

                operands.add(new ExpressionOperand(OperandType.EXPRESSION, expressionString.substring(index, expressionString.length() - subExpressionResult.getRestString().length()), subExpressionResult.getExpression()));

                // use rest String
                expressionString = subExpressionResult.getRestString();
                index = 0;

            } else {

                // get Operand
                OperandTypeSearchResult operandTypeSearchResult = getOperandType(expressionString, index);
                operands.add(new Operand(operandTypeSearchResult.getValue(), expressionString.substring(index, operandTypeSearchResult.getEndIndex())));
                index = operandTypeSearchResult.getEndIndex();

            }

            // get Closing brace
            if (usedBrackets) {

                Matcher closingBracketMatcher = closingBracePattern.matcher(expressionString);
                if (closingBracketMatcher.find(index) && closingBracketMatcher.start() == index) {

                    return new ExpressionParseResult(new Expression(operands.toArray(), (OperationType[]) operations.toArray(new OperationType[operations.size()])), expressionString.substring(closingBracketMatcher.end()));

                }

            }


        }

        if (usedBrackets) {
            throw new IllegalArgumentException("Can't find closing bracket");
        }
        OperationType[] operationsArray = operations.toArray(new OperationType[operations.size()]);
        return new ExpressionParseResult(new Expression(operands.toArray(), operationsArray), expressionString.substring(index));

    }


    /**
     * Gets the next operation type.
     *
     * @param expressionString the expression string
     * @param index            the current processing index
     * @return
     */
    public static OperationTypeSearchResult getOperationType(String expressionString, int index) {

        for (OperationType operationType : OperationType.values()) {

            Matcher matcher = operationType.getOperationPattern().matcher(expressionString);
            if (matcher.find(index) && matcher.start() == index) {
                return new OperationTypeSearchResult(operationType, matcher.start(), matcher.end());
            }

        }

        throw new IllegalArgumentException("Can't determine operation type for string : " + expressionString.substring(index));
    }

    public static OperandTypeSearchResult getOperandType(String expressionString, int index) {

        for (OperandType operandType : OperandType.getPatternBasedOperandTypes()) {

            Matcher matcher = operandType.getOperandPattern().matcher(expressionString);
            if (matcher.find(index) && matcher.start() == index) {
                return new OperandTypeSearchResult(operandType, matcher.start(), matcher.end());
            }

        }

        throw new IllegalArgumentException("Can't determine operands type for string : " + expressionString.substring(index));
    }

}
