package io.toolisticon.aptk.tools.annotationvalueutilstestclasses;

import io.toolisticon.aptk.tools.AnnotationValueUtils;

import javax.tools.StandardLocation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for a {@link AnnotationValueUtils}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnnotationValueTestAnnotation {

    String stringValue() default "";

    char charValue() default 'X';

    long longValue() default 0L;

    int intValue() default 0;

    //short shortValue() default 0;

    //byte byteValue() default 0;

    float floatValue() default 0.0f;

    double doubleValue() default 0.0;

    boolean booleanValue() default true;

    StandardLocation enumValue() default StandardLocation.SOURCE_OUTPUT;

    Deprecated annotationValue();

    Class<?> classValue();

    // --------------------------------------------------------
    // ARRAY VALUES
    // --------------------------------------------------------

    String[] stringArrayValue() default {};

    char[] charArrayValue() default {};

    long[] longArrayValue() default {};

    int[] intArrayValue() default {};

    //short[] shortArrayValue() default {};

    //byte[] byteArrayValue() default {};

    float[] floatArrayValue() default {};

    double[] doubleArrayValue() default {};

    boolean[] booleanArrayValue() default {};

    StandardLocation[] enumArrayValue() default {};

    Deprecated[] annotationArrayValue() default {};

    Class<?>[] classArrayValue() default {};


}
