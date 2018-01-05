package de.holisticon.annotationprocessortoolkit.testhelper.validator;

/**
 * A test that checks for compiler messages.
 */
public class TestMessageValidator implements TestValidator {

    private final String[] errors;
    private final String[] warnings;
    private final String[] infos;

    public TestMessageValidator(String[] errors, String[] warnings, String[] infos) {
        this.errors = errors;
        this.warnings = warnings;
        this.infos = infos;
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

    public String[] getInfos() { return infos; }
}
