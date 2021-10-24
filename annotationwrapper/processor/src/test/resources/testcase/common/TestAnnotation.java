package io.toolisticon.aptk.wrapper.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotation {

    char charAttribute() default 'X';

    String stringAttribute();

    int intAttribute();

    long longAttribute();

    float floatAttribute() default 0.0f;

    double doubleAttribute();

    boolean booleanAttribute() default false;

    Class<?> typeAttribute();

    TestEnum enumAttribute();

    EmbeddedAnnotation annotationAttribute();


    int[] intArrayAttribute() default{};
    long[] longArrayAttribute() default{};
    float[] floatArrayAttribute() default{};
    double[] doubleArrayAttribute() default{};
    boolean[] booleanArrayAttribute() default{};
    char[] charArrayAttribute() default {};

    String[] stringArrayAttribute() default {};

    TestEnum[] enumArrayAttribute();

    Class<?>[] typeArrayAttribute();

    EmbeddedAnnotation[] annotationArrayAttribute();

}