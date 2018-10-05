package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

public class AssertionSpiServiceLocator {

    public static AssertionSpi locate() {
        return new TestAssertion();
    }

}
