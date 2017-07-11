package de.holisticon.annotationprocessortoolkit.testhelper.unittest;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * Base annotation processor for unit tests.
 */
public abstract class AbstractTestAnnotationProcessorClass extends AbstractAnnotationProcessor {

    private final static Set<String> SUPPORTED_ANNOTATION_TYPES = AbstractAnnotationProcessor.createSupportedAnnotationSet(TestAnnotation.class);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }

    protected abstract void testCase(TypeElement element);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(TestAnnotation.class);

        if (set.size() == 1) {
            testCase((TypeElement) set.iterator().next());
        }

        return false;
    }

    protected void triggerError(String message) {

        this.getMessager().getMessager().printMessage(Diagnostic.Kind.ERROR, message);

    }
}



