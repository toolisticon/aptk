package de.holisticon.annotationprocessortoolkit.testhelper.integrationtest;

import de.holisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorCommonTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;

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
