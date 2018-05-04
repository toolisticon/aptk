package io.toolisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericTypeKind;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericTypeWildcard;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Unit test for {@link TypeUtils.Generics}.
 */
@RunWith(Parameterized.class)
public class TypeUtils_GenericsTest extends AbstractAnnotationProcessorUnitTest {


    public TypeUtils_GenericsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "TypeUtils.Generics.createGenericType - simple non generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(String.class);

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 0 type parameters", genericType.getTypeParameters().length == 0);

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.createGenericType - simple non generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(String.class.getCanonicalName());

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 0 type parameters", genericType.getTypeParameters().length == 0);

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.createGenericType - simple generic type with one type parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Collection.class,
                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                              );

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[0]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.createGenericType - simple generic type with one pure wildcard type parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Collection.class,
                                                                      TypeUtils.Generics.createPureWildcard()
                                                              );

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                                                              MatcherAssert.assertThat("Type parameter shoulb be pure wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).isPureWildcard());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.createGenericType - simple generic type with one extends wildcard type parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Collection.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                                                              MatcherAssert.assertThat("Type parameter should be extends bound wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).hasExtendsBound());
                                                              MatcherAssert.assertThat(((GenericTypeWildcard) genericType.getTypeParameters()[0]).getExtendsBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.createGenericType - simple generic type with one super wildcard type parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Collection.class,
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                                                              MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                                                              MatcherAssert.assertThat("Type parameter should be super bound wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).hasSuperBound());
                                                              MatcherAssert.assertThat(((GenericTypeWildcard) genericType.getTypeParameters()[0]).getSuperBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.createGenericType - more complex generic type with two type parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class,
                                                                              TypeUtils.Generics.createWildcardWithExtendsBound(TypeUtils.Generics.createGenericType(String.class)),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));
                                                              MatcherAssert.assertThat("Should have 2 type parameters", genericType.getTypeParameters().length == 2);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[0]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                                                              MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));
                                                              MatcherAssert.assertThat("Should have 2 type parameters", genericType.getTypeParameters().length == 2);
                                                              MatcherAssert.assertThat(genericType.getTypeParameters()[1].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));

                                                              MatcherAssert.assertThat("Should have 2 type parameters", ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters().length == 2);

                                                              MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                                                              MatcherAssert.assertThat(((GenericTypeWildcard) ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[0]).getExtendsBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                                                              MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[1].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                                                              MatcherAssert.assertThat(((GenericType) ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[1]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        // #######################

                        {
                                "TypeUtils.Generics.genericTypeEquals : test null safety",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should return false if passed typeMirror is null", !TypeUtils.Generics.genericTypeEquals(null, genericType));
                                                              MatcherAssert.assertThat("Should return false if passed genericType is null", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), null));
                                                              MatcherAssert.assertThat("Should return false if passed typeMirror and genericType are null", !TypeUtils.Generics.genericTypeEquals(null, null));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "TypeUtils.Generics.genericTypeEquals : test case 1 - matching type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect matching types and return true", TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.genericTypeEquals : test case 2 - mismatching type - Different genericType",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class,
                                                                              TypeUtils.Generics.createGenericType(Integer.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect mismatching types and return false", !TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.genericTypeEquals : test case 3 - mismatching type - Type parameter RAW type vs generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect mismatching types and return false", !TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // #######################


                        {
                                "TypeUtils.Generics.isAssignable : test case 1 - exactly same type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              Map.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect assignable for exactly the same type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 1 - non matching type parameter inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              HashMap.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect non matching type parameter inbetween", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 1 - pure wildcard for 2nd Map inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createPureWildcard()

                                                              );


                                                              MatcherAssert.assertThat("Should detect assignability for pure wildcard", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "TypeUtils.Generics.isAssignable : test case 1 - with super - matching type parameter for super inbetween",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect assignability for extends case", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 2 - with extends - matching type parameter for extends inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase2")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("Should detect assignability for extends case", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              HashMap.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and super on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side can't be assigned to a super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createPureWildcard()

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      Map.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - missing generic types - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      Map.class
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 3 - with extends clause on Type Mirror and invalid extends wildcard  - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? extends HashMap<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      TreeMap.class
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a invalid extends wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        // #######################

                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              HashMap.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and extends on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side can't be assigned to a super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createPureWildcard()

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - missing generic types - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 4 - with super clause on Type Mirror and invalid super wildcard  - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      Collection.class
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a invalid super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        // #######################

                        {
                                "TypeUtils.Generics.isAssignable : test case 5 - with pure wildcare clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createGenericType(
                                                                              HashMap.class,
                                                                              TypeUtils.Generics.createGenericType(String.class),
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Pure wildcard on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and extends on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("pure wildcard on assignee side can't be assigned to a extends wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createPureWildcard()

                                                              );


                                                              MatcherAssert.assertThat("Pure wildcard on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Generics.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and  super wildcard - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                                              // Map<String, ? super Map<String, String>>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Map.class,
                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                      TypeUtils.Generics.createWildcardWithSuperBound(
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      HashMap.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                                              )
                                                                      )

                                                              );


                                                              MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        {
                                "TypeUtils.Arrays.isArrayOfType : test case 6 - comparison with GenericType - matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              // Comparator<String>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Comparator.class,
                                                                      TypeUtils.Generics.createGenericType(String.class)
                                                              );


                                                              MatcherAssert.assertThat("comparison with GenericType for exactly matching types should return true", TypeUtils.Arrays.isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.isArrayOfType : test case 6 - comparison with GenericType - non matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              // Comparator<String>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Comparator.class,
                                                                      TypeUtils.Generics.createGenericType(Object.class)
                                                              );


                                                              MatcherAssert.assertThat("comparison with GenericType for assignable types should return false", !TypeUtils.Arrays.isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - matching - with wildcard",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              // Comparator<String>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Comparator.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(Map.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("comparison with GenericType - matching - with wildcard should return true", TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.isArrayAssignableTo : test case 6 - comparison with GenericType - with wildcard - non matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              // Comparator<String>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Comparator.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(String.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("comparison with GenericType - with wildcard - non matching return false", !TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - assignable type - with wildcards on both sides",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                                                                      .getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              // Comparator<String>
                                                              GenericType genericType = TypeUtils.Generics.createGenericType(
                                                                      Comparator.class,
                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                              TypeUtils.Generics.createGenericType(Map.class)
                                                                      )
                                                              );


                                                              MatcherAssert.assertThat("comparison with GenericType - assignable type - with wildcards on both sides should return true", TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(2).asType(), genericType));


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
        return JavaFileObjects.forResource("GenericsTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

}