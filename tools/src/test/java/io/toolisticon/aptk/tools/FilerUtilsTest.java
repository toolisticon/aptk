package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.generators.FileObjectUtilsTestAnnotationProcessor;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.junit.Before;
import org.junit.Test;

import javax.tools.StandardLocation;

public class FilerUtilsTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilderApi.CompilationTestBuilder compilationTestBuilder = CompileTestBuilder
            .compilationTest()
            .addSources(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Test
    public void testValidUsage() {

        compilationTestBuilder.addProcessors(FileObjectUtilsTestAnnotationProcessor.class)
                .addSources(JavaFileObjectUtils.readFromResource("/testcases/generators/FilerUtilsTestClass.java"))
                .compilationShouldSucceed()
                .expectThatFileObjectExists(StandardLocation.CLASS_OUTPUT, "", "testOutput.txt", JavaFileObjectUtils.readFromResource("/testcases/generators/expectedResult.txt"))
                .executeTest();

    }


}
