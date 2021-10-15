package io.toolisticon.aptk.annotationwrapper.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDefaultsAnnotation {

    String withDefault() default "default";

    String withoutDefault() default "notDefault";

}
