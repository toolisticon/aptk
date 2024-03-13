package io.toolisticon.aptk.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnAnnotationAttributeOfType {

    enum AttributeType {
        FLOAT,
        DOUBLE,
        INTEGER,
        LONG,
        STRING,
        CLASS,
        ENUM,
        ANNOTATION,
        FLOAT_ARRAY,
        DOUBLE_ARRAY,
        INTEGER_ARRAY,
        LONG_ARRAY,
        STRING_ARRAY,
        CLASS_ARRAY,
        ENUM_ARRAY,
        ANNOTATION_ARRAY,
        /**
         * Any kind of single value.
         */
        SINGLE_VALUE,
        /**
         * Any kind of array value.
         */
        ARRAY
    }

    AttributeType[] value();

}
