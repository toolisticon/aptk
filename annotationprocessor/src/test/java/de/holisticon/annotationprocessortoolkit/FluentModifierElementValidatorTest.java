package de.holisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import de.holisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import de.holisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.validators.FluentModifierElementValidator;
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
public class FluentModifierElementValidatorTest extends AbstractAnnotationProcessorUnitTest {


    public FluentModifierElementValidatorTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "has and hasn't modifier : succeeding validation",
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
                                                MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));


                                            }
                                        })
                                        .build()


                        },
                        {
                                "has and hasn't modifier : failing validation for executable element",
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


                                                              // check for nonexistence
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "has and hasn't modifier : failing validation for type element",
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

                                                              // Do the same on TypeElement
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "has / hasn't modifier",
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

                                                              // check for existence of parameters
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                                              // check for nonexistence
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), testElement).hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));

                                                              // Do the same on TypeElement
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentModifierElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


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
