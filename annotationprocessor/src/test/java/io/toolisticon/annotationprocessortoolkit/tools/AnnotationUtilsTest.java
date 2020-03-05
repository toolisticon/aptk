package io.toolisticon.annotationprocessortoolkit.tools;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassArrayAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.ClassAttributeAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.annotationutilstestclasses.DefaultValueAnnotation;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.compiletesting.CompileTestBuilder;
import io.toolisticon.compiletesting.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;


/**
 * Integration test for {@link ElementUtils}.
 * <p/>
 * Test is executed at compile time of a test class.
 */
public class AnnotationUtilsTest {

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    // -----------------------------------
    // Single class attribute
    // -----------------------------------


    @Test
    public void annotationUtilsTest_classAttribute_emptyValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_empty")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class), Matchers.nullValue());


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void annotationUtilsTest_classAttribute_StringClassValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_atDefaultValue")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class), Matchers.equalTo(String.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void annotationUtilsTest_classAttributeWithExplicitAttributeName_LongClassValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classAttribute_atNamedAttribute")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(AnnotationUtils.getClassAttributeFromAnnotationAsFqn(testElement, ClassAttributeAnnotation.class, "classAttribute"), Matchers.equalTo(Long.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // -----------------------------------
    // array class attribute
    // -----------------------------------

    @Test
    public void annotationUtilsTest_arrayClassAttribute_emptyValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_empty")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class), Matchers.arrayWithSize(0));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationUtilsTest_arrayClassAttribute_StringDoubleFloatValues() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atDefaultValue")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class)), Matchers.contains(String.class.getCanonicalName(), Double.class.getCanonicalName(), Float.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void annotationUtilsTest_arrayClassAttributeWithExplicitAttributeName_LongIntegerValues() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atNamedAttribute")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class, "classArrayAttribute")), Matchers.contains(Long.class.getCanonicalName(), Integer.class.getCanonicalName()));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void annotationUtilsTest_arrayClassAttributeWithExplicitAttributeName_LongIntegerAnnotationClassAttributeTestClassValues() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ANNOTATION).filterByAllOf(ClassArrayAttributeAnnotation.class)
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .getResult();

                Element testElement = FluentElementFilter.createFluentElementFilter(result)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("test_classArrayAttribute_atNamedAttribute_withUncompiledClass")
                        .getResult().get(0);


                // shouldn't find nonexisting
                MatcherAssert.assertThat(Arrays.asList(AnnotationUtils.getClassArrayAttributeFromAnnotationAsFqn(testElement, ClassArrayAttributeAnnotation.class, "classArrayAttribute")), Matchers.contains(Long.class.getCanonicalName(), Integer.class.getCanonicalName(), "io.toolisticon.annotationprocessor.AnnotationClassAttributeTestClass"));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // --------------------------------------------
    // -- getAnnotationValueOfAttribute
    // --------------------------------------------


    @Test
    public void annotationUtilsTest_getAnnotationValueOfAttribute_getImplicitlySetAnnotationValueMustReturnNull() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // precondition
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);
                MatcherAssert.assertThat(annotationMirror, Matchers.notNullValue());

                // test
                AnnotationValue value = AnnotationUtils.getAnnotationValueOfAttribute(annotationMirror);

                // shouldn't find nonexisting
                MatcherAssert.assertThat(value, Matchers.nullValue());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // --------------------------------------------
    // -- getAnnotationValueOfAttributeWithDefaults
    // --------------------------------------------

    @Test
    public void annotationUtilsTest_getAnnotationValueOfAttributeWithDefaults_getImplicitlySetAnnotationValue_defaultValue() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // precondition
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);
                MatcherAssert.assertThat(annotationMirror, Matchers.notNullValue());

                // test
                AnnotationValue value = AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirror);

                // shouldn't find nonexisting
                MatcherAssert.assertThat((Long) value.getValue(), Matchers.is(5L));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    // --------------------------------------------
    // -- getMandatoryAttributeValueNames
    // --------------------------------------------

    @Test
    public void annotationUtilsTest_getMandatoryAttributeValueNames_getMandatoryAttributeValueNames() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // precondition
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);
                MatcherAssert.assertThat(annotationMirror, Matchers.notNullValue());

                String[] names = AnnotationUtils.getMandatoryAttributeValueNames(annotationMirror);

                // shouldn't find nonexisting
                MatcherAssert.assertThat(Arrays.asList(names), Matchers.contains("mandatoryValue"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // --------------------------------------------
    // -- getOptionalAttributeValueNames
    // --------------------------------------------

    @Test
    public void annotationUtilsTest_getOptionalAttributeValueNames_getOptionalAttributeValueNames() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // precondition
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);
                MatcherAssert.assertThat(annotationMirror, Matchers.notNullValue());

                // test
                String[] names = AnnotationUtils.getOptionalAttributeValueNames(annotationMirror);

                // shouldn't find nonexisting
                MatcherAssert.assertThat(Arrays.asList(names), Matchers.contains("value"));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    // --------------------------------------------
    // -- getElementForAnnotationMirror
    // --------------------------------------------

    @Test
    public void getElementForAnnotationMirror_getElement() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // precondition
                AnnotationMirror annotationMirror = AnnotationUtils.getAnnotationMirror(element, DefaultValueAnnotation.class);
                MatcherAssert.assertThat(annotationMirror, Matchers.notNullValue());

                // test
                TypeElement result = (TypeElement) AnnotationUtils.getElementForAnnotationMirror(annotationMirror);

                MatcherAssert.assertThat(result, Matchers.notNullValue());
                MatcherAssert.assertThat(result.toString(), Matchers.is(DefaultValueAnnotation.class.getCanonicalName()));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

}