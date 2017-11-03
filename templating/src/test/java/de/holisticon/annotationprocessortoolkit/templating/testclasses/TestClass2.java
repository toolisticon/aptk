package de.holisticon.annotationprocessortoolkit.templating.testclasses;

/**
 * Used to test access paths.
 */
public class TestClass2 {
    private final TestClass1 testClass1 = new TestClass1();

    public TestClass1 getTestClass1() {
        return testClass1;
    }
}
