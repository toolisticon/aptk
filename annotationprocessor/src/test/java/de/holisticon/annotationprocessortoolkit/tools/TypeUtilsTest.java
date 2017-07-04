package de.holisticon.annotationprocessortoolkit.tools;


import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.AccessEnclosedElements;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.CastElement;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.tools.TypeUtils}.
 */
@RunWith(Parameterized.class)
public class TypeUtilsTest extends AbstractAnnotationProcessorTestBaseClass {


    public TypeUtilsTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(TypeUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement : Get TypeElement for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractTestAnnotationProcessorClass.class.getSimpleName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement : Get TypeElement for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(String[].class);

                                        MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement : Get TypeElement for primitive class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(int.class);

                                        MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror : Get TypeMirror for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror : Get TypeMirror for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror : Test null safety",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror((Class) null);

                                        MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror : Get TypeMirror for atomic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(int.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for int",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // int
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(int.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for long",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // long
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(long.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for short",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // short
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(short.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for boolean",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // boolean
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(boolean.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for byte",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // byte
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(byte.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for float",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // float
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(float.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for double",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // double
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(double.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for char",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // char
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(char.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for null value should return null",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // null value
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(null);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getPrimitiveTypeMirror : Get TypeMirror for non primitive type should return String",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // non primitive type value
                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getPrimitiveTypeMirror(String.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror : get TypeMirror of array",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypes : get encapsulated javax.lang.model.util.Types instance",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypes(), Matchers.notNullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement() : test to get Element by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeElement((String) null),
                                                Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeMirror() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror((String) null), Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_RETRIEVAL.getTypeElement() : test behavior with anonymous class type mirror - should return null because no TyoeElement exists",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        Comparable<Long> anonymousComparable = new Comparable<Long>() {
                                            @Override
                                            public int compareTo(Long o) {
                                                return 0;
                                            }
                                        };

                                        MatcherAssert.assertThat(getTypeUtils().TYPE_RETRIEVAL.getTypeElement(anonymousComparable.getClass()), Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.ARRAYS.getArraysComponentType : Get component type of TypeMirror array ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat(getTypeUtils().ARRAYS.getArraysComponentType(typeMirror), Matchers.is(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.ARRAYS.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().ARRAYS.isArrayOfType(typeMirror, String.class));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().ARRAYS.isArrayOfType(typeMirror, Boolean.class));

                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.ARRAYS.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().ARRAYS.isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().ARRAYS.isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.ARRAYS.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().ARRAYS.isArrayOfType(typeMirror, getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String.class)));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().ARRAYS.isArrayOfType(typeMirror, getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(Boolean.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.ARRAYS.getArraysComponentType() : test if component type of TypeMirror of kind ARRAY is returned correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        TypeMirror input = getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(getTypeUtils().ARRAYS.getArraysComponentType(input), Matchers.is(getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(String.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_COMPARISON.isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, Object.class));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, InputStream.class));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_COMPARISON.isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, getTypeUtils().TYPE_RETRIEVAL.getTypeElement(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, getTypeUtils().TYPE_RETRIEVAL.getTypeElement(InputStream.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.TYPE_COMPARISON.isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().TYPE_COMPARISON.isAssignableTo(element, getTypeUtils().TYPE_RETRIEVAL.getTypeMirror(InputStream.class)));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.TYPE_COMPARISON.isTypeEqual(TypeElement,Class) : test if matching class is detected correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        Class<StringBuilder> type = StringBuilder.class;
                                        TypeElement elementForComparison = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(type);


                                        MatcherAssert.assertThat("Should have found match", getTypeUtils().TYPE_COMPARISON.isTypeEqual(elementForComparison, type));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.TYPE_COMPARISON.isTypeEqual(TypeElement,Class) : test if non matching class is detected correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        Class<StringBuilder> type = StringBuilder.class;
                                        TypeElement elementForComparison = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(type);


                                        MatcherAssert.assertThat("Should have found match", !getTypeUtils().TYPE_COMPARISON.isTypeEqual(elementForComparison, String.class));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.CHECK_TYPE_KIND.isVoid : test check for void type ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().CHECK_TYPE_KIND.isVoid(CastElement.castMethod(AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                        MatcherAssert.assertThat(getTypeUtils().CHECK_TYPE_KIND.isVoid(element.asType()), Matchers.is(false));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.GENERICS.genericTypeEquals : Should be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filter.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                getTypeUtils().GENERICS.genericTypeEquals(
                                                        method.getParameters().get(0).asType(),
                                                        getTypeUtils().GENERICS.createGenericType(Map.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().GENERICS.createGenericType(Long.class)
                                                                )
                                                        )
                                                )
                                        );

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.GENERICS.genericTypeEquals : Should not be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filter.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                !getTypeUtils().GENERICS.genericTypeEquals(
                                                        method.getParameters().get(0).asType(),
                                                        getTypeUtils().GENERICS.createGenericType(Map.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().GENERICS.createGenericType(Double.class)
                                                                )
                                                        )
                                                )
                                        );


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.GENERICS.genericTypeEquals : Should be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filter.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                getTypeUtils().GENERICS.genericTypeEquals(
                                                        method.getParameters().get(1).asType(),
                                                        getTypeUtils().GENERICS.createGenericType(Map.class,
                                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                                        getTypeUtils().GENERICS.createGenericType(StringBuilder.class)
                                                                ),
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                                                getTypeUtils().GENERICS.createGenericType(
                                                                                        List.class,
                                                                                        getTypeUtils().GENERICS.createWildcardPure()
                                                                                )
                                                                        )

                                                                )
                                                        )
                                                )
                                        );

                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.genericTypeEquals : Should not be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filter.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filter.NAME_FILTER).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                !getTypeUtils().GENERICS.genericTypeEquals(
                                                        method.getParameters().get(1).asType(),
                                                        getTypeUtils().GENERICS.createGenericType(Map.class,
                                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                                        getTypeUtils().GENERICS.createGenericType(StringBuilder.class)
                                                                ),
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                                                getTypeUtils().GENERICS.createGenericType(
                                                                                        List.class,
                                                                                        getTypeUtils().GENERICS.createWildcardWithExtendsBound(getTypeUtils().GENERICS.createGenericType(String.class))
                                                                                )
                                                                        )

                                                                )
                                                        )
                                                )
                                        );

                                    }
                                },
                                true


                        },


                }

        );


    }

}
