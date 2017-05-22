package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import de.holisticon.annotationprocessortoolkit.FilterTestAnnotation2;
import de.holisticon.annotationprocessortoolkit.TestAnnotation;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filter;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.tools.TypeUtils}.
 */
@RunWith(Parameterized.class)
public class ElementUtilsTest extends AbstractAnnotationProcessorTestBaseClass {

    public ElementUtilsTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(ElementUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{

                        {
                                "isAnnotatedWith test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Should have detected that element is annotated with TestAnnotation annotation", ElementUtils.isAnnotatedWith(element, TestAnnotation.class));
                                        MatcherAssert.assertThat("Should have detected that element is not annotated with Override annotation", !ElementUtils.isAnnotatedWith(element, Override.class));

                                    }
                                },
                                true


                        },
                        {
                                "isAnnotatedWithAllOf test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        FluentElementFilter<? extends Element> elementFilter = FluentElementFilter.createFluentFilter(element.getEnclosedElements()).applyFilter(Filter.NAME_FILTER).filterByOneOf("synchronizedMethod");

                                        // Check Preconditions
                                        MatcherAssert.assertThat("Precondition : Should have found exactly one Executable element", elementFilter.hasSingleElement(), Matchers.is(true));
                                        ExecutableElement method = (ExecutableElement) elementFilter.getResult().get(0);

                                        // test happy path
                                        MatcherAssert.assertThat("Should have detected that element is annotated with passed FilterTestAnnotation1 and FilterTestAnnotation2 annotations", ElementUtils.isAnnotatedWithAllOf(method, FilterTestAnnotation1.class, FilterTestAnnotation2.class));

                                        // test missing annotation
                                        MatcherAssert.assertThat("Should have detected that element is not annotated with all passed annotations", !ElementUtils.isAnnotatedWithAllOf(method, FilterTestAnnotation1.class, FilterTestAnnotation2.class, TestAnnotation.class));

                                        // test if null valued element is passed
                                        MatcherAssert.assertThat("Should have returned false if passed element is null", !ElementUtils.isAnnotatedWithAllOf(null, FilterTestAnnotation1.class, FilterTestAnnotation2.class, TestAnnotation.class));

                                        // test if no annottions are passed
                                        MatcherAssert.assertThat("Should have returned true if passed annotations are empty", ElementUtils.isAnnotatedWithAllOf(method));


                                    }
                                },
                                true


                        },
                        {
                                "isAnnotatedWithAtLeastOneOf test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        FluentElementFilter elementFilter = FluentElementFilter.createFluentFilter(element.getEnclosedElements()).applyFilter(Filter.NAME_FILTER).filterByOneOf("synchronizedMethod");

                                        // Check Preconditions
                                        MatcherAssert.assertThat("Precondition : Should have found exactly one Executable element", elementFilter.hasSingleElement(), Matchers.is(true));
                                        ExecutableElement method = (ExecutableElement) elementFilter.getResult().get(0);

                                        // test happy path
                                        MatcherAssert.assertThat("all matched annotations should have been detected as valid", ElementUtils.isAnnotatedWithAtLeastOneOf(method, FilterTestAnnotation1.class, FilterTestAnnotation2.class));

                                        // test at least one matched annotation
                                        MatcherAssert.assertThat("two matched and one missed annotations should have been detected as valid", ElementUtils.isAnnotatedWithAtLeastOneOf(method, FilterTestAnnotation1.class, FilterTestAnnotation2.class, TestAnnotation.class));
                                        MatcherAssert.assertThat("one matched and one missed annotations should have been detected as valid", ElementUtils.isAnnotatedWithAtLeastOneOf(method, FilterTestAnnotation1.class, TestAnnotation.class));

                                        // test non matching annotation
                                        MatcherAssert.assertThat("none matching annotations should have been detected as invalid", !ElementUtils.isAnnotatedWithAtLeastOneOf(method, TestAnnotation.class));

                                        // test if null valued element is passed
                                        MatcherAssert.assertThat("Should have returned false if passed element is null", !ElementUtils.isAnnotatedWithAtLeastOneOf(null, FilterTestAnnotation1.class, FilterTestAnnotation2.class, TestAnnotation.class));

                                        // test if no annottions are passed
                                        MatcherAssert.assertThat("Should have returned false if passed annotations are empty", !ElementUtils.isAnnotatedWithAtLeastOneOf(method));


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedElementsByName test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // find field
                                        List<? extends Element> result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "privateField");
                                        MatcherAssert.assertThat(result, Matchers.hasSize(1));
                                        MatcherAssert.assertThat(result.get(0).getKind(), Matchers.is(ElementKind.FIELD));
                                        MatcherAssert.assertThat(result.get(0).getSimpleName().toString(), Matchers.is("privateField"));


                                        // shouldn't find nonexisting
                                        result = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(result, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedElementsByNameRegex test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> results = ElementUtils.AccessEnclosedElements.getEnclosedElementsByNameRegex(element, ".*ublic.*");
                                        MatcherAssert.assertThat(results, Matchers.hasSize(4));
                                        for (Element result : results) {
                                            MatcherAssert.assertThat(result.getKind(), Matchers.is(ElementKind.FIELD));
                                            MatcherAssert.assertThat(result.getSimpleName().toString(), Matchers.startsWith("public"));
                                        }

                                        // shouldn't find nonexisting
                                        results = ElementUtils.AccessEnclosedElements.getEnclosedElementsByNameRegex(element, "XXXXXXXX");
                                        MatcherAssert.assertThat(results, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },
                        {
                                "hasModifiers test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should have public modifier", Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(element, Modifier.PUBLIC));
                                        MatcherAssert.assertThat("Class should not have abstract modifier", !Validator.MODIFIER_VALIDATOR.getValidator().hasAllOf(element, Modifier.PUBLIC, Modifier.ABSTRACT));

                                    }
                                },
                                true


                        },
                        {
                                "hasPublicModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should  be detected to have public modifier", ElementUtils.CheckModifierOfElement.hasPublicModifier(element));

                                        MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(null));
                                    }
                                },
                                true


                        },
                        {
                                "hasProtectedModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should  be detected to have protected modifier", ElementUtils.CheckModifierOfElement.hasProtectedModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "protectedField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(null));


                                    }
                                },
                                true


                        },
                        {
                                "hasPrivateModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should be detected to have private modifier", ElementUtils.CheckModifierOfElement.hasPrivateModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "protectedField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(null));


                                    }
                                },
                                true


                        },
                        {
                                "hasAbstractModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        Element abstractType = ElementUtils.FilterElements.filterElementListByModifier(ElementUtils.FilterElements.filterElementListByKind(element.getEnclosedElements(), ElementKind.CLASS), Modifier.ABSTRACT).get(0);

                                        MatcherAssert.assertThat("Class should be detected to have abstract modifier", ElementUtils.CheckModifierOfElement.hasAbstractModifier(ElementUtils.FilterElements.filterElementListByName(abstractType.getEnclosedElements(), "abstractMethod").get(0)))
                                        ;

                                        MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(null));


                                    }
                                },
                                true


                        },
                        {
                                "hasStaticModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should  be detected to have static modifier", ElementUtils.CheckModifierOfElement.hasStaticModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "publicStaticField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(null));


                                    }
                                },
                                true


                        },
                        {
                                "hasFinalModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should be detected to have final modifier", ElementUtils.CheckModifierOfElement.hasFinalModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "publicFinalField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(null));

                                    }
                                },
                                true


                        },
                        {
                                "hasTransientModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should  be detected to have transient modifier", ElementUtils.CheckModifierOfElement.hasTransientModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "publicTransientField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(null));

                                    }
                                },
                                true


                        },
                        {
                                "hasSynchronizedModifier test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("Class should be detected to have synchronized modifier", ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "synchronizedMethod").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(ElementUtils.FilterElements.filterElementListByName(element.getEnclosedElements(), "privateField").get(0)));

                                        MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(null));


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedFields test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<VariableElement> fields = ElementUtils.AccessEnclosedElements.getEnclosedFields(element);

                                        MatcherAssert.assertThat(fields, Matchers.notNullValue());
                                        MatcherAssert.assertThat(fields, Matchers.not(Matchers.<VariableElement>empty()));

                                        for (VariableElement field : fields) {
                                            MatcherAssert.assertThat(field.getKind(), Matchers.is(ElementKind.FIELD));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedMethods test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<ExecutableElement> methods = ElementUtils.AccessEnclosedElements.getEnclosedMethods(element);

                                        MatcherAssert.assertThat(methods, Matchers.notNullValue());
                                        MatcherAssert.assertThat(methods, Matchers.not(Matchers.<ExecutableElement>empty()));

                                        for (ExecutableElement method : methods) {
                                            MatcherAssert.assertThat(method.getKind(), Matchers.is(ElementKind.METHOD));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedConstructors test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<ExecutableElement> constructors = ElementUtils.AccessEnclosedElements.getEnclosedConstructors(element);

                                        MatcherAssert.assertThat(constructors, Matchers.notNullValue());
                                        MatcherAssert.assertThat(constructors, Matchers.not(Matchers.<ExecutableElement>empty()));

                                        for (ExecutableElement constructor : constructors) {
                                            MatcherAssert.assertThat(constructor.getKind(), Matchers.is(ElementKind.CONSTRUCTOR));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedTypes test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<TypeElement> types = ElementUtils.AccessEnclosedElements.getEnclosedTypes(element);

                                        MatcherAssert.assertThat(types, Matchers.notNullValue());
                                        MatcherAssert.assertThat(types, Matchers.not(Matchers.<TypeElement>empty()));

                                        for (TypeElement type : types) {
                                            MatcherAssert.assertThat(type.getKind(), Matchers.is(ElementKind.CLASS));
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedElementsWithAnnotation test",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAnnotation(element, FilterTestAnnotation1.class);

                                        MatcherAssert.assertThat(elements, Matchers.notNullValue());
                                        MatcherAssert.assertThat(elements, Matchers.not(Matchers.<Element>empty()));

                                        for (Element element1 : elements) {
                                            MatcherAssert.assertThat(element1.getAnnotation(FilterTestAnnotation1.class), Matchers.notNullValue());
                                        }


                                    }
                                },
                                true


                        },
                        {
                                "getEnclosedElementsWithAnnotation test with null valued element or annotation",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAnnotation(null, FilterTestAnnotation1.class);

                                        MatcherAssert.assertThat(elements, Matchers.<Element>empty());

                                        elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAnnotation(element, null);

                                        MatcherAssert.assertThat(elements, Matchers.<Element>empty());

                                        elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAnnotation(element);

                                        MatcherAssert.assertThat(elements, Matchers.<Element>empty());

                                    }
                                },
                                true


                        },


                }

        );


    }
}