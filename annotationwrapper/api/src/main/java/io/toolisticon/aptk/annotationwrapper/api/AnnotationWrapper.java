package io.toolisticon.aptk.annotationwrapper.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to create a wrapper class for an annotation.
 * The wrapper
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnotationWrapper {
    /**
     * The annotations to create wrappers for.
     *
     * @return
     */
    Class<? extends Annotation>[] value();

    /**
     * Adds a custom interface to the wrapper.
     *
     * @return
     */
    CustomInterface[] customInterfaces() default {};

    /**
     * Bind custom code into the api.
     *
     * @return
     */
    Class[] bindCustomCode() default {};

    /**
     * Configures to generate wrappers for all embedded annotations as well.
     * Defaults to true.
     *
     * @return
     */
    boolean automaticallyWrapEmbeddedAnnotations() default true;

    /**
     * Configures to use public visibility for all wrappers.
     * Package private visibility will be used, if set to false
     * Defaults to false.
     *
     * @return
     */
    boolean usePublicVisibility() default false;
}
