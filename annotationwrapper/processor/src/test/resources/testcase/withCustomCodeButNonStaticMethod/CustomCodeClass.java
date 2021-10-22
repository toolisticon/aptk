package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod
    public String shouldntWork(TestAnnotationWrapper testAnnotationWrapper, String arg1) {
        return "yes " + arg1;
    }

}