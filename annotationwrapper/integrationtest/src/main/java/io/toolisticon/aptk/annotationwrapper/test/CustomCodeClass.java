package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod
    public static String forwardedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

    @CustomCodeMethod
    public static void forwardedMethodWithNoReturnValue(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
    }

}
