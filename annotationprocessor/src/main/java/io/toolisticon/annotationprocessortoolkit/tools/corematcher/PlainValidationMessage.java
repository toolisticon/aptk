package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

/**
 * Plain validation message.
 * <p/>
 * Can be used to create ValidationMessage with message String and optional code.
 */
public class PlainValidationMessage implements ValidationMessage {

    private final String code;
    private final String message;


    private PlainValidationMessage(String code, String message) {
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

    /**
     * Convenience method to create validation message without code but a message
     *
     * @param message the message to use
     * @return a PlainValidationMessage instance
     */
    public static PlainValidationMessage create(String message) {
        return new PlainValidationMessage(null, message);
    }

    /**
     * Convenience method to create validation message with code and message
     *
     * @param code    the code to use
     * @param message the message to use
     * @return a PlainValidationMessage instance
     */
    public static PlainValidationMessage create(String code, String message) {
        return new PlainValidationMessage(code, message);
    }
}
