package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;


import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.UnitTestProcessor;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class ImplementsSpecificInterfaceCheckAnnotationProcessorUnitTest {


    @Test
    public void testValidUsageWithAssignableParameters() {

        CompileTestBuilder.unitTest()
                .useProcessor(new UnitTestProcessor() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat("Should be assignable to Object", unit.isAssignableTo(typeElement, Object.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void testValidUsageWithNonAssignableParameters() {

        CompileTestBuilder.unitTest()
                .useProcessor(new UnitTestProcessor() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat("Should not be assignable to String", !unit.isAssignableTo(typeElement, String.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .testCompilation();

    }


}
