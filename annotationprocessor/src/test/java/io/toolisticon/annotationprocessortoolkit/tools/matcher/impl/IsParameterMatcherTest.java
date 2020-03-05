package io.toolisticon.annotationprocessortoolkit.tools.matcher.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Unit test for {@link IsParameterMatcher}.
 */
public class IsParameterMatcherTest {


    public void testMethod(String parameter) {

    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(IsParameterMatcherTest.class);
                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "testMethod");
                MatcherAssert.assertThat("Precondition: found test method", methods.size() == 1);
                ExecutableElement testMethod = (ExecutableElement) methods.get(0);
                MatcherAssert.assertThat("Precondition: found at least one parameter", ((ExecutableElement) methods.get(0)).getParameters().size() >= 1);

                MatcherAssert.assertThat("Should return true for parameter : ", CoreMatchers.IS_PARAMETER.getMatcher().check(testMethod.getParameters().get(0)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingParameter_class() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for non parameter : ", !CoreMatchers.IS_PARAMETER.getMatcher().check(element));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkNullValuedElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_PARAMETER.getMatcher().check(null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

}