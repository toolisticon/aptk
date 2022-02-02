package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception that is thrown if an if control block has multiple else cases.
 */
public class MultipleElseCasesException extends RuntimeException {

    public MultipleElseCasesException(String message) {
        super(message);
    }

    public MultipleElseCasesException(String message, Throwable e) {
        super(message, e);
    }

}
