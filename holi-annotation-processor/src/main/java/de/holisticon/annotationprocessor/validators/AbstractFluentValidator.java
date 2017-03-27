package de.holisticon.annotationprocessor.validators;

import javax.tools.Diagnostic;

/**
 * Abstract validator.
 * <p/>
 * Configuration methods like info or error don't produce immutable instances!
 */
public abstract class AbstractFluentValidator<T extends AbstractFluentValidator> {

    private Diagnostic.Kind messageLevel = Diagnostic.Kind.ERROR;

    AbstractFluentValidator(AbstractFluentValidator previousFluentValidator) {
        this.messageLevel = previousFluentValidator != null && previousFluentValidator.messageLevel != null ? previousFluentValidator.messageLevel : Diagnostic.Kind.ERROR;
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
        return messageLevel;
    }

    /**
     * Checks if error level is set.
     * Useful to determine if validation affects validation result.
     *
     * @return
     */
    protected boolean isErrorLevel() {
        return Diagnostic.Kind.ERROR.equals(messageLevel);
    }

}
