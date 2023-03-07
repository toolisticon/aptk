package io.toolisticon.aptk.compilermessage.test;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;

public class ByAnnotationAttribute {

    @DeclareCompilerMessage(processorClass = OnClassAndNestedClass.class, enumValueName = "BY_ANNOTATION", code="001", message = "Test ${0}")
    public static void doSomething() {
        System.out.println(OnClassAndNestedClassCompilerMessages.BY_ANNOTATION.getCode());
    }

}
