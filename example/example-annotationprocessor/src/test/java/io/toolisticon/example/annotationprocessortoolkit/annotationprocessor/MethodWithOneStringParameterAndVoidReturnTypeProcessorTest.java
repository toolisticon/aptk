package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.compiletesting.CompileTestBuilder;
import org.junit.Before;
import org.junit.Test;


public class MethodWithOneStringParameterAndVoidReturnTypeProcessorTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    private CompileTestBuilder.CompilationTestBuilder compilationTestBuilder = CompileTestBuilder.compilationTest()
            .addProcessors(MethodHasStringParameterAndVoidReturnTypeCheckAnnotationProcessor.class);

    @Test
    public void testValidUsage() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/ValidUsageTest.java")
                .compilationShouldSucceed()
                .expectedNoteMessages("Start processing")
                .testCompilation();
    }

    @Test
    public void testInvalidUsage_nonVoidReturnType() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonVoidReturnType.java")
                .compilationShouldFail()
                .expectedErrorMessages(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())
                .testCompilation();
    }

    @Test
    public void testInvalidUsage_nonStringParameter() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java")
                .compilationShouldFail()
                .expectedErrorMessages(CoreMatcherValidationMessages.BY_PARAMETER_TYPE.getCode())
                .testCompilation();
    }


}