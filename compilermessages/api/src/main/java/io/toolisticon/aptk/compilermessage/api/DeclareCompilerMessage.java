package io.toolisticon.aptk.compilermessage.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to define a compiler message directly at the processor code or a method.
 * The annotation will be used to generate an enum that represents the available compiler messages.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DeclareCompilerMessages.class)
@Documented
public @interface DeclareCompilerMessage {

    /*+
     * The compiler messages code. Must be unique.
     * @return the code of the message
     */
    String code();


    /**
     * The name of the enum value.
     * Must be a valid enum name and must be unique within a compiler message enum.
     * @return the enum value name
     */
    String enumValueName();

    /**
     * The compiler message. Might contain zero based placeholders "${INDEX}".
     * @return the message
     */
    String message();

    /**
     * Binds the compiler message to a class.
     * If not set the compiler message will be generated in a file
     * @return
     */
    Class<?> processorClass() default Void.class;


}
