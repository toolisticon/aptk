package io.toolisticon.aptk.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@WithTargetOfKind(WithTargetOfKind.TargetKind.ANNOTATION_ATTRIBUTE)
@WithAnnotationAttributeTargetOfType({WithAnnotationAttributeTargetOfType.AttributeType.CLASS, WithAnnotationAttributeTargetOfType.AttributeType.CLASS_ARRAY})
public @interface WithTargetElementAssignableTo {

    TargetElement targetElement() default TargetElement.ANNOTATED_ELEMENT;

}
