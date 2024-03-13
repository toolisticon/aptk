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
 * Unit test for {@link IsSetterMethodMatcher}.
 */
public class IsSetterMethodMatcherTest {


    public static abstract class TestClass {
        public String setWithReturnType(String param) {
            return "";
        }

        private void setNonVisible(String param) {
        }

        public void setHasMultipleParam(String param1, String param2) {

        }

        public void setHasNoParam() {

        }

        public static void setIsStatic(String param) {

        }

        public abstract void setIsAbstract(String param);


        public void xxxNameInvalid(String param) {

        }

        public void setValid(String param) {
        }
    }


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
    public void checkMatchingInterface() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestClass.class);

                                checkSetter(typeElement, "setWithReturnType", false);
                                checkSetter(typeElement, "setNonVisible", false);
                                checkSetter(typeElement, "setHasMultipleParam", false);
                                checkSetter(typeElement, "setHasNoParam", false);
                                checkSetter(typeElement, "setIsStatic", false);
                                checkSetter(typeElement, "setIsAbstract", false);
                                checkSetter(typeElement, "xxxNameInvalid", false);
                                checkSetter(typeElement, "setValid", true);

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

                                MatcherAssert.assertThat("Should return false for null valued element : ", !AptkCoreMatchers.IS_SETTER_METHOD.getMatcher().check(null));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    public static void checkSetter(TypeElement typeElement, String methodName, boolean expectedResult) {

        List<ExecutableElement> executableElements =
                FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                        .applyFilter(AptkCoreMatchers.IS_METHOD)
                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(methodName)
                        .getResult();

        MatcherAssert.assertThat("Precondition: Single result for " + methodName, executableElements.size() == 1);

        MatcherAssert.assertThat("methodName", AptkCoreMatchers.IS_SETTER_METHOD.getMatcher().check(executableElements.get(0)), Matchers.is(expectedResult));

    }

}
