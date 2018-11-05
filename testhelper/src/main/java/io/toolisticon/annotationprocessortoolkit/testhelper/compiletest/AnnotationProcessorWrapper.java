package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

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

        // skip processing over round
        if (annotations.size() == 0 && roundEnv.processingOver()) {
            return true;
        }

        // now set note message before calling the processor
        messager.printMessage(Diagnostic.Kind.NOTE, getProcessorWasAppliedMessage());

        try {
            boolean returnValue = wrappedProcessor.process(annotations, roundEnv);

            if (this.expectedThrownException != null) {
                AssertionSpiServiceLocator.locate().fail("Expected exception of type '" + this.expectedThrownException.getCanonicalName() + "' to be thrown, but wasn't");
            }

            return returnValue;

        } catch (Throwable e) {

            // pass through assertions
            if (AssertionError.class.isAssignableFrom(e.getClass())) {
                throw (AssertionError) e;
            }

            if (this.expectedThrownException != null) {

                if (!this.expectedThrownException.isAssignableFrom(e.getClass())) {
                    AssertionSpiServiceLocator.locate().fail("Expected exception of type '" + this.expectedThrownException.getCanonicalName() + "' but exception of type '" + e.getClass().getCanonicalName() + (e.getMessage() != null ? "'  with message '" + e.getMessage() : "") + "' was thrown instead.");
                }


            } else {

                // Got unexpected exception
                AssertionSpiServiceLocator.locate().fail("An unexpected exception of type '" + e.getClass().getCanonicalName() + (e.getMessage() != null ? "'  with message '" + e.getMessage() : "") + "' has been thrown.");

            }

            return true;

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

        return new AnnotationProcessorWrapper(processorToWrap, expectedThrownException);
    }

    public static <T extends Processor> AnnotationProcessorWrapper wrapProcessor(Class<T> processorTypeToWrap) {
        if (processorTypeToWrap == null) {
            throw new IllegalArgumentException("passed type must not be null");
        }

        try {
            return new AnnotationProcessorWrapper(processorTypeToWrap.newInstance());
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot instantiate passed processor Class '" + processorTypeToWrap.getCanonicalName() + "'. Make sure that a NoArg constructor exists and is accessible.", e);
        }

    }

    public static <T extends Processor> AnnotationProcessorWrapper wrapProcessor(Class<T> processorTypeToWrap, Class<? extends Throwable> expectedThrownException) {

        if (processorTypeToWrap == null) {
            throw new IllegalArgumentException("passed type must not be null");
        }

        if (expectedThrownException == null) {
            throw new IllegalArgumentException("passed expectedThrownException must not be null");
        }

        try {
            return new AnnotationProcessorWrapper(processorTypeToWrap.newInstance(), expectedThrownException);
        } catch (Exception e) {
            throw new IllegalArgumentException(" instantiate passed processor Class '" + processorTypeToWrap.getCanonicalName() + "'. Make sure that a NoArg constructor exists and is accessible.", e);
        }

    }


}
