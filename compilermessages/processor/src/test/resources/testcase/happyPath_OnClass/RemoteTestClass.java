package io.toolisticon.aptk.compilermessage.processor.test;


import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessage;
import io.toolisticon.aptk.compilermessage.api.DeclareCompilerMessageCodePrefix;

@DeclareCompilerMessage(processorClass = io.toolisticon.aptk.compilermessage.processor.test.TestClass.class, enumValueName = "ON_REMOTE_CLASS", code = "005", message = "TEST 5")
public class RemoteTestClass {

}