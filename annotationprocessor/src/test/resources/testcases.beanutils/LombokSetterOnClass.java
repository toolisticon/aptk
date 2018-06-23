package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Setter;


@TestAnnotation
@Setter
public class LombokSetterOnClass {

    private String testField;


}

