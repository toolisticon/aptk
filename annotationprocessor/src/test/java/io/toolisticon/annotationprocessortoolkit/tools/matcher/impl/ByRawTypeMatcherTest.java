package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.TestAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Unit test for {@link ByRawTypeMatcher}.
 */
@RunWith(Parameterized.class)
public class ByRawTypeMatcherTest extends AbstractAnnotationProcessorUnitTest {


    public ByRawTypeMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getStringRepresentationOfPassedCharacteristic : happyPath  ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat("Should return cannonical class name of annotation class", CoreMatchers.BY_RAW_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(ByRawTypeMatcherTest.class).equals(ByRawTypeMatcherTest.class.getCanonicalName()));


                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "getStringRepresentationOfPassedCharacteristic : passed null value => should return null",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Should return null for null valued parameter", CoreMatchers.BY_RAW_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(null) == null);

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "checkForMatchingCharacteristic : match",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement tmpElement = TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(ByRawTypeMatcherTest.class);
                                                              MatcherAssert.assertThat("Should find match correctly", CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(tmpElement, ByRawTypeMatcherTest.class));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "checkForMatchingCharacteristic : mismatch",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeElement tmpElement = TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(String.class);
                                                              MatcherAssert.assertThat("Should find match correctly", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(tmpElement, ByRawTypeMatcherTest.class));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "checkForMatchingCharacteristic : null valued element",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Should return false in case of null valued element", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(null, TestAnnotation.class));


                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "checkForMatchingCharacteristic : null valued raw type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Should return false in case of null valued annotation", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(element, null));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "checkForMatchingCharacteristic : null valued element and raw type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {
                                                              MatcherAssert.assertThat("Should return false in case of null valued parameters", !CoreMatchers.BY_RAW_TYPE.getMatcher().checkForMatchingCharacteristic(null, null));

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





