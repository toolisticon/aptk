package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link io.toolisticon.annotationprocessortoolkit.tools.ElementUtils.AccessTypeHierarchy}.
 */
@RunWith(Parameterized.class)
public class ElementUtils_AccessTypeHierarchyTest extends AbstractAnnotationProcessorUnitTest {

    public ElementUtils_AccessTypeHierarchyTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    public interface TestInterface {

    }


    public static class TopMostType implements TestInterface {

    }

    public static class InbetweenType extends TopMostType {

    }

    public static class TestType extends InbetweenType {

    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getDirectSuperTypeElements test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // find field
                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(TestType.class);

                                                              MatcherAssert.assertThat("PRECONDITION : typelement must not be null", typeElement != null);

                                                              TypeElement inBetweenTypeElement = TypeUtils.TypeRetrieval.getTypeElement(InbetweenType.class);

                                                              MatcherAssert.assertThat(Arrays.asList(ElementUtils.AccessTypeHierarchy.getDirectSuperTypeElements(typeElement)), Matchers.contains(
                                                                      inBetweenTypeElement));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getSuperTypeElements test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getDirectSuperTypeElementsOfKindType test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getDirectSuperTypeElementsOfKindType test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getSuperTypeElementsOfKindType test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getSuperTypeElementsOfKindType test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },


                }

        );


    }

    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }
}
