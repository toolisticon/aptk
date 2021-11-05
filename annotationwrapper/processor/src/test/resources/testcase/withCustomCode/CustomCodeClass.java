package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.cute.TestAnnotation;

public class CustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static String shouldWork(TestAnnotationWrapper testAnnotationWrapper, String arg1) {
        return "yes " + arg1;
    }

    @CustomCodeMethod(TestAnnotation.class)
    public static void shouldWorkWithVoidReturnValue(TestAnnotationWrapper testAnnotationWrapper, String arg1) {

    }

}