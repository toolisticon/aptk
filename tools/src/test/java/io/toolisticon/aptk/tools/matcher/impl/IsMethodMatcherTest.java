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
import javax.lang.model.element.TypeElement;
import java.util.List;


/**
 * Unit test for {@link IsMethodMatcher}.
 */
public class IsMethodMatcherTest {


    public void testMethod(String parameter) {

    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingMethod() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(IsParameterMatcherTest.class);
                                List<? extends Element> methods = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "testMethod");
                                MatcherAssert.assertThat("Precondition: found test method", methods.size() == 1);

                                MatcherAssert.assertThat("Should return true for method : ", AptkCoreMatchers.IS_METHOD.getMatcher().check(methods.get(0)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingMethod() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for non method : ", !AptkCoreMatchers.IS_METHOD.getMatcher().check(element));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkNullValuedElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_METHOD.getMatcher().check(null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
