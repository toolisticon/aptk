package io.toolisticon.annotationprocessortoolkit.tools;


import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

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


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);

                MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractUnitTestAnnotationProcessorClass.class.getSimpleName()));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForArrayClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(String[].class);

                MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForTypeMirror() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(element.asType());

                MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is("AnnotationProcessorTestClass"));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForNullValuedTypeMirror() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement((TypeMirror) null);
                MatcherAssert.assertThat(typeElement, Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_GetTypeElementForPrimitiveTypeClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(int.class);
                MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_GetTypeMirrorForClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(AbstractUnitTestAnnotationProcessorClass.class);

                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_GetTypeMirrorForArrayClass() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror((Class) null);
                MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_getTypeMirrorForPrimitive() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(int.class);

                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForInt() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // int
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(int.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForLong() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // long
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(long.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForShort() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // short
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(short.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForBoolean() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // boolean
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(boolean.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForByte() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // byte
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(byte.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForFloat() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // float
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(float.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForDouble() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // double
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(double.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForChar() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // char
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(char.class);
                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForNullValue_shouldReturnNull() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // null value
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(null);
                MatcherAssert.assertThat(typeMirror, Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getPrimitiveTypeMirror_getTypeMirrorForNonPrimitiveType_shouldReturnNull() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // non primitive type value
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getPrimitiveTypeMirror(String.class);
                MatcherAssert.assertThat(typeMirror, Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_getTypeMirrorOfArray() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypes_getEncapsulatedTypesInstance() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.getTypes(), Matchers.notNullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testToGetElementByClassName() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithNonExistingClassNameParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithNullValuedClassNameParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeElement((String) null),
                        Matchers.nullValue());
            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testToGetTypeMirrorByClassName() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testBehaviorWithNonExistingClassNameParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror("io.toolisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    @Test
    public void typeUtils_typeRetrieval_getTypeMirror_testBehaviorWithNullValuedClassNameParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.TypeRetrieval.getTypeMirror((String) null), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_typeRetrieval_getTypeElement_testBehaviorWithAnonymousClassTypeMirror_shouldReturnNullBecauseNoTypeElementExists() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_getComponentTypeOfTypeMirrorString() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly_byType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));
                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly_byCanonicalClassName() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));
                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_isArrayOfType_shouldCheckIfTheTypeMirrorHasASpecificComponentTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                MatcherAssert.assertThat("Should detect matching component type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                MatcherAssert.assertThat("Should detect non matching component type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class)));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_testIfComponentTypeOfTypeMirrorOfkindArrayIsReturnedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror input = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat(TypeUtils.Arrays.getArraysComponentType(input), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_Type() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, Object.class));
                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, InputStream.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_TypeElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(Object.class)));
                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeElement(InputStream.class)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_Class() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, Object.class));
                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, InputStream.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_TypeMirror() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("type element should be detected as assignable to Object", TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));
                MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !TypeUtils.TypeComparison.isAssignableTo(element, TypeUtils.TypeRetrieval.getTypeMirror(InputStream.class)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    @Test
    public void typeUtils_TypeComparison_TETM_isAssignableTo_nullValuedTypeElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("should return false for null valued type element", !TypeUtils.TypeComparison.isAssignableTo((TypeElement) null, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isAssignableTo_nullValuedTypeMirrors() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo((TypeMirror) null, TypeUtils.TypeRetrieval.getTypeMirror(Object.class)));
                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo(TypeUtils.TypeRetrieval.getTypeMirror(Object.class), (TypeMirror) null));
                MatcherAssert.assertThat("should return false for null valued type mirror", !TypeUtils.TypeComparison.isAssignableTo((TypeMirror) null, (TypeMirror) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_matchingByType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(elementForComparison, type));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_mismatchingByType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, String.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_CLTE_isTypeEqual_nullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, String.class));
                MatcherAssert.assertThat("Should return false for null valued class", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, (Class) null));
                MatcherAssert.assertThat("Should return false for null valued type element and class", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (Class) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(type);
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);


                MatcherAssert.assertThat("Shouldn't have found match", TypeUtils.TypeComparison.isTypeEqual(elementForComparison, typeMirror));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, typeMirror));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTE_isTypeEqual_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                TypeElement elementForComparison = TypeUtils.TypeRetrieval.getTypeElement(type);

                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, typeMirror));
                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual(elementForComparison, (TypeMirror) null));
                MatcherAssert.assertThat("Should return false for null valued type element and type mirror", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (TypeMirror) null));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(String.class);


                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(typeElement1, typeElement2));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(Boolean.class);

                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeElement1, typeElement2));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TETE_isTypeEqual_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeElement typeElement1 = TypeUtils.TypeRetrieval.getTypeElement(String.class);
                TypeElement typeElement2 = TypeUtils.TypeRetrieval.getTypeElement(String.class);

                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, typeElement2));
                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual(typeElement1, (TypeElement) null));
                MatcherAssert.assertThat("Should return false for null valued type elements", !TypeUtils.TypeComparison.isTypeEqual((TypeElement) null, (TypeElement) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                Class<StringBuilder> type = StringBuilder.class;
                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(type);
                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(type);


                MatcherAssert.assertThat("Shouldn't have found match", TypeUtils.TypeComparison.isTypeEqual(typeMirror1, typeMirror2));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class);

                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeMirror1, typeMirror2));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMTM_isTypeEqual_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror1 = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                TypeMirror typeMirror2 = TypeUtils.TypeRetrieval.getTypeMirror(Boolean.class);

                MatcherAssert.assertThat("Should return false for null valued type element", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, typeMirror2));
                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual(typeMirror1, (TypeMirror) null));
                MatcherAssert.assertThat("Should return false for null valued type element and class", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, (TypeMirror) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testIfMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);
                MatcherAssert.assertThat("Should have found match", TypeUtils.TypeComparison.isTypeEqual(typeMirror, StringBuilder.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testIfNonMatchingClassIsDetectedCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);
                MatcherAssert.assertThat("Shouldn't have found match", !TypeUtils.TypeComparison.isTypeEqual(typeMirror, String.class));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_TypeComparison_TMCL_isTypeEqual_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);

                MatcherAssert.assertThat("Should return false for null valued type mirror", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, String.class));
                MatcherAssert.assertThat("Should return false for null valued class", !TypeUtils.TypeComparison.isTypeEqual(typeMirror, (Class) null));
                MatcherAssert.assertThat("Should return false for null valued type mirror and class", !TypeUtils.TypeComparison.isTypeEqual((TypeMirror) null, (Class) null));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_CheckTypeKind_isVoid_testForVoidType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(ElementUtils.CastElement.castMethod(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                MatcherAssert.assertThat(TypeUtils.CheckTypeKind.isVoid(element.asType()), Matchers.is(false));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldBeAbleToCompareGenericType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldNotBeAbleToCompareGenericType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldBeAbleToCompareGenericTypeWithWildcards() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_shouldNotBeAbleToCompareGenericTypeWithWildcards() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    // -------------------------------------------------
    // -- ARRAYS
    // -------------------------------------------------


    @Test
    public void typeUtils_Arrays_isArray_detectArrayCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect array correctly", TypeUtils.Arrays.isArray(typeMirror));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_isArray_detectNonArrayCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArray(typeMirror));


            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_isArray_handleNullValueCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArray(null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_detectArraysComponentTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect array correctly", TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.equalTo(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    @Test
    public void typeUtils_Arrays_getArraysComponentType_detectForNonArraysCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should detect non array correctly", TypeUtils.Arrays.getArraysComponentType(typeMirror), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_getArraysComponentType_nullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                MatcherAssert.assertThat("Should return false for null value", TypeUtils.Arrays.getArraysComponentType(null), Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    // isArrayOfType(TypeMirror typeMirror, Class type)


    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectMatchingArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect matching array correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectMismatchingArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, StringBuilder.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_handleNonArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, String.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMCLisArrayOfType_handleNullValueCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (Class) null));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, String.class));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (Class) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    // TypeUtils.Arrays.isArrayOfType(TypeMirror typeMirror, String type)

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectMatchingArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect matching array correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectMismatchingArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, StringBuilder.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_handleNonArrayTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, String.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_handleNullValueCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (String) null));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, String.class.getCanonicalName()));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (String) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


    // TypeUtils.Arrays.isArrayOfType(TypeMirror typeMirror, TypeMirror componentType)

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectMatchingArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);

                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayOfType(typeMirror, componentTypeMirror));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectMismatchingArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(StringBuilder.class);

                MatcherAssert.assertThat("Should detect mismatching array type correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, componentTypeMirror));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_handleNonArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);
                MatcherAssert.assertThat("Should detect non array correctly", !TypeUtils.Arrays.isArrayOfType(typeMirror, typeMirror));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_handleNullValuesCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String.class);

                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType(typeMirror, (TypeMirror) null));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, typeMirror));
                MatcherAssert.assertThat("Should return false for null value", !TypeUtils.Arrays.isArrayOfType((TypeMirror) null, (TypeMirror) null));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMCL_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, Object.class));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMSTR_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);

                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, Object.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayOfType_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(String[].class);
                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(Object.class);

                MatcherAssert.assertThat("Should detect matching array type correctly", TypeUtils.Arrays.isArrayAssignableTo(typeMirror, componentTypeMirror));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isErasedArrayAssignableTo_detectAssignableArraysTypeCorrectly() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

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
                .testCompilation();
    }

    @Test
    public void typeUtils_Arrays_TMTM_isArrayAssignableTo_checkNullSafetyAndNonArrayTM() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                TypeMirror typeMirror = TypeUtils.TypeRetrieval.getTypeMirror(List[].class);
                TypeMirror componentTypeMirror = TypeUtils.TypeRetrieval.getTypeMirror(Collection.class);

                MatcherAssert.assertThat("Should return false if typeMirror is null", !TypeUtils.Arrays.isArrayAssignableTo(null, componentTypeMirror));
                MatcherAssert.assertThat("Should return false if componentType is null", !TypeUtils.Arrays.isArrayAssignableTo(typeMirror, (TypeMirror) null));
                MatcherAssert.assertThat("Should return false if both typeMirror and componentType are null", !TypeUtils.Arrays.isArrayAssignableTo(null, (TypeMirror) null));


                MatcherAssert.assertThat("Should return for non array typeMirror", !TypeUtils.Arrays.isArrayAssignableTo(TypeUtils.TypeRetrieval.getTypeMirror(List.class), componentTypeMirror));

            }
        })
                .compilationShouldSucceed()
                .testCompilation();
    }


}
