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

/**
 * Integration test for {@link ImplementsSpecificInterfaceCheckAnnotationProcessor}.
 */
@RunWith(Parameterized.class)
public class ImplementsSpecificInterfaceCheckAnnotationProcessorTest extends AbstractAnnotationProcessorIntegrationTest<ImplementsSpecificInterfaceCheckAnnotationProcessor> {

    public ImplementsSpecificInterfaceCheckAnnotationProcessorTest(String description, AnnotationProcessorIntegrationTestConfiguration annotationProcessorIntegrationTestConfiguration) {
        super(annotationProcessorIntegrationTestConfiguration);
    }

    @Override
    protected ImplementsSpecificInterfaceCheckAnnotationProcessor getAnnotationProcessor() {
        return new ImplementsSpecificInterfaceCheckAnnotationProcessor();
    }

    @Before
    public void init() {
        CoreMatcherValidationMessages.setPrintMessageCodes(true);
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage : implements",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/ValidUsageTest.java")
                                .compilationShouldSucceed()
                                .build()
                },
                {
                        "Test invalid usage : extends ",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/ValidUsageTestExtendsCase.java")
                                .compilationShouldSucceed()
                                .build()
                },
                {
                        "Test invalid usage : non String parameter",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("testcases/implementsSpecificInterfaceCheckAnnotationProcessor/InvalidUsageTest.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                .setErrorChecks(CoreMatcherValidationMessages.IS_ASSIGNABLE_TO.getCode())
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