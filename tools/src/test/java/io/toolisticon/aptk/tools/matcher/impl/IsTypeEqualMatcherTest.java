package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;


/**
 * Unit test for {@link IsTypeEqualMatcher}.
 */
public class IsTypeEqualMatcherTest {


    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingEqualToCase() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return true for matching assignable to case : ", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(String.class), String.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public static class MatcherTestCases {

        public int intType;
        public int[] intArray;
        public List<Long> genericType;
        public List<Long>[] genericTypeArray;

    }

    public static VariableElement getField(TypeElement typeElement, String fieldName) {
        return FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_FIELD)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(fieldName)
                .getResult().get(0);
    }

    @Test
    public void checkMatchingEqualToCase_intValue() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement testcaseElement = TypeUtils.TypeRetrieval.getTypeElement(MatcherTestCases.class);


                                MatcherAssert.assertThat("Should return true for matching assignable to case (primitive) : ", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(getField(testcaseElement, "intType"), int.class));
                                MatcherAssert.assertThat("Should return true for matching assignable to case (array) : ", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(getField(testcaseElement, "intArray"), int[].class));
                                MatcherAssert.assertThat("Should return true for matching assignable to case (generic type) : ", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(getField(testcaseElement, "genericType"), List.class));
                                MatcherAssert.assertThat("Should return true for matching assignable to case (generic type array) : ", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(getField(testcaseElement, "genericTypeArray"), List[].class));


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
                                MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(Object.class), String.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingAssignableToCase_Object() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(String.class), Object.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void checkNullValuedElementAndClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(null, String.class));
                                MatcherAssert.assertThat("Should return false for null valued assignable to class : ", !AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(element, null));
                                MatcherAssert.assertThat("Should return false for null valued element and assignable to class : ", !AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(null, null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void getStringRepresentationOfPassedCharacteristic_nullValue() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should not have found matching parameters", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void getStringRepresentationOfPassedCharacteristic_getSTringRepresentation() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should have created valid string representation", AptkCoreMatchers.IS_TYPE_EQUAL.getMatcher().getStringRepresentationOfPassedCharacteristic(String.class), Matchers.is("java.lang.String"));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


}

