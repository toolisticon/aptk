package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.Utilities;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link ByParameterTypeMatcher}.
 */

public class ByParameterTypeMatcherTest {


    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void byParameterTypeMatcher_match() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // find field
                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);
                                MatcherAssert.assertThat("Precondition: first parameter must be of type Boolean but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getParameters().get(0).asType().toString().equals(Boolean.class.getCanonicalName()));
                                MatcherAssert.assertThat("Precondition: second parameter must be of type String but is " + executableElement.getParameters().get(1).asType().toString(), executableElement.getParameters().get(1).asType().toString().equals(String.class.getCanonicalName()));


                                MatcherAssert.assertThat("Should have found matching parameters", AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class, String.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    private static class GenericTypeTestClass {

        public void methodWithGenericParameter(List<Long> values, String secondParameter, int intValue, int[] intArray, List<Long>[] arrayWithGenerics) {

        }

    }

    @Test
    public void byParameterTypeMatcher_match_withGenericType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // First get test class
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(GenericTypeTestClass.class);
                                MatcherAssert.assertThat("Precondition: should have found the  testclass", typeElement, Matchers.notNullValue());


                                // find field
                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "methodWithGenericParameter");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have 5 parameters", executableElement.getParameters().size() == 5);
                                MatcherAssert.assertThat("Precondition: first parameter must be of type List but is " + executableElement.getParameters().get(0).asType().toString(), TypeUtils.getTypes().erasure(executableElement.getParameters().get(0).asType()).toString().equals(List.class.getCanonicalName()));
                                MatcherAssert.assertThat("Precondition: second parameter must be of type String but is " + executableElement.getParameters().get(1).asType().toString(), executableElement.getParameters().get(1).asType().toString().equals(String.class.getCanonicalName()));


                                MatcherAssert.assertThat("Should have found matching parameters", AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(List.class, String.class, int.class, int[].class, List[].class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void byParameterTypeMatcher_noMatch() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(String.class, Boolean.class)));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class)));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class, String.class, String.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byParameterTypeMatcher_nullValues() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(null, Utilities.convertVarargsToArray(String.class, Boolean.class)));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().checkForMatchingCharacteristic(null, null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_nullValue() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should not have found matching parameters", AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_getStringRepresentation() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should have created valid string representation", AptkCoreMatchers.BY_PARAMETER_TYPE.getMatcher().getStringRepresentationOfPassedCharacteristic(Utilities.convertVarargsToArray(String.class, Boolean.class)), Matchers.is("[java.lang.String, java.lang.Boolean]"));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
