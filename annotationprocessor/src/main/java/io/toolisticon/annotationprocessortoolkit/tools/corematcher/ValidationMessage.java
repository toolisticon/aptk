package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

/**
 * Interface to access message and message code.
 */
public interface ValidationMessage {

    /**
     * Gets the code of the message.
     *
     * @return the message code
     */
    String getCode();

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    String getMessage();


}
