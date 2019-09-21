package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.TestAnnotation;
import lombok.Data;


@TestAnnotation
@Data
public class LombokDataOnClass {

    private String testField;


}

