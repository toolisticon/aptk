package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import org.junit.Assert;

import javax.annotation.processing.Completion;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Wrapper class for {@link Processor}. Allows generic creation of generic unit tests.
 */
public final class AnnotationProcessorWrapper implements Processor {

    private final Processor wrappedProcessor;
    private final Class<? extends Throwable> expectedThrownException;
    private Messager messager;

    private AnnotationProcessorWrapper(Processor processor) {
        this(processor, null);
    }

    private AnnotationProcessorWrapper(Processor processor, Class<? extends Throwable> expectedThrownException) {
        this.wrappedProcessor = processor;
        this.expectedThrownException = expectedThrownException;
    }


    @Override
    public Set<String> getSupportedOptions() {
        return wrappedProcessor.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return wrappedProcessor.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return wrappedProcessor.getSupportedSourceVersion();
    }

    @Override
    public void init(ProcessingEnvironment processingEnv) {

        // get messager
        messager = processingEnv.getMessager();

        wrappedProcessor.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // now set note message before calling the processor
        messager.printMessage(Diagnostic.Kind.NOTE, getProcessorWasAppliedMessage());

        try {
            boolean returnValue = wrappedProcessor.process(annotations, roundEnv);

            if (this.expectedThrownException != null) {
                Assert.fail("Expected exception of type '" + this.expectedThrownException.getClass().getCanonicalName() + "' to be thrown");
            }

            return returnValue;

        } catch (Throwable e) {

            if (this.expectedThrownException != null) {


                if (e.getClass().equals(this.expectedThrownException)) {
                    return true;
                }

            }

            // rethrow e if thrown exception didn't match excepted one
            throw new RuntimeException("Caught and rethrown Exception. Had been repacked because it could be a checked exception. '" + e.getMessage() + "'", e);


        }
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return wrappedProcessor.getCompletions(element, annotation, member, userText);
    }

    public String getProcessorWasAppliedMessage() {
        return CompileTestUtilities.getAnnotationProcessorWasAppliedMessage(wrappedProcessor);
    }

    public Processor getWrappedProcessor() {
        return wrappedProcessor;
    }

    public static AnnotationProcessorWrapper wrapProcessor(Processor processorToWrap) {
        return wrapProcessor(processorToWrap, null);
    }

    public static AnnotationProcessorWrapper wrapProcessor(Processor processorToWrap, Class<? extends Throwable> expectedThrownException) {

        if (processorToWrap == null) {
            throw new IllegalArgumentException("Passed processor must not be null");
        }

        return new AnnotationProcessorWrapper(processorToWrap);
    }

    public static <T extends Processor> AnnotationProcessorWrapper wrapProcessor(Class<T> processorTypeToWrap) {
        return wrapProcessor(processorTypeToWrap, null);
    }

    public static <T extends Processor> AnnotationProcessorWrapper wrapProcessor(Class<T> processorTypeToWrap, Class<? extends Throwable> expectedThrownException) {

        if (processorTypeToWrap == null) {
            throw new IllegalArgumentException("passed type must not be null");
        }

        try {
            return new AnnotationProcessorWrapper(processorTypeToWrap.newInstance());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot instantiate passed Class. Make sure that a NoArg constructor exists and is accessible.", e);
        }

    }


}
