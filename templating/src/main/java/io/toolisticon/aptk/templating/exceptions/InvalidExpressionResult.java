package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception is thrown if the result of an expression has an unexpected type.
 */
public class InvalidExpressionResult extends RuntimeException {

    public InvalidExpressionResult(String message) {
        super(message);
    }

    public InvalidExpressionResult(String message, Throwable e) {
        super(message,e);
    }

}