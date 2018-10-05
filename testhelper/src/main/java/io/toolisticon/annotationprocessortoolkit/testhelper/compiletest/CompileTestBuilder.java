package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.annotation.processing.Processor;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;

/**
 * Compile test builder.
 * Implemented with immutable state / configuration, so it's safe to use this instance f.e. in unit test before classes.
 */
public class CompileTestBuilder {

    private static CompileTestConfiguration cloneConfiguration(CompileTestConfiguration compileTestConfiguration) {
        return new CompileTestConfiguration(compileTestConfiguration);
    }

    public static class MessageEvaluation<T extends BasicBuilder<T>> {

        private final CompileTestConfiguration compileTestConfiguration;
        private final T returnInstance;

        private MessageEvaluation(T returnInstance, CompileTestConfiguration compileTestConfiguration) {

            this.compileTestConfiguration = cloneConfiguration(compileTestConfiguration);
            this.returnInstance = returnInstance;
        }

        /**
         * Adds some warning checks.
         *
         * @param warningChecks the warning checks to set, null values will be ignored.
         * @return the next builder instance
         */
        public MessageEvaluation<T> addWarningChecks(String... warningChecks) {

            CompileTestConfiguration nextConfig = cloneConfiguration(compileTestConfiguration);
            if (warningChecks != null) {
                nextConfig.addWarningMessageCheck(warningChecks);
            }
            return new MessageEvaluation<T>(returnInstance, nextConfig);

        }

        /**
         * Adds some mandatory warning checks.
         *
         * @param mandatoryWarningChecks the mandatory warning checks to set, null values will be ignored.
         * @return the next builder instance
         */
        public MessageEvaluation<T> addMandatoryWarningChecks(String... mandatoryWarningChecks) {
            CompileTestConfiguration nextConfig = cloneConfiguration(compileTestConfiguration);
            if (mandatoryWarningChecks != null) {
                compileTestConfiguration.addMandatoryWarningMessageCheck(mandatoryWarningChecks);
            }
            return new MessageEvaluation<T>(returnInstance, compileTestConfiguration);
        }

        /**
         * Adds some error checks.
         *
         * @param errorChecksToSet the error checks to set, null values will be ignored.
         * @return the next builder instance
         */
        public MessageEvaluation<T> addErrorChecks(String... errorChecksToSet) {
            CompileTestConfiguration nextConfig = cloneConfiguration(compileTestConfiguration);
            if (errorChecksToSet != null) {
                compileTestConfiguration.addErrorMessageCheck(errorChecksToSet);
            }
            return new MessageEvaluation<T>(returnInstance, compileTestConfiguration);
        }

        /**
         * Adds some notes checks.
         *
         * @param noteChecksToSet the notes checks to set, null values will be ignored.
         * @return the next builder instance
         */
        public MessageEvaluation<T> addNoteChecks(String... noteChecksToSet) {
            CompileTestConfiguration nextConfig = cloneConfiguration(compileTestConfiguration);
            if (noteChecksToSet != null) {
                compileTestConfiguration.addNoteMessageCheck(noteChecksToSet);
            }
            return new MessageEvaluation<T>(returnInstance, compileTestConfiguration);
        }

        /**
         * Finishes message validation builder.
         *
         * @return the basic builder instance
         */
        public T finishAddMessageChecks() {
            CompileTestConfiguration nextConfig = cloneConfiguration(compileTestConfiguration);
            return returnInstance.createNextInstance(nextConfig);
        }

    }


    public static abstract class BasicBuilder<T extends BasicBuilder<T>> {

        protected final CompileTestConfiguration compileTestConfiguration;

        protected BasicBuilder(CompileTestConfiguration compileTestConfiguration) {
            this.compileTestConfiguration = cloneConfiguration(compileTestConfiguration);
        }

        public T compilationShouldSucceed() {
            compileTestConfiguration.setCompilationShouldSucceed(true);
            return createNextInstance(compileTestConfiguration);
        }

        public T compilationShouldFail() {
            compileTestConfiguration.setCompilationShouldSucceed(false);
            return createNextInstance(compileTestConfiguration);
        }


        public T addExpectedGeneratedJavaFileObjects(JavaFileObject... expectedGeneratedJavaFileObjects) {

            if (expectedGeneratedJavaFileObjects != null) {
                compileTestConfiguration.addExpectedGeneratedJavaFileObjectCheck(expectedGeneratedJavaFileObjects);
            }
            return createNextInstance(compileTestConfiguration);

        }

        public T addExpectedGeneratedFileObjects(FileObject... expectedGeneratedFileObjects) {

            if (expectedGeneratedFileObjects != null) {
                compileTestConfiguration.addExpectedGeneratedFileObjectCheck(expectedGeneratedFileObjects);
            }
            return createNextInstance(compileTestConfiguration);

        }

        public MessageEvaluation<T> addMessageChecks() {
            return new MessageEvaluation<T>(createNextInstance(compileTestConfiguration), compileTestConfiguration);
        }

        /**
         * Created the compile test configuration instance.
         *
         * @return the configuration instance
         */
        public CompileTestConfiguration createCompileTestConfiguration() {
            return cloneConfiguration(compileTestConfiguration);
        }

        /**
         * Starts the compilation tests.
         * @throws IllegalStateException if there's some invalid configuration
         */
        public void testCompilation() {
            if (compileTestConfiguration.getSourceFiles().size() == 0) {
                throw new IllegalArgumentException("At least one source file has to be added to the compiler test configuration");
            }

            new CompileTest(createCompileTestConfiguration()).executeTest();
        }

        /**
         * Creates the next immutable builder instance
         *
         * @param compileTestConfiguration the configuration to be used
         * @return the next builder instance
         */
        protected abstract T createNextInstance(CompileTestConfiguration compileTestConfiguration);


    }

    public static class CompileTimeTestBuilder extends BasicBuilder<CompileTimeTestBuilder> {

        private CompileTimeTestBuilder(CompileTestConfiguration compileTestConfiguration) {
            super(compileTestConfiguration);
        }


        public CompileTimeTestBuilder useProcessors(Processor... processors) {
            if (processors != null) {
                compileTestConfiguration.addProcessors(processors);
            }
            return createNextInstance(compileTestConfiguration);
        }

        public CompileTimeTestBuilder useProcessorAndExpectException(Processor processor, Class<? extends Throwable> exception) {
            if (processor == null) {
                throw new IllegalArgumentException("Passed processor must not be null");
            }
            if (exception == null) {
                throw new IllegalArgumentException("Passed exception must not be null");
            }
            compileTestConfiguration.addProcessorWithExpectedException(processor, exception);
            return createNextInstance(compileTestConfiguration);
        }

        public CompileTimeTestBuilder addSources(JavaFileObject... sources) {
            if (sources != null) {
                compileTestConfiguration.addSourceFiles(sources);
            }
            return createNextInstance(compileTestConfiguration);
        }

        protected CompileTimeTestBuilder createNextInstance(CompileTestConfiguration compileTestConfiguration) {
            return new CompileTimeTestBuilder(compileTestConfiguration);
        }

    }

    public static class UnitTestBuilder extends BasicBuilder<UnitTestBuilder> {

        /**
         * Sets the processor to use.
         *
         * @param processor the processor to use
         * @return the UnitTestBuilder instance
         * @throws IllegalArgumentException if passed processor is null.
         */
        public UnitTestBuilder useProcessor(Processor processor) {

            if (processor == null) {
                throw new IllegalArgumentException("passed processor must not be null!");
            }

            // remove existing processor
            compileTestConfiguration.getProcessors().clear();
            compileTestConfiguration.addProcessors(processor);

            return createNextInstance(compileTestConfiguration);
        }

        /**
         * Sets the source file used to apply processor on.
         * The source file must contain an annootation that is processed by the processor.
         *
         * @param source The source file to use
         * @return the UnitTestBuilder instance
         * @throws IllegalArgumentException if passed source is null.
         */
        public UnitTestBuilder useSource(JavaFileObject source) {


            if (source == null) {
                throw new IllegalArgumentException("passed source file must not be null!");
            }

            // clear existing sources
            compileTestConfiguration.getSourceFiles().clear();
            compileTestConfiguration.addSourceFiles(source);

            return createNextInstance(compileTestConfiguration);
        }


        public void testCompilation() {
            if (compileTestConfiguration.getProcessors().size() == 0) {
                throw new IllegalArgumentException("At least one processor has to be added to the compiler test configuration");
            }

            super.testCompilation();
        }

        /**
         * Internal constructor
         *
         * @param compileTestConfiguration
         */
        private UnitTestBuilder(CompileTestConfiguration compileTestConfiguration) {
            super(compileTestConfiguration);
        }


        /**
         * Returns the default Source file object.
         *
         * @return the default source file object
         */
        public static JavaFileObject getDefaultSource() {
            return new JavaFileObjectUtils.JavaSourceFromResource("/AnnotationProcessorUnitTestClass.java");
        }

        protected UnitTestBuilder createNextInstance(CompileTestConfiguration compileTestConfiguration) {
            return new UnitTestBuilder(compileTestConfiguration);
        }

    }


    /**
     * public static class ExpectedCompilationResultBuilder<T extends BasicBuilder>{
     * <p>
     * protected final CompileTestConfiguration compileTestConfiguration;
     * protected final T basicBuilderType;
     * <p>
     * protected ExpectedCompilationResultBuilder(T basicBuilderType, CompileTestConfiguration compileTestConfiguration) {
     * this.basicBuilderType = basicBuilderType;
     * this.compileTestConfiguration = cloneConfiguration(compileTestConfiguration);
     * }
     * <p>
     * T compilationShouldSucceed() {
     * compileTestConfiguration.setCompilationShouldSucceed(true);
     * return createNextInstance(compileTestConfiguration);
     * }
     * <p>
     * T compilationShouldFail() {
     * compileTestConfiguration.setCompilationShouldSucceed(false);
     * return createNextInstance(compileTestConfiguration);
     * }
     * <p>
     * }
     */


    public static class TestTypeBuilder {

        /**
         * Does a compilation test.
         * You can freely  choose sources to compile and processors to use.
         *
         * @return the builder
         */
        public CompileTimeTestBuilder compilationTest() {
            return new CompileTimeTestBuilder(new CompileTestConfiguration());
        }


        /**
         * Do a unit test.
         *
         * @return the UnitTestBuilder instance
         */
        public UnitTestBuilder unitTest() {
            CompileTestConfiguration compileTestConfiguration = new CompileTestConfiguration();
            compileTestConfiguration.addSourceFiles(UnitTestBuilder.getDefaultSource());
            return new UnitTestBuilder(compileTestConfiguration);
        }

    }


    public static TestTypeBuilder createCompileTestBuilder() {
        return new TestTypeBuilder();
    }

}
