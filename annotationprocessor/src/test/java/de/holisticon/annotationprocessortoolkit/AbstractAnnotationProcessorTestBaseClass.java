package de.holisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Set;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;


/**
 * Abstract base class for testing annotation processor internal stuff where tools offered by {@link ProcessEnvironment} are needed.
 */
public abstract class AbstractAnnotationProcessorTestBaseClass {

    public final static String TEST_EXECUTION_MESSAGE = "TEST WAS EXECUTED";

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

    private boolean compilationShouldSucceed;

    public AbstractAnnotationProcessorTestBaseClass(String message, AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {

        this.message = message;
        this.testcase = testcase;
        this.compilationShouldSucceed = compilationShouldSucceed;

    }


    // This old version of the test runs with compile tester version 0.10.0 which is build with java 8.
    // So tests can't be executed with java version <8.
    //@Test
    /*
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
    */

    @Test
    public void test() {

        JavaFileObject testClassSource = JavaFileObjects.forResource("AnnotationProcessorTestClass.java");

        if (compilationShouldSucceed) {
            assertAbout(javaSource())
                    .that(testClassSource)
                    .processedWith(testcase).compilesWithoutError();
        } else {
            assertAbout(javaSource())
                    .that(testClassSource)
                    .processedWith(testcase).failsToCompile();
        }


    }


}
