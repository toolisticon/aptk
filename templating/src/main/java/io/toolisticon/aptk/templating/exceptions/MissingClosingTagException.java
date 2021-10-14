package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception that is thrown if a control block seems not to be properly ended.
 * <p/>
 */
public class MissingClosingTagException extends RuntimeException {

    public MissingClosingTagException(String message) {
        super(message);
    }

    public MissingClosingTagException(String message, Throwable e) {
        super(message, e);
    }

}
