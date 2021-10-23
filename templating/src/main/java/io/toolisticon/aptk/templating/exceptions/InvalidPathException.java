package io.toolisticon.aptk.templating.exceptions;

/**
 * Exception that is thrown if a path seems to be invalid.
 * <p>
 * This may occur if a path token is applied on a primitive or array.
 */
public class InvalidPathException extends RuntimeException {

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable e) {
        super(message, e);
    }

}
