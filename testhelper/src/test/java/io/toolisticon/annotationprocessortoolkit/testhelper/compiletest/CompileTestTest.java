package io.toolisticon.annotationprocessortoolkit.testhelper.compiletest;

import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;

public class CompileTestTest {

    @Before
    public void init() {
        TestAssertion.reset();
    }

    @Test
    public void test_UnitTest_checkMatchingFileObject() {


        CompileTestBuilder.createCompileTestBuilder()
                .unitTest()
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        try {
                            FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "root", "Jupp.txt", element);
                            Writer writer = fileObject.openWriter();
                            writer.write("TATA!");
                            writer.close();


                        } catch (IOException e) {

                        }

                    }
                })

                .compilationShouldSucceed()

                .addExpectedGeneratedFileObjects(new JavaFileObjectUtils.JavaSourceFromString("test", "TATA!"))
                .testCompilation();

        MatcherAssert.assertThat("Should not have called fail", !TestAssertion.hasFailBeenTriggered());

    }

    @Test
    public void test_UnitTest_checkNonMatchingFileObject() {


        CompileTestBuilder.createCompileTestBuilder()
                .unitTest()
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        try {
                            FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "root", "Jupp.txt", element);
                            Writer writer = fileObject.openWriter();
                            writer.write("TATA!");
                            writer.close();


                        } catch (IOException e) {

                        }

                    }
                })

                .compilationShouldSucceed()

                .addExpectedGeneratedFileObjects(new JavaFileObjectUtils.JavaSourceFromString("test", "WURST!"))
                .testCompilation();

        MatcherAssert.assertThat("Should have called fail", TestAssertion.hasFailBeenTriggered());

    }
}
