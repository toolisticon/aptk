package de.holisticon.annotationprocessortoolkit.testhelper;

/**
 * Configuration of the an annotation processor test.
 */
public class AnnotationProcessorTestConfiguration {

    private final String source;
    private final Boolean compilingShouldSucceed;

    private final TestValidator[] testcases;

    public AnnotationProcessorTestConfiguration( String source, Boolean compilingShouldSucceed, TestValidator... testcases) {
        this.source = source;
        this.compilingShouldSucceed = compilingShouldSucceed;
        this.testcases = testcases;
    }



    public String getSource() {
        return source;
    }


    public Boolean getCompilingShouldSucceed() {
        return compilingShouldSucceed;
    }

    public TestValidator[] getTestcases() {
        return testcases;
    }
}
