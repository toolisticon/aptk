package io.toolisticon.aptk.compilermessage.processor;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

public enum CompilerMessageProcessorMessages implements ValidationMessage {

    ERROR_CODE_MUST_BE_UNIQUE("COMPILER_MESSAGE_ERROR_001", "The message code '${0}' must be unique."),
    ERROR_CANT_CREATE_MESSAGE_ENUM("COMPILER_MESSAGE_ERROR_002", "Can't create compiler message enum ${0}."),
    ERROR_CODE_ENUM_VALUE_NAME_MUST_VALID("COMPILER_MESSAGE_ERROR_003", "The enum value name '${0}' is no adequate enum value name."),;

    /**
     * the message code.
     */
    private final String code;
    /**
     * s
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
    CompilerMessageProcessorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code of the message.
     *
     * @return the message code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the message text.
     *
     * @return the message text
     */
    public String getMessage() {
        return message;
    }


    public void writeErrorMessage(Element element, Object... args) {
        MessagerUtils.error(element, this, args);
    }

    public void writeErrorMessage(Element element, AnnotationMirror annotationMirror, Object... args) {
        MessagerUtils.error(element, annotationMirror, this, args);
    }

    public void writeErrorMessage(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, Object... args) {
        MessagerUtils.error(element, annotationMirror, annotationValue, this, args);
    }
}
