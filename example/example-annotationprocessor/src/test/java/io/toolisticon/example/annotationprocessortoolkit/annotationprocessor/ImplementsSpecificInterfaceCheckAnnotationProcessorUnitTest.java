package io.toolisticon.example.annotationprocessortoolkit.annotationprocessor;


import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class ImplementsSpecificInterfaceCheckAnnotationProcessorUnitTest extends AbstractAnnotationProcessorUnitTest {

    public ImplementsSpecificInterfaceCheckAnnotationProcessorUnitTest(String description, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage with assignable parameters",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                                        unit.init(this.processingEnv);

                                        MatcherAssert.assertThat("Should be assignable to Object", unit.isAssignableTo(element, Object.class.getCanonicalName()));

                                    }
                                })
                                .compilationShouldSucceed()
                                .build()
                },
                {
                        "Test valid usage with non assignable parameters",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        ImplementsSpecificInterfaceCheckAnnotationProcessor unit = new ImplementsSpecificInterfaceCheckAnnotationProcessor();
                                        unit.init(this.processingEnv);

                                        MatcherAssert.assertThat("Should not be assignable to String", !unit.isAssignableTo(element, String.class.getCanonicalName()));

                                    }
                                })
                                .compilationShouldSucceed()
                                .build()
                },


        });

    }


    @Test
    public void test() {
        super.test();
    }

}
