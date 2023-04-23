package io.toolisticon.aptk.compilermessage.processor.test;


import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;

@DeclareCompilerMessageCodePrefix("WTF")
@DeclareCompilerMessage(enumValueName = "ON_CLASS", code = "001", message = "TEST 1")
@DeclareCompilerMessage(enumValueName = "ON_CLASS_WO_CODE", message = "TEST 6")
public class TestClass {



    @DeclareCompilerMessage(enumValueName = "ON_METHOD", code = "002", message = "TEST 2")
    private void aMethod() {

    }

    @DeclareCompilerMessage(enumValueName = "ON_NESTED_CLASS", code = "003", message = "TEST 3")
    static class NestedClass {


        @DeclareCompilerMessage(enumValueName = "ON_NESTED_CLASS_METHOD", code = "004", message = "TEST 4")
        private void anotherMethod() {

        }

    }


}