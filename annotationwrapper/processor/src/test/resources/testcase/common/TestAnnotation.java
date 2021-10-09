package io.toolisticon.annotationprocessortoolkit.wrapper.test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotation {

    String stringAttribute();

    long longAttribute();

    double doubleAttribute();

    Class<?> typeAttribute();

    TestEnum enumAttribute();

    EmbeddedAnnotation annotationAttribute();

    String[] stringArrayAttribute() default {};

    TestEnum[] enumArrayAttribute();

    Class<?>[] typeArrayAttribute();

    EmbeddedAnnotation[] annotationArrayAttribute();

}