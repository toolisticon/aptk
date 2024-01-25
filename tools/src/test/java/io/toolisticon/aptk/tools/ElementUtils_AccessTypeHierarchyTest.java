package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;

/**
 * Unit test for {@link ElementUtils.AccessTypeHierarchy}.
 */
public class ElementUtils_AccessTypeHierarchyTest {


    public interface TestInterface {

    }


    public static class TopMostType implements TestInterface {

    }

    public static class InbetweenType extends TopMostType {

    }

    public static class TestType extends InbetweenType {

    }


    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationValueUtilsTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void getDirectSuperTypeElements() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                // find field
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement inBetweenTypeElement = TypeUtils.TypeRetrieval.getTypeElement(InbetweenType.class);

                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElements(typeElement)), Matchers.contains(
                                        inBetweenTypeElement));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void getSuperTypeElements() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                // find field
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement inBetweenTypeElement = TypeUtils.TypeRetrieval.getTypeElement(InbetweenType.class);
                                TypeElement topMostTypeElement = TypeUtils.TypeRetrieval.getTypeElement(TopMostType.class);
                                TypeElement objectTypeElement = TypeUtils.TypeRetrieval.getTypeElement(Object.class);
                                TypeElement testInterfaceTypeElement = TypeUtils.TypeRetrieval.getTypeElement(TestInterface.class);


                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getSuperTypeElements(typeElement)), Matchers.contains(
                                        inBetweenTypeElement,
                                        topMostTypeElement,
                                        objectTypeElement,
                                        testInterfaceTypeElement));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getDirectSuperTypeElementsOfKindType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                // Test 1 : has super type
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement inBetweenTypeElement = TypeUtils.TypeRetrieval.getTypeElement(InbetweenType.class);

                                MatcherAssert.assertThat(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElementOfKindType(typeElement), Matchers.equalTo(
                                        inBetweenTypeElement
                                ));

                                // Test 2 : has no super type
                                typeElement = TypeUtils.TypeRetrieval.getTypeElement(Object.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);


                                MatcherAssert.assertThat(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElementOfKindType(typeElement), Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getDirectSuperTypeElementsOfKindInterface() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                // Test 1: has no interface
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElementsOfKindInterface(typeElement)), Matchers.<TypeElement>empty());


                                // Test 2: has interface
                                typeElement = TypeUtils.TypeRetrieval.getTypeElement(TopMostType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement testInterfaceTypeElement = TypeUtils.TypeRetrieval.getTypeElement(TestInterface.class);

                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElementsOfKindInterface(typeElement)), Matchers.<TypeElement>contains(testInterfaceTypeElement));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getSuperTypeElementsOfKindType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // find field
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement inBetweenTypeElement = TypeUtils.TypeRetrieval.getTypeElement(InbetweenType.class);
                                TypeElement topMostTypeElement = TypeUtils.TypeRetrieval.getTypeElement(TopMostType.class);
                                TypeElement objectTypeElement = TypeUtils.TypeRetrieval.getTypeElement(Object.class);


                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getSuperTypeElementsOfKindType(typeElement)), Matchers.contains(
                                        inBetweenTypeElement,
                                        topMostTypeElement,
                                        objectTypeElement
                                ));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getSuperTypeElementsOfKindInterface() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                                // find field
                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                TypeElement testInterfaceTypeElement = TypeUtils.TypeRetrieval.getTypeElement(TestInterface.class);


                                MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getSuperTypeElementsOfKindInterface(typeElement)), Matchers.contains(
                                        testInterfaceTypeElement
                                ));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

}
