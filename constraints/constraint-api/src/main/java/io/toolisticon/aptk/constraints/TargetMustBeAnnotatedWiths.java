package io.toolisticon.aptk.constraints;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@On(On.Location.ANNOTATION)
public @interface TargetMustBeAnnotatedWiths {
    TargetMustBeAnnotatedWith[] value();
}
