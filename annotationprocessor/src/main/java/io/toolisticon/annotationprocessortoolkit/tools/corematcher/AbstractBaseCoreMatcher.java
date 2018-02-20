package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

/**
 * Abstract base core matcher class.
 */
public class AbstractBaseCoreMatcher {

    private final CoreMatcherValidationMessages defaultValidatorMessage;

    protected AbstractBaseCoreMatcher(CoreMatcherValidationMessages defaultValidatorMessage) {
        this.defaultValidatorMessage = defaultValidatorMessage;
    }

    protected String getDefaultValidatorMessage() {
        return this.defaultValidatorMessage.getMessage();
    }

}
