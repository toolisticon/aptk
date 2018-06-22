package io.toolisticon.annotationprocessortoolkit.testhelper.unittest;

import io.toolisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

import javax.tools.JavaFileObject;

/**
 * Configuration class for unit tests.
 */
public class AnnotationProcessorUnitTestConfiguration extends AnnotationProcessorCommonTestConfiguration {

    private final AbstractUnitTestAnnotationProcessorClass processor;
    private final String customSourceFile;

    public AnnotationProcessorUnitTestConfiguration(
            String customSourceFile,
            AbstractUnitTestAnnotationProcessorClass processor,
            Boolean compilingShouldSucceed,
            JavaFileObject[] expectedGeneratedJavaFileObjects,
            TestValidator... testcases) {

        super(compilingShouldSucceed, expectedGeneratedJavaFileObjects, testcases);
        this.customSourceFile = customSourceFile;
        this.processor = processor;

    }

    public AbstractUnitTestAnnotationProcessorClass getProcessor() {
        return processor;
    }

    public String getCustomSourceFile() {
        return customSourceFile;
    }


}
