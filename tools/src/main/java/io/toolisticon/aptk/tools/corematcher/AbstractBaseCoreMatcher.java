package io.toolisticon.aptk.tools.corematcher;

/**
 * Abstract base core matcher class.
 */
public class AbstractBaseCoreMatcher {

    private final ValidationMessage defaultValidatorMessage;

    protected AbstractBaseCoreMatcher(ValidationMessage defaultValidatorMessage) {
        this.defaultValidatorMessage = defaultValidatorMessage;
    }

    protected ValidationMessage getDefaultValidatorMessage() {
        return this.defaultValidatorMessage;
    }



}
