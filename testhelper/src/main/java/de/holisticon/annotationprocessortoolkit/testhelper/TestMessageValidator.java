package de.holisticon.annotationprocessortoolkit.testhelper;

/**
 * A test that checks for compiler messages.
 */
public class TestMessageValidator implements TestValidator {

    private final String[] errors;
    private final String[] warnings;

    public TestMessageValidator(String[] errors, String[] warnings) {
        this.errors = errors;
        this.warnings = warnings;
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
}
