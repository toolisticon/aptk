package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link ByNumberOfParametersMatcher}.
 */
@RunWith(Parameterized.class)
public class ByNumberOfParametersMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public ByNumberOfParametersMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public void noParameters() {

    }

    public void oneParameters(boolean first) {

    }

    public void twoParameters(boolean first, boolean second) {

    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "ByNumberOfParametersMatcher -  method with 0 parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                                                              MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                                                              // find field
                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "noParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                                                              MatcherAssert.assertThat("Should match 0 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                                                              MatcherAssert.assertThat("Should match 1 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                                                              MatcherAssert.assertThat("Should match 2 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));
                                                          }
                                                      }
                                        )
                                        .build()

                        },

                        {
                                "ByNumberOfParametersMatcher -  method with 1 parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                                                              MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                                                              // find field
                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "oneParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                                                              MatcherAssert.assertThat("Should match 0 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                                                              MatcherAssert.assertThat("Should match 1 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                                                              MatcherAssert.assertThat("Should match 2 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));
                                                          }
                                                      }
                                        )
                                        .build()

                        },

                        {
                                "ByNumberOfParametersMatcher -  method with 2 parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                                                              MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                                                              // find field
                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "twoParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                                                              MatcherAssert.assertThat("Should match 0 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                                                              MatcherAssert.assertThat("Should match 1 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                                                              MatcherAssert.assertThat("Should match 2 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));
                                                          }
                                                      }
                                        )
                                        .build()

                        },


                        {
                                "ByParameterTypeMatcher null values",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                                              MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                                                              MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                                                              MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(null, 1));
                                                              MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(null, null));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "getStringRepresentationOfPassedCharacteristic null",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "getStringRepresentationOfPassedCharacteristic get String representation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().getStringRepresentationOfPassedCharacteristic(1), Matchers.is("1"));


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
