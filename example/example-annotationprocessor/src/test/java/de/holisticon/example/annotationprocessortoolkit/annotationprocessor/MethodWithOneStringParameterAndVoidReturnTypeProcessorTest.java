package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;

import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorProcessingTest;
import de.holisticon.annotationprocessortoolkit.testhelper.AnnotationProcessorTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.builder.AnnotationProcessorTestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class MethodWithOneStringParameterAndVoidReturnTypeProcessorTest extends AbstractAnnotationProcessorProcessingTest<MethodWithOneStringParameterAndVoidReturnTypeAnnotationProcessor> {


    public MethodWithOneStringParameterAndVoidReturnTypeProcessorTest(String description, AnnotationProcessorTestConfiguration annotationProcessorTestConfiguration) {
        super(annotationProcessorTestConfiguration);
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
                        AnnotationProcessorTestBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/ValidUsageTest.java")
                                .compilationShouldSucceed()
                                .build()
                },
                {
                        "Test invalid usage : non void return type",
                        AnnotationProcessorTestBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonVoidReturnType.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                    .setErrorChecks("Method must have void return type")
                                .finishMessageEvaluation()
                                .build()
                },
                {
                        "Test invalid usage : non String parameter",
                        AnnotationProcessorTestBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/methodWithOneStringParameterAndVoidReturn/InvalidUsageNonStringParameter.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                    .setErrorChecks("Method must have parameters of types [java.lang.String], but has parameters of types [java.lang.Object]")
                                .finishMessageEvaluation()
                                .build()
                },


        });

    }


    @Test
    public void testCorrectnessOfAdviceArgumentAnnotation() {
        super.test();
    }


}