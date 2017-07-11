package de.holisticon.annotationprocessortoolkit.testhelper.integrationtest;


import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;

public class AnnotationProcessorIntegrationTestConfigurationBuilder {


    private AnnotationProcessorIntegrationTestConfigurationBuilder() {
    }

    public BaseConfigurationBuilder start() {
        return new BaseConfigurationBuilder();
    }

    public class BaseConfigurationBuilder {

        protected Boolean shouldCompileSuccessfully;
        protected TestMessageValidator testMessageValidator;
        protected String sourceFileToCompile;

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

        public AnnotationProcessorIntegrationTestConfiguration build() {
            if (testMessageValidator == null) {
                return new AnnotationProcessorIntegrationTestConfiguration(sourceFileToCompile, shouldCompileSuccessfully);
            } else {
                return new AnnotationProcessorIntegrationTestConfiguration(sourceFileToCompile, shouldCompileSuccessfully,
                        testMessageValidator);
            }
        }
    }




    public class MessageEvaluation {

        private final BaseConfigurationBuilder baseConfigurationBuilder;
        private String[] warnings;
        private String[] errors;


        MessageEvaluation(BaseConfigurationBuilder baseConfigurationBuilder) {
            this.baseConfigurationBuilder = baseConfigurationBuilder;
        }

        public MessageEvaluation setWarningChecks(String... warnings) {

            this.warnings = warnings;
            return this;

        }

        public MessageEvaluation setErrorChecks(String... errors) {
            this.errors = errors;
            return this;
        }

        public BaseConfigurationBuilder finishMessageEvaluation() {
            baseConfigurationBuilder.testMessageValidator = new TestMessageValidator(
                    errors != null ? errors : new String[0],
                    warnings != null ? warnings : new String[0]
            );
            return baseConfigurationBuilder;
        }

    }


    public static BaseConfigurationBuilder createTestConfig() {

        AnnotationProcessorIntegrationTestConfigurationBuilder testBuilder = new AnnotationProcessorIntegrationTestConfigurationBuilder();

        return testBuilder.start();

    }

    public static BaseConfigurationBuilder createUnitTestConfig() {

        AnnotationProcessorIntegrationTestConfigurationBuilder testBuilder = new AnnotationProcessorIntegrationTestConfigurationBuilder();

        return testBuilder.start();

    }


}
