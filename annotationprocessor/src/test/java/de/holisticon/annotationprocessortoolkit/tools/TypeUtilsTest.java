package de.holisticon.annotationprocessortoolkit.tools;


import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.AccessEnclosedElements;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.CastElement;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
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
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractTestAnnotationProcessorClass.class.getSimpleName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(String[].class);

                                        MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement : Get TypeElement for primitive class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(int.class);

                                        MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().doTypeRetrieval().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Test null safety",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror((Class) null);

                                        MatcherAssert.assertThat("Should return null for null valued input parameter", typeMirror, Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : Get TypeMirror for atomic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(int.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for int",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // int
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(int.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for long",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // long
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(long.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for short",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // short
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(short.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for boolean",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // boolean
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(boolean.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for byte",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // byte
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(byte.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for float",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // float
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(float.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for double",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // double
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(double.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for char",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // char
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(char.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for null value should return null",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // null value
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(null);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getPrimitiveTypeMirror : Get TypeMirror for non primitive type should return String",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // non primitive type value
                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getPrimitiveTypeMirror(String.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror : get TypeMirror of array",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().doTypeRetrieval().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypes : get encapsulated javax.lang.model.util.Types instance",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypes(), Matchers.notNullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test to get Element by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeElement((String) null),
                                                Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeMirror() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeMirror((String) null), Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeRetrieval().getTypeElement() : test behavior with anonymous class type mirror - should return null because no TyoeElement exists",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        Comparable<Long> anonymousComparable = new Comparable<Long>() {
                                            @Override
                                            public int compareTo(Long o) {
                                                return 0;
                                            }
                                        };

                                        MatcherAssert.assertThat(getTypeUtils().doTypeRetrieval().getTypeElement(anonymousComparable.getClass()), Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().getArraysComponentType : Get component type of TypeMirror array ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat(getTypeUtils().doArrays().getArraysComponentType(typeMirror), Matchers.is(getTypeUtils().doTypeRetrieval().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().doArrays().isArrayOfType(typeMirror, String.class));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().doArrays().isArrayOfType(typeMirror, Boolean.class));

                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().doArrays().isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().doArrays().isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().doArrays().isArrayOfType(typeMirror, getTypeUtils().doTypeRetrieval().getTypeMirror(String.class)));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().doArrays().isArrayOfType(typeMirror, getTypeUtils().doTypeRetrieval().getTypeMirror(Boolean.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().getArraysComponentType() : test if component type of TypeMirror of kind ARRAY is returned correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        TypeMirror input = getTypeUtils().doTypeRetrieval().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(getTypeUtils().doArrays().getArraysComponentType(input), Matchers.is(getTypeUtils().doTypeRetrieval().getTypeMirror(String.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().doTypeComparison().isAssignableTo(element, Object.class));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().doTypeComparison().isAssignableTo(element, InputStream.class));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().doTypeComparison().isAssignableTo(element, getTypeUtils().doTypeRetrieval().getTypeElement(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().doTypeComparison().isAssignableTo(element, getTypeUtils().doTypeRetrieval().getTypeElement(InputStream.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doTypeComparison().isAssignableTo : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().doTypeComparison().isAssignableTo(element, getTypeUtils().doTypeRetrieval().getTypeMirror(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().doTypeComparison().isAssignableTo(element, getTypeUtils().doTypeRetrieval().getTypeMirror(InputStream.class)));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.doTypeComparison().isTypeEqual(TypeElement,Class) : test if matching class is detected correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        Class<StringBuilder> type = StringBuilder.class;
                                        TypeElement elementForComparison = getTypeUtils().doTypeRetrieval().getTypeElement(type);


                                        MatcherAssert.assertThat("Should have found match", getTypeUtils().doTypeComparison().isTypeEqual(elementForComparison, type));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.doTypeComparison().isTypeEqual(TypeElement,Class) : test if non matching class is detected correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        Class<StringBuilder> type = StringBuilder.class;
                                        TypeElement elementForComparison = getTypeUtils().doTypeRetrieval().getTypeElement(type);


                                        MatcherAssert.assertThat("Should have found match", !getTypeUtils().doTypeComparison().isTypeEqual(elementForComparison, String.class));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils.doCheckTypeKind().isVoid : test check for void type ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().doCheckTypeKind().isVoid(CastElement.castMethod(AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                        MatcherAssert.assertThat(getTypeUtils().doCheckTypeKind().isVoid(element.asType()), Matchers.is(false));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                getTypeUtils().doGenerics().genericTypeEquals(
                                                        method.getParameters().get(0).asType(),
                                                        getTypeUtils().doGenerics().createGenericType(Map.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().doGenerics().createGenericType(Long.class)
                                                                )
                                                        )
                                                )
                                        );

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should not be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                !getTypeUtils().doGenerics().genericTypeEquals(
                                                        method.getParameters().get(0).asType(),
                                                        getTypeUtils().doGenerics().createGenericType(Map.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().doGenerics().createGenericType(Double.class)
                                                                )
                                                        )
                                                )
                                        );


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doGenerics().genericTypeEquals : Should be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                getTypeUtils().doGenerics().genericTypeEquals(
                                                        method.getParameters().get(1).asType(),
                                                        getTypeUtils().doGenerics().createGenericType(Map.class,
                                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                                        getTypeUtils().doGenerics().createGenericType(StringBuilder.class)
                                                                ),
                                                                getTypeUtils().doGenerics().createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                                                getTypeUtils().doGenerics().createGenericType(
                                                                                        List.class,
                                                                                        getTypeUtils().doGenerics().createPureWildcard()
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
                                "TypeUtils.doGenerics().genericTypeEquals : Should not be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                        MatcherAssert.assertThat("Should be equal and therefore true",
                                                !getTypeUtils().doGenerics().genericTypeEquals(
                                                        method.getParameters().get(1).asType(),
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                Map.class,
                                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                                        getTypeUtils().doGenerics().createGenericType(StringBuilder.class)
                                                                ),
                                                                getTypeUtils().doGenerics().createGenericType(
                                                                        Comparator.class,
                                                                        getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                                                getTypeUtils().doGenerics().createGenericType(
                                                                                        List.class,
                                                                                        getTypeUtils().doGenerics().createWildcardWithExtendsBound(getTypeUtils().doGenerics().createGenericType(String.class))
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
