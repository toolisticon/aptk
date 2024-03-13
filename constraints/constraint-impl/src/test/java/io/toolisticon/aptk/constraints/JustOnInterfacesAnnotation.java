package io.toolisticon.aptk.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@On(On.Location.INTERFACE)
public @interface JustOnInterfacesAnnotation {

}
