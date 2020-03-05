package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.compiletesting.CompileTestBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test for {@link ImplementsSpecificInterfaceCheckAnnotationProcessor}.
 */

public class ImplementsSpecificInterfaceCheckAnnotationProcessorTest {


    private CompileTestBuilder.CompilationTestBuilder compilationTestBuilder = CompileTestBuilder.compilationTest()
            .addProcessors(ImplementsSpecificInterfaceCheckAnnotationProcessor.class);

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void testValidUsage_implements() {
        compilationTestBuilder.addSources("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/ValidUsageTest.java")
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void testValidUsage_extends() {
        compilationTestBuilder.addSources("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/ValidUsageTestExtendsCase.java")
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void testInvalidUsage_nonStringParameter() {
        compilationTestBuilder.addSources("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/InvalidUsageTest.java")
                .compilationShouldFail()
                .expectErrorMessagesThatContain(CoreMatcherValidationMessages.IS_ASSIGNABLE_TO.getCode())
                .executeTest();
    }


}