package de.holisticon.annotationprocessortoolkit.testhelper.validator;

/**
 * Interface that helps to detect the type of testcase.
 */
public interface TestValidator {

    /**
     * Gets the type of the annotation processor test.
     *
     * @return the type of the annotation processor test
     */
    TestValidatorType getAnnotationProcessorTestType();

}
