package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.Utilities;
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
 * Unit test for {@link ByParameterTypeMatcher}.
 */
@RunWith(Parameterized.class)
public class ByParameterTypeMirrorMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public ByParameterTypeMirrorMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "ByParameterTypeMirrorMatcher match",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // find field
                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                                              MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);
                                                              MatcherAssert.assertThat("Precondition: first parameter must be of type Boolean but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getParameters().get(0).asType().toString().equals(Boolean.class.getCanonicalName()));
                                                              MatcherAssert.assertThat("Precondition: second parameter must be of type String but is " + executableElement.getParameters().get(1).asType().toString(), executableElement.getParameters().get(1).asType().toString().equals(String.class.getCanonicalName()));


                                                              MatcherAssert.assertThat("Should have found matching parameters", CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement,  Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class), TypeUtils.TypeRetrieval.getTypeMirror(String.class))));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "ByParameterTypeMirrorMatcher no match",
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

                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(String.class), TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class))));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class))));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class), TypeUtils.TypeRetrieval.getTypeMirror(String.class), TypeUtils.TypeRetrieval.getTypeMirror(String.class))));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "ByParameterTypeMirrorMatcher null values",
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

                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(null, Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(String.class), TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class))));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(null, null));

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


                                                              MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

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

                                                              MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.BY_PARAMETER_TYPE_MIRROR.getMatcher().getStringRepresentationOfPassedCharacteristic(Utilities.convertVarargsToArray(TypeUtils.TypeRetrieval.getTypeMirror(String.class), TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class))), Matchers.is("[java.lang.String, java.lang.Boolean]"));


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
