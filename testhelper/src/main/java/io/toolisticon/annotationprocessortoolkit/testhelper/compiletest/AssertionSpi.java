package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import io.toolisticon.spiap.api.Spi;

/**
 * Spi to set a failing assertion during compile testing.
 */
@Spi
public interface AssertionSpi {

    /**
     * Triggers a failing assertion.
     *
     * @param message
     */
    void fail(String message);

}
