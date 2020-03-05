package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;


import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.UnitTest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

public class ImplementsSpecificInterfaceCheckAnnotationProcessorUnitTest {


    @Test
    public void testValidUsageWithAssignableParameters() {

        CompileTestBuilder.unitTest()
                .defineTest(new UnitTest<Element>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, Element typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat("Should be assignable to Object", unit.isAssignableTo(typeElement, Object.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void testValidUsageWithNonAssignableParameters() {

        CompileTestBuilder.unitTest()
                .defineTest(new UnitTest<Element>() {
                    @Override
                    public void unitTest(ProcessingEnvironment processingEnvironment, Element typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);
                        ToolingProvider.setTooling(processingEnvironment);

                        MatcherAssert.assertThat("Should not be assignable to String", !unit.isAssignableTo(typeElement, String.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }


}
