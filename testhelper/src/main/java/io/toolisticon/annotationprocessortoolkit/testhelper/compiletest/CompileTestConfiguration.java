package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.annotation.processing.Processor;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompileTestConfiguration {

    public static class ProcessorWithExpectedException {

        private Processor processor;
        private Class<? extends Throwable> throwable;

        public ProcessorWithExpectedException(Processor processor, Class<? extends Throwable> throwable) {
            this.processor = processor;
            this.throwable = throwable;
        }

        public Processor getProcessor() {
            return processor;
        }

        public Class<? extends Throwable> getThrowable() {
            return throwable;
        }
    }

    private final Set<JavaFileObject> sourceFiles = new HashSet<JavaFileObject>();
    private final Set<Processor> processors = new HashSet<Processor>();
    private final Set<ProcessorWithExpectedException> processorsWithExpectedExceptions = new HashSet<ProcessorWithExpectedException>();

    private Boolean compilationShouldSucceed;
    private final Set<String> warningMessageCheck = new HashSet<String>();
    private final Set<String> mandatoryWarningMessageCheck = new HashSet<String>();
    private final Set<String> errorMessageCheck = new HashSet<String>();
    private final Set<String> noteMessageCheck = new HashSet<String>();

    private final Set<JavaFileObject> expectedGenerateJavaFileObjectsCheck = new HashSet<JavaFileObject>();
    private final Set<FileObject> expectedGenerateFileObjectsCheck = new HashSet<FileObject>();


    public CompileTestConfiguration() {

    }

    /**
     * Clone constructor.
     */
    public CompileTestConfiguration(CompileTestConfiguration source) {

        this.sourceFiles.addAll(source.getSourceFiles());
        this.processors.addAll(source.getProcessors());
        this.processorsWithExpectedExceptions.addAll(source.processorsWithExpectedExceptions);

        this.compilationShouldSucceed = source.getCompilationShouldSucceed();
        this.warningMessageCheck.addAll(source.getWarningMessageCheck());
        this.mandatoryWarningMessageCheck.addAll(source.getMandatoryWarningMessageCheck());
        this.noteMessageCheck.addAll(source.getNoteMessageCheck());
        this.errorMessageCheck.addAll(source.getErrorMessageCheck());

        this.expectedGenerateJavaFileObjectsCheck.addAll(source.getExpectedGeneratedJavaFileObjectsCheck());
        this.expectedGenerateFileObjectsCheck.addAll(source.getExpectedGeneratedFileObjectsCheck());

    }


    public Boolean getCompilationShouldSucceed() {
        return compilationShouldSucceed;
    }

    public void setCompilationShouldSucceed(Boolean compilationShouldSucceed) {
        this.compilationShouldSucceed = compilationShouldSucceed;
    }

    public void addSourceFiles(JavaFileObject... sourceFiles) {
        if (sourceFiles != null) {
            this.sourceFiles.addAll(Arrays.asList(sourceFiles));
            this.sourceFiles.remove(null);
        }
    }

    public void addProcessors(Processor... processors) {
        if (processors != null) {
            this.processors.addAll(Arrays.asList(processors));
            this.processors.remove(null);
        }
    }

    public void addProcessorWithExpectedException(Processor processors, Class<? extends Throwable> e) {
        this.processorsWithExpectedExceptions.add(new ProcessorWithExpectedException(processors, e));
    }

    public void addWarningMessageCheck(String... warningMessage) {
        if (warningMessage != null) {
            this.warningMessageCheck.addAll(Arrays.asList(warningMessage));
            this.warningMessageCheck.remove(null);
        }
    }

    public void addMandatoryWarningMessageCheck(String... mandatoryWarningMessage) {
        if (mandatoryWarningMessage != null) {
            this.mandatoryWarningMessageCheck.addAll(Arrays.asList(mandatoryWarningMessage));
            this.mandatoryWarningMessageCheck.remove(null);
        }
    }

    public void addErrorMessageCheck(String... errorMessage) {
        if (errorMessage != null) {
            this.errorMessageCheck.addAll(Arrays.asList(errorMessage));
            this.errorMessageCheck.remove(null);
        }
    }

    public void addNoteMessageCheck(String... noteMessage) {
        if (noteMessage != null) {
            this.noteMessageCheck.addAll(Arrays.asList(noteMessage));
            this.noteMessageCheck.remove(null);
        }
    }

    public void addExpectedGeneratedJavaFileObjectCheck(JavaFileObject... javaFileObjects) {
        this.expectedGenerateJavaFileObjectsCheck.addAll(Arrays.asList(javaFileObjects));
        this.expectedGenerateJavaFileObjectsCheck.remove(null);
    }

    public void addExpectedGeneratedFileObjectCheck(FileObject... fileObjects) {
        this.expectedGenerateFileObjectsCheck.addAll(Arrays.asList(fileObjects));
        this.expectedGenerateFileObjectsCheck.remove(null);
    }

    public Set<JavaFileObject> getSourceFiles() {
        return sourceFiles;
    }

    public Set<Processor> getProcessors() {
        return processors;
    }

    public Set<ProcessorWithExpectedException> getProcessorsWithExpectedExceptions() {
        return processorsWithExpectedExceptions;
    }

    public Set<AnnotationProcessorWrapper> getWrappedProcessors() {

        Set<AnnotationProcessorWrapper> wrappedProcessors = new HashSet<AnnotationProcessorWrapper>();

        for (Processor processor : this.processors) {
            wrappedProcessors.add(AnnotationProcessorWrapper.wrapProcessor(processor));
        }

        for (ProcessorWithExpectedException processor : this.processorsWithExpectedExceptions) {
            wrappedProcessors.add(AnnotationProcessorWrapper.wrapProcessor(processor.processor, processor.throwable));
        }

        return wrappedProcessors;

    }

    public Set<String> getWarningMessageCheck() {
        return warningMessageCheck;
    }

    public Set<String> getMandatoryWarningMessageCheck() {
        return mandatoryWarningMessageCheck;
    }

    public Set<String> getErrorMessageCheck() {
        return errorMessageCheck;
    }

    public Set<String> getNoteMessageCheck() {
        return noteMessageCheck;
    }

    public Set<JavaFileObject> getExpectedGeneratedJavaFileObjectsCheck() {
        return expectedGenerateJavaFileObjectsCheck;
    }

    public Set<FileObject> getExpectedGeneratedFileObjectsCheck() {
        return expectedGenerateFileObjectsCheck;
    }


}
