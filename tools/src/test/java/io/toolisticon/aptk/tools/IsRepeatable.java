package io.toolisticon.aptk.tools;


import java.lang.annotation.Repeatable;

@Repeatable(IsRepeatableWrapper.class)
public @interface IsRepeatable {
    String value() default "";
}
