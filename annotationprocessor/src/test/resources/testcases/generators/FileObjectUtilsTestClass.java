package de.holisticon.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation2;
import de.holisticon.annotationprocessortoolkit.generators.FileObjectUtilsTestAnnotation;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
