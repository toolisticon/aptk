package io.toolisticon.aptk.compilermessage.processor;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.matchers.CoreGeneratedFileObjectMatchers;
import org.junit.Before;
import org.junit.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

/**
 * Tests for {@link CompilerMessageProcessor}.
 */
public class CompilerMessageProcessorTest {



    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void test_wrapperCreation() {
        CompileTestBuilder.compilationTest()
                .addProcessors(CompilerMessageProcessor.class)
                .addSources("testcase/happyPath_OnClass/TestClass.java", "testcase/happyPath_OnClass/RemoteTestClass.java")
                .expectThatJavaFileObjectExists(
                        StandardLocation.SOURCE_OUTPUT,
                        "io.toolisticon.aptk.compilermessage.processor.test.TestClassCompilerMessages",
                        JavaFileObject.Kind.SOURCE,
                        CoreGeneratedFileObjectMatchers.createContainsSubstringsMatcher(
                                "ON_CLASS(\"WTF_001\", \"TEST 1\"),",
                                "ON_METHOD(\"WTF_002\", \"TEST 2\"),",
                                "ON_NESTED_CLASS(\"WTF_003\", \"TEST 3\"),",
                                "ON_NESTED_CLASS_METHOD(\"WTF_004\", \"TEST 4\"),",
                                "ON_REMOTE_CLASS(\"WTF_005\", \"TEST 5\"),",
                                "ON_CLASS_WO_CODE(\"WTF_ON_CLASS_WO_CODE\", \"TEST 6\"),"
                        ))
                .compilationShouldSucceed()
                .executeTest();
    }



    @Test
    public void test_error_nonUniqueCode() {
        CompileTestBuilder.compilationTest()
                .addProcessors(CompilerMessageProcessor.class)
                .addSources("testcase/error_nonUniqueCode/TestClass.java")
                .expectErrorMessage().thatContains(CompilerMessageProcessorMessages.ERROR_CODE_MUST_BE_UNIQUE.getCode())
                .compilationShouldFail()
                .executeTest();
    }


}
