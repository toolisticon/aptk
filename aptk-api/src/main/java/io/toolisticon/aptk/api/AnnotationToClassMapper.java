package io.toolisticon.aptk.api;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * This annotation supports mapping of annotation attributes to constructor or static method invocations.
 * It also allows mapping of annotated method parameters if annotated annotation type can be placed on method parameters.
 *
 */

@Documented
@Retention(RUNTIME)
@Target(ANNOTATION_TYPE)
public @interface AnnotationToClassMapper {

	Class<?> mappedClass();
	
	/**
	 * the attribute names to map against method or constructor parameters.
	 * In case if an annotated annotation can be placed on Method Parameters, an  empty String will trigger the usage of the corresponding parameter name.
	 * Names enclosed in {} will be handled as wildcards (local variable references) which cannot be validated at compile time. So be careful if you use them.
	 * @return
	 */
	String[] mappedAttributeNames();
	
}
