package de.holisticon.example.annotationprocessortoolkit.annotationprocessor;


import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class TypeThatIsAssignableToInterfaceAnnotationProcessorUnitTest extends AbstractAnnotationProcessorUnitTest {

    public TypeThatIsAssignableToInterfaceAnnotationProcessorUnitTest(String description, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\"")
    public static List<Object[]> data() {

        return Arrays.asList(new Object[][]{
                {
                        "Test valid usage with assignable parameters",
                        AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                .setProcessor(new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeThatIsAssignableToInterfaceAnnotationProcessor unit = new TypeThatIsAssignableToInterfaceAnnotationProcessor();
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
                                .setProcessor(new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeThatIsAssignableToInterfaceAnnotationProcessor unit = new TypeThatIsAssignableToInterfaceAnnotationProcessor();
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
