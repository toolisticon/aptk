package de.holisticon.annotationprocessortoolkit.testhelper;

import com.google.testing.compile.CompileTester;
import com.google.testing.compile.JavaFileObjects;

import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;


/**
 * Abstract base class which allows parameterized tests.
 */

public abstract class AbstractAnnotationProcessorTest<T extends Processor> {

    public final static String TEST_EXECUTION_MESSAGE = "!!!--- TEST WAS EXECUTED ---!!!";

    private final String description;
    private final String resource;
    private final String[] errors;
    private final String[] warnings;

    public AbstractAnnotationProcessorTest(String description, String resource, String[] errors, String[] warnings) {
        this.description = description;
        this.resource = resource;
        this.errors = errors;
        this.warnings = warnings;
    }

    protected abstract Processor getAnnotationProcessor();

    private Processor getWrappedProcessor() {
        return AnnotationProcessorWrapper.wrapProcessor(getAnnotationProcessor());
    }

    protected void test() {

        /*-
        // unfortunately this will only work with java 8 ...

        Compilation compilation = Compiler.javac().withProcessors(this.getAnnotationProcessor()).compile(JavaFileObjects.forResource(resource));

        System.out.println("ERRORS:");
        for (Diagnostic error : compilation.errors()) {
            System.out.println(error.getMessage(Locale.GERMANY));
        }

        if (errors.length > 0) {
            detectMessages(errors, compilation.errors());
        }
        assertThat("Should detect " + errors.length + " errors:", compilation.errors().size(), is(errors.length));


        System.out.println("WARNINGS:");
        for (Diagnostic warning : compilation.warnings()) {
            System.out.println(warning.getMessage(Locale.GERMANY));
        }

        if (warnings.length > 0) {
            detectMessages(warnings, compilation.warnings());
        }
        assertThat("Should detect " + warnings.length + " warnings:", compilation.warnings().size(), is(warnings.length));

        */

        JavaFileObject testClassSource = JavaFileObjects.forResource(resource);

        if (errors.length == 0) {
            CompileTester.SuccessfulCompilationClause compileTester = assertAbout(javaSource())
                    .that(testClassSource)
                    .processedWith(this.getWrappedProcessor()).compilesWithoutError();


            // check for warnings
            for (String warning : warnings) {
                compileTester.withWarningContaining(warning);
            }


            // check if test was executed
            compileTester.withNoteContaining(TEST_EXECUTION_MESSAGE);

        } else {
            CompileTester.UnsuccessfulCompilationClause compileTester = assertAbout(javaSource())
                    .that(testClassSource)
                    .processedWith(this.getWrappedProcessor()).failsToCompile();

            // check for warnings
            for (String error : errors) {
                compileTester.withErrorContaining(error);
            }

            // check if test was executed
            compileTester.withNoteContaining(TEST_EXECUTION_MESSAGE);
        }


    }

    /*- UUsed with Java 8
    private void detectMessages(String[] messages, ImmutableList<Diagnostic<? extends JavaFileObject>> compileMessages) {

        for (String message : messages) {

            boolean found = false;

            UnmodifiableIterator<Diagnostic<? extends JavaFileObject>> iterator = compileMessages.iterator();

            while (iterator.hasNext()) {

                Diagnostic<? extends JavaFileObject> diagnostic = iterator.next();


                if (message.equals(diagnostic.getMessage(Locale.GERMANY))) {
                    found = true;
                    break;
                }

            }

            Assert.assertTrue(String.format("Should have contained '%s' message", message), found);


        }


    }
    */


}
