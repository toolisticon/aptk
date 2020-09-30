package io.toolisticon.annotationprocessortoolkit.cute;

import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.cute.UnitTestForTestingAnnotationProcessors;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;

/**
 * Convenient unit test processor for testing annotation processors build with the APTK with toolisticon's compiletesting framework.
 *
 * @param <PROCESSOR> The processor under test. init method will be called and {@link ToolingProvider} will be set.
 */
public abstract class APTKUnitTestProcessorForTestingAnnotationProcessors<PROCESSOR extends Processor, ELEMENT_TYPE extends Element> implements UnitTestForTestingAnnotationProcessors<PROCESSOR, ELEMENT_TYPE> {


    /**
     * The original unit test processor method. Contains logic to initialize the ToolingProvider.
     * Will be called by compiletesting framework. Propagates call to aptkUnitTest method after initializations.
     *
     * @param processor             The processor under test
     * @param processingEnvironment the processing environment
     * @param typeElement           the default typeElement
     */
    @Override
    public final void unitTest(PROCESSOR processor, ProcessingEnvironment processingEnvironment, ELEMENT_TYPE typeElement) {

        try {
            // do initializations
            ToolingProvider.setTooling(processingEnvironment);

            // propagate to unit test implementation
            this.aptkUnitTest(processor, processingEnvironment, typeElement);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    /**
     * The unit test method.
     *
     * @param unit                  the initialized processor under test
     * @param processingEnvironment the processingEnvironment
     * @param typeElement           the element the underlying annotation processor is applied on
     */
    public abstract void aptkUnitTest(PROCESSOR unit, ProcessingEnvironment processingEnvironment, ELEMENT_TYPE typeElement);
}
