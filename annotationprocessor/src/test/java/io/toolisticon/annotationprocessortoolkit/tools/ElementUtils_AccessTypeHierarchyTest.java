package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;

/**
 * Unit test for {@link io.toolisticon.annotationprocessortoolkit.tools.ElementUtils.AccessTypeHierarchy}.
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


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationValueUtilsTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void getDirectSuperTypeElements() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
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
