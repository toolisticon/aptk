package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.ValidationMessage;

/**
 * Validator message used in tests.
 */
public class DummyValidatorMessage implements ValidationMessage {

    public final static DummyValidatorMessage DUMMY_MESSAGE = new DummyValidatorMessage("DUMMY", "DUMMY_CODE");


    private final String message;

    private final String code;

    public DummyValidatorMessage(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return "[" + code + "]: " + message;
    }

    public static DummyValidatorMessage create(String message, String code) {
        return new DummyValidatorMessage(message, code);
    }
}
