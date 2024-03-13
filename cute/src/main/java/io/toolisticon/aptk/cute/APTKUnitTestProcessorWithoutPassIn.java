package io.toolisticon.aptk.cute;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.UnitTestWithoutPassIn;

import javax.annotation.processing.ProcessingEnvironment;

public abstract class APTKUnitTestProcessorWithoutPassIn implements UnitTestWithoutPassIn {

    /**
     * The original unit test processor method. Contains logic to initialize the ToolingProvider.
     * Will be called by cute framework. Propagates call to aptkUnitTest method after initializations.
     *
     * @param processingEnvironment the processing environment
     */
    @Override
    public final void unitTest(ProcessingEnvironment processingEnvironment) {

        try {
            // do initializations
            ToolingProvider.setTooling(processingEnvironment);

            // propagate to unit test implementation
            this.aptkUnitTest(processingEnvironment);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    /**
     * The unit test method.
     *
     * @param processingEnvironment the processingEnvironment
     */
    public abstract void aptkUnitTest(ProcessingEnvironment processingEnvironment);
}