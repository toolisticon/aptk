package de.holisticon.annotationprocessortoolkit.testhelper.unittest;

import de.holisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

/**
 * Configuration class for unit tests.
 */
public class AnnotationProcessorUnitTestConfiguration extends AnnotationProcessorCommonTestConfiguration {

    private final AbstractUnitTestAnnotationProcessorClass processor;


    public AnnotationProcessorUnitTestConfiguration(
            AbstractUnitTestAnnotationProcessorClass processor,
            Boolean compilingShouldSucceed,
            TestValidator... testcases) {

        super(compilingShouldSucceed, testcases);
        this.processor = processor;

    }

    public AbstractUnitTestAnnotationProcessorClass getProcessor() {
        return processor;
    }


}
