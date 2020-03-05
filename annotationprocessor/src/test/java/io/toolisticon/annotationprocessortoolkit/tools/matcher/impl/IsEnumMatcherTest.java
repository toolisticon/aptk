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
 * Unit test for {@link IsEnumMatcher}.
 */
public class IsEnumMatcherTest {


    public enum TestEnum {
        TEST
    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingEnum() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(IsEnumMatcherTest.class);
                List<? extends Element> enumList = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "TestEnum");
                MatcherAssert.assertThat("Precondition: must have found a enum", enumList.size() >= 1);


                MatcherAssert.assertThat("Should return true for enum : ", CoreMatchers.IS_ENUM.getMatcher().check(enumList.get(0)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMisatchingEnum_class() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for non enum : ", !CoreMatchers.IS_ENUM.getMatcher().check(element));

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

                MatcherAssert.assertThat("Should return false for null valued element : ", !CoreMatchers.IS_ENUM.getMatcher().check(null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}