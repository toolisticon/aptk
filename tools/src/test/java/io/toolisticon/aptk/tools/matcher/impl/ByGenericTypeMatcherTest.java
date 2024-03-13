package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.generics.GenericType;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link ByGenericTypeMatcher}.
 */
public class ByGenericTypeMatcherTest {

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_createStringRepresentationCorrectly() {
        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

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


                                MatcherAssert.assertThat(AptkCoreMatchers.BY_GENERIC_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(genericTypeToConvert), org.hamcrest.Matchers.is("java.util.Map<? extends java.lang.StringBuilder, java.util.Comparator<? super java.util.List<?>>>"));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void getStringRepresentationOfPassedCharacteristic_shouldBeAbleToCompareGenericType() {
        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
                                        .getResult();

                                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                GenericType genericType = TypeUtils.Generics.createGenericType(Map.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(
                                                Comparator.class,
                                                TypeUtils.Generics.createGenericType(Long.class)
                                        )
                                );

                                MatcherAssert.assertThat("Should compare successful", AptkCoreMatchers.BY_GENERIC_TYPE.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                            }


                        })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_shoulNotdBeAbleToCompareGenericType() {
        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
                                        .getResult();

                                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                GenericType genericType = TypeUtils.Generics.createGenericType(Map.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(
                                                Comparator.class,
                                                TypeUtils.Generics.createGenericType(Double.class)
                                        )

                                );

                                MatcherAssert.assertThat("Should not compare successful", !AptkCoreMatchers.BY_GENERIC_TYPE.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                            }


                        })
                .compilationShouldSucceed()
                .executeTest();

    }


    @Test
    public void getStringRepresentationOfPassedCharacteristic_shouldBeAbleToCompareGenericTypeWithWildcards() {
        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

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

                                MatcherAssert.assertThat("Should compare successful", AptkCoreMatchers.BY_GENERIC_TYPE.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));

                            }


                        })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_shouldNotBeAbleToCompareGenericTypeWithWildcards() {
        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter")
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

                                MatcherAssert.assertThat("Should not compare successful", !AptkCoreMatchers.BY_GENERIC_TYPE.getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));


                            }


                        })
                .compilationShouldSucceed()
                .executeTest();

    }

}
