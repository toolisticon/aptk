package io.toolisticon.annotationprocessortoolkit.testhelper.unittest;

import io.toolisticon.annotationprocessortoolkit.ToolingProvider;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 * Base annotation processor for unit tests.
 */
public abstract class AbstractUnitTestAnnotationProcessorClass extends AbstractProcessor {

    private static final Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

    static {
        SUPPORTED_ANNOTATION_TYPES.add(TestAnnotation.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        ToolingProvider.setTooling(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }

    protected abstract void testCase(TypeElement element);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            ToolingProvider.setTooling(processingEnv);

            Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(TestAnnotation.class);

            if (set.size() == 1) {
                testCase((TypeElement) set.iterator().next());
            }

        } finally {
            ToolingProvider.clearTooling();
        }
        return false;
    }

    protected void triggerError(String message) {

        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);

    }
}



