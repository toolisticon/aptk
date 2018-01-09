package io.toolisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link ParameterExecutableMatcher}.
 */
@RunWith(Parameterized.class)
public class TypeElementCharacteristicMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public TypeElementCharacteristicMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "TypeElementCharacteristicMatcherTest match",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "publicField");
                                                              MatcherAssert.assertThat("Precondition: should have found one field", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of type ExecutableElement", result.get(0) instanceof VariableElement);


                                                              MatcherAssert.assertThat("Should have found matching type", Matchers.getRawTypeMatcher(new FrameworkToolWrapper(processingEnv)).getMatcher().checkForMatchingCharacteristic(result.get(0), String.class));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeElementCharacteristicMatcherTest no match",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "publicField");
                                                              MatcherAssert.assertThat("Precondition: should have found one field", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of type ExecutableElement", result.get(0) instanceof VariableElement);


                                                              MatcherAssert.assertThat("Should not have found matching type", !Matchers.getRawTypeMatcher(new FrameworkToolWrapper(processingEnv)).getMatcher().checkForMatchingCharacteristic(result.get(0), Boolean.class));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                }

        );


    }

    @Test
    public void test() {
        super.test();
    }
}
