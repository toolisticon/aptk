package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.generators.FileObjectUtilsTestAnnotationProcessor;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class FilerUtilsTest extends AbstractAnnotationProcessorIntegrationTest<FileObjectUtilsTestAnnotationProcessor> {


    public FilerUtilsTest(String description, AnnotationProcessorIntegrationTestConfiguration annotationProcessorIntegrationTestConfiguration) {
        super(annotationProcessorIntegrationTestConfiguration);
    }

    @Override
    protected FileObjectUtilsTestAnnotationProcessor getAnnotationProcessor() {
        return new FileObjectUtilsTestAnnotationProcessor();
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage",
                        AnnotationProcessorIntegrationTestConfigurationBuilder.createTestConfig()
                                .setSourceFileToCompile("/testcases/generators/FilerUtilsTestClass.java")
                                .compilationShouldSucceed()
                                .javaFileObjectShouldMatch(JavaFileObjectUtils.readFromResource("/testcases/generators/expectedResult.txt"))
                                .build()
                },


        });

    }


    @Test
    public void test() {
        super.test();
    }


}