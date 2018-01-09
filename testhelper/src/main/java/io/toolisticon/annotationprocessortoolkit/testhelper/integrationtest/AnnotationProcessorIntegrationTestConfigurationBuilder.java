package io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest;


import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;

import javax.tools.JavaFileObject;

/**
 * Configuration builder class for  of integration tests.
 */
public final class AnnotationProcessorIntegrationTestConfigurationBuilder {


    private AnnotationProcessorIntegrationTestConfigurationBuilder() {
    }

    public BaseConfigurationBuilder start() {
        return new BaseConfigurationBuilder();
    }


    public static class BaseConfigurationBuilder {


        private Boolean shouldCompileSuccessfully;
        private TestMessageValidator testMessageValidator;
        private String sourceFileToCompile;
        private JavaFileObject[] expectedGeneratedJavaFileObjects;

        public MessageEvaluation addMessageValidator() {
            return new MessageEvaluation(this);
        }


        public BaseConfigurationBuilder setSourceFileToCompile(String srcFileToCompile) {
            sourceFileToCompile = srcFileToCompile;
            return this;
        }

        public BaseConfigurationBuilder compilationShouldFail() {
            shouldCompileSuccessfully = false;
            return this;
        }

        public BaseConfigurationBuilder compilationShouldSucceed() {
            shouldCompileSuccessfully = true;
            return this;
        }

        public BaseConfigurationBuilder resourceShouldMatch(JavaFileObject... expectedGeneratedJavaFileObjects) {
            this.expectedGeneratedJavaFileObjects = expectedGeneratedJavaFileObjects;
            return this;
        }

        public AnnotationProcessorIntegrationTestConfiguration build() {
            if (testMessageValidator == null) {
                return new AnnotationProcessorIntegrationTestConfiguration(
                        sourceFileToCompile,
                        shouldCompileSuccessfully,
                        expectedGeneratedJavaFileObjects);
            } else {
                return new AnnotationProcessorIntegrationTestConfiguration(
                        sourceFileToCompile,
                        shouldCompileSuccessfully,
                        expectedGeneratedJavaFileObjects,
                        testMessageValidator);
            }
        }
    }


    public static class MessageEvaluation {

        private final BaseConfigurationBuilder baseConfigurationBuilder;
        private String[] warningChecks;
        private String[] errorChecks;
        private String[] infoChecks;


        MessageEvaluation(BaseConfigurationBuilder baseConfigurationBuilder) {
            this.baseConfigurationBuilder = baseConfigurationBuilder;
        }

        public MessageEvaluation setWarningChecks(String... warningChecksToSet) {
            this.warningChecks = warningChecksToSet;
            return this;
        }

        public MessageEvaluation setErrorChecks(String... errorChecksToSet) {
            this.errorChecks = errorChecksToSet;
            return this;
        }

        public MessageEvaluation setInfoChecks(String... infoChecksToSet) {
            this.infoChecks = infoChecksToSet;
            return this;
        }

        public BaseConfigurationBuilder finishMessageValidator() {
            baseConfigurationBuilder.testMessageValidator = new TestMessageValidator(
                    errorChecks != null ? errorChecks : new String[0],
                    warningChecks != null ? warningChecks : new String[0],
                    infoChecks != null ? infoChecks : new String[0]
            );
            return baseConfigurationBuilder;
        }

    }


    public static BaseConfigurationBuilder createTestConfig() {

        AnnotationProcessorIntegrationTestConfigurationBuilder testBuilder = new AnnotationProcessorIntegrationTestConfigurationBuilder();

        return testBuilder.start();

    }


}
