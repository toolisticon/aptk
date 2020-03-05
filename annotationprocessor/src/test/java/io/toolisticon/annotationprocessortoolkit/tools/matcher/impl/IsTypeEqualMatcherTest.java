package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;


/**
 * Unit test for {@link IsAssignableToMatcher}.
 */
public class IsTypeEqualMatcherTest {


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingEqualToCase() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return true for matching assignable to case : ", CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(String.class), String.class));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void checkMismatchingAssignableToCase() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(Object.class), String.class));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingAssignableToCase_Object() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for mismatching assignable to case : ", !CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(TypeUtils.TypeRetrieval.getTypeElement(String.class), Object.class));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void checkNullValuedElementAndClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(null, String.class));
                MatcherAssert.assertThat("Should return false for null valued assignable to class : ", !CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(element, null));
                MatcherAssert.assertThat("Should return false for null valued element and assignable to class : ", !CoreMatchers.IS_TYPE_EQUAL.getMatcher().checkForMatchingCharacteristic(null, null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void getStringRepresentationOfPassedCharacteristic_nullValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.IS_TYPE_EQUAL.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    public void getStringRepresentationOfPassedCharacteristic_getSTringRepresentation() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.IS_TYPE_EQUAL.getMatcher().getStringRepresentationOfPassedCharacteristic(String.class), Matchers.is("java.lang.String"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}

