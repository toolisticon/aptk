package io.toolisticon.annotationprocessor;

import io.toolisticon.aptk.tools.generators.FileObjectUtilsTestAnnotation;
import io.toolisticon.aptk.tools.TestAnnotation;

/**
 * Test class for annotation processor tools.
 */
@TestAnnotation
public class FilerUtilsTestClass {


    @FileObjectUtilsTestAnnotation
    public String testMethod1() {
        return null;
    }

    @FileObjectUtilsTestAnnotation
    public String testMethod2() {
        return null;
    }


}
