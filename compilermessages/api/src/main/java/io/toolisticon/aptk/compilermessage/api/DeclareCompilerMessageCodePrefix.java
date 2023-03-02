package io.toolisticon.aptk.compilermessage.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define a message code prefix.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeclareCompilerMessageCodePrefix {

    /**
     * The prefix to be used in compiler message codes.
     * Defaults to class name if not explicitly set.
     * @return the message code prefix
     */
    String value() default "";

}
