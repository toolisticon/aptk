package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod
    private static String shouldntWork(TestAnnotationWrapper testAnnotationWrapper, String arg1) {
        return "yes " + arg1;
    }

}