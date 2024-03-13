package io.toolisticon.aptk.annotationwrapper.test.annotationonpackage;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.annotationwrapper.test.ExampleTestAnnotation;

public class CustomCodeClass {

    @CustomCodeMethod(ExampleTestAnnotation.class)
    public static String forwardedMethod(ExampleTestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

    @CustomCodeMethod(ExampleTestAnnotation.class)
    public static void forwardedMethodWithNoReturnValue(ExampleTestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
    }

}
