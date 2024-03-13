package io.toolisticon.aptk.constraints;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Target annotation is kind of fuzzy in some cases about where annotations might be used.
 * <p>
 * E.g. this is the case for TYPE, which allows you to use annotations on Classes, Interfaces, Enums and Annotations.
 * Another example is METHOD, which allows you to use annotations on methods or annotation attribute declarations.
 * <p>
 */
@Documented
@Constraint
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface On {

    enum Location {
        PACKAGE,
        ANNOTATION_ATTRIBUTE,
        ANNOTATION,
        CLASS,
        INTERFACE,
        ENUM,
        METHOD,
        CONSTRUCTOR,
        PARAMETER,
        METHOD_PARAMETER,
        CONSTRUCTOR_PARAMETER,
        FIELD

    }

    Location[] value();

}
