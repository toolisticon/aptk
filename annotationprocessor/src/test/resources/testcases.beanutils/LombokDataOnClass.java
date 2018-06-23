package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Data;


@TestAnnotation
@Data
public class LombokDataOnClass {

    private String testField;


}

