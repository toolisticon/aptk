package io.toolisticon.annotationprocessortoolkit.testhelper;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;
import java.net.URL;

/**
 * Abstract base class for testing annotation processor internal stuff where tools offered by {@link ProcessEnvironment} are needed.
 */
public abstract class AbstractAnnotationProcessorUnitTest extends AbstractAnnotationProcessorTest<AnnotationProcessorUnitTestConfiguration> {


    public AbstractAnnotationProcessorUnitTest(AnnotationProcessorUnitTestConfiguration annotationProcessorTestConfiguration) {

        super(annotationProcessorTestConfiguration);

    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        URL url = AbstractAnnotationProcessorUnitTest.class.getClassLoader().getResource("AnnotationProcessorUnitTestClass.java");
        return url != null ? JavaFileObjects.forResource(url) : null;
    }

    @Override
    protected Processor getAnnotationProcessor() {
        return getAnnotationProcessorTestConfiguration().getProcessor();
    }
}
