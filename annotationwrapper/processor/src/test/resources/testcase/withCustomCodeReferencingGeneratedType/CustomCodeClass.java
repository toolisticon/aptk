package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.wrapper.test.TestAnnotation;

public class CustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static TestAnnotation2Wrapper shouldWork(TestAnnotationWrapper testAnnotationWrapper) {
        return null;
    }

    @CustomCodeMethod(TestAnnotation.class)
    public static io.toolisticon.aptk.wrapper.othertest.TestAnnotation3Wrapper shouldWorkAsWell(TestAnnotationWrapper testAnnotationWrapper) {
        return null;
    }

}