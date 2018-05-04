package io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest;

import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidatorType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Unit test for {@link AnnotationProcessorIntegrationTestConfigurationBuilder}.
 */
public class AnnotationProcessorIntegrationTestConfigurationBuilderTest {

    @Test
    public void testBuildingOfConfiguration_noInput_justBuild() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig().build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setShouldSucceed() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .compilationShouldSucceed()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setShouldNotSucceed() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .compilationShouldFail()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(false));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setSource() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .setSourceFileToCompile("XXX")
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getSource(), Matchers.is("XXX"));


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_noWarningsAndErrorsAndInfos() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .addMessageValidator()
                .finishMessageValidator()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.arrayWithSize(1));
        MatcherAssert.assertThat(configuration.getTestcases()[0].getAnnotationProcessorTestType(), Matchers.is(TestValidatorType.MESSAGE_VALIDATOR));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getErrors(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getWarnings(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getInfos(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_withErrors() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .addMessageValidator()
                .setErrorChecks("A", "B", "C")
                .finishMessageValidator()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.arrayWithSize(1));
        MatcherAssert.assertThat(configuration.getTestcases()[0].getAnnotationProcessorTestType(), Matchers.is(TestValidatorType.MESSAGE_VALIDATOR));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getErrors(), Matchers.arrayWithSize(3));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getErrors(), Matchers.arrayContainingInAnyOrder("A", "B", "C"));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getWarnings(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getInfos(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_withWarnings() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .addMessageValidator()
                .setWarningChecks("A", "B", "C")
                .finishMessageValidator()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.arrayWithSize(1));
        MatcherAssert.assertThat(configuration.getTestcases()[0].getAnnotationProcessorTestType(), Matchers.is(TestValidatorType.MESSAGE_VALIDATOR));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getErrors(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getWarnings(), Matchers.arrayWithSize(3));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getWarnings(), Matchers.arrayContainingInAnyOrder("A", "B", "C"));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getInfos(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_withInfos() {

        AnnotationProcessorIntegrationTestConfiguration configuration = AnnotationProcessorIntegrationTestConfigurationBuilder
                .createTestConfig()
                .addMessageValidator()
                .setInfoChecks("A", "B", "C")
                .finishMessageValidator()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.arrayWithSize(1));
        MatcherAssert.assertThat(configuration.getTestcases()[0].getAnnotationProcessorTestType(), Matchers.is(TestValidatorType.MESSAGE_VALIDATOR));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getErrors(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getWarnings(), Matchers.arrayWithSize(0));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getInfos(), Matchers.arrayWithSize(3));
        MatcherAssert.assertThat(((TestMessageValidator) configuration.getTestcases()[0]).getInfos(), Matchers.arrayContainingInAnyOrder("A", "B", "C"));
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }


}
