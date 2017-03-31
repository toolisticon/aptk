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

    protected final FrameworkToolWrapper frameworkToolWrapper;
    protected final MessagerUtils messagerUtils;
    protected final TypeUtils typeUtils;

    protected final E element;

    protected final boolean currentValidationResult;

    private Diagnostic.Kind messageLevel = Diagnostic.Kind.ERROR;

    //custom message
    private String customMessage;
    private Object[] customMessageParameter;
    private Diagnostic.Kind customMessageLevel;

    public AbstractFluentValidator(FrameworkToolWrapper frameworkToolWrapper, E element) {

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


    AbstractFluentValidator(AbstractFluentValidator<T, E> previousFluentValidator, boolean nextResult) {
        this.messageLevel = previousFluentValidator != null && previousFluentValidator.messageLevel != null ? previousFluentValidator.messageLevel : Diagnostic.Kind.ERROR;

        // config validator
        this.frameworkToolWrapper = previousFluentValidator.frameworkToolWrapper;
        this.messagerUtils = previousFluentValidator.messagerUtils;
        this.typeUtils = previousFluentValidator.typeUtils;

        // element and current validation result
        this.element = previousFluentValidator.element;
        this.currentValidationResult = nextResult;

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
     * @param customMessage          the custom message string
     * @param customMessageParameter the custom message parameters
     */
    public T setCustomMessage(String customMessage, Object... customMessageParameter) {
        setCustomMessage(this.messageLevel, customMessage, customMessageParameter);

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
     * @param messageLevel           the message level to use with for the next validation
     * @param customMessage          the custom message string
     * @param customMessageParameter the custom message parameters
     */
    public T setCustomMessage(Diagnostic.Kind messageLevel, String customMessage, Object... customMessageParameter) {
        this.messageLevel = messageLevel;
        this.customMessage = customMessage;
        this.customMessageParameter = customMessageParameter;

        return (T) this;
    }

    protected String getCustomOrDefaultMessage(String defaultMessage, Object... defaultMessageParameters) {

        if (customMessage == null) {
            // use default message
            return String.format(defaultMessage, defaultMessageParameters);
        } else {
            // use custom message
            String result = this.customMessage;

            if (this.customMessageParameter != null) {

                for (int i = 0; i < this.customMessageParameter.length; i++) {
                    result = result.replaceAll("\\$\\{" + i + "\\}", this.customMessageParameter[i] != null ? this.customMessageParameter[i].toString() : "null");
                }
            }

            return result;
        }

    }

    public boolean getValidationResult() {
        return this.currentValidationResult;
    }

    protected abstract T createNextFluentValidator(boolean nextResult);


}
