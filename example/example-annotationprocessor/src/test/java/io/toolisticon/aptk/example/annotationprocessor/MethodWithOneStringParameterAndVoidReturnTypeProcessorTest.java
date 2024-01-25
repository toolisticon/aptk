package io.toolisticon.aptk.example.annotationprocessor;

import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import org.junit.Before;
import org.junit.Test;


public class MethodWithOneStringParameterAndVoidReturnTypeProcessorTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    private CompileTestBuilderApi.CompilationTestBuilder compilationTestBuilder = CompileTestBuilder.compilationTest()
            .addProcessors(MethodHasStringParameterAndVoidReturnTypeCheckAnnotationProcessor.class);

    @Test
    public void testValidUsage() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/ValidUsageTest.java")
                .compilationShouldSucceed()
                .expectNoteMessageThatContains("Start processing")
                .executeTest();
    }

    @Test
    public void testInvalidUsage_nonVoidReturnType() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonVoidReturnType.java")
                .compilationShouldFail()
                .expectErrorMessageThatContains(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())
                .executeTest();
    }

    @Test
    public void testInvalidUsage_nonStringParameter() {
        compilationTestBuilder.addSources("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java")
                .compilationShouldFail()
                .expectErrorMessage().thatContains(CoreMatcherValidationMessages.BY_PARAMETER_TYPE.getCode())
                .expectErrorMessageThatContains(CoreMatcherValidationMessages.BY_PARAMETER_TYPE.getCode())
                .executeTest();
        
    }


}
