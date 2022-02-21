package io.toolisticon.aptk.annotationwrapper.test.annotationontype;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;
import io.toolisticon.aptk.annotationwrapper.test.TestAnnotation;

public class AutoDetectedCustomCodeClass {

    @CustomCodeMethod(TestAnnotation.class)
    public static String autoDetectedMethod(TestAnnotationWrapper testAnnotationWrapper, String firstArg) {
        testAnnotationWrapper.enumAttribute();
        return "it worked : " + firstArg;
    }

}
