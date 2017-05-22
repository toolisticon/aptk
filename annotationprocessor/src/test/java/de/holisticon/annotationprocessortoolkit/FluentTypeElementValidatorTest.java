package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filter;
import de.holisticon.annotationprocessortoolkit.validators.FluentTypeElementValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.validators.FluentTypeElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentTypeElementValidatorTest extends AbstractAnnotationProcessorTestBaseClass {


    public FluentTypeElementValidatorTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(FluentTypeElementValidator.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "has / hasn't modifier",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "check if type is assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(Object.class).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(String.class).getValidationResult(), Matchers.is(false));

                                    }
                                },
                                false


                        },
                        {
                                "check for noarg constructor",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                        List<? extends Element> elements = createFluentElementFilter(
                                                ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClass"))
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.CLASS)
                                                .getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        TypeElement _2ndTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                        MatcherAssert.assertThat(getFluentTypeValidator(_2ndTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(true));

                                        elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "EmbeddedClassWithNoNoargConstructor"))
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.CLASS).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        TypeElement _3rdTestElement = ElementUtils.CastElement.castToTypeElement(elements.get(0));

                                        MatcherAssert.assertThat(getFluentTypeValidator(_3rdTestElement).hasNoArgConstructor().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                true


                        }
                }

        );


    }
}
