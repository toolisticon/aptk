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
@WithAnnotationAttributeTargetOfType({WithAnnotationAttributeTargetOfType.AttributeType.LONG, WithAnnotationAttributeTargetOfType.AttributeType.LONG_ARRAY})
public @interface WithLongInBounds {

    long lowerBound() default Long.MIN_VALUE;

    boolean inclusiveLowerBound() default true;

    long upperBound() default Long.MAX_VALUE;

    boolean inclusiveUpperBound() default true;

}
