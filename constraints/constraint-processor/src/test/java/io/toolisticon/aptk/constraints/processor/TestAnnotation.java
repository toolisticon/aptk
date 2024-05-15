package io.toolisticon.aptk.constraints.processor;

import io.toolisticon.aptk.constraints.WithTargetOfKind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@WithTargetOfKind(WithTargetOfKind.TargetKind.INTERFACE)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) public @interface TestAnnotation {
}
