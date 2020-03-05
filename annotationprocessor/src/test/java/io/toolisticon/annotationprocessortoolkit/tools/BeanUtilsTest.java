package io.toolisticon.annotationprocessortoolkit.tools;

import com.sun.source.tree.StatementTree;
import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unit test for {@link BeanUtils}.
 */
public class BeanUtilsTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationValueUtilsTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void getPossibleGetterOrSetterNames_getter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                MatcherAssert.assertThat(Arrays.asList(BeanUtils.getPrefixedName("get", "testField")), Matchers.contains("getTestField"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------------------
    // Field level annotations
    // -----------------------------------

    @Test
    public void fieldWithImplementedGetterAndSetters_checkHasGetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                .getResult().get(0);


                        // shouldn't find nonexisting
                        MatcherAssert.assertThat("Should detect getter ", BeanUtils.checkHasGetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void fieldWithImplementedGetterAndSetters_checkHasSetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                .getResult().get(0);


                        // shouldn't find nonexisting
                        MatcherAssert.assertThat("Should detect setter ", BeanUtils.checkHasSetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithImplementedGetterAndSetterAnnotation_checkHasSetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetterAnnotation")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should detect lombok generated setter ", BeanUtils.checkHasSetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithImplementedGetterAndSetterAnnotation_checkHasGetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedSetterAndGetterAnnotation")
                                .getResult().get(0);


                        // shouldn't find nonexisting
                        MatcherAssert.assertThat("Should detect lombok generated getter ", BeanUtils.checkHasGetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithoutGetter_checkHasGetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutGetter")
                                .getResult().get(0);


                        // shouldn't find nonexisting
                        MatcherAssert.assertThat("Should not detect getter ", !BeanUtils.checkHasGetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithoutSetter_checkHasSetter() {

        unitTestBuilder.useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutSetter")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should not detect setter ", !BeanUtils.checkHasSetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------------------
    // Type level lombok annotations
    // -----------------------------------

    @Test
    public void fieldWithoutSetter_checkHasLombokSetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/LombokDataOnClass.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should  detect setter ", BeanUtils.checkHasSetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithoutSetter_checkHasLombokGetterOnClass() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/LombokGetterOnClass.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should  detect getter ", BeanUtils.checkHasGetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void fieldWithoutSetter_checkHasLombokSetterOnClass() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/LombokSetterOnClass.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should detect setter ", BeanUtils.checkHasSetter(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------
    // get getter name
    // -----------------------

    @Test
    public void getGetterName_booleanFieldWithLombokGetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("booleanFieldWithGetterAnnotation")
                                .getResult().get(0);


                        MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("isBooleanFieldWithGetterAnnotation"));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getGetterName_nonBooleanFieldWithLombokGetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedSetterAndGetterAnnotation")
                                .getResult().get(0);


                        MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("getFieldWithImplementedSetterAndGetterAnnotation"));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getGetterName_nonBooleanFieldWithLombokGetterAndSetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                .getResult().get(0);


                        MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("getFieldWithImplementedGetterAndSetters"));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------
    // is attribute
    // -----------------------

    @Test
    public void isAttribute_fieldWithBothGetterAndSetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Must return true for field with getter and setter", BeanUtils.isAttribute(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isAttribute_fieldWithoutSetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutGetter")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Must return true for field without setter", !BeanUtils.isAttribute(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isAttribute_fieldWithoutGetter() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/FieldLevelTestcases.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_FIELD)
                                .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutSetter")
                                .getResult().get(0);


                        MatcherAssert.assertThat("Must return true for field without getter", !BeanUtils.isAttribute(field));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------
    // get attributes
    // -----------------------

    @Test
    public void getAttributes_withTypeHierarchy1() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/TypeHierarchyTestClass.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*InheritingType")
                                .getResult().get(0);


                        BeanUtils.AttributeResult[] results = BeanUtils.getAttributes(typeElement);

                        Set<String> attributeNames = new HashSet<String>();

                        for (BeanUtils.AttributeResult result : results) {
                            attributeNames.add(result.getFieldName());
                        }

                        MatcherAssert.assertThat(attributeNames, Matchers.contains("booleanField", "stringField"));
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void getAttributes_withTypeHierarchy2() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/TypeHierarchyTestClass.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CLASS)
                                .applyFilter(CoreMatchers.BY_REGEX_NAME).filterByOneOf(".*InheritingType")
                                .getResult().get(0);


                        BeanUtils.AttributeResult[] results = BeanUtils.getAttributesWithInheritance(typeElement);

                        Set<String> attributeNames = new HashSet<String>();

                        for (BeanUtils.AttributeResult result : results) {
                            attributeNames.add(result.getFieldName());
                        }

                        MatcherAssert.assertThat(attributeNames, Matchers.containsInAnyOrder("booleanField", "stringField", "superBooleanField", "superStringField"));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------
    // default constructor tests
    // -----------------------

    @Test
    public void isDefaultNoargConstructor_defaultNoargConstructor() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/DefaultNoargConstructorTest.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should return true for default constructor", BeanUtils.isDefaultNoargConstructor(constructor));
                        MatcherAssert.assertThat("Should return true for default constructor", BeanUtils.hasDefaultNoargsConstructor(element));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isDefaultNoargConstructor_explicitNoargConstructor() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/ExplicitNoargConstructorTest.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                .getResult().get(0);


                        MatcherAssert.assertThat("Should return false for explicit constructor", !BeanUtils.isDefaultNoargConstructor(constructor));
                        MatcherAssert.assertThat("Should return false for explicit constructor", !BeanUtils.hasDefaultNoargsConstructor(element));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void isDefaultNoargConstructor_explicitNoargConstructor2() {

        unitTestBuilder
                .useSource(JavaFileObjectUtils.readFromResource("testcases.beanutils/ExplicitNoargConstructorTest2.java"))
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {
                        ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                .getResult().get(0);

                        List<? extends StatementTree> statements = ProcessingEnvironmentUtils.getTrees().getTree(constructor).getBody().getStatements();


                        MatcherAssert.assertThat("Should return true for explicit constructor that looks like default constructor", BeanUtils.isDefaultNoargConstructor(constructor));
                        MatcherAssert.assertThat("Should return true for explicit constructor that looks like default constructor", BeanUtils.hasDefaultNoargsConstructor(element));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


}