package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
public class ElementUtilsTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void getEnclosedElementsByName() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void getEnclosedElementsByNameRegex() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasModifiers() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should have public modifier", CoreMatchers.BY_MODIFIER.getValidator().hasAllOf(element, Modifier.PUBLIC));
                MatcherAssert.assertThat("Class should not have abstract modifier", !CoreMatchers.BY_MODIFIER.getValidator().hasAllOf(element, Modifier.PUBLIC, Modifier.ABSTRACT));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasPublicModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should  be detected to have public modifier", ElementUtils.CheckModifierOfElement.hasPublicModifier(element));

                MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasProtectedModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should  be detected to have protected modifier", ElementUtils.CheckModifierOfElement.hasProtectedModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "protectedField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasPrivateModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should be detected to have private modifier", ElementUtils.CheckModifierOfElement.hasPrivateModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "protectedField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasAbstractModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Element abstractType = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf(CoreMatchers.BY_ELEMENT_KIND.getFilter().filterByOneOf(element.getEnclosedElements(), ElementKind.CLASS), Modifier.ABSTRACT).get(0);

                MatcherAssert.assertThat("Class should be detected to have abstract modifier", ElementUtils.CheckModifierOfElement.hasAbstractModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(abstractType.getEnclosedElements(), "abstractMethod").get(0)))
                ;

                MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasStaticModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should  be detected to have static modifier", ElementUtils.CheckModifierOfElement.hasStaticModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "publicStaticField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasFinalModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should be detected to have final modifier", ElementUtils.CheckModifierOfElement.hasFinalModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "publicFinalField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasTransientModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should  be detected to have transient modifier", ElementUtils.CheckModifierOfElement.hasTransientModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "publicTransientField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void hasSynchronizedModifier() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Class should be detected to have synchronized modifier", ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "synchronizedMethod").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(CoreMatchers.BY_NAME.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(null));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedFields() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends VariableElement> fields = ElementUtils.AccessEnclosedElements.getEnclosedFields(element);

                MatcherAssert.assertThat(fields, Matchers.notNullValue());
                MatcherAssert.assertThat(fields, Matchers.not(Matchers.<VariableElement>empty()));

                for (VariableElement field : fields) {
                    MatcherAssert.assertThat(field.getKind(), Matchers.is(ElementKind.FIELD));
                }

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedMethods() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends ExecutableElement> methods = ElementUtils.AccessEnclosedElements.getEnclosedMethods(element);

                MatcherAssert.assertThat(methods, Matchers.notNullValue());
                MatcherAssert.assertThat(methods, Matchers.not(Matchers.<ExecutableElement>empty()));

                for (ExecutableElement method : methods) {
                    MatcherAssert.assertThat(method.getKind(), Matchers.is(ElementKind.METHOD));
                }
            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedConstructors() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends ExecutableElement> constructors = ElementUtils.AccessEnclosedElements.getEnclosedConstructors(element);

                MatcherAssert.assertThat(constructors, Matchers.notNullValue());
                MatcherAssert.assertThat(constructors, Matchers.not(Matchers.<ExecutableElement>empty()));

                for (ExecutableElement constructor : constructors) {
                    MatcherAssert.assertThat(constructor.getKind(), Matchers.is(ElementKind.CONSTRUCTOR));
                }
            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedTypes() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends TypeElement> types = ElementUtils.AccessEnclosedElements.getEnclosedTypes(element);

                MatcherAssert.assertThat(types, Matchers.notNullValue());
                MatcherAssert.assertThat(types, Matchers.not(Matchers.<TypeElement>empty()));

                for (TypeElement type : types) {
                    MatcherAssert.assertThat(type.getKind(), Matchers.is(ElementKind.CLASS));
                }

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedElementsWithAllAnnotationsOf() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, FilterTestAnnotation1.class);

                MatcherAssert.assertThat(elements, Matchers.notNullValue());
                MatcherAssert.assertThat(elements, Matchers.not(Matchers.<Element>empty()));

                for (Element element1 : elements) {
                    MatcherAssert.assertThat(element1.getAnnotation(FilterTestAnnotation1.class), Matchers.notNullValue());
                }

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getEnclosedElementsWithAllAnnotationsOf_withNullValuedElementOrAnnotation() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(null, FilterTestAnnotation1.class);

                MatcherAssert.assertThat(elements, Matchers.<Element>empty());

                elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, null);

                MatcherAssert.assertThat(elements, Matchers.hasSize(element.getEnclosedElements().size()));

                elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element);

                MatcherAssert.assertThat(elements, Matchers.hasSize(element.getEnclosedElements().size()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
