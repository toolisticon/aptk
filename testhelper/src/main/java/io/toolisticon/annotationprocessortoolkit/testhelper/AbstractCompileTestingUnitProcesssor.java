package io.toolisticon.annotationprocessortoolkit.testhelper;

import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.compiletesting.UnitTestProcessor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Abstract base class for compile testing framework that initializes the ToolingProvider.
 */
public abstract class AbstractCompileTestingUnitProcesssor implements UnitTestProcessor {


    @Override
    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {
        try {

            ToolingProvider.setTooling(processingEnvironment);
            unitTest(typeElement);

        } finally {
            ToolingProvider.clearTooling();
        }

    }

    public abstract void unitTest(TypeElement typeElement);


}
