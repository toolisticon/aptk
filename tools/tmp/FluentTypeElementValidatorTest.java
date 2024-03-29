package io.toolisticon.aptk;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.aptk.filter.FluentElementFilter;
import io.toolisticon.aptk.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.aptk.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.aptk.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.aptk.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.aptk.tools.ElementUtils;
import io.toolisticon.aptk.tools.characteristicsfilter.Filters;
import io.toolisticon.aptk.validators.FluentTypeElementValidator;
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


                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


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

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).isAssignableTo(Object.class).getValidationResult(), Matchers.is(true));
                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).isAssignableTo(String.class).getValidationResult(), Matchers.is(false));

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

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(element).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                                              List<? extends Element> elements = FluentElementFilter.createFluentFilter(
                                                                      ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClass"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.CLASS)
                                                                      .getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              TypeElement _2ndTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(_2ndTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                                              elements = FluentElementFilter.createFluentFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClassWithNoNoargConstructor"))
                                                                      .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.CLASS).getResult();
                                                              MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                                              TypeElement _3rdTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                                              MatcherAssert.assertThat(new FluentTypeElementValidator(_3rdTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(false));


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
