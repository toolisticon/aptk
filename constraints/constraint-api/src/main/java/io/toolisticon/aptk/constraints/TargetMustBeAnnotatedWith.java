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
@On(On.Location.ANNOTATION)
@Repeatable(TargetMustBeAnnotatedWiths.class)
public @interface TargetMustBeAnnotatedWith {

    enum TargetElement {
        ANNOTATED_ELEMENT,
        PARENT_TYPE_ELEMENT,
        TOP_LEVEL_TYPE_ELEMENT
    }


    Class<? extends Annotation> value();

    TargetMustBeAnnotatedWith.TargetElement target() default TargetMustBeAnnotatedWith.TargetElement.ANNOTATED_ELEMENT;

}
