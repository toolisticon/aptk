package io.toolisticon.aptk.cute;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.cute.CompileTestBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

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
