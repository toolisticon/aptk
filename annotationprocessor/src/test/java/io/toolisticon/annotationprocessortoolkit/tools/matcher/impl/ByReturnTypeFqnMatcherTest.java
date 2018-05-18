package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
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
 * Unit test for {@link ByReturnTypeFqnMatcher}.
 */
@RunWith(Parameterized.class)
public class ByReturnTypeFqnMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public ByReturnTypeFqnMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "ByReturnTypeMirrorMatcher match",
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
                                                              MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                                              MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                                                              MatcherAssert.assertThat("Should have found matching return type", CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, String.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "ByReturnTypeMirrorMatcher no match",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);


                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                                              MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                                              MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                                                              MatcherAssert.assertThat("Should have found a non matching return type", !CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Boolean.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "ByReturnTypeMirrorMatcher null values",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                                              MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                                              MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                                              ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                                              MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                                              MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));

                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, String.class.getCanonicalName()));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                                                              MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, null));

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


                                                              MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.is(""));

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

                                                              MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(String.class.getCanonicalName()), Matchers.is(String.class.getCanonicalName()));


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
