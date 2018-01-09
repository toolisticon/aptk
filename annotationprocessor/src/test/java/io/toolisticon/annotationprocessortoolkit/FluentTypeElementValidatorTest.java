package io.toolisticon.annotationprocessortoolkit;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.internal.FrameworkToolWrapper;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.ElementUtils;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.validators.FluentTypeElementValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link FluentTypeElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentTypeElementValidatorTest extends AbstractAnnotationProcessorUnitTest {


    public FluentTypeElementValidatorTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "has / hasn't modifier",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "check if type is assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldFail()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).isAssignableTo(Object.class).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).isAssignableTo(String.class).getValidationResult(), Matchers.is(false));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "check for noarg constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), element).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(
                                                                      ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClass"))
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.CLASS)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              TypeElement _2ndTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), _2ndTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                                              elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClassWithNoNoargConstructor"))
                                                                      .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.CLASS).getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              TypeElement _3rdTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(new FrameworkToolWrapper(processingEnv), _3rdTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(false));


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
