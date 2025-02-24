package io.toolisticon.aptk.cute;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.UnitTestForTestingAnnotationProcessorsWithoutPassIn;

/**
 * Convenient unit test processor for testing annotation processors build with the APTK with toolisticon's cute framework.
 *
 * @param <PROCESSOR> The processor under test. init method will be called and {@link ToolingProvider} will be set.
 */
public abstract class APTKUnitTestProcessorForTestingAnnotationProcessorsWithouPassIn<PROCESSOR extends Processor> implements UnitTestForTestingAnnotationProcessorsWithoutPassIn<PROCESSOR> {


    /**
     * The original unit test processor method. Contains logic to initialize the ToolingProvider.
     * Will be called by cute framework. Propagates call to aptkUnitTest method after initializations.
     *
     * @param processor             The processor under test
     * @param processingEnvironment the processing environment
     */
    @Override
    public final void unitTest(PROCESSOR processor, ProcessingEnvironment processingEnvironment) {

        try {
            // do initializations
            ToolingProvider.setTooling(processingEnvironment);

            // propagate to unit test implementation
            this.aptkUnitTest(processor, processingEnvironment);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    /**
     * The unit test method.
     *
     * @param unit                  the initialized processor under test
     * @param processingEnvironment the processingEnvironment
     */
    public abstract void aptkUnitTest(PROCESSOR unit, ProcessingEnvironment processingEnvironment);
}
