package io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator;

/**
 * Enum that declares all validator messages
 */
public enum ValidatorMesssageEnum {

    MODIFIER("VALIDATION_FAILED_MODIFIER", "Element modifiers should have ${0} : ${1}"),

    NAME("VALIDATION_FAILED_NAME", "Element name should match ${0}: ${1}"),

    NAME_REGEX("VALIDATION_FAILED_NAME_REGEX", "Element name should match ${0} regular expression: ${1}"),

    ANNOTATION("VALIDATION_FAILED_ANNOTATIONS", "Element should have ${0} annotations: ${1}"),

    ELEMENT_KIND("VALIDATION_FAILED_ELEMENT_KIND", "Element kind should be ${0} : ${1}"),

    PARAMETER_TYPES("VALIDATION_FAILED_PARAMETER_TYPES", "Element parameters types should be ${0} : ${1}"),

    FQN_PARAMETER_TYPES("VALIDATION_FAILED_FQN_PARAMETER_TYPES", "Element parameters types should be ${0} : ${1}"),

    RAW_TYPE("VALIDATION_FAILED_FQN_PARAMETER_TYPES", "Element raw type should be ${0} : ${1}"),

    GENERIC_TYPE("VALIDATION_FAILED_FQN_PARAMETER_TYPES", "Element generic type should be ${0} : ${1}");


    /**
     * Flag that defines if messages codes will be written as part of the message.
     */
    private static boolean printMessageCodes = false;

    /**
     * the message code.
     */
    private final String code;
    /**
     * the message text.
     */
    private final String message;

    /**
     * Constructor.
     *
     * @param code    the message code
     * @param message the message text
     */
    private ValidatorMesssageEnum(String code, String message) {
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
        return (printMessageCodes ? "[" + code + "] : " : "") + message;
    }


    /**
     * Allows toggling if message codes should be printed.
     *
     * @param printMessageCodes defines if message codes should be part of the message text
     */
    public static void setPrintMessageCodes(boolean printMessageCodes) {
        ValidatorMesssageEnum.printMessageCodes = printMessageCodes;
    }

    /**
     * Checks if message code should be printed as message prefix.
     *
     * @return
     */
    public static boolean shouldPrintMessageCode() {
        return ValidatorMesssageEnum.printMessageCodes;
    }

}
