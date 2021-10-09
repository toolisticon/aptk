package io.toolisticon.annotationprocessortoolkit.cute;

import io.toolisticon.annotationprocessortoolkit.AbstractAnnotationProcessor;
import io.toolisticon.annotationprocessortoolkit.ToolingProvider;
import io.toolisticon.cute.CompileTestBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Unit test for {@link APTKUnitTestProcessor}.
 */
public class APTKUnitTestProcessorTest {

    private static boolean unitTestMethodCalled = false;



    @Test
    public void testIfToolingIsSetCorrectly() {

        CompileTestBuilder.unitTest().defineTest( new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest( ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                unitTestMethodCalled = true;

                MatcherAssert.assertThat(ToolingProvider.getTooling(), Matchers.notNullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();


        // check if init was called
        MatcherAssert.assertThat("aptkUnitTest method must have been called", unitTestMethodCalled);

    }


}
