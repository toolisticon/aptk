package io.toolisticon.aptk.wrapper.test;

import io.toolisticon.aptk.annotationwrapper.api.CustomCodeMethod;

public class CustomCodeClass {

    @CustomCodeMethod
    public static String shouldntWork( String arg1) {
        return "yes " + arg1;
    }

}