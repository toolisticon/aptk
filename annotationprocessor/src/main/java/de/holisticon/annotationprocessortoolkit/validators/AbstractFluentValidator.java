package de.holisticon.annotationprocessortoolkit.validators;

import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.tools.MessagerUtils;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Abstract validator.
 * <p/>
 * Configuration methods like info or error don't produce immutable instances!
 */
public abstract class AbstractFluentValidator<T extends AbstractFluentValidator, E extends Element> {

    private final FrameworkToolWrapper frameworkToolWrapper;
    private final MessagerUtils messagerUtils;
    private final TypeUtils typeUtils;

    private final E element;

    private final boolean currentValidationResult;

    private Diagnostic.Kind messageLevel = Diagnostic.Kind.ERROR;

    //custom message
    private String customMessage;
    private Object[] customMessageParameter;
    private Diagnostic.Kind customMessageLevel;

    protected AbstractFluentValidator(FrameworkToolWrapper frameworkToolWrapper, E element) {

        // set default message level to ERROR
        this.messageLevel = Diagnostic.Kind.ERROR;

        // config validator
        this.frameworkToolWrapper = frameworkToolWrapper;
        this.messagerUtils = MessagerUtils.getMessagerUtils(frameworkToolWrapper);
        this.typeUtils = TypeUtils.getTypeUtils(frameworkToolWrapper);

        // element and current validation result
        this.element = element;
        this.currentValidationResult = true;
    }


    /**
     * Constructor used to create instance based on previous validation and a new result.
     *
     * @param previousFluentValidator the previous fluent validator instance
     * @param nextResult              the validation result to be used with validator
     */
    protected AbstractFluentValidator(AbstractFluentValidator<T, E> previousFluentValidator, boolean nextResult) {


        this.messageLevel = previousFluentValidator != null ? previousFluentValidator.messageLevel : Diagnostic.Kind.ERROR;

        // config validator
        this.frameworkToolWrapper = previousFluentValidator != null ? previousFluentValidator.frameworkToolWrapper : null;
        this.messagerUtils = previousFluentValidator != null ? previousFluentValidator.messagerUtils : null;
        this.typeUtils = previousFluentValidator != null ? previousFluentValidator.typeUtils : null;

        // element and current validation result
        this.element = previousFluentValidator != null ? previousFluentValidator.element : null;
        this.currentValidationResult = nextResult;

    }


    /**
     * Gets the element which is validated.
     *
     * @return the element under validation
     */
    protected E getElement() {
        return element;
    }

    /**
     * Gets the TypeUtils utils.
     *
     * @return the TypeUtils
     */
    protected TypeUtils getTypeUtils() {
        return typeUtils;
    }

    /**
     * Gets the FrameworkToolWrapper.
     *
     * @return the FrameworkToolWrapper
     */
    protected FrameworkToolWrapper getFrameworkToolWrapper() {
        return frameworkToolWrapper;
    }

    /**
     * Gets the MessagerUtils utils.
     *
     * @return the MessagerUtils
     */
    protected MessagerUtils getMessagerUtils() {
        return messagerUtils;
    }

    /**
     * Gets the current validation result.
     *
     * @return true if element under validation is valid, otherwise false
     */
    public boolean isCurrentValidationResult() {
        return currentValidationResult;
    }


    /**
     * Sets the log level for all following validations to Diagnostic.Kind.NOTE.
     * <p/>
     * "Informative message from the tool."
     *
     * @return this instance
     */

    public T info() {
        messageLevel = Diagnostic.Kind.NOTE;

        return (T) this;
    }

    /**
     * Sets the log level for all following validations to Diagnostic.Kind.NOTE.
     * <p/>
     * "Problem which prevents the tool's normal completion."
     *
     * @return this instance
     */
    public T error() {
        messageLevel = Diagnostic.Kind.ERROR;

        return (T) this;
    }

    /**
     * Sets the log level for all following validations to Diagnostic.Kind.NOTE.
     * <p/>
     * "Problem which does not usually prevent the tool from completing normally."
     *
     * @return this instance
     */
    public T warning() {
        messageLevel = Diagnostic.Kind.WARNING;

        return (T) this;
    }

    /**
     * Sets the log level for all following validations to Diagnostic.Kind.NOTE.
     * <p/>
     * "Problem similar to a warning, but is mandated by the tool's
     * specification.  For example, the Java&trade; Language
     * Specification mandates warnings on certain
     * unchecked operations and the use of deprecated methods."
     *
     * @return this instance
     */
    public T mandatoryWarning() {
        messageLevel = Diagnostic.Kind.MANDATORY_WARNING;

        return (T) this;
    }

    /**
     * Sets the log level for all following validations to Diagnostic.Kind.NOTE.
     * <p/>
     * "Diagnostic which does not fit within the other kinds."
     *
     * @return this instance
     */
    public T other() {
        messageLevel = Diagnostic.Kind.OTHER;

        return (T) this;
    }

    /**
     * Gets the currently used message level.
     *
     * @return the currently used message level
     */
    protected Diagnostic.Kind getMessageLevel() {
        return customMessageLevel != null ? customMessageLevel : messageLevel;
    }

    /**
     * Checks if error level is set.
     * Useful to determine if validation affects validation result.
     *
     * @return
     */
    protected boolean isErrorLevel() {
        return Diagnostic.Kind.ERROR.equals(getMessageLevel());
    }

    /**
     * Sets a custom message with parameters.
     * It's possible to use placeholders for message parameters in the custom message string.
     * Placeholders have the following format '${&lt;index&gt;}' like '${1}' for second parameter.
     * The index is starting with 0.
     *
     * @param customMessageParam          the custom message string
     * @param customMessageParameterParam the custom message parameters
     */
    public T setCustomMessage(String customMessageParam, Object... customMessageParameterParam) {
        setCustomMessage(this.messageLevel, customMessageParam, customMessageParameterParam);

        return (T) this;
    }

    /**
     * Sets a custom message with parameters with passe message level.
     * It's possible to use placeholders for message parameters in the custom message string.
     * Placeholders have the following format '${&lt;index&gt;}' like '${1}' for second parameter.
     * The index is starting with 0.
     * Keep in mind that the passed message level only affects the next validation.
     * If you want to change the message level for all following validation, choose dedicated message level setter methods instead.
     *
     * @param messageLevelParam           the message level to use with for the next validation
     * @param customMessageParam          the custom message string
     * @param customMessageParameterParam the custom message parameters
     */
    public T setCustomMessage(Diagnostic.Kind messageLevelParam, String customMessageParam, Object... customMessageParameterParam) {
        this.customMessageLevel = messageLevelParam;
        this.customMessage = customMessageParam;
        this.customMessageParameter = customMessageParameterParam;

        return (T) this;
    }

    protected String getCustomOrDefaultMessage(String defaultMessage, Object... defaultMessageParameters) {

        if (customMessage == null) {
            // use default message
            return MessagerUtils.createMessage(defaultMessage, defaultMessageParameters);
        } else {
            // use custom message
            return MessagerUtils.createMessage(customMessage, customMessageParameter);
        }

    }

    /**
     * Gets the validation result.
     *
     * @return true if the element was proved valid during all validations, otherwise false
     */
    public boolean getValidationResult() {
        return this.currentValidationResult;
    }


    protected abstract T createNextFluentValidator(boolean nextResult);


}
