package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import io.toolisticon.spiap.api.Service;

@Service(value = AssertionSpi.class, priority = -10, description = "Allows testing of assertions")
public class TestAssertion implements AssertionSpi {

    private static boolean failTriggered = false;

    @Override
    public void fail(String message) {
        failTriggered = true;
    }


    public static void reset() {
        failTriggered = false;
    }

    public static boolean hasFailBeenTriggered() {
        return failTriggered;
    }
}
