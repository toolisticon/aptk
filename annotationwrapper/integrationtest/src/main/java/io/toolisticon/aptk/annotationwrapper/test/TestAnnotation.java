package io.toolisticon.aptk.annotationwrapper.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotation {

    // single value attributes

    String stringAttribute();

    char charAttribute() default 'X';

    int intAttribute() default 0;

    long longAttribute();

    float floatAttribute() default 0.0f;

    double doubleAttribute();

    short shortAttribute() default 0;

    byte byteAttribute() default 0;

    boolean booleanAttribute() default false;

    Class<?> typeAttribute();

    TestEnum enumAttribute();

    EmbeddedAnnotation annotationAttribute();

    // Array based attributes

    String[] stringArrayAttribute() default {};

    char[] charArrayAttribute() default {};

    int[] intArrayAttribute() default {};

    long[] longArrayAttribute() default {};

    float[] floatArrayAttribute() default {};

    double[] doubleArrayAttribute() default {};

    boolean[] booleanArrayAttribute() default {};

    short[] shortArrayAttribute() default {};

    byte[] byteArrayAttribute() default {};

    TestEnum[] enumArrayAttribute();

    Class<?>[] typeArrayAttribute();

    EmbeddedAnnotation[] annotationArrayAttribute();

}