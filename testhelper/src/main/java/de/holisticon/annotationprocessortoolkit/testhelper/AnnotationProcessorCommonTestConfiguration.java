package de.holisticon.annotationprocessortoolkit.testhelper;

import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

import javax.tools.JavaFileObject;

/**
 * Common test configuration.
 */
public class AnnotationProcessorCommonTestConfiguration {

    private final Boolean compilingShouldSucceed;

    private final JavaFileObject[] expectedGeneratedJavaFileObjects;

    private final TestValidator[] testcases;

    public AnnotationProcessorCommonTestConfiguration(Boolean compilingShouldSucceed, JavaFileObject[] expectedGeneratedJavaFileObjects, TestValidator... testcases) {
        this.compilingShouldSucceed = compilingShouldSucceed;
        this.expectedGeneratedJavaFileObjects = expectedGeneratedJavaFileObjects;
        this.testcases = testcases;
    }

    public Boolean getCompilingShouldSucceed() {
        return compilingShouldSucceed;
    }

    public JavaFileObject[] getExpectedGeneratedJavaFileObjects() {
        return expectedGeneratedJavaFileObjects;
    }


    public TestValidator[] getTestcases() {
        return testcases;
    }

}
