package io.toolisticon.annotationprocessortoolkit.tools;

import com.sun.source.tree.StatementTree;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
@RunWith(Parameterized.class)
public class BeanUtilsTest extends AbstractAnnotationProcessorUnitTest {

    public BeanUtilsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(


                new Object[][]{


                        {
                                "getPossibleGetterOrSetterNames - getter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              MatcherAssert.assertThat(Arrays.asList(BeanUtils.getPrefixedName("get", "testField")), Matchers.contains("getTestField"));


                                                          }
                                                      }
                                        )
                                        .build()


                        },


                        // -----------------------------------
                        // Field level annotations
                        // -----------------------------------

                        {
                                "BeanUtilsTest - fieldWithImplementedGetterAndSetters - test checkHasGetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat("Should detect getter ", BeanUtils.checkHasGetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithImplementedGetterAndSetters - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat("Should detect setter ", BeanUtils.checkHasSetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithImplementedGetterAndSetterAnnotation - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetterAnnotation")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should detect lombok generated setter ", BeanUtils.checkHasSetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithImplementedSetterAndGetterAnnotation - test checkHasGetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedSetterAndGetterAnnotation")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat("Should detect lombok generated getter ", BeanUtils.checkHasGetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithoutGetter - test checkHasGetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutGetter")
                                                                      .getResult().get(0);


                                                              // shouldn't find nonexisting
                                                              MatcherAssert.assertThat("Should not detect getter ", !BeanUtils.checkHasGetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithoutSetter - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutSetter")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should not detect setter ", !BeanUtils.checkHasSetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------------------
                        // Type level lombok annotations
                        // -----------------------------------

                        {
                                "BeanUtilsTest - fieldWithoutSetter - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/LombokDataOnClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should  detect setter ", BeanUtils.checkHasSetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithoutSetter - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/LombokGetterOnClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should  detect getter ", BeanUtils.checkHasGetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - fieldWithoutSetter - test checkHasSetter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/LombokSetterOnClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("testField")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should detect setter ", BeanUtils.checkHasSetter(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------
                        // get getter name
                        // -----------------------

                        {
                                "BeanUtilsTest - getGetterName - boolean field with lombok @Getter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("booleanFieldWithGetterAnnotation")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("isBooleanFieldWithGetterAnnotation"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "BeanUtilsTest - getGetterName - non boolean field with lombok @Getter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedSetterAndGetterAnnotation")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("getFieldWithImplementedSetterAndGetterAnnotation"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "BeanUtilsTest - getGetterName - non boolean field with lombok @Getter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat(BeanUtils.getGetterMethodName(field), Matchers.is("getFieldWithImplementedGetterAndSetters"));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------
                        // is attribute
                        // -----------------------

                        {
                                "BeanUtilsTest - isAttribute - field with both getter and setter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithImplementedGetterAndSetters")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Must return true for field with getter and setter", BeanUtils.isAttribute(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - isAttribute - field without setter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutGetter")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Must return true for field without setter", !BeanUtils.isAttribute(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - isAttribute - field without getter",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/FieldLevelTestcases.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              VariableElement field = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_FIELD)
                                                                      .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("fieldWithoutSetter")
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Must return true for field without getter", !BeanUtils.isAttribute(field));

                                                          }
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------
                        // get attributes
                        // -----------------------

                        {
                                "BeanUtilsTest - getAttributes",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/TypeHierarchyTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },

                        {
                                "BeanUtilsTest - getAttributes",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/TypeHierarchyTestClass.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
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
                                                      }
                                        )
                                        .build()


                        },

                        // -----------------------
                        // default constructor tests
                        // -----------------------
/*-
                        {
                                "BeanUtilsTest - isDefaultNoargConstructor - default noarg constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/DefaultNoargConstructorTest.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should return true for default constructor", BeanUtils.isDefaultNoargConstructor(constructor));
                                                              MatcherAssert.assertThat("Should return true for default constructor", BeanUtils.hasDefaultNoargsConstructor(element));



                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "BeanUtilsTest - isDefaultNoargConstructor - explicit noarg constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/ExplicitNoargConstructorTest.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                                                      .getResult().get(0);


                                                              MatcherAssert.assertThat("Should return false for explicit constructor", !BeanUtils.isDefaultNoargConstructor(constructor));
                                                              MatcherAssert.assertThat("Should return false for explicit constructor", !BeanUtils.hasDefaultNoargsConstructor(element));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
                        {
                                "BeanUtilsTest - isDefaultNoargConstructor - explicit noarg constructor",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .useCustomSourceFile("testcases.beanutils/ExplicitNoargConstructorTest2.java")
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                                          @Override
                                                          protected void testCase(TypeElement element) {


                                                              ExecutableElement constructor = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                                                      .applyFilter(CoreMatchers.IS_CONSTRUCTOR)
                                                                      .getResult().get(0);

                                                              List<? extends StatementTree> statements = ProcessingEnvironmentUtils.getTrees().getTree(constructor).getBody().getStatements();


                                                              MatcherAssert.assertThat("Should return true for explicit constructor that looks like default constructor", BeanUtils.isDefaultNoargConstructor(constructor));
                                                              MatcherAssert.assertThat("Should return true for explicit constructor that looks like default constructor", BeanUtils.hasDefaultNoargsConstructor(element));

                                                          }
                                                      }
                                        )
                                        .build()


                        },
**/

                }

        );


    }


    @Test
    public void test() {
        super.test();
    }
}