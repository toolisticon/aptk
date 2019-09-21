package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.TestAnnotation;
import lombok.Setter;


@TestAnnotation
@Setter
public class LombokSetterOnClass {

    private String testField;


}

