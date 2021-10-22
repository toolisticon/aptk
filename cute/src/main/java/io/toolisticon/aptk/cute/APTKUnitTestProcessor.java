package io.toolisticon.aptk.cute;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.UnitTest;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Convenient unit test processor for unit testing code based on APTK with toolisticon's cute framework.
 */
public abstract class APTKUnitTestProcessor<ELEMENT_TYPE extends Element> implements UnitTest<ELEMENT_TYPE> {

    /**
     * The original unit test processor method. Contains logic to initialize the ToolingProvider.
     * Will be called by cute framework. Propagates call to aptkUnitTest method after initializations.
     *
     * @param processingEnvironment the processing environment
     * @param element  the passed in element
     */
    @Override
    public final void unitTest(ProcessingEnvironment processingEnvironment, ELEMENT_TYPE element) {

        try {
            // do initializations
            ToolingProvider.setTooling(processingEnvironment);

            // propagate to unit test implementation
            this.aptkUnitTest(processingEnvironment, element);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    /**
     * The unit test method.
     *
     * @param processingEnvironment the processingEnvironment
     * @param typeElement           the element the underlying annotation processor is applied on
     */
    public abstract void aptkUnitTest(ProcessingEnvironment processingEnvironment, ELEMENT_TYPE typeElement);
}
