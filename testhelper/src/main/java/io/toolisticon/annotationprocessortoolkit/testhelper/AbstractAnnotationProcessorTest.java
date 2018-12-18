package io.toolisticon.annotationprocessortoolkit.testhelper;

import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestMessageValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidator;
import io.toolisticon.annotationprocessortoolkit.testhelper.validator.TestValidatorType;
import io.toolisticon.compiletesting.impl.AnnotationProcessorWrapper;
import io.toolisticon.compiletesting.impl.CompileTest;
import io.toolisticon.compiletesting.impl.CompileTestConfiguration;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;


/**
 * Abstract base class which allows parameterized tests.
 */

public abstract class AbstractAnnotationProcessorTest<T extends AnnotationProcessorCommonTestConfiguration> {


    private T annotationProcessorCommonTestConfiguration;

    public AbstractAnnotationProcessorTest(T annotationProcessorCommonTestConfiguration) {
        this.annotationProcessorCommonTestConfiguration = annotationProcessorCommonTestConfiguration;
    }


    /**
     * Gets the annotation processor to used for testing.
     *
     * @return the annotation processor to use for testing
     */
    protected abstract Processor getAnnotationProcessor();

    /**
     * Gets the {@link JavaFileObject} to be compiled.
     *
     * @return the code to be compiled
     */
    protected abstract JavaFileObject getSourceFileForCompilation();


    private Processor getWrappedProcessor() {
        return AnnotationProcessorWrapper.wrapProcessor(getAnnotationProcessor());
    }


    protected void test() {

        TestMessageValidator messageValidationTest = (TestMessageValidator) getTestOfType(
                annotationProcessorCommonTestConfiguration.getTestcases(),
                TestValidatorType.MESSAGE_VALIDATOR);

        CompileTestConfiguration configuration = new CompileTestConfiguration();
        configuration.addSourceFiles(getSourceFileForCompilation());
        configuration.setCompilationShouldSucceed(annotationProcessorCommonTestConfiguration.getCompilingShouldSucceed());
        if (messageValidationTest != null) {
            configuration.addNoteMessageCheck(messageValidationTest.getNotes());
            configuration.addWarningMessageCheck(messageValidationTest.getWarnings());
            configuration.addErrorMessageCheck(messageValidationTest.getErrors());
        }
        configuration.addProcessors(getWrappedProcessor());

        new CompileTest(configuration).executeTest();





/*-
            // check for created files
            if (annotationProcessorCommonTestConfiguration.getExpectedGeneratedJavaFileObjects() != null) {
                for (JavaFileObject fileObject : annotationProcessorCommonTestConfiguration.getExpectedGeneratedJavaFileObjects()) {
                    compileTester = compileTester.and().generatesFiles(fileObject);
                }
            }
*/


    }

    protected T getAnnotationProcessorTestConfiguration() {
        return annotationProcessorCommonTestConfiguration;
    }

    private TestValidator getTestOfType(TestValidator[] testValidators, TestValidatorType type) {

        if (testValidators == null || type == null) {
            return null;
        }

        for (TestValidator testValidator : testValidators) {

            if (type.equals(testValidator.getAnnotationProcessorTestType())) {
                return testValidator;
            }

        }

        return null;

    }


}
