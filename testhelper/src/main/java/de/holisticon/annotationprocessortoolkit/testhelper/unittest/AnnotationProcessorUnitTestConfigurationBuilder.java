package de.holisticon.annotationprocessortoolkit.testhelper.unittest;


import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;

public class AnnotationProcessorUnitTestConfigurationBuilder {


    private AnnotationProcessorUnitTestConfigurationBuilder() {
    }

    public BaseConfigurationBuilder start() {
        return new BaseConfigurationBuilder();
    }

    public class BaseConfigurationBuilder {

        protected Boolean shouldCompileSuccessfully;
        protected TestMessageValidator testMessageValidator;
        protected AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor;

        public MessageEvaluation addMessageValidator() {
            return new MessageEvaluation(this);
        }

        public BaseConfigurationBuilder setProcessor(AbstractAnnotationProcessorUnitTest.AbstractTestAnnotationProcessorClass processor) {
            this.processor = processor;
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

        public AnnotationProcessorUnitTestConfiguration build() {
            if (testMessageValidator == null) {
                return new AnnotationProcessorUnitTestConfiguration(processor, shouldCompileSuccessfully);
            } else {
                return new AnnotationProcessorUnitTestConfiguration(processor, shouldCompileSuccessfully,
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

        AnnotationProcessorUnitTestConfigurationBuilder testBuilder = new AnnotationProcessorUnitTestConfigurationBuilder();

        return testBuilder.start();

    }


}
