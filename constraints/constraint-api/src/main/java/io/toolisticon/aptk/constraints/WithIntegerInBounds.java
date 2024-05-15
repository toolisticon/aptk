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
@WithAnnotationAttributeTargetOfType({WithAnnotationAttributeTargetOfType.AttributeType.INTEGER, WithAnnotationAttributeTargetOfType.AttributeType.INTEGER_ARRAY})
public @interface WithIntegerInBounds {

    int lowerBound() default Integer.MIN_VALUE;

    boolean inclusiveLowerBound() default true;

    int upperBound() default Integer.MAX_VALUE;

    boolean inclusiveUpperBound() default true;

}
