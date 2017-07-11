package de.holisticon.annotationprocessortoolkit.testhelper;

import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

/**
 * Common test configuration.
 */
public class AnnotationProcessorCommonTestConfiguration {

    private final Boolean compilingShouldSucceed;

    private final TestValidator[] testcases;

    public AnnotationProcessorCommonTestConfiguration(Boolean compilingShouldSucceed, TestValidator... testcases) {
        this.compilingShouldSucceed = compilingShouldSucceed;
        this.testcases = testcases;
    }

    public Boolean getCompilingShouldSucceed() {
        return compilingShouldSucceed;
    }

    public TestValidator[] getTestcases() {
        return testcases;
    }

}
