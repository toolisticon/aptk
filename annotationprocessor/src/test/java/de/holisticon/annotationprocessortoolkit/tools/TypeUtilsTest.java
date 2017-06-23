package de.holisticon.annotationprocessortoolkit.tools;


import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.AccessEnclosedElements;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils.CastElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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
                                "TypeUtils : Get TypeElement for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElement(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeElement, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeElement.getSimpleName().toString(), Matchers.is(AbstractTestAnnotationProcessorClass.class.getSimpleName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeElement for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElement(String[].class);

                                        MatcherAssert.assertThat("An array TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeElement for primitive class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeElement typeElement = getTypeUtils().getTypeElement(int.class);

                                        MatcherAssert.assertThat("A primitive TypeMirror can't be converted into a TypeElement so result has to be null", typeElement, Matchers.nullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeMirror for class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(AbstractTestAnnotationProcessorClass.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DECLARED));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeMirror for array class",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : Get TypeMirror for atomic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(int.class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for int",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        // int
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(int.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.INT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for long",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // long
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(long.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.LONG));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for short",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // short
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(short.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.SHORT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for boolean",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // boolean
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(boolean.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BOOLEAN));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for byte",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // byte
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(byte.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.BYTE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for float",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // float
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(float.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.FLOAT));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for double",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // double
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(double.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.DOUBLE));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for char",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // char
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(char.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.CHAR));
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for null value should return null",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // null value
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(null);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getPrimitiveTypeMirror : Get TypeMirror for non primitive type should return String",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        // non primitive type value
                                        TypeMirror typeMirror = getTypeUtils().getPrimitiveTypeMirror(String.class);
                                        MatcherAssert.assertThat(typeMirror, Matchers.nullValue());
                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.isArrayOfType : ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat(typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));
                                        MatcherAssert.assertThat(((ArrayType) typeMirror).getComponentType(), Matchers.is(getTypeUtils().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getArraysComponentType : Get component type of TypeMirror array ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat(getTypeUtils().getArraysComponentType(typeMirror), Matchers.is(getTypeUtils().getTypeMirror(String.class)));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().isArrayOfType(typeMirror, String.class));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().isArrayOfType(typeMirror, Boolean.class));

                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().isArrayOfType(typeMirror, String.class.getCanonicalName()));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().isArrayOfType(typeMirror, Boolean.class.getCanonicalName()));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.isArrayOfType : Should check if the TypeMirror has a specific component type correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeMirror typeMirror = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should not be null", typeMirror, Matchers.notNullValue());
                                        MatcherAssert.assertThat("PRECONDITION : typeMirror should be array", typeMirror.getKind(), Matchers.is(TypeKind.ARRAY));

                                        MatcherAssert.assertThat("Should detect matching component type correctly", getTypeUtils().isArrayOfType(typeMirror, getTypeUtils().getTypeMirror(String.class)));
                                        MatcherAssert.assertThat("Should detect non matching component type correctly", !getTypeUtils().isArrayOfType(typeMirror, getTypeUtils().getTypeMirror(Boolean.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableTo(element, Object.class));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableTo(element, InputStream.class));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableTo(element, getTypeUtils().getTypeElement(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableTo(element, getTypeUtils().getTypeElement(InputStream.class)));

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : test isAssignableTo",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        MatcherAssert.assertThat("type element should be detected as assignable to Object", getTypeUtils().isAssignableTo(element, getTypeUtils().getTypeMirror(Object.class)));
                                        MatcherAssert.assertThat("type element shouldn't be detected as assignable to InputStream", !getTypeUtils().isAssignableTo(element, getTypeUtils().getTypeMirror(InputStream.class)));

                                    }
                                },
                                true

                        },
                        {
                                "TypeUtils : test check for void type ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().checkTypeKind().isVoid(CastElement.castMethod(AccessEnclosedElements.getEnclosedElementsByName(element, "synchronizedMethod").get(0)).getReturnType()), Matchers.is(true));
                                        MatcherAssert.assertThat(getTypeUtils().checkTypeKind().isVoid(element.asType()), Matchers.is(false));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils : get encapsulated javax.lang.model.util.Types instance",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypes(), Matchers.notNullValue());

                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getTypeElement() : test to get Element by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getTypeElement() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeElement("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.getTypeElement() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeElement((String) null),
                                                Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getTypeElement() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getTypeMirror() : test to get TypeMirror by class name",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClass"), Matchers.is(element.asType()));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getTypeMirror() : test behavior with non existing class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeMirror("de.holisticon.annotationprocessor.AnnotationProcessorTestClassXXXX"), Matchers.nullValue());


                                    }
                                },
                                true
                        },
                        {
                                "TypeUtils.getTypeMirror() : test behavior with null valued class name parameter",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        MatcherAssert.assertThat(getTypeUtils().getTypeMirror((String) null), Matchers.nullValue());


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.getArraysComponentType() : test if component type of TypeMirror of kind ARRAY is returned correctly",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {


                                        TypeMirror input = getTypeUtils().getTypeMirror(String[].class);

                                        MatcherAssert.assertThat(getTypeUtils().getArraysComponentType(input), Matchers.is(getTypeUtils().getTypeMirror(String.class)));

                                    }
                                },
                                true


                        },


                }

        );


    }

}
