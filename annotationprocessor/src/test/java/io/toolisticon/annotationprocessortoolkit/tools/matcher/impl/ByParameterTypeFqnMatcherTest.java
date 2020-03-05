package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.Utilities;
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
 * Unit test for {@link ByParameterTypeFqnMatcher}.
 */
public class ByParameterTypeFqnMatcherTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void byParameterTypeMatcher_match() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: found method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);
                MatcherAssert.assertThat("Precondition: first parameter must be of type Boolean but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getParameters().get(0).asType().toString().equals(Boolean.class.getCanonicalName()));
                MatcherAssert.assertThat("Precondition: second parameter must be of type String but is " + executableElement.getParameters().get(1).asType().toString(), executableElement.getParameters().get(1).asType().toString().equals(String.class.getCanonicalName()));


                MatcherAssert.assertThat("Should have found matching parameters", CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class.getCanonicalName(), String.class.getCanonicalName())));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void byParameterTypeMatcher_noMatch() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(String.class.getCanonicalName(), Boolean.class.getCanonicalName())));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class.getCanonicalName())));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, Utilities.convertVarargsToArray(Boolean.class.getCanonicalName(), String.class.getCanonicalName(), String.class.getCanonicalName())));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byParameterTypeMatcher_nullValues() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have 2 parameters", executableElement.getParameters().size() == 2);

                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, Utilities.convertVarargsToArray(String.class.getCanonicalName(), Boolean.class.getCanonicalName())));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().checkForMatchingCharacteristic(null, null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_nullValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_getStringRepreentation() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.BY_PARAMETER_TYPE_FQN.getMatcher().getStringRepresentationOfPassedCharacteristic(Utilities.convertVarargsToArray(String.class.getCanonicalName(), Boolean.class.getCanonicalName())), Matchers.is("[java.lang.String, java.lang.Boolean]"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

}
