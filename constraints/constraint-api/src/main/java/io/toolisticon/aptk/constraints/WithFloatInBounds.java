package io.toolisticon.aptk.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithTargetOfKind(WithTargetOfKind.TargetKind.ANNOTATION_ATTRIBUTE)
@WithAnnotationAttributeTargetOfType({WithAnnotationAttributeTargetOfType.AttributeType.FLOAT, WithAnnotationAttributeTargetOfType.AttributeType.FLOAT_ARRAY})
public @interface WithFloatInBounds {

    float lowerBound() default Float.MIN_VALUE;

    boolean inclusiveLowerBound() default true;

    float upperBound() default Float.MAX_VALUE;

    boolean inclusiveUpperBound() default true;

}
