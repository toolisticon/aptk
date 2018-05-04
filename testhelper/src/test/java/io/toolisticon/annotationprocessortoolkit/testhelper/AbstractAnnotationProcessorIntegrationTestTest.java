package io.toolisticon.annotationprocessortoolkit.testhelper;

import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.annotation.processing.Processor;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorIntegrationTestTest extends AbstractAnnotationProcessorIntegrationTest {

    private final AbstractUnitTestAnnotationProcessorClass processor;
    private final Class<Throwable> throwable;

    public AbstractAnnotationProcessorIntegrationTestTest(
            String description,
            AnnotationProcessorIntegrationTestConfiguration configuration,
            AbstractUnitTestAnnotationProcessorClass processor,
            Class<Throwable> throwable) {

        super(configuration);
        this.processor = processor;
        this.throwable = throwable;

    }

    @Override
    protected Processor getAnnotationProcessor() {
        return processor;
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test compilation that succeeds",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldSucceed()
                                .build(),

                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {

                            }
                        },
                        null
                },
                {
                        "Test compilation that succeeds with warning",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldSucceed()
                                .build(),

                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "MURKS", element);
                            }

                        },
                        null
                },
                {
                        "Test compilation that succeeds with warning - check warning message",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldSucceed()
                                .addMessageValidator()
                                .setWarningChecks("MURKS")
                                .finishMessageValidator()
                                .build(),

                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "MURKS", element);
                            }
                        },
                        null
                },
                {
                        "Test compilation that succeeds with warning - check note/info message",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldSucceed()
                                .addMessageValidator()
                                .setInfoChecks("MURKS")
                                .finishMessageValidator()
                                .build(),

                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MURKS", element);
                            }
                        },
                        null
                },
                {
                        "Test compilation that throws error",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldFail()
                                .build(),
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "MURKS", element);
                            }
                        },
                        null
                },
                {
                        "Test compilation that throws error - check error message",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                .setErrorChecks("MURKS")
                                .finishMessageValidator()
                                .build(),
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "MURKS", element);
                            }
                        },
                        null
                },
                {
                        "Test invalid test configuration: compilationShouldSucceed and error messages in MessageValidator",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldSucceed()
                                .addMessageValidator()
                                .setErrorChecks("MURKS")
                                .finishMessageValidator()
                                .build(),
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "MURKS", element);
                            }
                        },
                        InvalidTestConfigurationException.class
                },

                {
                        "Test failing compilation with and note, warning and error messages in MessageValidator",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("TestClass.java")
                                .compilationShouldFail()
                                .addMessageValidator()
                                .setErrorChecks("MURKS_ERROR")
                                .setWarningChecks("MURKS_WARNING")
                                .setInfoChecks("MURKS_NOTE")
                                .finishMessageValidator()
                                .build(),
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "MURKS_NOTE", element);
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "MURKS_WARNING", element);
                                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "MURKS_ERROR", element);
                            }
                        },
                        null
                },

                


        });

    }


    @Test
    public void test() {

        boolean exceptionThrown = false;

        try {
            super.test();
        } catch (Throwable e) {

            if (throwable != null) {

                MatcherAssert.assertThat(throwable, Matchers.equalTo((Class) e.getClass()));

                exceptionThrown = true;

            } else {
                throw new RuntimeException("Unexpected exception", e);
            }

        }

        if (throwable != null) {
            MatcherAssert.assertThat("Expected exception " + throwable.getSimpleName() + " , but it was not thrown", exceptionThrown);
        }
    }

}
