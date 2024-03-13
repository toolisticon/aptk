package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
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
 * Unit test for {@link ByReturnTypeFqnMatcher}.
 */

public class ByReturnTypeFqnMatcherTest {

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void byReturnTypeMirrorMatcher_match() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // find field
                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                                MatcherAssert.assertThat("Should have found matching return type", AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, String.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byReturnTypeMirrorMatcher_noMatch() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);


                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                                MatcherAssert.assertThat("Should have found a non matching return type", !AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Boolean.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byReturnTypeMirrorMatcher_nullValues() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));

                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, String.class.getCanonicalName()));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                                MatcherAssert.assertThat("Should not have found matching parameters", !AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, null));

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

                                MatcherAssert.assertThat("Should not have found matching parameters", AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.is(""));

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

                                MatcherAssert.assertThat("Should have created valid string representation", AptkCoreMatchers.BY_RETURN_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(String.class.getCanonicalName()), Matchers.is(String.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

}
