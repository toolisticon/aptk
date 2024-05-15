package io.toolisticon.aptk.constraints;


import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@WithTargetOfKind(WithTargetOfKind.TargetKind.ANNOTATION)
@Repeatable(WithTargetElementAnnotatedWithRepeatable.class)
public @interface WithTargetElementAnnotatedWith {

    Class<? extends Annotation> value();

   TargetElement targetElement() default TargetElement.ANNOTATED_ELEMENT;

}
