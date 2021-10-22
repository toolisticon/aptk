package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod
    public static String shouldWork(TestAnnotationWrapper testAnnotationWrapper, String arg1) {
        return "yes " + arg1;
    }

    @CustomCodeMethod
    public static void shouldWorkWithVoidReturnValue(TestAnnotationWrapper testAnnotationWrapper, String arg1) {

    }

}