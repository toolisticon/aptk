package io.toolisticon.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.TestAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassArrayAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.DefaultValueAnnotation;

/**
 * Test class for annotation processor tools.
 */
@TestAnnotation
@DefaultValueAnnotation(mandatoryValue = 10L)
public class AnnotationClassAttributeTestClass {

    @ClassAttributeAnnotation()
    public void test_classAttribute_empty() {

    };

    @ClassAttributeAnnotation(String.class)
    public void test_classAttribute_atDefaultValue() {

    };

    @ClassAttributeAnnotation(classAttribute = Long.class)
    public void test_classAttribute_atNamedAttribute() {

    };

    @ClassArrayAttributeAnnotation()
    public void test_classArrayAttribute_empty() {

    };

    @ClassArrayAttributeAnnotation({String.class, Double.class, Float.class})
    public void test_classArrayAttribute_atDefaultValue() {

    };

    @ClassArrayAttributeAnnotation(classArrayAttribute = {Long.class, Integer.class})
    public void test_classArrayAttribute_atNamedAttribute() {

    };

    @ClassArrayAttributeAnnotation(classArrayAttribute = {Long.class, Integer.class, AnnotationClassAttributeTestClass.class})
    public void test_classArrayAttribute_atNamedAttribute_withUncompiledClass() {

    };


}
