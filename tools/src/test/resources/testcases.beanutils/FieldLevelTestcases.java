package io.toolisticon.aptk.test;


import io.toolisticon.cute.TestAnnotation;


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

