package io.toolisticon.annotationprocessortoolkit.testhelper;

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
    private Messager messager;


    private AnnotationProcessorWrapper(Processor processor) {
        this.wrappedProcessor = processor;
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
        messager.printMessage(Diagnostic.Kind.NOTE, AbstractAnnotationProcessorTest.TEST_EXECUTION_MESSAGE);

        return wrappedProcessor.process(annotations, roundEnv);
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return wrappedProcessor.getCompletions(element, annotation, member, userText);
    }

    public static Processor wrapProcessor(Processor processorToWrap) {

        if (processorToWrap == null) {
            throw new IllegalArgumentException("Passed processor must not be null");
        }

        return new AnnotationProcessorWrapper(processorToWrap);
    }

    public static <G extends Processor> Processor wrapProcessor(Class<G> processorTypeToWrap) {

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
