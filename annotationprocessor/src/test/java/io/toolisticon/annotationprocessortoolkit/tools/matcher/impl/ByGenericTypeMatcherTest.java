package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.matcher.Matchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link ByGenericTypeMatcher}.
 */
@RunWith(Parameterized.class)
public class ByGenericTypeMatcherTest extends AbstractAnnotationProcessorUnitTest {

    public ByGenericTypeMatcherTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getStringRepresentationOfPassedCharacteristic : create String representation correctl",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(
                                                new AbstractUnitTestAnnotationProcessorClass() {
                                                    @Override
                                                    protected void testCase(TypeElement element) {


                                                        GenericType genericTypeToConvert = TypeUtils.Generics.createGenericType(Map.class,
                                                                TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                        TypeUtils.Generics.createGenericType(StringBuilder.class)
                                                                ),
                                                                TypeUtils.Generics.createGenericType(
                                                                        Comparator.class,
                                                                        TypeUtils.Generics.createWildcardWithSuperBound(
                                                                                TypeUtils.Generics.createGenericType(
                                                                                        List.class,
                                                                                        TypeUtils.Generics.createPureWildcard()
                                                                                )
                                                                        )

                                                                )
                                                        );


                                                        MatcherAssert.assertThat(Matchers.GENERIC_TYPE_MATCHER.getStringRepresentationOfPassedCharacteristic(genericTypeToConvert), org.hamcrest.Matchers.is("java.util.Map<? extends java.lang.StringBuilder, java.util.Comparator<? super java.util.List<?>>>"));


                                                    }

                                                }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              GenericType genericType = TypeUtils.Generics.createGenericType(Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Comparator.class,
                                                                              TypeUtils.Generics.createGenericType(Long.class)
                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should compare successful", Matchers.GENERIC_TYPE_MATCHER.checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {



                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              GenericType genericType = TypeUtils.Generics.createGenericType(Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Comparator.class,
                                                                              TypeUtils.Generics.createGenericType(Double.class)
                                                                      )

                                                              );

                                                              MatcherAssert.assertThat("Should not compare successful", !Matchers.GENERIC_TYPE_MATCHER.checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {



                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              GenericType genericType = TypeUtils.Generics.createGenericType(Map.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(StringBuilder.class)
                                                                      ),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Comparator.class,
                                                                              TypeUtils.Generics.createWildcardWithSuperBound(
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              List.class,
                                                                                              TypeUtils.Generics.createPureWildcard()
                                                                                      )
                                                                              )

                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should compare successful", Matchers.GENERIC_TYPE_MATCHER.checkForMatchingCharacteristic(method.getParameters().get(1), genericType));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>


                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(StringBuilder.class)
                                                                      ),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Comparator.class,
                                                                              TypeUtils.Generics.createWildcardWithSuperBound(
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              List.class,
                                                                                              TypeUtils.Generics.createWildcardWithExtendsBound(TypeUtils.Generics.createGenericType(String.class))
                                                                                      )
                                                                              )

                                                                      )
                                                              );

                                                              MatcherAssert.assertThat("Should not compare successful", !Matchers.GENERIC_TYPE_MATCHER.checkForMatchingCharacteristic(method.getParameters().get(1), genericType));


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
