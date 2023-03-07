package io.toolisticon.aptk.compilermessage.test;

import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;

@DeclareCompilerMessageCodePrefix("WTF")
@DeclareCompilerMessage(enumValueName = "ON_CLASS", code = "002",message = "ON CLASS Test ${0}")
public class OnClassAndNestedClass {

    @DeclareCompilerMessage(enumValueName = "ON_CLASS_METHOD", code = "003",message = "ON CLASS METHOD Test ${0}")
    public void anotherMethod() {

    }

    @DeclareCompilerMessage(enumValueName = "ON_NESTED_CLASS", code = "004",message = "ON NESTED CLASS Test ${0}")
    static class nestedClass {


        @DeclareCompilerMessage(enumValueName = "ON_NESTED_CLASS_METHOD", code = "005",message = "ON NESTED CLASS METHOD Test ${0}")
        public void anotherNestedMethod() {

        }


    }


}
