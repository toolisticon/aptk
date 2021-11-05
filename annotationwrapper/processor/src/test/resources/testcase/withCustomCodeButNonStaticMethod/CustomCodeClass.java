package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.cute.TestAnnotation;

public class CustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public String shouldntWork(TestAnnotationWrapper testAnnotationWrapper, String arg1) {
        return "yes " + arg1;
    }

}