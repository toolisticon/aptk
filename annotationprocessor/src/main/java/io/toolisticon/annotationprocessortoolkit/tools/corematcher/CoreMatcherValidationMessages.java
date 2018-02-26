package io.toolisticon.annotationprocessortoolkit.tools.corematcher;

/**
 * Core Matcher failing validation default messages
 */
public enum CoreMatcherValidationMessages implements ValidationMessage{

    BY_NAME("CM_BY_NAME", "Elements name must ${0} match ${1} of the following names: ${2}"),
    BY_NAME_REGEX("CM_BY_NAME_REGEX", "Elements name must ${0} match ${1} of the following regular expressions ${2}"),
    BY_ANNOTATION("CM_BY_ANNOTATION", "Element must ${0} be annotated with ${1} of the following annotations: ${2}"),
    BY_ELEMENT_KIND("CM_BY_ELEMENT_KIND", "Element must ${0} have ${1} of the following element kinds: ${2}"),
    BY_GENERIC_TYPE("CM_BY_GENERIC_TYPE", "Element must ${0} be ${1} of the following generic types: ${2}"),
    BY_MODIFIER("CM_BY_MODIFIER", "Element must ${0} have ${1} of the following modifiers: ${2}"),
    BY_PARAMETER_TYPE("CM_BY_PARAMETER_TYPE", "Element must ${0} have  ${1} of the following parameter type sets: ${2}"),
    BY_PARAMETER_TYPE_FQN("CM_BY_PARAMETER_TYPE_FQN", "Element must ${0} have  ${1} of the following parameter type sets: ${2}"),
    BY_RAW_TYPE("CM_BY_RAW_TYPE", "Element must ${0} have ${1} of the following raw types: ${2}"),
    IS_ASSIGNABLE_TO("CM_IS_ASSIGNABLE_TO", "Element must ${0} be assignable to ${1} of the following types: ${2}"),
    HAS_VOID_RETURN_TYPE("CM_HAS_VOID_RETURN_TYPE", "Element must ${0} have void return type"),
    IS_EXECUTABLE_ELEMENT("CM_IS_EXECTUABLE_ELEMENT", "Element must ${0} be a ExecutableElement"),
    IS_TYPE_ELEMENT("CM_IS_TYPE_ELEMENT", "Element must ${0} be a TypeElement"),
    IS_VARIABLE_ELEMENT("CM_IS_VARIABLE_ELEMENT", "Element must ${0} be a VariableElement"),
    IS_PACKAGE_ELEMENT("CM_IS_PACKAGE_ELEMENT", "Element must ${0} be a PackageElement"),

    IS_CLASS("CM_IS_CLASS", "Element must ${0} represent a Class"),
    IS_ENUM("CM_IS_ENUM", "Element must ${0} represent an enum"),
    IS_INTERFACE("CM_IS_PACKAGE_ELEMENT", "Element must ${0} represent an interface"),
    IS_METHOD("CM_IS_METHOD", "Element must ${0} represent a method"),
    IS_CONSTRUCTOR("CM_IS_CONSTRUCTOR", "Element must ${0} represent a constructor"),
    IS_ANNOTATION_TYPE("CM_IS_ANNOTATION_TYPE", "Element must ${0} represent an annotation type"),
    IS_FIELD("CM_IS_PACKAGE_ELEMENT", "Element must ${0} represent a field"),
    IS_PARAMETER("CM_IS_PACKAGE_ELEMENT", "Element must ${0} represent a parameter"),
    IS_PACKAGE("CM_IS_PACKAGE_ELEMENT", "Element must ${0} represent a package"),;


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
    private CoreMatcherValidationMessages(String code, String message) {
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
        CoreMatcherValidationMessages.printMessageCodes = printMessageCodes;
    }

}
