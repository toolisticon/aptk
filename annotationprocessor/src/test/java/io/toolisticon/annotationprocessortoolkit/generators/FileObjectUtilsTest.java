package io.toolisticon.annotationprocessortoolkit.generators;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorIntegrationTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.integrationtest.AnnotationProcessorIntegrationTestConfigurationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class FileObjectUtilsTest extends AbstractAnnotationProcessorIntegrationTest<FileObjectUtilsTestAnnotationProcessor> {


    public FileObjectUtilsTest(String description, AnnotationProcessorIntegrationTestConfiguration annotationProcessorIntegrationTestConfiguration) {
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
                                .setSourceFileToCompile("testcases/generators/FileObjectUtilsTestClass.java")
                                .compilationShouldSucceed()
                                .resourceShouldMatch(JavaFileObjects.forResource("testcases/generators/expectedResult.txt"))
                                .build()
                },


        });

    }


    @Test
    public void test() {
        super.test();
    }


}