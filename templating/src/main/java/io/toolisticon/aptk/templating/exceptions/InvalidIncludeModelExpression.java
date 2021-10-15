package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception is thrown if a statement to setup the model in include command is incorrect.
 */
public class InvalidIncludeModelExpression extends RuntimeException{

    public InvalidIncludeModelExpression(String message) {
        super(message);
    }

    public InvalidIncludeModelExpression(String message, Throwable e) {
        super(message,e);
    }

}
