package io.toolisticon.annotationprocessortoolkit.tools.validator;

/**
 * Base interface for validators.
 */
public class AbstractBaseValidator {

    private final String message;

    public AbstractBaseValidator(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }


}
