package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Unit test for {@link ByNumberOfParametersMatcher}.
 */
public class ByNumberOfParametersMatcherTest {


    public void noParameters() {

    }

    public void oneParameters(boolean first) {

    }

    public void twoParameters(boolean first, boolean second) {

    }


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void byNumberOfParametersMatcher_methodWith0Parameters() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "noParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                MatcherAssert.assertThat("Should match 0 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                MatcherAssert.assertThat("Should match 1 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                MatcherAssert.assertThat("Should match 2 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void byNumberOfParametersMatcher_methodWith1Parameters() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "oneParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                MatcherAssert.assertThat("Should match 0 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                MatcherAssert.assertThat("Should match 1 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                MatcherAssert.assertThat("Should match 2 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));
            }

        })
                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void byNumberOfParametersMatcher_methodWith2Parameters() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(ByNumberOfParametersMatcherTest.class);
                MatcherAssert.assertThat("Precondition: should have found TypeElement", typeElement, Matchers.notNullValue());


                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "twoParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: found method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);


                MatcherAssert.assertThat("Should match 0 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 0));
                MatcherAssert.assertThat("Should match 1 parameters", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 1));
                MatcherAssert.assertThat("Should match 2 parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, 2));
            }

        })
                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void byNumberOfParametersMatcher_nullValue() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of type ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(null, 1));
                MatcherAssert.assertThat("Should return false", !CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().checkForMatchingCharacteristic(null, null));

            }

        })
                .compilationShouldSucceed()
                .testCompilation();

    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_nullValue() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {


                MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

            }

        })
                .compilationShouldSucceed()
                .testCompilation();

    }


    @Test
    public void getStringRepresentationOfPassedCharacteristic_getStringRepresentation() {
        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {


                MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_NUMBER_OF_PARAMETERS.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

            }

        })
                .compilationShouldSucceed()
                .testCompilation();

    }


}
