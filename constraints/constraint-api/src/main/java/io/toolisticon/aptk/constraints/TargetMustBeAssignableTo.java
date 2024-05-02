package io.toolisticon.aptk.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@On(On.Location.ANNOTATION_ATTRIBUTE)
@OnAnnotationAttributeOfType({OnAnnotationAttributeOfType.AttributeType.CLASS, OnAnnotationAttributeOfType.AttributeType.CLASS_ARRAY})
public @interface TargetMustBeAssignableTo {

    enum TargetElement {
        ANNOTATED_ELEMENT,
        PARENT_TYPE_ELEMENT,
        TOP_LEVEL_TYPE_ELEMENT
    }

    TargetElement targetElement() default TargetElement.ANNOTATED_ELEMENT;

}
