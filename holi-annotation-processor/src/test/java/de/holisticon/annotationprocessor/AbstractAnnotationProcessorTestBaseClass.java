package de.holisticon.annotationprocessor;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Abstract base class for testing annotation processor internal stuff where tools offered by {@link ProcessEnvironment} are needed.
 */
public abstract class AbstractAnnotationProcessorTestBaseClass {

    public final static String TEST_EXECUTION_MESSAGE = "TEST WAS EXECUTED";


    @SupportedAnnotationTypes({"de.holisticon.annotationprocessor.TestAnnotation"})
    public abstract static class AbstractTestAnnotationProcessorClass extends AbstractAnnotationProcessor {


        private final static Set<String> SUPPORTED_ANNOTATION_TYPES = new HashSet<String>();

        static {
            SUPPORTED_ANNOTATION_TYPES.add(TestAnnotation.class.getCanonicalName());
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return SUPPORTED_ANNOTATION_TYPES;
        }

        protected abstract void testCase(TypeElement element);

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

            Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(TestAnnotation.class);

            if (set.size() == 1) {

                // set that test has been executed
                this.getMessager().getMessager().printMessage(Diagnostic.Kind.NOTE, TEST_EXECUTION_MESSAGE);
                testCase((TypeElement) set.iterator().next());
            }

            return false;
        }

        protected void triggerError(String message) {

            this.getMessager().getMessager().printMessage(Diagnostic.Kind.ERROR, message);

        }
    }


    private String message;
    private AbstractTestAnnotationProcessorClass testcase;

    public AbstractAnnotationProcessorTestBaseClass(String message, AbstractTestAnnotationProcessorClass testcase) {
        this.message = message;
        this.testcase = testcase;
    }

    @Test
    public void test() {

        JavaFileObject testClassSource = JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
        Compilation compilation = Compiler.javac()
                .withProcessors(testcase)
                .compile(testClassSource);


        boolean wasExecuted = false;
        for (Diagnostic note : compilation.notes()) {
            if (TEST_EXECUTION_MESSAGE.equals(note.getMessage(Locale.getDefault()))) {
                wasExecuted = true;
                break;
            }
        }

        MatcherAssert.assertThat("TESTCASE WAS NOT EXECUTED", wasExecuted);

    }


}
