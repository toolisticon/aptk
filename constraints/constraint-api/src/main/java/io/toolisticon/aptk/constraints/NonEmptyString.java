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
@On(On.Location.ANNOTATION_ATTRIBUTE)
@OnAnnotationAttributeOfType({OnAnnotationAttributeOfType.AttributeType.STRING, OnAnnotationAttributeOfType.AttributeType.STRING_ARRAY})
public @interface NonEmptyString {
}
