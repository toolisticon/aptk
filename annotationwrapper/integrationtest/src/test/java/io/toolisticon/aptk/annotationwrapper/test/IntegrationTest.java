package io.toolisticon.aptk.annotationwrapper.test;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.AnnotationUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.TypeMirrorWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Integration Test to test correctness of generated code
 */
public class IntegrationTest {

    CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder.unitTest();

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);

    }

    @PassIn
    @TestAnnotation(
            stringAttribute = "WTF",
            doubleAttribute = 1.0,
            longAttribute = 1L,
            enumAttribute = TestEnum.TWO,
            typeAttribute = String.class,
            annotationAttribute = @EmbeddedAnnotation(1),
            stringArrayAttribute = {"1", "2", "3"},
            typeArrayAttribute = {Long.class, String.class},
            enumArrayAttribute = {TestEnum.TWO, TestEnum.THREE},
            annotationArrayAttribute = {@EmbeddedAnnotation(1), @EmbeddedAnnotation(2)}

    )
    public static class TestUsage {
    }

    @Test
    public void testWrappedAccess() {
        unitTestBuilder.defineTestWithPassedInElement(TestUsage.class, new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestAnnotationWrapper testAnnotationWrapper = TestAnnotationWrapper.wrapAnnotationOfElement(typeElement);
                MatcherAssert.assertThat(testAnnotationWrapper.stringAttribute(), Matchers.is("WTF"));
                MatcherAssert.assertThat(testAnnotationWrapper.doubleAttribute(), Matchers.is(1.0));
                MatcherAssert.assertThat(testAnnotationWrapper.longAttribute(), Matchers.is(1L));
                MatcherAssert.assertThat(testAnnotationWrapper.enumAttribute(), Matchers.is(TestEnum.TWO));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsFqn(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsTypeMirror().toString(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsTypeMirrorWrapper().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat((Long) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(testAnnotationWrapper.annotationAttributeAsAnnotationMirror()).getValue(), Matchers.is(1L));
                MatcherAssert.assertThat(testAnnotationWrapper.annotationAttribute().value(), Matchers.is(1L));

                // array based attribute values
                MatcherAssert.assertThat(testAnnotationWrapper.stringArrayAttribute(), Matchers.arrayContaining("1", "2", "3"));
                MatcherAssert.assertThat(testAnnotationWrapper.typeArrayAttributeAsFqn(), Matchers.arrayContaining(Long.class.getCanonicalName(), String.class.getCanonicalName()));
                TypeMirror[] typeMirrorArray = testAnnotationWrapper.typeArrayAttributeAsTypeMirror();
                MatcherAssert.assertThat(typeMirrorArray[0].toString(), Matchers.is(Long.class.getCanonicalName()));
                MatcherAssert.assertThat(typeMirrorArray[1].toString(), Matchers.is(String.class.getCanonicalName()));
                TypeMirrorWrapper[] typeMirrorWrapperArray = testAnnotationWrapper.typeArrayAttributeAsTypeMirrorWrapper();
                MatcherAssert.assertThat(typeMirrorWrapperArray[0].getQualifiedName(), Matchers.is(Long.class.getCanonicalName()));
                MatcherAssert.assertThat(typeMirrorWrapperArray[1].getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat(testAnnotationWrapper.enumArrayAttribute(), Matchers.arrayContaining(TestEnum.TWO, TestEnum.THREE));
                AnnotationMirror[] annotationMirrors = testAnnotationWrapper.annotationArrayAttributeAsAnnotationMirrorArray();
                MatcherAssert.assertThat((Long) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirrors[0]).getValue(), Matchers.is(1L));
                MatcherAssert.assertThat((Long) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(annotationMirrors[1]).getValue(), Matchers.is(2L));
                EmbeddedAnnotationWrapper[] embeddedAnnotationWrappers = testAnnotationWrapper.annotationArrayAttribute();
                MatcherAssert.assertThat(embeddedAnnotationWrappers[0].value(), Matchers.is(1L));
                MatcherAssert.assertThat(embeddedAnnotationWrappers[1].value(), Matchers.is(2L));
            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @PassIn
    @TestDefaultsAnnotation(withoutDefault = "ABC")
    public static class DefaultTestCase {

    }

    @Test
    public void testDefaultValueDetection() {
        unitTestBuilder.defineTestWithPassedInElement(DefaultTestCase.class, new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestDefaultsAnnotationWrapper wrappedAnnotation = TestDefaultsAnnotationWrapper.wrapAnnotationOfElement(typeElement);
                MatcherAssert.assertThat(wrappedAnnotation.withDefaultIsDefaultValue(), Matchers.is(true));
                MatcherAssert.assertThat(wrappedAnnotation.withoutDefaultIsDefaultValue(), Matchers.is(false));
            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void testCustomCodeForwarding(){
        unitTestBuilder.defineTestWithPassedInElement(TestUsage.class, new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestAnnotationWrapper wrappedAnnotation = TestAnnotationWrapper.wrapAnnotationOfElement(typeElement);
                MatcherAssert.assertThat(wrappedAnnotation.forwardedMethod("yes"), Matchers.is("it worked : " + "yes"));
                wrappedAnnotation.forwardedMethodWithNoReturnValue("yes");
            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }
}
