package de.holisticon.annotationprocessortoolkit;

import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
import de.holisticon.annotationprocessortoolkit.validators.FluentExecutableElementValidator;
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
public class FluentExecutableElementValidatorTest extends AbstractAnnotationProcessorTestBaseClass {


    public FluentExecutableElementValidatorTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(FluentExecutableElementValidator.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "Validate void return type method",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(true));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(false));


                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(false));


                                        getTypeUtils().getTypeElementForClass(AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass.class);

                                    }
                                },
                                false


                        },
                        {
                                "Validate non void return type method",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().getValidationResult(), Matchers.is(false));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().getValidationResult(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).getValidationResult(), Matchers.is(true));

                                        // check for assignable supertype of return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Object.class).getValidationResult(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Boolean.class).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "isMethod: Validate when ExecutableElement is method",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().getValidationResult(), Matchers.is(true));

                                    }
                                },
                                true


                        },
                        {
                                "isMethod: Validate when ExecutableElement isn't method",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                        MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                        ExecutableElement constructorElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(constructorElement).isMethod().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "isConstructor: Validate when ExecutableElement is constructor",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        List<? extends Element> elements = ElementUtils.AccessEnclosedElements.getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                        MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                        ExecutableElement constructorElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(constructorElement).isConstructor().getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "isConstructor: Validate when ExecutableElement isn't constructor",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isConstructor().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "hasName: Validate if element has name",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasName("methodWithReturnTypeAndParameters").getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "hasName: Validate if element hasn't name",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));


                                        // check if element name doesn't match
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasName("XXX").getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "hasParameters: validate when element has matching parameters",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class, String.class).getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "hasParameters: validate when element has non matching parameters",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                        // check non matching parameter length
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class).getValidationResult(), Matchers.is(false));

                                        // check non matching parameter types
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(String.class, Boolean.class).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "hasParameters: validate when element has at least one parameters",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                        // check non matching parameter length
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(true));


                                    }
                                },
                                true


                        },
                        {
                                "hasParameters: validate when element has no parameters",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);

                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));
                                        // check non matching parameter length
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters().getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        },
                        {
                                "hasModifiers: Validate if element has / hasn't modifier",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PUBLIC).hasNotModifiers(Modifier.PROTECTED, Modifier.FINAL).getValidationResult(), Matchers.is(true));

                                    }
                                },
                                true


                        },
                        {
                                "hasModifiers: Validate if element has / hasn't modifier",
                                new AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.CastElement.castMethod(elements.get(0));

                                        // check for nonexistence
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasNotModifiers(Modifier.PUBLIC).getValidationResult(), Matchers.is(false));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasModifiers(Modifier.SYNCHRONIZED, Modifier.PROTECTED).getValidationResult(), Matchers.is(false));
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasNotModifiers(Modifier.PROTECTED, Modifier.SYNCHRONIZED).getValidationResult(), Matchers.is(false));


                                    }
                                },
                                false


                        }

                }

        );


    }


}
