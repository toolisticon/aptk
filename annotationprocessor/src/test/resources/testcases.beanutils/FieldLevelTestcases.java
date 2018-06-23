package io.toolisticon.annotationprocessortoolkit.test;


import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import lombok.Getter;
import lombok.Setter;


@TestAnnotation
public class FieldLevelTestcases {

    public FieldLevelTestcases() {

    }

    private String fieldWithImplementedGetterAndSetters;

    public String getFieldWithImplementedGetterAndSetters() {
        return fieldWithImplementedGetterAndSetters;
    }

    public void setFieldWithImplementedGetterAndSetters(String fieldWithImplementedGetterAndSetters) {
        this.fieldWithImplementedGetterAndSetters = fieldWithImplementedGetterAndSetters;
    }

    // --------------------------------------------------------------

    @Setter
    private String fieldWithImplementedGetterAndSetterAnnotation;

    public String getFieldWithImplementedGetterAndSetterAnnotation() {
        return fieldWithImplementedGetterAndSetterAnnotation;
    }

    // --------------------------------------------------------------

    @Getter
    private String fieldWithImplementedSetterAndGetterAnnotation;

    public void setFieldWithImplementedSetterAndGetterAnnotation(String fieldWithImplementedSetterAndGetterAnnotation) {
        this.fieldWithImplementedSetterAndGetterAnnotation = fieldWithImplementedSetterAndGetterAnnotation;
    }

    // --------------------------------------------------------------

    @Getter
    private boolean booleanFieldWithGetterAnnotation;

    // --------------------------------------------------------------

    private String fieldWithoutGetterAndSetter;

    // --------------------------------------------------------------

    private String fieldWithoutGetter;

    public void setFieldWithoutGetter(String fieldWithoutGetter) {
        this.fieldWithoutGetter = fieldWithoutGetter;
    }

    // --------------------------------------------------------------

    private String fieldWithoutSetter;

    public String getFieldWithoutSetter() {
        return fieldWithoutSetter;
    }
}

