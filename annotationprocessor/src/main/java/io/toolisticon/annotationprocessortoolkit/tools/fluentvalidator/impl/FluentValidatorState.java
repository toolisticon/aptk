package io.toolisticon.annotationprocessortoolkit.tools.fluentvalidator.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the state of the validation.
 */
public class FluentValidatorState {

    private boolean validationResult = true;
    private List<FluentValidatorMessage> validatorMessages = new ArrayList<>();

    public FluentValidatorState() {

    }

    /**
     * Adds a message.
     *
     * @param validatorMessage the validator message
     */
    public void addMessage(FluentValidatorMessage validatorMessage) {
        this.validatorMessages.add(validatorMessage);
    }

    /**
     * Gets the validation result.
     *
     * @return true if validation was successful, otherwise false.
     */
    public boolean getValidationResult() {
        return validationResult;
    }

    /**
     * Set the result to failed.
     */
    public void setAsFailedValidation() {
        validationResult = false;
    }

    /**
     * Issues all messages.
     */
    public void issueMessages() {

        for (FluentValidatorMessage message : validatorMessages) {
            message.issueMessage();
        }

    }

}
