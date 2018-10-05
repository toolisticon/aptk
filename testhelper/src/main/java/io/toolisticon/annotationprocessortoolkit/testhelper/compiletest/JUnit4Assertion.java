package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import io.toolisticon.spiap.api.Service;
import org.junit.Assert;

@Service(value = AssertionSpi.class,priority = 0, description = "junit4 is default assertion")
public class JUnit4Assertion implements AssertionSpi {
    @Override
    public void fail(String message) {
        Assert.fail(message);
    }
}
