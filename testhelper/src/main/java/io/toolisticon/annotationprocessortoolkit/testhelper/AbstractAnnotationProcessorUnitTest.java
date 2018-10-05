package io.toolisticon.annotationprocessortoolkit.testhelper;

import io.toolisticon.annotationprocessortoolkit.testhelper.compiletest.JavaFileObjectUtils;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

/**
 * Abstract base class for testing annotation processor internal stuff where tools offered by {@link ProcessEnvironment} are needed.
 */
public abstract class AbstractAnnotationProcessorUnitTest extends AbstractAnnotationProcessorTest<AnnotationProcessorUnitTestConfiguration> {


    public AbstractAnnotationProcessorUnitTest(AnnotationProcessorUnitTestConfiguration annotationProcessorTestConfiguration) {

        super(annotationProcessorTestConfiguration);

    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {

        return JavaFileObjectUtils.readFromResource(getAnnotationProcessorTestConfiguration().getCustomSourceFile() != null ?
                getAnnotationProcessorTestConfiguration().getCustomSourceFile() :
                "/AnnotationProcessorUnitTestClass.java");

    }

    @Override
    protected Processor getAnnotationProcessor() {
        return getAnnotationProcessorTestConfiguration().getProcessor();
    }
}
