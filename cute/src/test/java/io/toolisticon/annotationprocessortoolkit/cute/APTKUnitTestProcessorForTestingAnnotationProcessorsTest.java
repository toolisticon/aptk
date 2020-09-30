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
 * Unit test for {@link APTKUnitTestProcessorForTestingAnnotationProcessors}.
 */
public class APTKUnitTestProcessorForTestingAnnotationProcessorsTest {

    private static boolean unitTestMethodCalled = false;

    public static class TestProcessor extends AbstractAnnotationProcessor {

        private static boolean initCalled = false;


        @Override
        public synchronized void init(ProcessingEnvironment processingEnv) {
            super.init(processingEnv);

            initCalled = true;
        }

        @Override
        public boolean processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            return false;
        }

    }

    @Before
    public void init() {
        TestProcessor.initCalled = false;
        unitTestMethodCalled = false;
    }

    @Test
    public void testIfToolingIsSetCorrectly() {

        CompileTestBuilder.unitTest().defineTest(TestProcessor.class, new APTKUnitTestProcessorForTestingAnnotationProcessors<TestProcessor,TypeElement>() {
            @Override
            public void aptkUnitTest(TestProcessor processor, ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                unitTestMethodCalled = true;

                MatcherAssert.assertThat(ToolingProvider.getTooling(), Matchers.notNullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();


        // check if init was called
        MatcherAssert.assertThat("Init must have been called", TestProcessor.initCalled);
        MatcherAssert.assertThat("aptkUnitTest method must have been called", unitTestMethodCalled);

    }


}
