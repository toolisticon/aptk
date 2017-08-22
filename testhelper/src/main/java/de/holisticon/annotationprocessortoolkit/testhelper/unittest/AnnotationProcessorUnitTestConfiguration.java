package de.holisticon.annotationprocessortoolkit.testhelper.unittest;

import de.holisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

import javax.tools.JavaFileObject;

/**
 * Configuration class for unit tests.
 */
public class AnnotationProcessorUnitTestConfiguration extends AnnotationProcessorCommonTestConfiguration {

    private final AbstractUnitTestAnnotationProcessorClass processor;


    public AnnotationProcessorUnitTestConfiguration(
            AbstractUnitTestAnnotationProcessorClass processor,
            Boolean compilingShouldSucceed,
            JavaFileObject[] expectedGeneratedJavaFileObjects,
            TestValidator... testcases) {

        super(compilingShouldSucceed, expectedGeneratedJavaFileObjects, testcases);
        this.processor = processor;

    }

    public AbstractUnitTestAnnotationProcessorClass getProcessor() {
        return processor;
    }


}
