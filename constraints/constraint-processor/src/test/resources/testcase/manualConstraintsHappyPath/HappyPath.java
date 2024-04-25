package io.toolisticon.aptk.tests;


import io.toolisticon.aptk.constraints.On;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@On(On.Location.ANNOTATION)
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HappyPath {

}