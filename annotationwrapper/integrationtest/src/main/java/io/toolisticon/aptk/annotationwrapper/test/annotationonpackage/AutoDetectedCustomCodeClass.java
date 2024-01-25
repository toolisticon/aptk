package io.toolisticon.aptk.annotationwrapper.test.annotationonpackage;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.annotationwrapper.test.ExampleTestAnnotation;

public class AutoDetectedCustomCodeClass {

    @CustomCodeMethod(ExampleTestAnnotation.class)
    public static String autoDetectedMethod(ExampleTestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

}
