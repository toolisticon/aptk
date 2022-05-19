package io.toolisticon.aptk.annotationwrapper.api;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds custom interfaces to AnnotationWrapper
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomInterface {

    /**
     * Annotation type.
     * @return
     */
    Class<? extends Annotation> annotationToWrap();

    /**
     * Interfaces to apply
     * @return
     */
    Class<?>[] interfacesToApply();

}
