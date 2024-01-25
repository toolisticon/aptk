package io.toolisticon.aptk.tools;


import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link TypeUtils}.
 */
public class TypeUtilsTest {


    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {



                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

                                MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractUnitTestAnnotationProcessorClass.class.getSimpleName()));


                            }
                        }
                )
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForArrayClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(String[].class);

                                MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForTypeMirror() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(element.asType());

                                MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is("AnnotationProcessorTestClass"));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForNullValuedTypeMirror() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement((TypeMirror) null);
                                MatcherAssert.assertThat(typeElement, Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForPrimitiveTypeClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(int.class);
                                MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_GetTypeMirrorForClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(AbstractUnitTestAnnotationProcessorClass.class);

                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_GetTypeMirrorForArrayClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror((Class) null);
                                MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_getTypeMirrorForPrimitive() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(int.class);

                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForInt() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // int
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(int.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForLong() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // long
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(long.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForShort() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // short
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(short.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForBoolean() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // boolean
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(boolean.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForByte() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // byte
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(byte.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForFloat() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // float
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(float.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForDouble() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // double
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(double.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForChar() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // char
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(char.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForNullValue_shouldReturnNull() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // null value
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(null);
                                MatcherAssert.assertThat(typeMirror, Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForNonPrimitiveType_shouldReturnNull() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // non primitive type value
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(String.class);
                                MatcherAssert.assertThat(typeMirror, Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_getTypeMirrorOfArray() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypes_getEncapsulatedTypesInstance() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.getTypes(), Matchers.notNullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testToGetElementByClassName() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithNonExistingClassNameParameter() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithNullValuedClassNameParameter() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement((String) null),
                                        Matchers.nullValue());
                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testToGetTypeMirrorByClassName() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("int").getKind(), Matchers.is(TypeKind.INT));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("long").getKind(), Matchers.is(TypeKind.LONG));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("short").getKind(), Matchers.is(TypeKind.SHORT));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("float").getKind(), Matchers.is(TypeKind.FLOAT));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("double").getKind(), Matchers.is(TypeKind.DOUBLE));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("byte").getKind(), Matchers.is(TypeKind.BYTE));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("boolean").getKind(), Matchers.is(TypeKind.BOOLEAN));
                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("char").getKind(), Matchers.is(TypeKind.CHAR));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testBehaviorWithNonExistingClassNameParameter() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testBehaviorWithNullValuedClassNameParameter() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror((String) null), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithAnonymousClassTypeMirror_shouldReturnNullBecauseNoTypeElementExists() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Comparable<Long> anonymousComparable = new Comparable<Long>() {
                                    @Override
                                    public int compareTo(Long o) {
                                        return 0;
                                    }
                                };

                                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement(anonymousComparable.getClass()), Matchers.nullValue());


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_getComponentTypeOfTypeMirrorString() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly_byType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));
                                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly_byCanonicalClassName() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class)));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_testIfComponentTypeOfTypeMirrorOfkindArrayIsReturnedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror input = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(input), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_Type() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, Object.class));
                                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, InputStream.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_TypeElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(Object.class)));
                                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(InputStream.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_Class() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, Object.class));
                                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, InputStream.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_TypeMirror() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));
                                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(InputStream.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_nullValuedTypeElement() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("should return false for null valued type element", !TypeUtils.TypeComparison.isAssignableTo((TypeElement) null, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isAssignableTo_nullValuedTypeMirrors() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo((TypeMirror) null, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));
                                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo(TypeUtils.TypeRetrieval.getTypeMirror(Object.class), (TypeMirror) null));
                                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo((TypeMirror) null, (TypeMirror) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_matchingByType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(elementForComparison, type));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_mismatchingByType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, String.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_nullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, String.class));
                                MatcherAssert.assertThat("Should return false for null valued class", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, (Class) null));
                                MatcherAssert.assertThat("Should return false for null valued type element and class", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (Class) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(type);
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                                MatcherAssert.assertThat("Shouldn't have found match", TypeUtils.TypeComparison.isTypeEqual(elementForComparison, typeMirror));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, typeMirror));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, typeMirror));
                                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, (TypeMirror) null));
                                MatcherAssert.assertThat("Should return false for null valued type element and type mirror", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (TypeMirror) null));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(String.class);


                                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(typeElement1, typeElement2));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(Boolean.class);

                                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeElement1, typeElement2));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(String.class);

                                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, typeElement2));
                                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual(typeElement1, (TypeElement) null));
                                MatcherAssert.assertThat("Should return false for null valued type elements", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (TypeElement) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                Class<StringBuilder> type = StringBuilder.class;
                                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(type);
                                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(type);


                                MatcherAssert.assertThat("Shouldn't have found match", TypeUtils.TypeComparison.isTypeEqual(typeMirror1, typeMirror2));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class);

                                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeMirror1, typeMirror2));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class);

                                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, typeMirror2));
                                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual(typeMirror1, (TypeMirror) null));
                                MatcherAssert.assertThat("Should return false for null valued type element and class", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, (TypeMirror) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);
                                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(typeMirror, StringBuilder.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);
                                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeMirror, String.class));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testNullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);

                                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, String.class));
                                MatcherAssert.assertThat("Should return false for null valued class", !TypeUtils.TypeComparison.isTypeEqual(typeMirror, (Class) null));
                                MatcherAssert.assertThat("Should return false for null valued type mirror and class", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, (Class) null));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_CheckTypeKind_isVoid_testForVoidType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(ElementUtils.CastElement.castMethod(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(element.asType()), Matchers.is(false));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldBeAbleToCompareGenericType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

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
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldNotBeAbleToCompareGenericType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

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
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldBeAbleToCompareGenericTypeWithWildcards() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

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
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldNotBeAbleToCompareGenericTypeWithWildcards() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                                        .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf("testGenericsOnParameter").getResult();

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
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -------------------------------------------------
    // -- ARRAYS
    // -------------------------------------------------


    @Test
    public void typeUtils_Arrays_isArray_detectArrayCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect array correctly", TypeUtils.Arrays.isArray(typeMirror));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_isArray_detectNonArrayCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArray(typeMirror));


                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_isArray_handleNullValueCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArray(null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_detectArraysComponentTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect array correctly", TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.equalTo(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_Arrays_getArraysComponentType_detectForNonArraysCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should detect non array correctly", TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_nullSafety() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                MatcherAssert.assertThat("Should return false for null value", TypeUtils.Arrays.getArraysComponentType(null), Matchers.nullValue());

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // isArrayOfType(TypeMirror typeMirror, Class type)


    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectMatchingArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect matching array correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectMismatchingArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, StringBuilder.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_handleNonArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMCLisArrayOfType_handleNullValueCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (Class) null));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, String.class));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (Class) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // TypeUtils.Arrays.isArrayOfType(TypeMirror typeMirror, String type)

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectMatchingArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect matching array correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectMismatchingArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, StringBuilder.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_handleNonArrayTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_handleNullValueCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (String) null));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, String.class.getCanonicalName()));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (String) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // TypeUtils.Arrays.isArrayOfType(TypeMirror typeMirror, TypeMirror componentType)

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectMatchingArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);

                                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, componentTypeMirror));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectMismatchingArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);

                                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, componentTypeMirror));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_handleNonArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, typeMirror));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_handleNullValuesCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);

                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (TypeMirror) null));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, typeMirror));
                                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (TypeMirror) null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, Object.class));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, Object.class.getCanonicalName()));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(Object.class);

                                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, componentTypeMirror));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isErasedArrayAssignableTo_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(List[].class);
                                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(Collection.class);

                                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isErasedArrayAssignableTo(typeMirror, componentTypeMirror));


                                // null safety
                                MatcherAssert.assertThat("Should return false if first typeMirror is null", !TypeUtils.Arrays.isErasedArrayAssignableTo(null, componentTypeMirror));
                                MatcherAssert.assertThat("Should return false if second typeMirror is null", !TypeUtils.Arrays.isErasedArrayAssignableTo(typeMirror, null));
                                MatcherAssert.assertThat("Should return false if both typeMirror is null", !TypeUtils.Arrays.isErasedArrayAssignableTo(null, null));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayAssignableTo_checkNullSafetyAndNonArrayTM() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(List[].class);
                                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(Collection.class);

                                MatcherAssert.assertThat("Should return false if typeMirror is null", !TypeUtils.Arrays.isArrayAssignableTo(null, componentTypeMirror));
                                MatcherAssert.assertThat("Should return false if componentType is null", !TypeUtils.Arrays.isArrayAssignableTo(typeMirror, (TypeMirror) null));
                                MatcherAssert.assertThat("Should return false if both typeMirror and componentType are null", !TypeUtils.Arrays.isArrayAssignableTo(null, (TypeMirror) null));


                                MatcherAssert.assertThat("Should return for non array typeMirror", !TypeUtils.Arrays.isArrayAssignableTo(TypeUtils.TypeRetrieval.getTypeMirror(List.class), componentTypeMirror));

                            }
                        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
