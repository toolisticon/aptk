package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import javax.tools.DiagnosticCollector;

public class CompilationResult {

    private final Boolean compilationSucceeded;
    private final DiagnosticCollector diagnostics;
    private final CompileTestFileManager compileTestFileManager;


    public CompilationResult(Boolean compilationSucceeded,
                             DiagnosticCollector diagnostics,
                             CompileTestFileManager compileTestFileManager) {

        this.compilationSucceeded = compilationSucceeded;
        this.diagnostics = diagnostics;
        this.compileTestFileManager = compileTestFileManager;

    }

    public Boolean getCompilationSucceeded() {
        return compilationSucceeded;
    }

    public DiagnosticCollector getDiagnostics() {
        return diagnostics;
    }

    public CompileTestFileManager getCompileTestFileManager() {
        return compileTestFileManager;
    }
}
