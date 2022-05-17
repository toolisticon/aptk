package io.toolisticon.aptk.annotationwrapper.processor;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.cute.APTKUnitTestProcessorForTestingAnnotationProcessors;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.corematcher.CoreMatcherValidationMessages;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.wrapper.ExecutableElementWrapper;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.matchers.CoreGeneratedFileObjectMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link AnnotationWrapperProcessor}.
 */
public class AnnotationWrapperProcessorTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void test_wrapperCreation() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withWrappingOfEmbedded/package-info.java")
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotationWrapper", JavaFileObject.Kind.SOURCE)
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.EmbeddedAnnotationWrapper", JavaFileObject.Kind.SOURCE)
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_wrapperCreationWithoutAutoDiscoveryForEmbeddedAnnotations() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withoutWrappingOfEmbedded/package-info.java")
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotationWrapper", JavaFileObject.Kind.SOURCE)
                .expectThatJavaFileObjectDoesntExist(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.EmbeddedAnnotationWrapper", JavaFileObject.Kind.SOURCE)
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withCustomCode/package-info.java", "testcase/withCustomCode/CustomCodeClass.java")
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotationWrapper", JavaFileObject.Kind.SOURCE, CoreGeneratedFileObjectMatchers.createContainsSubstringsMatcher("String shouldWork(String arg1)", "void shouldWorkWithVoidReturnValue(String arg1)"))
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode_withWrongFirstParameterType() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withCustomCodeButWrongFirstParameter/package-info.java", "testcase/withCustomCodeButWrongFirstParameter/CustomCodeClass.java")
                .expectErrorMessage().thatContains(AnnotationWrapperProcessorMessages.ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE.getCode())
                .compilationShouldFail()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode_wrongFirstParameterType() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withCustomCodeButWrongFirstParameter/package-info.java", "testcase/withCustomCodeButWrongFirstParameter/CustomCodeClass.java")
                .expectErrorMessage().thatContains(AnnotationWrapperProcessorMessages.ERROR_FIRST_PARAMETER_OF_CUSTOM_CODE_METHOD_MUST_BE_WRAPPER_TYPE.getCode())
                .compilationShouldFail()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode_nonStaticMethod() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withCustomCodeButNonStaticMethod/package-info.java", "testcase/withCustomCodeButNonStaticMethod/CustomCodeClass.java")
                .expectErrorMessageThatContains(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "static")
                .compilationShouldFail()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode_notVisibleMethod() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java", "testcase/common/EmbeddedAnnotation.java", "testcase/common/TestEnum.java", "testcase/withCustomCodeButNotVisibleMethod/package-info.java", "testcase/withCustomCodeButNotVisibleMethod/CustomCodeClass.java")
                .expectErrorMessageThatContains(CoreMatcherValidationMessages.BY_MODIFIER.getCode(), "private")
                .compilationShouldFail()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomCode_referencingGeneratedClass() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources("testcase/common/TestAnnotation.java",
                        "testcase/common/EmbeddedAnnotation.java",
                        "testcase/common/TestEnum.java",
                        "testcase/withCustomCodeReferencingGeneratedType/TestAnnotation2.java",
                        "testcase/withCustomCodeReferencingGeneratedType/package-info.java",
                        "testcase/withCustomCodeReferencingGeneratedType/CustomCodeClass.java",
                        "testcase/withCustomCodeReferencingGeneratedType/otherTest/package-info.java",
                        "testcase/withCustomCodeReferencingGeneratedType/otherTest/TestAnnotation3.java"

                )
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotationWrapper", JavaFileObject.Kind.SOURCE)
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotation2Wrapper", JavaFileObject.Kind.SOURCE)
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.othertest.TestAnnotation3Wrapper", JavaFileObject.Kind.SOURCE)
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_() {
        CompileTestBuilder.unitTest().<AnnotationWrapperProcessor, TypeElement>defineTestWithPassedInElement(AnnotationWrapperProcessor.class, UnitTestAnnotation.class, new APTKUnitTestProcessorForTestingAnnotationProcessors<AnnotationWrapperProcessor, TypeElement>() {
                    @Override
                    public void aptkUnitTest(AnnotationWrapperProcessor unit, ProcessingEnvironment processingEnvironment, TypeElement typeElement) {


                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_AnnotationToWrap_getSimpleName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest(new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        AnnotationWrapperProcessor.AnnotationToWrap unit = new AnnotationWrapperProcessor.AnnotationToWrap(
                                UnitTestAnnotation.class.getCanonicalName(),
                                new ArrayList<>(),
                                new AnnotationWrapperProcessor.AnnotationWrapperCustomCode(UnitTestAnnotation.class.getCanonicalName()),
                                new ArrayList<>()
                        );

                        MatcherAssert.assertThat(unit.getSimpleName(), Matchers.is(UnitTestAnnotation.class.getSimpleName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_AnnotationToWrap_getQualifiedName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest(new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        AnnotationWrapperProcessor.AnnotationToWrap unit = new AnnotationWrapperProcessor.AnnotationToWrap(
                                UnitTestAnnotation.class.getCanonicalName(),
                                new ArrayList<>(),
                                new AnnotationWrapperProcessor.AnnotationWrapperCustomCode(UnitTestAnnotation.class.getCanonicalName()),
                                new ArrayList<>());

                        MatcherAssert.assertThat(unit.getQualifiedName(), Matchers.is(UnitTestAnnotation.class.getCanonicalName()));

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_AnnotationToWrap_getImports() {
        CompileTestBuilder.unitTest().<TypeElement>defineTest(new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        AnnotationWrapperProcessor.AnnotationAttribute attribute1 = Mockito.mock(AnnotationWrapperProcessor.AnnotationAttribute.class);
                        Mockito.when(attribute1.getImport()).thenReturn(Serializable.class.getCanonicalName());

                        AnnotationWrapperProcessor.AnnotationAttribute attribute2 = Mockito.mock(AnnotationWrapperProcessor.AnnotationAttribute.class);
                        Mockito.when(attribute2.getImport()).thenReturn(Collections.class.getCanonicalName());

                        List<AnnotationWrapperProcessor.AnnotationAttribute> attributes = Arrays.asList(attribute1, attribute2);

                        AnnotationWrapperProcessor.AnnotationToWrap unit = new AnnotationWrapperProcessor.AnnotationToWrap(
                                UnitTestAnnotation.class.getCanonicalName(),
                                attributes,
                                new AnnotationWrapperProcessor.AnnotationWrapperCustomCode(UnitTestAnnotation.class.getCanonicalName()),
                                new ArrayList<>());

                        MatcherAssert.assertThat(unit.getImports(), Matchers.containsInAnyOrder(UnitTestAnnotation.class.getCanonicalName(), Serializable.class.getCanonicalName(), Collections.class.getCanonicalName()));


                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_AnnotationToWrap_getAttributes() {

        AnnotationWrapperProcessor.AnnotationAttribute attribute1 = Mockito.mock(AnnotationWrapperProcessor.AnnotationAttribute.class);
        Mockito.when(attribute1.getImport()).thenReturn(Serializable.class.getCanonicalName());

        AnnotationWrapperProcessor.AnnotationAttribute attribute2 = Mockito.mock(AnnotationWrapperProcessor.AnnotationAttribute.class);
        Mockito.when(attribute2.getImport()).thenReturn(Collections.class.getCanonicalName());

        List<AnnotationWrapperProcessor.AnnotationAttribute> attributes = Arrays.asList(attribute1, attribute2);

        AnnotationWrapperProcessor.AnnotationToWrap unit = new AnnotationWrapperProcessor.AnnotationToWrap(
                UnitTestAnnotation.class.getCanonicalName(),
                attributes,
                new AnnotationWrapperProcessor.AnnotationWrapperCustomCode(UnitTestAnnotation.class.getCanonicalName()),
                new ArrayList<>());

        MatcherAssert.assertThat(unit.getAttributes(), Matchers.is(attributes));

    }


    @PassIn
    @interface UnitTestAnnotation {
        String stringAttribute();

        long longAttribute();

        double doubleAttribute();

        Class<?> typeAttribute();

        TypeKind enumAttribute();

        UnitTestEmbeddedAnnotation annotationAttribute();

        String[] stringArrayAttribute() default {};

        TypeKind[] enumArrayAttribute();

        Class<?>[] typeArrayAttribute();

        UnitTestEmbeddedAnnotation[] annotationArrayAttribute();

    }

    @interface UnitTestEmbeddedAnnotation {
        long value();
    }


    @Test
    public void test_AnnotationToWrap_getAttributeType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getAttributeType(typeElement, "stringAttribute", String.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "longAttribute", long.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "doubleAttribute", double.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "typeAttribute", Class.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "enumAttribute", TypeKind.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "annotationAttribute", UnitTestEmbeddedAnnotation.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "stringArrayAttribute", String.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "enumArrayAttribute", TypeKind.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "typeArrayAttribute", Class.class);
                        test_AnnotationToWrap_getAttributeType(typeElement, "annotationArrayAttribute", UnitTestEmbeddedAnnotation.class);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getAttributeType(TypeElement typeElement, String name, Class<?> expectedType) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.getAttributeType(), Matchers.is(expectedType.getSimpleName()));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_getComponentAttributeType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getComponentAttributeType(typeElement, "stringArrayAttribute", String.class);
                        test_AnnotationToWrap_getComponentAttributeType(typeElement, "enumArrayAttribute", TypeKind.class);
                        test_AnnotationToWrap_getComponentAttributeType(typeElement, "typeArrayAttribute", Class.class);
                        test_AnnotationToWrap_getComponentAttributeType(typeElement, "annotationArrayAttribute", UnitTestEmbeddedAnnotation.class);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getComponentAttributeType(TypeElement typeElement, String name, Class<?> expectedType) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.getComponentAttributeType(), Matchers.is(expectedType.getSimpleName()));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_getImport() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getImport(typeElement, "stringAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "longAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "doubleAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "typeAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "enumAttribute", TypeKind.class.getCanonicalName());
                        test_AnnotationToWrap_getImport(typeElement, "annotationAttribute", UnitTestEmbeddedAnnotation.class.getCanonicalName());
                        test_AnnotationToWrap_getImport(typeElement, "stringArrayAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "enumArrayAttribute", TypeKind.class.getCanonicalName());
                        test_AnnotationToWrap_getImport(typeElement, "typeArrayAttribute", null);
                        test_AnnotationToWrap_getImport(typeElement, "annotationArrayAttribute", UnitTestEmbeddedAnnotation.class.getCanonicalName());

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getImport(TypeElement typeElement, String name, String expectedImport) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.getImport(), Matchers.is(expectedImport));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_getName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getName(typeElement, "stringAttribute");
                        test_AnnotationToWrap_getName(typeElement, "longAttribute");
                        test_AnnotationToWrap_getName(typeElement, "doubleAttribute");
                        test_AnnotationToWrap_getName(typeElement, "typeAttribute");
                        test_AnnotationToWrap_getName(typeElement, "enumAttribute");
                        test_AnnotationToWrap_getName(typeElement, "annotationAttribute");
                        test_AnnotationToWrap_getName(typeElement, "stringArrayAttribute");
                        test_AnnotationToWrap_getName(typeElement, "enumArrayAttribute");
                        test_AnnotationToWrap_getName(typeElement, "typeArrayAttribute");
                        test_AnnotationToWrap_getName(typeElement, "annotationArrayAttribute");

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getName(TypeElement typeElement, String name) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.getName(), Matchers.is(name));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_getWrappedTypeMirror() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "stringAttribute", String.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "longAttribute", long.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "doubleAttribute", double.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "typeAttribute", Class.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "enumAttribute", TypeKind.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "annotationAttribute", UnitTestEmbeddedAnnotation.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "stringArrayAttribute", String.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "enumArrayAttribute", TypeKind.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "typeArrayAttribute", Class.class);
                        test_AnnotationToWrap_getWrappedTypeMirror(typeElement, "annotationArrayAttribute", UnitTestEmbeddedAnnotation.class);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getWrappedTypeMirror(TypeElement typeElement, String name, Class<?> expectedType) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.getWrappedTypeMirror().getQualifiedName(), Matchers.is(expectedType.getCanonicalName()));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isAnnotationType() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isAnnotationType(typeElement, "stringAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "longAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "doubleAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "typeAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "enumAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "annotationAttribute", true);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "stringArrayAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "enumArrayAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "typeArrayAttribute", false);
                        test_AnnotationToWrap_isAnnotationType(typeElement, "annotationArrayAttribute", true);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isAnnotationType(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.isAnnotationType(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isArray() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isArray(typeElement, "stringAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "longAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "doubleAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "typeAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "enumAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "annotationAttribute", false);
                        test_AnnotationToWrap_isArray(typeElement, "stringArrayAttribute", true);
                        test_AnnotationToWrap_isArray(typeElement, "enumArrayAttribute", true);
                        test_AnnotationToWrap_isArray(typeElement, "typeArrayAttribute", true);
                        test_AnnotationToWrap_isArray(typeElement, "annotationArrayAttribute", true);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isArray(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.isArray(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isClass() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isClass(typeElement, "stringAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "longAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "doubleAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "typeAttribute", true);
                        test_AnnotationToWrap_isClass(typeElement, "enumAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "annotationAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "stringArrayAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "enumArrayAttribute", false);
                        test_AnnotationToWrap_isClass(typeElement, "typeArrayAttribute", true);
                        test_AnnotationToWrap_isClass(typeElement, "annotationArrayAttribute", false);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isClass(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.isClass(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isEnum() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isEnum(typeElement, "stringAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "longAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "doubleAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "typeAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "enumAttribute", true);
                        test_AnnotationToWrap_isEnum(typeElement, "annotationAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "stringArrayAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "enumArrayAttribute", true);
                        test_AnnotationToWrap_isEnum(typeElement, "typeArrayAttribute", false);
                        test_AnnotationToWrap_isEnum(typeElement, "annotationArrayAttribute", false);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isEnum(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.isEnum(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isPrimitiveOrString() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "stringAttribute", true);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "longAttribute", true);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "doubleAttribute", true);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "typeAttribute", false);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "enumAttribute", false);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "annotationAttribute", false);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "stringArrayAttribute", true);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "enumArrayAttribute", false);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "typeArrayAttribute", false);
                        test_AnnotationToWrap_isPrimitiveOrString(typeElement, "annotationArrayAttribute", false);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isPrimitiveOrString(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        MatcherAssert.assertThat(unit.isPrimitiveOrString(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_isOptional() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_isOptional(typeElement, "stringAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "longAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "doubleAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "typeAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "enumAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "annotationAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "stringArrayAttribute", true);
                        test_AnnotationToWrap_isOptional(typeElement, "enumArrayAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "typeArrayAttribute", false);
                        test_AnnotationToWrap_isOptional(typeElement, "annotationArrayAttribute", false);

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_isOptional(TypeElement typeElement, String name, boolean expectedResult) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);
        MatcherAssert.assertThat(unit.isOptional(), Matchers.is(expectedResult));

        // make sure that there are no interactions with state
        Mockito.verifyNoInteractions(state);

    }

    @Test
    public void test_AnnotationToWrap_getTargetWrapperAnnotationName() {
        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(UnitTestAnnotation.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                        test_AnnotationToWrap_getTargetWrapperAnnotationName(typeElement, "annotationAttribute", UnitTestEmbeddedAnnotation.class.getCanonicalName());
                        test_AnnotationToWrap_getTargetWrapperAnnotationName(typeElement, "annotationArrayAttribute", UnitTestEmbeddedAnnotation.class.getCanonicalName());

                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }

    private void test_AnnotationToWrap_getTargetWrapperAnnotationName(TypeElement typeElement, String name, String expectedCanonicalName) {

        AnnotationWrapperProcessor.State state = Mockito.mock(AnnotationWrapperProcessor.State.class);
        ExecutableElementWrapper executableElement = getExecutableElement(typeElement, name);

        AnnotationWrapperProcessor.AnnotationAttribute unit = new AnnotationWrapperProcessor.AnnotationAttribute(state, executableElement);

        unit.getTargetWrapperAnnotationName();

        // make sure that there are no interactions with state
        Mockito.verify(state).getWrapperName(Mockito.eq(expectedCanonicalName));

    }

    private ExecutableElementWrapper getExecutableElement(TypeElement typeElement, String name) {
        return ExecutableElementWrapper.wrap(FluentElementFilter.createFluentElementFilter(typeElement.getEnclosedElements())
                .applyFilter(AptkCoreMatchers.IS_METHOD)
                .applyFilter(AptkCoreMatchers.BY_NAME).filterByOneOf(name)
                .getResult().get(0));
    }


    static class CustomCodeClassMethodTestClass {

        @PassIn
        public static <T extends Serializable, X extends InputStream> T wtf(String dropFirst, List<X> list, X type, String anotherOne, Map<? extends T, X> map) {
            return null;
        }

    }

    @Test
    public void test_CustomCodeClassMethod() {
        CompileTestBuilder.unitTest().<ExecutableElement>defineTestWithPassedInElement(CustomCodeClassMethodTestClass.class, new APTKUnitTestProcessor<ExecutableElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, ExecutableElement element) {

                                AnnotationWrapperProcessor.CustomCodeClassMethod unit = new AnnotationWrapperProcessor.CustomCodeClassMethod(ExecutableElementWrapper.wrap(element));
                                String forwardCall = unit.getForwardCall();
                                String methodDeclarationString = unit.getMethodDeclarationString();

                                MatcherAssert.assertThat(forwardCall, Matchers.is("return CustomCodeClassMethodTestClass.wtf(this, list, type, anotherOne, map)"));
                                MatcherAssert.assertThat(methodDeclarationString, Matchers.is("<T, X>T wtf(List<X> list, X type, String anotherOne, Map<? extends T, X> map)"));

                            }
                        }
                )
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void test_wrapperCreation_withCustomInterface() {
        CompileTestBuilder.compilationTest()
                .addProcessors(AnnotationWrapperProcessor.class)
                .addSources(
                        "testcase/withCustomInterface/package-info.java"
                )
                .expectThatJavaFileObjectExists(StandardLocation.SOURCE_OUTPUT, "io.toolisticon.aptk.wrapper.test.TestAnnotationWrapper", JavaFileObject.Kind.SOURCE, CoreGeneratedFileObjectMatchers.createContainsSubstringsMatcher("implements Serializable, Cloneable"))
                .compilationShouldSucceed()
                .executeTest();
    }
}