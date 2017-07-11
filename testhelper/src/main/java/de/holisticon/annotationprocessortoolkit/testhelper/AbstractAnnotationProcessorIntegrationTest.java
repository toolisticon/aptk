package de.holisticon.annotationprocessortoolkit.testhelper;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

/**
 * Abstract test base class used to check if source files are processed correctly.
 */
public abstract class AbstractAnnotationProcessorIntegrationTest<T extends Processor>
        extends AbstractAnnotationProcessorTest<AnnotationProcessorIntegrationTestConfiguration> {



    protected AbstractAnnotationProcessorIntegrationTest(
            AnnotationProcessorIntegrationTestConfiguration annotationProcessorIntegrationTestConfiguration) {

        super(annotationProcessorIntegrationTestConfiguration);

    }

    @Override
    protected T getAnnotationProcessor() {
        return null;
    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource(getAnnotationProcessorTestConfiguration().getSource());
    }
}
