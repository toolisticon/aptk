package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception that is thrown if an if control block has multiple else cases.
 */
public class InvalidElseIfException extends RuntimeException {

    public InvalidElseIfException(String message) {
        super(message);
    }

    public InvalidElseIfException(String message, Throwable e) {
        super(message, e);
    }

}
