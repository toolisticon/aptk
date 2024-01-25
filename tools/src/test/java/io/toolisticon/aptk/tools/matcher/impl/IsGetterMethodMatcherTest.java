package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link IsInterfaceMatcher}.
 */
public class IsGetterMethodMatcherTest {


    public static abstract class TestClass {
        public void getNoReturnType() {

        }

        private int getNonVisible() {
            return 0;
        }

        public int getHasParam(String param) {
            return 0;
        }

        public static int getIsStatic() {
            return 0;
        }

        public abstract int getIsAbstract();

        public int getValid() {
            return 0;
        }

        public boolean getGetterValid() {
            return true;
        }

        public boolean hasGetterValid() {
            return true;
        }

        public boolean isGetterValid() {
            return true;
        }

        public String hasGetterInvalid() {
            return "";
        }

        public String isGetterInvalid() {
            return "";
        }

    }


    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void checkMatchingInterface() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);


                                checkGetter(typeElement, "getNoReturnType", false);
                                checkGetter(typeElement, "getNonVisible", false);
                                checkGetter(typeElement, "getIsStatic", false);
                                checkGetter(typeElement, "getIsAbstract", false);
                                checkGetter(typeElement, "getValid", true);
                                checkGetter(typeElement, "getGetterValid", true);
                                checkGetter(typeElement, "hasGetterValid", true);
                                checkGetter(typeElement, "isGetterValid", true);
                                checkGetter(typeElement, "hasGetterInvalid", false);
                                checkGetter(typeElement, "isGetterInvalid", false);

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

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_GETTER_METHOD.getMatcher().check(null));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    public static void checkGetter(TypeElement typeElement, String methodName, boolean expectedResult) {

        List<ExecutableElement> executableElements =
                FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                        .applyFilter(AptkCoreMatchers.IS_METHOD)
                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(methodName)
                        .getResult();

        MatcherAssert.assertThat("Precondition: Single result for " + methodName, executableElements.size() == 1);

        MatcherAssert.assertThat("methodName", AptkCoreMatchers.IS_GETTER_METHOD.getMatcher().check(executableElements.get(0)), Matchers.is(expectedResult));

    }

}
