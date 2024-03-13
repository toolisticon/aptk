package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;


/**
 * Unit test for {@link IsVariableElementMatcher}.
 */

public class IsVariableElementMatcherTest {

    private String field = "sds";

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingVariableElement_field() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // find field
                                List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(TypeUtils.TypeRetrieval.getTypeElement(IsVariableElementMatcherTest.class), ElementKind.FIELD);
                                MatcherAssert.assertThat("Precondition: should have found one field", result.size() >= 1);
                                MatcherAssert.assertThat("Precondition: found method has to be of type VariableElement", result.get(0) instanceof VariableElement);

                                MatcherAssert.assertThat("Should return true for field : ", AptkCoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(result.get(0)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingVariableElement_class() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for non VariableElement : ", !AptkCoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(element));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingVariableElement_nullValuedElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_VARIABLE_ELEMENT.getMatcher().check(null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

}
