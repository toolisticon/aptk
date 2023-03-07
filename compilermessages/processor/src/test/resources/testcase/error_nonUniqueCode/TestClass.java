package io.toolisticon.aptk.compilermessage.processor.test;


import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;

@DeclareCompilerMessageCodePrefix("WTF")
public class TestClass {


    @DeclareCompilerMessage(enumValueName = "FIRST", code = "002", message = "TEST 1")
    @DeclareCompilerMessage(enumValueName = "SECOND", code = "002", message = "TEST 2")
    private void method1() {

    }

}