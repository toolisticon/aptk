package io.toolisticon.aptk.wrapper.test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EmbeddedAnnotation {
    long value();
}
