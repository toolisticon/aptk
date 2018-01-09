package io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest;

import io.toolisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

import javax.tools.JavaFileObject;

/**
 * Configuration of the an annotation processor test.
 */
public class AnnotationProcessorIntegrationTestConfiguration extends AnnotationProcessorCommonTestConfiguration {

    private final String source;


    public AnnotationProcessorIntegrationTestConfiguration(
            String source,
            Boolean compilingShouldSucceed,
            JavaFileObject[] expectedGeneratedFileObjects,
            TestValidator... testcases) {

        super(compilingShouldSucceed, expectedGeneratedFileObjects, testcases);
        this.source = source;

    }


    public String getSource() {
        return source;
    }

}
