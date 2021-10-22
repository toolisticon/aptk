package io.toolisticon.aptk.annotationwrapper.processor;

import io.toolisticon.aptk.tools.corematcher.ValidationMessage;

public enum AnnotationWrapperProcessorMessages implements ValidationMessage {

    ERROR_CANT_CREATE_WRAPPER("ANNOTATION_WRAPPER_ERROR_001", "Can't create wrapper class for Annotation ${0}"),
    ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE("ANNOTATION_WRAPPER_ERROR_001", "The first parameter of a custom code method for an annotation wrapper must be of the annotation wrapper type ${0}");

    /**
     * the message code.
     */
    private final String code;
    /**s
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
    AnnotationWrapperProcessorMessages(String code, String message) {
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


}