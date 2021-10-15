package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.generators.FileObjectUtilsTestAnnotationProcessor;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.Before;
import org.junit.Test;

import javax.tools.StandardLocation;

public class FilerUtilsTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Test
    public void testValidUsage() {

        unitTestBuilder.useProcessor(new FileObjectUtilsTestAnnotationProcessor())
                .useSource(JavaFileObjectUtils.readFromResource("/testcases/generators/FilerUtilsTestClass.java"))
                .compilationShouldSucceed()
                .expectThatFileObjectExists(StandardLocation.CLASS_OUTPUT, "", "testOutput.txt", JavaFileObjectUtils.readFromResource("/testcases/generators/expectedResult.txt"))
                .executeTest();

    }


}
