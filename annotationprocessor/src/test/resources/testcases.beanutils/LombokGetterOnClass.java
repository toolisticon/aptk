package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.TestAnnotation;
import lombok.Getter;


@TestAnnotation
@Getter
public class LombokGetterOnClass {

    private String testField;


}

