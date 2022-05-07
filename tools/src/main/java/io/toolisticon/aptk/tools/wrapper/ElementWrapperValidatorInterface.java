package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import java.util.function.Predicate;

/**
 * Wrapper based Element validator
 */
public class ElementWrapperValidatorInterface {


    public interface FirstValidation<WRAPPER extends ElementWrapper<?>> {

        FirstValidationWithScope<WRAPPER> asInfo();

        FirstValidationWithScope<WRAPPER> asWarning();

        FirstValidationWithScope<WRAPPER> asMandatoryWarning();

        FirstValidationWithScope<WRAPPER> asError();

        FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(ValidationMessage message, Object... vars);

        FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(String message, Object... vars);

        FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate);

    }

    public interface FirstValidationWithScope<WRAPPER extends ElementWrapper<?>> {

        FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(ValidationMessage message, Object... vars);

        FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(String message, Object... vars);


        FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate);

    }

    public interface FirstValidationWithScopeAndCustomMessage<WRAPPER extends ElementWrapper<?>> {

        FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate);

    }

    public interface FollowUpValidationOrEndValidation<WRAPPER extends ElementWrapper<?>> {

        FollowUpValidationOrEndValidation<WRAPPER> and(Predicate<WRAPPER> predicate);

        /**
         * Validates all checks and returns a result.
         * Only checks with error level will cause the validation to fail.
         *
         * @return the validation result
         */
        boolean validate();

        /**
         * Validates all checks, issue compiler messages and returns the result.
         *
         * @return the validation result
         */
        boolean validateAndIssueMessages();
    }


}
