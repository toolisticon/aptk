package io.toolisticon.aptk.tools.matcher.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.TypeUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;


/**
 * Unit test for {@link IsAttributeFieldMatcher}.
 */
public class IsAttributeFieldMatcherTest {

    public static class TestClass {

        private String validAttributeField;
        private static String staticField;
        private String fieldWithoutGetter;
        private String fieldWithoutSetter;


        public String getValidAttributeField() {
            return validAttributeField;
        }

        public void setValidAttributeField(String validAttributeField) {
            this.validAttributeField = validAttributeField;
        }

        public String getFieldWithoutSetter() {
            return fieldWithoutSetter;
        }

        public void setFieldWithoutGetter(String fieldWithoutGetter) {
            this.fieldWithoutGetter = fieldWithoutGetter;
        }
    }


    @Test
    public void test_isAttributeField_match__shouldReturnTrue() {

        CompileTestBuilder
                .unitTest()
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorUnitTestClassInternal.java"))
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                // Do test
                                MatcherAssert.assertThat("Should return true for non static field with getter and setter : ",
                                        AptkCoreMatchers.IS_ATTRIBUTE_FIELD.getMatcher().check(getField("validAttributeField")));


                            }

                        })
                .compilationShouldSucceed()
                .executeTest();
        ;

    }

    @Test
    public void test_isNoAttributeField_noGetter_shouldReturnFalse() {

        CompileTestBuilder
                .unitTest()
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorUnitTestClassInternal.java"))
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // Do test
                                MatcherAssert.assertThat("Should return false for non static field without getter : ",
                                        !AptkCoreMatchers.IS_ATTRIBUTE_FIELD.getMatcher().check(getField("fieldWithoutGetter")));


                            }

                        })
                .compilationShouldSucceed()
                .executeTest();
        ;

    }

    @Test
    public void test_isNoAttributeField_noSetter_shouldReturnFalse() {

        CompileTestBuilder
                .unitTest()
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorUnitTestClassInternal.java"))
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // Do test
                                MatcherAssert.assertThat("Should return false for non static field without setter : ",
                                        !AptkCoreMatchers.IS_ATTRIBUTE_FIELD.getMatcher().check(getField("fieldWithoutSetter")));


                            }

                        })
                .compilationShouldSucceed()
                .executeTest();
        ;

    }

    @Test
    public void test_isNoAttributeField_isStatic_shouldReturnFalse() {

        CompileTestBuilder
                .unitTest()
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorUnitTestClassInternal.java"))
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // Do test
                                MatcherAssert.assertThat("Should return false for static field : ",
                                        !AptkCoreMatchers.IS_ATTRIBUTE_FIELD.getMatcher().check(getField("staticField")));


                            }

                        })
                .compilationShouldSucceed()
                .executeTest();
        ;

    }

    @Test
    public void test_passedNullValue_shouldReturnFalse() {

        CompileTestBuilder
                .unitTest()
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorUnitTestClassInternal.java"))
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                // Do test
                                MatcherAssert.assertThat("Should return false for passed null value : ",
                                        !AptkCoreMatchers.IS_ATTRIBUTE_FIELD.getMatcher().check(null));


                            }

                        })
                .compilationShouldSucceed()
                .executeTest();
        ;

    }


    private static VariableElement getField(String fieldName) {
        List<VariableElement> variableElement = FluentElementFilter.createFluentElementFilter(
                        TypeUtils.TypeRetrieval.getTypeElement(TestClass.class)
                                .getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_FIELD)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(fieldName)
                .getResult();

        // Check precondition
        MatcherAssert.assertThat("PRECONDITION : must have found VariableElement of field '" + fieldName + "' for test", variableElement.size() == 1);

        return variableElement.get(0);
    }


}


