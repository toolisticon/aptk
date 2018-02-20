package io.toolisticon.annotationprocessortoolkit.tools;


import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link TypeUtils}.
 */
@RunWith(Parameterized.class)
public class TypeUtilsTest extends AbstractAnnotationProcessorUnitTest {


    public TypeUtilsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

                                                              MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractUnitTestAnnotationProcessorClass.class.getSimpleName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for array class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(String[].class);

                                                              MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for primitive class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(int.class);

                                                              MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(AbstractUnitTestAnnotationProcessorClass.class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for array class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                                              MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(typeUtils.doTypeRetrieval().getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Test null safety",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror((Class) null);

                                                              MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for atomic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(int.class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for int",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // int
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(int.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for long",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // long
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(long.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for short",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // short
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(short.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for boolean",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // boolean
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(boolean.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for byte",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // byte
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(byte.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for float",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // float
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(float.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for double",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // double
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(double.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for char",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // char
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(char.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for null value should return null",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // null value
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(null);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for non primitive type should return String",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              // non primitive type value
                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getPrimitiveTypeMirror(String.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : get TypeMirror of array",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                                              MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(typeUtils.doTypeRetrieval().getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypes : get encapsulated javax.lang.model.util.Types instance",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.getTypes(), Matchers.notNullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test to get Element by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with non existing class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with null valued class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeElement((String) null),
                                                                      Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test to get TypeMirror by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test to get TypeMirror by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test behavior with non existing class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test behavior with null valued class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeMirror((String) null), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with anonymous class type mirror - should return null because no TyoeElement exists",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              Comparable<Long> anonymousComparable = new Comparable<Long>() {
                                                                  @Override
                                                                  public int compareTo(Long o) {
                                                                      return 0;
                                                                  }
                                                              };

                                                              MatcherAssert.assertThat(typeUtils.doTypeRetrieval().getTypeElement(anonymousComparable.getClass()), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doArrays().getArraysComponentType : Get component type of TypeMirror array ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat(typeUtils.doArrays().getArraysComponentType(typeMirror), Matchers.is(typeUtils.doTypeRetrieval().getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", typeUtils.doArrays().isArrayOfType(typeMirror, String.class));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !typeUtils.doArrays().isArrayOfType(typeMirror, Boolean.class));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", typeUtils.doArrays().isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !typeUtils.doArrays().isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror typeMirror = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", typeUtils.doArrays().isArrayOfType(typeMirror, typeUtils.doTypeRetrieval().getTypeMirror(String.class)));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !typeUtils.doArrays().isArrayOfType(typeMirror, typeUtils.doTypeRetrieval().getTypeMirror(Boolean.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doArrays().getArraysComponentType() : test if component type of TypeMirror of kind ARRAY is returned correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              TypeMirror input = typeUtils.doTypeRetrieval().getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(typeUtils.doArrays().getArraysComponentType(input), Matchers.is(typeUtils.doTypeRetrieval().getTypeMirror(String.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", typeUtils.doTypeComparison().isAssignableTo(element, Object.class));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !typeUtils.doTypeComparison().isAssignableTo(element, InputStream.class));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", typeUtils.doTypeComparison().isAssignableTo(element, typeUtils.doTypeRetrieval().getTypeElement(Object.class)));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !typeUtils.doTypeComparison().isAssignableTo(element, typeUtils.doTypeRetrieval().getTypeElement(InputStream.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", typeUtils.doTypeComparison().isAssignableTo(element, typeUtils.doTypeRetrieval().getTypeMirror(Object.class)));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !typeUtils.doTypeComparison().isAssignableTo(element, typeUtils.doTypeRetrieval().getTypeMirror(InputStream.class)));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.doTypeComparison().isTypeEqual(TypeElement,Class) : test if matching class is detected correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              Class<StringBuilder> type = StringBuilder.class;
                                                              TypeElement elementForComparison = typeUtils.doTypeRetrieval().getTypeElement(type);


                                                              MatcherAssert.assertThat("Should have found match", typeUtils.doTypeComparison().isTypeEqual(elementForComparison, type));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.doTypeComparison().isTypeEqual(TypeElement,Class) : test if non matching class is detected correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              Class<StringBuilder> type = StringBuilder.class;
                                                              TypeElement elementForComparison = typeUtils.doTypeRetrieval().getTypeElement(type);


                                                              MatcherAssert.assertThat("Should have found match", !typeUtils.doTypeComparison().isTypeEqual(elementForComparison, String.class));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.doCheckTypeKind().isVoid : test check for void type ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              MatcherAssert.assertThat(typeUtils.doCheckTypeKind().isVoid(ElementUtils.CastElement.castMethod(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                                              MatcherAssert.assertThat(typeUtils.doCheckTypeKind().isVoid(element.asType()), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      typeUtils.doGenerics().genericTypeEquals(
                                                                              method.getParameters().get(0).asType(),
                                                                              typeUtils.doGenerics().createGenericType(Map.class,
                                                                                      typeUtils.doGenerics().createGenericType(String.class),
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              Comparator.class,
                                                                                              typeUtils.doGenerics().createGenericType(Long.class)
                                                                                      )
                                                                              )
                                                                      )
                                                              );

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should not be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      !typeUtils.doGenerics().genericTypeEquals(
                                                                              method.getParameters().get(0).asType(),
                                                                              typeUtils.doGenerics().createGenericType(Map.class,
                                                                                      typeUtils.doGenerics().createGenericType(String.class),
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              Comparator.class,
                                                                                              typeUtils.doGenerics().createGenericType(Double.class)
                                                                                      )
                                                                              )
                                                                      )
                                                              );


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      typeUtils.doGenerics().genericTypeEquals(
                                                                              method.getParameters().get(1).asType(),
                                                                              typeUtils.doGenerics().createGenericType(Map.class,
                                                                                      typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                                                              typeUtils.doGenerics().createGenericType(StringBuilder.class)
                                                                                      ),
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              Comparator.class,
                                                                                              typeUtils.doGenerics().createWildcardWithSuperBound(
                                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                                              List.class,
                                                                                                              typeUtils.doGenerics().createPureWildcard()
                                                                                                      )
                                                                                              )

                                                                                      )
                                                                              )
                                                                      )
                                                              );

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should not be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeUtils typeUtils = TypeUtils.getTypeUtils();

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      !typeUtils.doGenerics().genericTypeEquals(
                                                                              method.getParameters().get(1).asType(),
                                                                              typeUtils.doGenerics().createGenericType(
                                                                                      Map.class,
                                                                                      typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                                                              typeUtils.doGenerics().createGenericType(StringBuilder.class)
                                                                                      ),
                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                              Comparator.class,
                                                                                              typeUtils.doGenerics().createWildcardWithSuperBound(
                                                                                                      typeUtils.doGenerics().createGenericType(
                                                                                                              List.class,
                                                                                                              typeUtils.doGenerics().createWildcardWithExtendsBound(typeUtils.doGenerics().createGenericType(String.class))
                                                                                                      )
                                                                                              )

                                                                                      )
                                                                              )
                                                                      )
                                                              );

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
