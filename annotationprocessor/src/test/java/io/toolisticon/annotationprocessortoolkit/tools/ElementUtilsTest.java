package io.toolisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.FilterTestAnnotation1;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsvalidator.Validators;
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
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.List;

/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
@RunWith(Parameterized.class)
public class ElementUtilsTest extends AbstractAnnotationProcessorUnitTest {

    public ElementUtilsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getEnclosedElementsByName test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedElementsByNameRegex test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasModifiers test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should have public modifier", Validators.MODIFIER_VALIDATOR.getValidator().hasAllOf(element, Modifier.PUBLIC));
                                                              MatcherAssert.assertThat("Class should not have abstract modifier", !Validators.MODIFIER_VALIDATOR.getValidator().hasAllOf(element, Modifier.PUBLIC, Modifier.ABSTRACT));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasPublicModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should  be detected to have public modifier", ElementUtils.CheckModifierOfElement.hasPublicModifier(element));

                                                              MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have public modifier", !ElementUtils.CheckModifierOfElement.hasPublicModifier(null));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasProtectedModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should  be detected to have protected modifier", ElementUtils.CheckModifierOfElement.hasProtectedModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "protectedField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have protected modifier", !ElementUtils.CheckModifierOfElement.hasProtectedModifier(null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasPrivateModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should be detected to have private modifier", ElementUtils.CheckModifierOfElement.hasPrivateModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "protectedField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have private modifier", !ElementUtils.CheckModifierOfElement.hasPrivateModifier(null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasAbstractModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              Element abstractType = Filters.MODIFIER_FILTER.getFilter().filterByAllOf(Filters.ELEMENT_KIND_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), ElementKind.CLASS), Modifier.ABSTRACT).get(0);

                                                              MatcherAssert.assertThat("Class should be detected to have abstract modifier", ElementUtils.CheckModifierOfElement.hasAbstractModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(abstractType.getEnclosedElements(), "abstractMethod").get(0)))
                                                              ;

                                                              MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have abstract modifier", !ElementUtils.CheckModifierOfElement.hasAbstractModifier(null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasStaticModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should  be detected to have static modifier", ElementUtils.CheckModifierOfElement.hasStaticModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "publicStaticField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have static modifier", !ElementUtils.CheckModifierOfElement.hasStaticModifier(null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasFinalModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should be detected to have final modifier", ElementUtils.CheckModifierOfElement.hasFinalModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "publicFinalField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have final modifier", !ElementUtils.CheckModifierOfElement.hasFinalModifier(null));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasTransientModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should  be detected to have transient modifier", ElementUtils.CheckModifierOfElement.hasTransientModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "publicTransientField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have transient modifier", !ElementUtils.CheckModifierOfElement.hasTransientModifier(null));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "hasSynchronizedModifier test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("Class should be detected to have synchronized modifier", ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "synchronizedMethod").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(Filters.NAME_FILTER.getFilter().filterByOneOf(element.getEnclosedElements(), "privateField").get(0)));

                                                              MatcherAssert.assertThat("Class should not be detected to have synchronized modifier", !ElementUtils.CheckModifierOfElement.hasSynchronizedModifier(null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedFields test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends VariableElement> fields = ElementUtils.AccessEnclosedElements.getEnclosedFields(element);

                                                              MatcherAssert.assertThat(fields, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(fields, Matchers.not(Matchers.<VariableElement>empty()));

                                                              for (VariableElement field : fields) {
                                                                  MatcherAssert.assertThat(field.getKind(), Matchers.is(ElementKind.FIELD));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedMethods test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends ExecutableElement> methods = ElementUtils.AccessEnclosedElements.getEnclosedMethods(element);

                                                              MatcherAssert.assertThat(methods, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(methods, Matchers.not(Matchers.<ExecutableElement>empty()));

                                                              for (ExecutableElement method : methods) {
                                                                  MatcherAssert.assertThat(method.getKind(), Matchers.is(ElementKind.METHOD));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedConstructors test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends ExecutableElement> constructors = ElementUtils.AccessEnclosedElements.getEnclosedConstructors(element);

                                                              MatcherAssert.assertThat(constructors, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(constructors, Matchers.not(Matchers.<ExecutableElement>empty()));

                                                              for (ExecutableElement constructor : constructors) {
                                                                  MatcherAssert.assertThat(constructor.getKind(), Matchers.is(ElementKind.CONSTRUCTOR));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedTypes test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends TypeElement> types = ElementUtils.AccessEnclosedElements.getEnclosedTypes(element);

                                                              MatcherAssert.assertThat(types, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(types, Matchers.not(Matchers.<TypeElement>empty()));

                                                              for (TypeElement type : types) {
                                                                  MatcherAssert.assertThat(type.getKind(), Matchers.is(ElementKind.CLASS));
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedElementsWithAllAnnotationsOf test",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, FilterTestAnnotation1.class);

                                                              MatcherAssert.assertThat(elements, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(elements, Matchers.not(Matchers.<Element>empty()));

                                                              for (Element element1 : elements) {
                                                                  MatcherAssert.assertThat(element1.getAnnotation(FilterTestAnnotation1.class), Matchers.notNullValue());
                                                              }


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "getEnclosedElementsWithAllAnnotationsOf test with null valued element or annotation",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<Element> elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(null, FilterTestAnnotation1.class);

                                                              MatcherAssert.assertThat(elements, Matchers.<Element>empty());

                                                              elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element, null);

                                                              MatcherAssert.assertThat(elements, Matchers.hasSize(element.getEnclosedElements().size()));

                                                              elements = (List<Element>) ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(element);

                                                              MatcherAssert.assertThat(elements, Matchers.hasSize(element.getEnclosedElements().size()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },


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
