package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Unit test for {@link IsPackageElementMatcher}.
 */
public class IsPackageElementMatcherTest {

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingPackageElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // find field
                Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(element, ElementKind.PACKAGE);
                MatcherAssert.assertThat("Precondition: should have found one method", result != null);
                MatcherAssert.assertThat("Precondition: found method has to be of type PackageElement", result instanceof PackageElement);

                MatcherAssert.assertThat("Should return true for method : ", AptkCoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(result));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkMismatchingPackageElement_class() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for non PackageElement : ", !AptkCoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(element));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void checkNullValuedElement() {

        unitTestBuilder.defineTest(
                        new UnitTest<TypeElement>() {
                            @Override
                            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_PACKAGE_ELEMENT.getMatcher().check(null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
