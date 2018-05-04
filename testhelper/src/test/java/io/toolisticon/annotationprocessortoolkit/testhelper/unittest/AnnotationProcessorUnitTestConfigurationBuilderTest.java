package io.toolisticon.annotationprocessortoolkit.testhelper.unittest;

import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidatorType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.tools.JavaFileObject;

/**
 * Unit test for {@link AnnotationProcessorUnitTestConfigurationBuilder}.
 */
public class AnnotationProcessorUnitTestConfigurationBuilderTest {

    @Test
    public void testBuildingOfConfiguration_noInput_justBuild() {

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig().build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setShouldSucceed() {

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder
                .createTestConfig()
                .compilationShouldSucceed()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setShouldNotSucceed() {

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder
                .createTestConfig()
                .compilationShouldFail()
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(false));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setSource() {

        AbstractUnitTestAnnotationProcessorClass processor = Mockito.mock(AbstractUnitTestAnnotationProcessorClass.class);

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder
                .createTestConfig()
                .setProcessor(processor)
                .build();

        MatcherAssert.assertThat(configuration, Matchers.notNullValue());
        MatcherAssert.assertThat(configuration.getCompilingShouldSucceed(), Matchers.is(true));
        MatcherAssert.assertThat(configuration.getTestcases(), Matchers.<TestValidator>emptyArray());
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.is(processor));


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_noWarningsAndErrors() {

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder
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
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.nullValue());


    }

    @Test
    public void testBuildingOfConfiguration_noInput_setMessageValidator_withErrors() {

        AnnotationProcessorUnitTestConfiguration configuration = AnnotationProcessorUnitTestConfigurationBuilder
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
        MatcherAssert.assertThat(configuration.getProcessor(), Matchers.nullValue());


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
        MatcherAssert.assertThat(configuration.getSource(), Matchers.nullValue());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullValuedCompilingSholdSucceedParameter() {
        new AnnotationProcessorUnitTestConfiguration(Mockito.mock(AbstractUnitTestAnnotationProcessorClass.class), null, new JavaFileObject[0]);
    }

}
