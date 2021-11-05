package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static String forwardedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

    @CustomCodeMethod(TestAnnotation.class)
    public static void forwardedMethodWithNoReturnValue(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
    }

}
