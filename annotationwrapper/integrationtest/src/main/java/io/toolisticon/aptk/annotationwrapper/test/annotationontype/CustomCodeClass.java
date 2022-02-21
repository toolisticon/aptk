package io.toolisticon.aptk.annotationwrapper.test.annotationontype;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.annotationwrapper.test.TestAnnotation;

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
