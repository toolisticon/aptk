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
 * Unit test for {@link ByReturnTypeMirrorMatcher}.
 */
public class ByReturnTypeMirrorMatcherTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void byReturnTypeMirrorMatcher_match() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // find field
                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                MatcherAssert.assertThat("Should have found matching return type", CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byReturnTypeMirrorMatcher_noMatch() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);


                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));


                MatcherAssert.assertThat("Should have found a non matching return type", !CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void byReturnTypeMirrorMatcher_nullValues() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {


                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters");
                MatcherAssert.assertThat("Precondition: should have found one method", result.size() == 1);
                MatcherAssert.assertThat("Precondition: dound method has to be of zype ExecutableElement", result.get(0) instanceof ExecutableElement);

                ExecutableElement executableElement = ElementUtils.CastElement.castElementList(result, ExecutableElement.class).get(0);
                MatcherAssert.assertThat("Precondition: method must have a return type", executableElement.getReturnType(), Matchers.notNullValue());
                MatcherAssert.assertThat("Precondition: return type must be of type String but is " + executableElement.getParameters().get(0).asType().toString(), executableElement.getReturnType().toString().equals(String.class.getCanonicalName()));

                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(null, TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(executableElement, null));
                MatcherAssert.assertThat("Should not have found matching parameters", !CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().checkForMatchingCharacteristic(null, null));

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


                MatcherAssert.assertThat("Should not have found matching parameters", CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().getStringRepresentationOfPassedCharacteristic(null), Matchers.is(""));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getStringRepresentationOfPassedCharacteristic_getStringRepresentation() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {


                MatcherAssert.assertThat("Should have created valid string representation", CoreMatchers.BY_RETURN_TYPE_MIRROR.getMatcher().getStringRepresentationOfPassedCharacteristic(TypeUtils.TypeRetrieval.getTypeMirror(String.class)), Matchers.is(String.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

}