package de.holisticon.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.tools.annotationvalueutilstestclasses.AnnotationValueTestAnnotation;

import javax.tools.StandardLocation;

/**
 * Test class for annotation processor tools.
 */
@TestAnnotation
@AnnotationValueTestAnnotation(
        stringValue = "stringValue",
        charValue = 'C',
        longValue = 1L,
        intValue = 1,
        booleanValue = true,
        //shortValue = 1,
        //byteValue = 1,
        doubleValue = 1.0,
        floatValue = 1.0f,
        enumValue = StandardLocation.SOURCE_OUTPUT,
        annotationValue = @Deprecated,
        classValue = Long.class,

        stringArrayValue = {"1", "2"},
        charArrayValue = {'1', '2'},
        booleanArrayValue = {true, false},
        longArrayValue = {1L, 2L, 3L},
        intArrayValue = {1, 2, 3},
        //shortArrayValue = {1, 2, 3},
        //byteArrayValue = {1, 2, 3},
        doubleArrayValue = {1.0, 2.0, 3.0},
        floatArrayValue = {1.0f, 2.0f, 3.0f},
        enumArrayValue = {StandardLocation.SOURCE_OUTPUT, StandardLocation.CLASS_OUTPUT},
        annotationArrayValue = {@Deprecated},
        classArrayValue = {Long.class, Integer.class}


)
public class AnnotationValueUtilsTestClass {

}
