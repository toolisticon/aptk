package io.toolisticon.aptk.example.annotationprocessor;


import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class ImplementsSpecificInterfaceCheckAnnotationProcessorUnitTest {


    @Test
    public void testValidUsageWithAssignableParameters() {

        CompileTestBuilder.unitTest()
                .defineTest(new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);


                        MatcherAssert.assertThat("Should be assignable to Object", unit.isAssignableTo(typeElement, Object.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void testValidUsageWithNonAssignableParameters() {

        CompileTestBuilder.unitTest()
                .defineTest(new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                        unit.init(processingEnvironment);

                        MatcherAssert.assertThat("Should not be assignable to String", !unit.isAssignableTo(typeElement, String.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();

    }


}
