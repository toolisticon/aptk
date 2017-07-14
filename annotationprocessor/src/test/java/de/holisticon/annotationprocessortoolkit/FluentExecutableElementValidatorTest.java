package de.holisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.TypeUtils;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
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
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator}.
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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasVoidReturnType().getValidationResult(), Matchers.is(true));

                                                              // check non null value
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(false));


                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(false));


                                                              TypeUtils.getTypeUtils(processingEnv).doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasVoidReturnType().getValidationResult(), Matchers.is(false));

                                                              // check non null value
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(true));

                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(true));

                                                              // check for assignable supertype of return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasReturnType(Object.class).getValidationResult(), Matchers.is(true));

                                                              // check specific return type
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).hasReturnType(Boolean.class).getValidationResult(), Matchers.is(false));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().getValidationResult(), Matchers.is(true));

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
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, constructorElement).isMethod().getValidationResult(), Matchers.is(false));


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
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, constructorElement).isConstructor().getValidationResult(), Matchers.is(true));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isConstructor().getValidationResult(), Matchers.is(false));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for method
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasName("methodWithReturnTypeAndParameters").getValidationResult(), Matchers.is(true));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                                              // check if element name doesn't match
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasName("XXX").getValidationResult(), Matchers.is(false));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters(Boolean.class, String.class).getValidationResult(), Matchers.is(true));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters(Boolean.class).getValidationResult(), Matchers.is(false));

                                                              // check non matching parameter types
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters(String.class, Boolean.class).getValidationResult(), Matchers.is(false));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                                              // check non matching parameter length
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(false));


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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                                      .getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

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
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD).getResult();

                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                                              // check for nonexistence
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(FluentExecutableElementValidator.createFluentExecutableElementValidator(processingEnv, testElement).isMethod().hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


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
