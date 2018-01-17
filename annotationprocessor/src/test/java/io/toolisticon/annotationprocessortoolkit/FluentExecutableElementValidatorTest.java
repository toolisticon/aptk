package io.toolisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.TypeUtils;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link FluentExecutableElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentExecutableElementValidatorTest extends AbstractAnnotationProcessorUnitTest {


    public FluentExecutableElementValidatorTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "Validate void return type method",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(true));

                                                              // check non null value
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(false));


                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(false));


                                                              TypeUtils.getTypeUtils().doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "Validate non void return type method",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // check null value
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(false));

                                                              // check non null value
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(true));

                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(true));

                                                              // check for assignable supertype of return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasReturnType(Object.class).getValidationResult(), Matchers.is(true));

                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).hasReturnType(Boolean.class).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "isMethod: Validate when ExecutableElement is method",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().getValidationResult(), Matchers.is(true));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "isMethod: Validate when ExecutableElement isn't method",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                                              MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                                              ExecutableElement constructorElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(constructorElement).isMethod().getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "isConstructor: Validate when ExecutableElement is constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> elements = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                                              MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                                              ExecutableElement constructorElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(constructorElement).isConstructor().getValidationResult(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "isConstructor: Validate when ExecutableElement isn't constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isConstructor().getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasName: Validate if element has name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasName("methodWithReturnTypeAndParameters").getValidationResult(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasName: Validate if element hasn't name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // check if element name doesn't match
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasName("XXX").getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasParameters: validate when element has matching parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters(Boolean.class, String.class).getValidationResult(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasParameters: validate when element has non matching parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters(Boolean.class).getValidationResult(), Matchers.is(false));

                                                              // check non matching parameter types
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters(String.class, Boolean.class).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasParameters: validate when element has at least one parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasParameters: validate when element has no parameters",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasModifiers: Validate if element has / hasn't modifier",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasModifiers: Validate if element has / hasn't modifier",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // do preparations
                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD).getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for nonexistence
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(testElement).isMethod().hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        }

                }

        );


    }


    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("AnnotationProcessorTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

}
