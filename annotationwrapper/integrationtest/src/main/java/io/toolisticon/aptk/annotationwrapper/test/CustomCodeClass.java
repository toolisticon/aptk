package io.toolisticon.aptk.annotationwrapper.test;

public class CustomCodeClass {

    public static String forwardedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

}
