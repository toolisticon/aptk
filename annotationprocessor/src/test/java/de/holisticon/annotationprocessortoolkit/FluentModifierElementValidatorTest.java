package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.validators.FluentModifierElementValidator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator}.
 */
@RunWith(Parameterized.class)
public class FluentModifierElementValidatorTest extends AbstractAnnotationProcessorTestBaseClass {


    public FluentModifierElementValidatorTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(FluentModifierElementValidator.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "has and hasn't modifier : succeeding validation",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "has and hasn't modifier : failing validation for executable element",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for nonexistence
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "has and hasn't modifier : failing validation for type element",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // Do the same on TypeElement
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false

                        },
                        {
                                "has / hasn't modifier",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod"))
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                        // check for nonexistence
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(testElement).hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));

                                        // Do the same on TypeElement
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC).hasNotModifiers(Modifier.FINAL, Modifier.ABSTRACT).getValidationResult(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentModifierElementValidator(element).hasNotModifiers(Modifier.ABSTRACT, Modifier.PUBLIC).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        }

                }

        );


    }

}
