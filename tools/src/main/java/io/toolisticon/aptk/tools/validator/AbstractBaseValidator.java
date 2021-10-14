package io.toolisticon.aptk.tools.validator;

import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

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
