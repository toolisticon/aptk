package de.holisticon.annotationprocessor;

import de.holisticon.annotationprocessor.testhelper.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessor.testhelper.TestAnnotation;
import de.holisticon.annotationprocessor.tools.ElementUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractAnnotationProcessorTest extends AbstractAnnotationProcessorTestBaseClass {

    public AbstractAnnotationProcessorTest(String message, AbstractTestAnnotationProcessorClass testcase) {
        super(message, testcase);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "TypeUtils : Get TypeElement for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractTestAnnotationProcessorClass.class.getSimpleName()));

                                    }
                                }


                        },
                        {
                                "TypeUtils : Get TypeMirror for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirrorForClass(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                    }
                                }


                        },
                        {
                                "TypeUtils : test isAssignableToType",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToType(element, Object.class));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToType(element, InputStream.class));

                                    }
                                }


                        },
                        {
                                "TypeUtils : test isAssignableToTypeElement",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToTypeElement(element, getTypeUtils().getTypeElementForClass(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToTypeElement(element, getTypeUtils().getTypeElementForClass(InputStream.class)));

                                    }
                                }


                        },
                        {
                                "TypeUtils : test isAssignableToTypeMirror",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableToTypeMirror(element, getTypeUtils().getTypeMirrorForClass(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableToTypeMirror(element, getTypeUtils().getTypeMirrorForClass(InputStream.class)));

                                    }
                                }

                        },
                        {
                                "TypeUtils : test check for void type ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().isVoidType(ElementUtils.getElementUtils().castMethod(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                        MatcherAssert.assertThat(getTypeUtils().isVoidType(element.asType()), Matchers.is(false));


                                    }
                                }


                        },
                        {
                                "TypeUtils : get encapsulated javax.lang.model.util.Types instance",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypes(), Matchers.notNullValue());

                                    }
                                }


                        },
                        {
                                "ElementUtils : isAnnotatedWith test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Should have detected that element is annotated with TestAnnotation annotation", ElementUtils.getElementUtils().isAnnotatedWith(element, TestAnnotation.class));
                                        MatcherAssert.assertThat("Should have detected that element is not annotated with Override annotation", !ElementUtils.getElementUtils().isAnnotatedWith(element, Override.class));

                                    }
                                }


                        },
                        {
                                "ElementUtils : getEnclosedElementsByName test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // find field
                                        List<? extends Element> result = ElementUtils.getElementUtils().getEnclosedElementsByName(element, "privateField");
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getKind(), Matchers.is(ElementKind.FIELD));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("privateField"));


                                        // shouldn't find nonexisting
                                        result = ElementUtils.getElementUtils().getEnclosedElementsByName(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());

                                    }
                                }


                        },
                        {
                                "ElementUtils : getEnclosedElementsByNameRegex test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = ElementUtils.getElementUtils().getEnclosedElementsByNameRegex(element, ".*ublic.*");
                                        MatcherAssert.assertThat(results, Matchers.hasSize(4));
                                        for (Element result : results) {
                                            MatcherAssert.assertThat(result.getKind(), Matchers.is(ElementKind.FIELD));
                                            MatcherAssert.assertThat(result.getSimpleName().toString(), Matchers.startsWith("public"));
                                        }

                                        // shouldn't find nonexisting
                                        results = ElementUtils.getElementUtils().getEnclosedElementsByNameRegex(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(results, Matchers.<Element>empty());

                                    }
                                }


                        },
                        {
                                "ElementUtils : hasModifiers test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should have public modifier", ElementUtils.getElementUtils().hasModifiers(element, Modifier.PUBLIC));
                                        MatcherAssert.assertThat("Class should not have abstract modifier", !ElementUtils.getElementUtils().hasModifiers(element, Modifier.PUBLIC, Modifier.ABSTRACT));

                                    }
                                }


                        },
                        {
                                "ElementUtils : hasPublicModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should not be detected to have public modifier", ElementUtils.getElementUtils().hasPublicModifier(element));

                                    }
                                }


                        },
                        {
                                "FluentElementFilter : Do filterings",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(7));


                                        result = createFluentElementFilter(element.getEnclosedElements()).filterByKinds(ElementKind.FIELD).filterByModifiers(Modifier.PUBLIC, Modifier.STATIC).getResult();
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("publicStaticField"));


                                    }
                                }


                        },
                        {
                                "FluentExecutableElementValidator: validate void return type method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "synchronizedMethod")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().validate(), Matchers.is(true));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().validate(), Matchers.is(false));


                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).validate(), Matchers.is(false));


                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                }


                        },
                        {
                                "FluentExecutableElementValidator: validate non void return type method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // check null value
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasVoidReturnType().validate(), Matchers.is(false));

                                        // check non null value
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasNonVoidReturnType().validate(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(String.class).validate(), Matchers.is(true));

                                        // check for assignable supertype of return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Object.class).validate(), Matchers.is(true));

                                        // check specific return type
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).hasReturnType(Boolean.class).validate(), Matchers.is(false));


                                    }
                                }


                        },
                        {
                                "FluentExecutableElementValidator: validate if ExecutableElement is method",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().validate(), Matchers.is(true));


                                        elements = ElementUtils.getElementUtils().getEnclosedElementsOfKind(element, ElementKind.CONSTRUCTOR);
                                        MatcherAssert.assertThat("precondition : must have found unique static init block", elements.size() == 2);
                                        ExecutableElement constructorElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(constructorElement).isMethod().validate(), Matchers.is(false));


                                    }
                                }


                        },
                        {
                                "FluentExecutableElementValidator: has name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for method
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasName("methodWithReturnTypeAndParameters").validate(), Matchers.is(true));


                                    }
                                }


                        },
                        {
                                "FluentExecutableElementValidator: has parameters",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // do preparations
                                        List<? extends Element> elements = createFluentElementFilter(ElementUtils.getElementUtils().getEnclosedElementsByName(element, "methodWithReturnTypeAndParameters")).filterByKinds(ElementKind.METHOD).getResult();
                                        MatcherAssert.assertThat("precondition : must have found unique testelement", elements.size() == 1);
                                        ExecutableElement testElement = ElementUtils.getElementUtils().castMethod(elements.get(0));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters().validate(), Matchers.is(true));

                                        // check for existence of parameters
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class, String.class).validate(), Matchers.is(true));

                                        // check non matching parameter length
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(Boolean.class).validate(), Matchers.is(false));

                                        // check non matching parameter types
                                        MatcherAssert.assertThat(getFluentMethodValidator(testElement).isMethod().hasParameters(String.class, Boolean.class).validate(), Matchers.is(false));


                                    }
                                }


                        },
                        {
                                "FluentTypeElementValidator : check if type is assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(Object.class).validate(), Matchers.is(true));
                                        MatcherAssert.assertThat(getFluentTypeValidator(element).isAssignableTo(String.class).validate(), Matchers.is(false));

                                    }
                                }


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                }


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                }


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                }


                        },
                        {
                                "",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        getTypeUtils().getTypeElementForClass(AbstractTestAnnotationProcessorClass.class);

                                    }
                                }


                        }

                }

        );


    }


}
