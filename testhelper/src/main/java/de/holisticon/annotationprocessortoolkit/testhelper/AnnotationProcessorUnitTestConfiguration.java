package de.holisticon.annotationprocessortoolkit.testhelper;

/**
 * Configuration class for unit tests.
 */
public class AnnotationProcessorUnitTestConfiguration extends AnnotationProcessorTestConfiguration {

    private AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor;

    public AnnotationProcessorUnitTestConfiguration(String description, AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor, String source, Boolean compilingShouldSucceed, TestValidator... testcases) {
        super(source, compilingShouldSucceed, testcases);
    }

    public AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass getProcessor() {
        return processor;
    }
}
