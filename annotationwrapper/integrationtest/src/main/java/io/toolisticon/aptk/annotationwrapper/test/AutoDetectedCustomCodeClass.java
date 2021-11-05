package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class AutoDetectedCustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static String autoDetectedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

}
