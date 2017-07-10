package de.holisticon.annotationprocessortoolkit.testhelper;

import com.google.testing.compile.JavaFileObjects;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

/**
 * Abstract test base class used to check if source files are processed correctly.
 */
public abstract class AbstractAnnotationProcessorProcessingTest<T extends Processor> extends AbstractAnnotationProcessorTest {

    protected AbstractAnnotationProcessorProcessingTest(AnnotationProcessorTestConfiguration annotationProcessorTestConfiguration) {
        super(annotationProcessorTestConfiguration);
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
