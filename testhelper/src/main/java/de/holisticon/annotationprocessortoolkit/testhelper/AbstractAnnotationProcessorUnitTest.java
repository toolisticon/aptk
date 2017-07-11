package de.holisticon.annotationprocessortoolkit.testhelper;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.net.URL;
import java.util.Set;

/**
 * Abstract base class for testing annotation processor internal stuff where tools offered by {@link ProcessEnvironment} are needed.
 */
public abstract class AbstractAnnotationProcessorUnitTest extends AbstractAnnotationProcessorTest<AnnotationProcessorUnitTestConfiguration> {

    public abstract static class AbstractTestAnnotationProcessorClass extends AbstractAnnotationProcessor {

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


    public AbstractAnnotationProcessorUnitTest(AnnotationProcessorUnitTestConfiguration annotationProcessorTestConfiguration) {

        super(annotationProcessorTestConfiguration);

    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        URL url = AbstractAnnotationProcessorTest.class.getClassLoader().getResource("AnnotationProcessorTestClass.java");
        return JavaFileObjects.forResource(url);
    }

    @Override
    protected Processor getAnnotationProcessor() {
        return getAnnotationProcessorTestConfiguration().getProcessor();
    }
}
