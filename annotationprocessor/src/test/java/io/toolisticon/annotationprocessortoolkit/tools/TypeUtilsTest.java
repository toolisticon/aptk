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
                                "TypeUtils.TypeRetrieval.getTypeElement : Get TypeElement for class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

                                                              MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractUnitTestAnnotationProcessorClass.class.getSimpleName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement : Get TypeElement for array class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(String[].class);

                                                              MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement : Get TypeElement for primitive class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(int.class);

                                                              MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror : Get TypeMirror for class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(AbstractUnitTestAnnotationProcessorClass.class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror : Get TypeMirror for array class",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                                              MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror : Test null safety",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror((Class) null);

                                                              MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror : Get TypeMirror for atomic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(int.class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for int",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // int
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(int.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for long",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              // long
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(long.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for short",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // short
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(short.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for boolean",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // boolean
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(boolean.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for byte",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // byte
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(byte.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for float",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // float
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(float.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for double",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // double
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(double.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for char",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // char
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(char.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for null value should return null",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // null value
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(null);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getPrimitiveTypeMirror : Get TypeMirror for non primitive type should return String",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              // non primitive type value
                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(String.class);
                                                              MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror : get TypeMirror of array",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                                              MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypes : get encapsulated javax.lang.model.util.Types instance",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.getTypes(), Matchers.notNullValue());

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement() : test to get Element by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement() : test behavior with non existing class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement() : test behavior with null valued class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                               MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement((String) null),
                                                                      Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement() : test to get TypeMirror by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror() : test to get TypeMirror by class name",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror() : test behavior with non existing class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()
                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeMirror() : test behavior with null valued class name parameter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                               MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror((String) null), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeRetrieval.getTypeElement() : test behavior with anonymous class type mirror - should return null because no TyoeElement exists",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              Comparable<Long> anonymousComparable = new Comparable<Long>() {
                                                                  @Override
                                                                  public int compareTo(Long o) {
                                                                      return 0;
                                                                  }
                                                              };

                                                              MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement(anonymousComparable.getClass()), Matchers.nullValue());


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.getArraysComponentType : Get component type of TypeMirror array ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.Arrays.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                                              MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                                              MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                                                              MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Arrays.getArraysComponentType() : test if component type of TypeMirror of kind ARRAY is returned correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              TypeMirror input = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                                              MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(input), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeComparison.isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, Object.class));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, InputStream.class));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeComparison.isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(Object.class)));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(InputStream.class)));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.TypeComparison.isAssignableTo : test isAssignableTo",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));
                                                              MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(InputStream.class)));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.TypeComparison.isTypeEqual(TypeElement,Class) : test if matching class is detected correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              Class<StringBuilder> type = StringBuilder.class;
                                                              TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                                                              MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(elementForComparison, type));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.TypeComparison.isTypeEqual(TypeElement,Class) : test if non matching class is detected correctly",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              Class<StringBuilder> type = StringBuilder.class;
                                                              TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                                                              MatcherAssert.assertThat("Should have found match", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, String.class));

                                                          }
                                                      }
                                        )
                                        .build()

                        },
                        {
                                "TypeUtils.CheckTypeKind.isVoid : test check for void type ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(ElementUtils.CastElement.castMethod(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                                              MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(element.asType()), Matchers.is(false));


                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.Generics.genericTypeEquals : Should be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      TypeUtils.Generics.genericTypeEquals(
                                                                              method.getParameters().get(0).asType(),
                                                                              TypeUtils.Generics.createGenericType(Map.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              Comparator.class,
                                                                                              TypeUtils.Generics.createGenericType(Long.class)
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
                                "TypeUtils.Generics.genericTypeEquals : Should not be able to compare generic type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      !TypeUtils.Generics.genericTypeEquals(
                                                                              method.getParameters().get(0).asType(),
                                                                              TypeUtils.Generics.createGenericType(Map.class,
                                                                                      TypeUtils.Generics.createGenericType(String.class),
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              Comparator.class,
                                                                                              TypeUtils.Generics.createGenericType(Double.class)
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
                                "TypeUtils.Generics.genericTypeEquals : Should be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      TypeUtils.Generics.genericTypeEquals(
                                                                              method.getParameters().get(1).asType(),
                                                                              TypeUtils.Generics.createGenericType(Map.class,
                                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                                              TypeUtils.Generics.createGenericType(StringBuilder.class)
                                                                                      ),
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              Comparator.class,
                                                                                              TypeUtils.Generics.createWildcardWithSuperBound(
                                                                                                      TypeUtils.Generics.createGenericType(
                                                                                                              List.class,
                                                                                                              TypeUtils.Generics.createPureWildcard()
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
                                "TypeUtils.Generics.genericTypeEquals : Should not be able to compare generic type with wildcards",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {

                                                              List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

                                                              ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                                              //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                                              MatcherAssert.assertThat("Should be equal and therefore true",
                                                                      !TypeUtils.Generics.genericTypeEquals(
                                                                              method.getParameters().get(1).asType(),
                                                                              TypeUtils.Generics.createGenericType(
                                                                                      Map.class,
                                                                                      TypeUtils.Generics.createWildcardWithExtendsBound(
                                                                                              TypeUtils.Generics.createGenericType(StringBuilder.class)
                                                                                      ),
                                                                                      TypeUtils.Generics.createGenericType(
                                                                                              Comparator.class,
                                                                                              TypeUtils.Generics.createWildcardWithSuperBound(
                                                                                                      TypeUtils.Generics.createGenericType(
                                                                                                              List.class,
                                                                                                              TypeUtils.Generics.createWildcardWithExtendsBound(TypeUtils.Generics.createGenericType(String.class))
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
