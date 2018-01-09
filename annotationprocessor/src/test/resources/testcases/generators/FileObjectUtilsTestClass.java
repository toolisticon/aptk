package io.toolisticon.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.generators.FileObjectUtilsTestAnnotation;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;

/**
 * Test class for annotation processor tools.
 */
@TestAnnotation
public class FileObjectUtilsTestClass {


    @FileObjectUtilsTestAnnotation
    public String testMethod1() {
        return null;
    }

    @FileObjectUtilsTestAnnotation
    public String testMethod2() {
        return null;
    }


}
