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
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Unit test for {@link IsFieldMatcher}.
 */
public class IsFieldMatcherTest {


    public String testField = "XXX";


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingInterface() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(IsFieldMatcherTest.class);
                List<? extends Element> fieldList = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "testField");
                MatcherAssert.assertThat("Precondition: must have found a field", fieldList.size() >= 1);


                MatcherAssert.assertThat("Should return true for field : ", CoreMatchers.IS_FIELD.getMatcher().check(fieldList.get(0)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingMethod_class() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for non field : ", !CoreMatchers.IS_FIELD.getMatcher().check(element));

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

                MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_FIELD.getMatcher().check(null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }
}