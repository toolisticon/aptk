package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

/**
 * Exception thrown in case of an configuration error.
 */
public class InvalidTestConfigurationException extends RuntimeException {

    public InvalidTestConfigurationException(String message) {
        super(message);
    }

}
