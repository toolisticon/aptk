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
@WithAnnotationAttributeTargetOfType({WithAnnotationAttributeTargetOfType.AttributeType.DOUBLE, WithAnnotationAttributeTargetOfType.AttributeType.DOUBLE_ARRAY})
public @interface WithDoubleInBounds {

    double lowerBound() default Double.MIN_VALUE;

    boolean inclusiveLowerBound() default true;

    double upperBound() default Double.MAX_VALUE;

    boolean inclusiveUpperBound() default true;

}
