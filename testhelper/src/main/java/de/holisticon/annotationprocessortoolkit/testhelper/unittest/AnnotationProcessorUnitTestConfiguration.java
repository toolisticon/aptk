package de.holisticon.annotationprocessortoolkit.testhelper.unittest;

import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

/**
 * Configuration class for unit tests.
 */
public class AnnotationProcessorUnitTestConfiguration extends AnnotationProcessorCommonTestConfiguration {

    private final AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor;


    public AnnotationProcessorUnitTestConfiguration(AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor, Boolean compilingShouldSucceed, TestValidator... testcases) {
        super(compilingShouldSucceed, testcases);

        this.processor = processor;

    }

    public AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass getProcessor() {
        return processor;
    }


}
