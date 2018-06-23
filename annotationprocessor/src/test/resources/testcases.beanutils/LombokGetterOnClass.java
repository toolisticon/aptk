package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Getter;


@TestAnnotation
@Getter
public class LombokGetterOnClass {

    private String testField;


}

