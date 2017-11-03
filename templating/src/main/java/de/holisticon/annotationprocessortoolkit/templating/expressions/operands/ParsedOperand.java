package de.holisticon.annotationprocessortoolkit.templating.expressions.operands;

/**
 * Abstract base class for operands parsed from expression.
 */
public abstract class ParsedOperand <T> extends Operand <T>{

    private final String expressionString;


    public ParsedOperand(String expressionString) {
        super();

        this.expressionString = expressionString;


        if (expressionString == null) {
            throw new IllegalArgumentException("expression String for ParsedOperand must not be null");
        }

    }

    public String getExpressionString() {
        return expressionString;
    }


}
