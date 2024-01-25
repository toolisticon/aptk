package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;


/**
 * Unit test for {@link IsAssignableToMatcher}.
 */

public class IsAssignableToMatcherTest {

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingAssignableToCase() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return true for matching assignable to case : ", AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(String.class), Object.class));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void checkMismatchingAssignableToCase() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(Object.class), String.class));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void checkNullValuedElementAndClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(null, String.class));
                                MatcherAssert.assertThat("Should return false for null valued assignable to class : ", !AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(element, null));
                                MatcherAssert.assertThat("Should return false for null valued element and assignable to class : ", !AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().checkForMatchingCharacteristic(null, null));

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

                                MatcherAssert.assertThat("Should not have found matching parameters", AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

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
                                MatcherAssert.assertThat("Should not have found matching parameters", AptkCoreMatchers.IS_ASSIGNABLE_TO.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }
}

