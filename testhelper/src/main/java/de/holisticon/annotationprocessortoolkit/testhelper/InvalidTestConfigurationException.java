package de.holisticon.annotationprocessortoolkit.testhelper;

/**
 * Exception thrown in case of an configuration error.
 */
public class InvalidTestConfigurationException extends RuntimeException{


    public InvalidTestConfigurationException () {

    }


    public InvalidTestConfigurationException (String message) {
        super(message);
    }
}
