package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
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
                                    .setErrorChecks("Method must have void return type")
                                .finishMessageValidator()
                                .build()
                },
                {
                        "Test invalid usage : non String parameter",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                    .setErrorChecks("Method must have parameters of types [java.lang.String], but has parameters of types [java.lang.Object]")
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