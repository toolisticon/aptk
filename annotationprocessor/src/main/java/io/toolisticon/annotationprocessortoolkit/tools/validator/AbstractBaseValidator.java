package io.toolisticon.annotationprocessortoolkit.tools.validator;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;

/**
 * Base interface for validators.
 */
public class AbstractBaseValidator {

    private final ValidationMessage defaultMessage;


    public AbstractBaseValidator(ValidationMessage defaultMessage) {
        this.defaultMessage = defaultMessage;
    }


    public ValidationMessage getDefaultMessage() {
        return this.defaultMessage;
    }


}
