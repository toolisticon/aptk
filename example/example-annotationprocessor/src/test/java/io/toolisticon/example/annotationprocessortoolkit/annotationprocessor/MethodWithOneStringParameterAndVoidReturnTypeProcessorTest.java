package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatcherValidationMessages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class MethodWithOneStringParameterAndVoidReturnTypeProcessorTest extends AbstractAnnotationProcessorIntegrationTest<MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor> {


    public MethodWithOneStringParameterAndVoidReturnTypeProcessorTest(String description, AnnotationProcessorIntegrationTestConfiguration annotationProcessorIntegrationTestConfiguration) {
        super(annotationProcessorIntegrationTestConfiguration);
    }

    @Override
    protected MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor getAnnotationProcessor() {
        return new MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor();
    }

    @Before
    public void init() {
        CoreMatcherValidationMessages.setPrintMessageCodes(true);
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/ValidUsageTest.java")
                                .addMessageValidator()
                                    .setInfoChecks("Start processing")
                                .finishMessageValidator()
                                .compilationShouldSucceed()
                                .build()
                },
                {
                        "Test invalid usage : non void return type",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonVoidReturnType.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                    .setErrorChecks(CoreMatcherValidationMessages.HAS_VOID_RETURN_TYPE.getCode())
                                .finishMessageValidator()
                                .build()
                },
                {
                        "Test invalid usage : non String parameter",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                    .setErrorChecks(CoreMatcherValidationMessages.BY_PARAMETER_TYPE.getCode())
                                .finishMessageValidator()
                                .build()
                },


        });

    }


    @Test
    public void test() {
        super.test();
    }


}