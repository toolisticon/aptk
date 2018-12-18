package io.toolisticon.annotationprocessortoolkit.testhelper.validator;

/**
 * A test that checks for compiler messages.
 */
public class TestMessageValidator implements TestValidator {

    private final String[] errors;
    private final String[] warnings;
    private final String[] notes;

    public TestMessageValidator(String[] errors, String[] warnings, String[] notes) {
        this.errors = errors;
        this.warnings = warnings;
        this.notes = notes;
    }

    @Override
    public TestValidatorType getAnnotationProcessorTestType() {
        return TestValidatorType.MESSAGE_VALIDATOR;
    }

    public String[] getErrors() {
        return errors;
    }

    public String[] getWarnings() {
        return warnings;
    }

    public String[] getNotes() {
        return notes;
    }
}
