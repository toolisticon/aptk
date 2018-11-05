package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.HashSet;
import java.util.Set;

public class CompileTest {

    private final CompileTestConfiguration compileTestConfiguration;


    public CompileTest(final CompileTestConfiguration compileTestConfiguration) {
        this.compileTestConfiguration = compileTestConfiguration;
    }

    public void executeTest() {


        // Do tests now
        CompilationResult compilationResult = compile(compileTestConfiguration);

        // Check if all processors have been applied
        checkIfProcessorsHaveBeenApplied(compilationResult.getDiagnostics());

        // check if error messages and shouldSucceed aren't set contradictionary
        if (compileTestConfiguration.getCompilationShouldSucceed() != null
                && compileTestConfiguration.getCompilationShouldSucceed()
                && compileTestConfiguration.getErrorMessageCheck().size() > 0) {
            throw new InvalidTestConfigurationException("Test configuration error : Compilation should succeed but also error messages !");
        }


        // Check if compilation succeeded
        if (compileTestConfiguration.getCompilationShouldSucceed() != null && !compileTestConfiguration.getCompilationShouldSucceed().equals(compilationResult.getCompilationSucceeded())) {

            AssertionSpiServiceLocator.locate().fail(compileTestConfiguration.getCompilationShouldSucceed() ? "Compilation should have succeeded but failed" : "Compilation should have failed but succeeded");

        }


        // Check messages
        checkMessages(compilationResult.getDiagnostics(), Diagnostic.Kind.ERROR, compileTestConfiguration.getErrorMessageCheck());
        checkMessages(compilationResult.getDiagnostics(), Diagnostic.Kind.WARNING, compileTestConfiguration.getWarningMessageCheck());
        checkMessages(compilationResult.getDiagnostics(), Diagnostic.Kind.MANDATORY_WARNING, compileTestConfiguration.getMandatoryWarningMessageCheck());
        checkMessages(compilationResult.getDiagnostics(), Diagnostic.Kind.NOTE, compileTestConfiguration.getNoteMessageCheck());

        // Check generated java source files
        for (JavaFileObject javaFileObject :
                this.compileTestConfiguration.getExpectedGeneratedJavaFileObjectsCheck()) {

            if (!compilationResult.getCompileTestFileManager().containsGeneratedJavaFileObject(javaFileObject)) {
                AssertionSpiServiceLocator.locate().fail("Expected generated file can't be found");
            }

        }

        for (FileObject fileObject :
                this.compileTestConfiguration.getExpectedGeneratedFileObjectsCheck()) {

            if (!compilationResult.getCompileTestFileManager().containsGeneratedFileObject(fileObject)) {
                AssertionSpiServiceLocator.locate().fail("Expected generated file can't be found");
            }

        }

    }

    public static CompilationResult compile(CompileTestConfiguration compileTestConfiguration) {

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        CompileTestFileManager javaFileManager = new CompileTestFileManager(compiler.getStandardFileManager(diagnostics, null, null));

        JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, javaFileManager, diagnostics, null, null, compileTestConfiguration.getSourceFiles());

        compilationTask.setProcessors(compileTestConfiguration.getWrappedProcessors());
        Boolean compilationSucceeded = compilationTask.call();

        return new CompilationResult(compilationSucceeded, diagnostics, javaFileManager);
    }

    protected void checkIfProcessorsHaveBeenApplied(DiagnosticCollector<JavaFileObject> diagnostics) {

        Set<String> messages = getMessages(diagnostics, Diagnostic.Kind.NOTE);

        outer:
        for (AnnotationProcessorWrapper processor : compileTestConfiguration.getWrappedProcessors()) {

            for (String message : messages) {
                if (message.equals(processor.getProcessorWasAppliedMessage())) {
                    continue outer;
                }
            }

            AssertionSpiServiceLocator.locate().fail("Annotation processor " + processor.getWrappedProcessor().getClass().getCanonicalName() + " hasn't been applied to a class");

        }

    }


    protected static void checkMessages(DiagnosticCollector<JavaFileObject> diagnostics, Diagnostic.Kind kind, Set<String> messsagesToCheck) {

        Set<String> messages = getMessages(diagnostics, kind);

        outer:
        for (String messageToCheck : messsagesToCheck) {

            for (String message : messages) {

                if (message.contains(messageToCheck)) {
                    continue outer;
                }

            }

            // Not found ==> assertion fails
            AssertionSpiServiceLocator.locate().fail("Haven't found expeected message string '" + messageToCheck + "' of kind " + kind.name());

        }

    }

    protected static Set<String> getMessages(DiagnosticCollector<JavaFileObject> diagnostics, Diagnostic.Kind kind) {

        Set<String> messages = new HashSet<String>();

        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            if (kind == diagnostic.getKind()) {
                messages.add(diagnostic.getMessage(null));
            }
        }

        return messages;

    }


}
