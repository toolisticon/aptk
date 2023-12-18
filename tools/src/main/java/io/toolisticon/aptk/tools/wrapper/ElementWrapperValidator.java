package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ElementWrapperValidator<WRAPPER extends ElementWrapper<?>> {

    static class MessageConfig {
        Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
        ValidationMessage customMessage = new DummyValidationMessage(null, "Validation FAILED!");
        Object[] customMessageVars = null;
    }

    public ElementWrapperValidator(WRAPPER wrapper) {
        this.wrapper = wrapper;
    }

    private final WRAPPER wrapper;

    private final MessageConfig messageConfig = new MessageConfig();

    private final List<Predicate<WRAPPER>> checks = new ArrayList<>();

    public ElementWrapperValidatorInterface.FirstValidation<WRAPPER> start() {
        return new FirstValidationImpl();
    }

    private void addCustomMessage(ValidationMessage message, Object... vars) {
        messageConfig.customMessage = message;
        messageConfig.customMessageVars = vars;
    }

    private void addCheck(Predicate<WRAPPER> predicate) {
        checks.add(predicate);
    }

    static class DummyValidationMessage implements ValidationMessage {

        private final String code;
        private final String message;

        public DummyValidationMessage(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class FirstValidationImpl implements ElementWrapperValidatorInterface.FirstValidation<WRAPPER> {

        public ElementWrapperValidatorInterface.FirstValidationWithScope<WRAPPER> asInfo() {
            messageConfig.kind = Diagnostic.Kind.NOTE;
            return new FirstValidationWithScopeImpl();
        }

        public ElementWrapperValidatorInterface.FirstValidationWithScope<WRAPPER> asWarning() {
            messageConfig.kind = Diagnostic.Kind.WARNING;
            return new FirstValidationWithScopeImpl();
        }

        public ElementWrapperValidatorInterface.FirstValidationWithScope<WRAPPER> asMandatoryWarning() {
            messageConfig.kind = Diagnostic.Kind.MANDATORY_WARNING;
            return new FirstValidationWithScopeImpl();
        }

        public ElementWrapperValidatorInterface.FirstValidationWithScope<WRAPPER> asError() {
            messageConfig.kind = Diagnostic.Kind.ERROR;
            return new FirstValidationWithScopeImpl();
        }


        public ElementWrapperValidatorInterface.FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(ValidationMessage message, Object... vars) {
            addCustomMessage(message, vars);
            return new FirstValidationWithScopeAndCustomMessageImpl();
        }

        public ElementWrapperValidatorInterface.FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(String message, Object... vars) {
            addCustomMessage(new DummyValidationMessage(null, message), vars);
            return new FirstValidationWithScopeAndCustomMessageImpl();
        }

        public ElementWrapperValidatorInterface.FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate) {
            addCheck(predicate);
            return new FollowUpValidationOrEndValidationImpl();
        }

    }

    class FirstValidationWithScopeImpl implements ElementWrapperValidatorInterface.FirstValidationWithScope<WRAPPER> {

        public ElementWrapperValidatorInterface.FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(ValidationMessage message, Object... vars) {
            addCustomMessage(message, vars);
            return new FirstValidationWithScopeAndCustomMessageImpl();
        }

        public ElementWrapperValidatorInterface.FirstValidationWithScopeAndCustomMessage<WRAPPER> withCustomMessage(String message, Object... vars) {
            addCustomMessage(new DummyValidationMessage(null, message), vars);
            return new FirstValidationWithScopeAndCustomMessageImpl();
        }

        public ElementWrapperValidatorInterface.FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate) {
            addCheck(predicate);
            return new FollowUpValidationOrEndValidationImpl();
        }

    }

    class FirstValidationWithScopeAndCustomMessageImpl implements ElementWrapperValidatorInterface.FirstValidationWithScopeAndCustomMessage<WRAPPER> {

        public ElementWrapperValidatorInterface.FollowUpValidationOrEndValidation<WRAPPER> check(Predicate<WRAPPER> predicate) {
            addCheck(predicate);
            return new FollowUpValidationOrEndValidationImpl();
        }

    }

    class FollowUpValidationOrEndValidationImpl implements ElementWrapperValidatorInterface.FollowUpValidationOrEndValidation<WRAPPER> {

        public ElementWrapperValidatorInterface.FollowUpValidationOrEndValidation<WRAPPER> and(Predicate<WRAPPER> predicate) {
            addCheck(predicate);
            return new FollowUpValidationOrEndValidationImpl();
        }

        public boolean validate() {
            boolean result = true;
            for (Predicate<WRAPPER> check : checks) {
                result = result & check.test(wrapper);
            }

            return result;
        }

        public boolean validateAndIssueMessages() {
            boolean result = validate();
            if (!result) {
                MessagerUtils.printMessage(wrapper.element, Diagnostic.Kind.ERROR, messageConfig.customMessage, messageConfig.customMessageVars);
            }
            return result;
        }
    }

    public static <WRAPPER extends ElementWrapper<? extends Element>> ElementWrapperValidatorInterface.FirstValidation<WRAPPER> startValidation(WRAPPER wrapper) {
        return new ElementWrapperValidator<>(wrapper).start();
    }

}
