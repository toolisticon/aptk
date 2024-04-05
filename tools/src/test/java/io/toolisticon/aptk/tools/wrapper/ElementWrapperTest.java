package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.common.ToolingProvider;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.corematcher.ValidationMessage;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Unit Test for {@link ElementWrapper}.
 */
public class ElementWrapperTest {

    @Test
    public void test_wrap_and_unwrap() {
        TypeElement te = Mockito.mock(TypeElement.class);

        MatcherAssert.assertThat(ElementWrapper.wrap(te).unwrap(), Matchers.is(te));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_wrap_nullSafety() {
        ElementWrapper.wrap((Element) null);
    }

    @PassIn
    public static class TestClass {

    }

    @Test
    public void test_getPackage() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                MatcherAssert.assertThat(unit.getPackage().getQualifiedName(), Matchers.is(this.getClass().getPackage().getName().toString()));
            }
        }).executeTest();

    }

    @Test
    public void test_getPackageName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                MatcherAssert.assertThat(unit.getPackageName(), Matchers.is(this.getClass().getPackage().getName().toString()));
            }
        }).executeTest();


    }

    @Test
    public void test_hasPackageName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, new UnitTest<TypeElement>() {
            @Override
            public void unitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {
                ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                MatcherAssert.assertThat("Expect package names to match", unit.hasPackageName(this.getClass().getPackage().getName().toString()));
                MatcherAssert.assertThat("Expect package names not to match", !unit.hasPackageName("abc.def"));
                MatcherAssert.assertThat("Expect package names not to match", !unit.hasPackageName(null));
            }
        }).executeTest();

    }

    @Test
    public void test_hasSimplePackageName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Expect package names to match", unit.hasSimplePackageName("wrapper"));
            MatcherAssert.assertThat("Expect package names not to match", !unit.hasSimplePackageName("abc"));
            MatcherAssert.assertThat("Expect package names not to match", !unit.hasSimplePackageName(null));
        }).executeTest();

    }

    @Test
    public void test_getSimpleName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat(unit.getSimpleName(), Matchers.is(TestClass.class.getSimpleName()));
        }).executeTest();

    }


    @Test
    public void test_hasSimpleName() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Expect names to match", unit.hasSimpleName(TestClass.class.getSimpleName()));
            MatcherAssert.assertThat("Expect names not to match", !unit.hasSimpleName("Abc"));
            MatcherAssert.assertThat("Expect names not to match", !unit.hasSimpleName(null));
        }).executeTest();

    }

    @Test
    public void test_hasModifiers() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Expect names to match", unit.hasModifiers(Modifier.PUBLIC, Modifier.STATIC));
            MatcherAssert.assertThat("Expect names not to match", !unit.hasModifiers(Modifier.PRIVATE, Modifier.STATIC));
            MatcherAssert.assertThat("Expect names not to match", !unit.hasModifiers(null));
        }).executeTest();

    }

    @Test
    public void test_getEnclosingElement() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat(unit.getEnclosingElement().get().asType().getQualifiedName(), Matchers.is(ElementWrapperTest.class.getCanonicalName()));
        }).executeTest();

    }

    @Test
    public void test_getAllEnclosingElements() {

        // THIS IS REALLY HARD TO TEST WITH COMPILE TEST SINCE RESULT DIFFERS BASED ON JDK VERSION (Additional unnamed module for Java >=9)
        TypeElement element = Mockito.mock(TypeElement.class);
        PackageElement packageElement = Mockito.mock(PackageElement.class);
        Mockito.when(element.getEnclosingElement()).thenReturn(packageElement);

        ElementWrapper<Element> unit = ElementWrapper.wrap(element);

        MatcherAssert.assertThat(unit.getAllEnclosingElements().stream().map(ElementWrapper::unwrap).collect(Collectors.toList()), Matchers.contains(packageElement));
        MatcherAssert.assertThat(unit.getAllEnclosingElements(true).stream().map(ElementWrapper::unwrap).collect(Collectors.toList()), Matchers.contains(element, packageElement));
    }


    @Test
    public void test_validateWithFluentElementValidator() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Expect names to match", unit.validateWithFluentElementValidator().applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PUBLIC, Modifier.STATIC).justValidate());
            MatcherAssert.assertThat("Expect names not to match", !unit.validateWithFluentElementValidator().applyValidator(AptkCoreMatchers.BY_MODIFIER).hasAllOf(Modifier.PRIVATE, Modifier.STATIC).justValidate());
        }).executeTest();

    }

    @Test
    public void test_validate() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Expect names to match", unit.validate().check(e -> e.hasModifiers(Modifier.PUBLIC, Modifier.STATIC)).validate());
            MatcherAssert.assertThat("Expect names not to match", !unit.validate().check(e -> e.hasModifiers(Modifier.PRIVATE, Modifier.STATIC)).validate());
        }).executeTest();

    }

    @Test
    public void test_getAnnotations() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Should find annotation", unit.getAnnotations(), Matchers.hasSize(1));
        }).executeTest();

    }

    @Test
    public void test_getAnnotation_byFqnString() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Should find annotation", unit.getAnnotation(PassIn.class.getCanonicalName()).isPresent());
            MatcherAssert.assertThat("Should not find annotation", !unit.getAnnotation(Target.class.getCanonicalName()).isPresent());
        }).executeTest();

    }


    @Test
    public void test_asType() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat(unit.asType().getQualifiedName(), Matchers.is(TestClass.class.getCanonicalName()));
        }).executeTest();

    }

    @Test
    public void test_getKind() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat(unit.getKind(), Matchers.is(ElementKind.CLASS));
        }).executeTest();

    }

    @Test
    public void test_getModifiers() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat(unit.getModifiers(), Matchers.containsInAnyOrder(Modifier.PUBLIC, Modifier.STATIC));
        }).executeTest();

    }

    // TODO: Add Tests for:
    /*-

    public ElementWrapper<Element> getEnclosingElement()
    public List<ElementWrapper<Element>> getAllEnclosingElements()
    public List<ElementWrapper<Element>> getAllEnclosingElements(boolean includeWrappedElement)
    public List<ElementWrapper<? extends Element>> getEnclosedElements()
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree()
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf)
    public List<ElementWrapper<Element>> getFlattenedEnclosedElementTree(boolean includeSelf, int maxDepth)

     */

    @Test
    public void test_accept() {
        VariableElement ve = Mockito.spy(VariableElement.class);
        VariableElementWrapper.wrap(ve).accept(null, null);
        Mockito.verify(ve, Mockito.times(1)).accept(null, null);
    }

    @Test
    public void test_getAnnotationsByType() {
        VariableElement ve = Mockito.spy(VariableElement.class);
        VariableElementWrapper.wrap(ve).getAnnotationsByType(PassIn.class);
        Mockito.verify(ve, Mockito.times(1)).getAnnotationsByType(PassIn.class);
    }


    @Test
    public void test_getFirstEnclosingElementWithKind() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);


            MatcherAssert.assertThat(unit.<TypeElementWrapper>getFirstEnclosingElementWithKind(ElementKind.CLASS).get().getQualifiedName(), Matchers.is(ElementWrapperTest.class.getCanonicalName()));
            MatcherAssert.assertThat(unit.<PackageElementWrapper>getFirstEnclosingElementWithKind(ElementKind.PACKAGE).get().getQualifiedName(), Matchers.is(ElementWrapperTest.class.getPackage().getName()));


        }).executeTest();

    }

    @Test
    public void test_getAnnotationMirror() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

            Optional<AnnotationMirrorWrapper> annotationMirrorWrapper = unit.getAnnotationMirror(PassIn.class);
            MatcherAssert.assertThat(annotationMirrorWrapper.get().asElement().getQualifiedName(), Matchers.is(PassIn.class.getCanonicalName()));

            annotationMirrorWrapper = unit.getAnnotationMirror(PassIn.class.getCanonicalName());
            MatcherAssert.assertThat(annotationMirrorWrapper.get().asElement().getQualifiedName(), Matchers.is(PassIn.class.getCanonicalName()));

        }).executeTest();

    }

    @Test
    public void test_getAnnotationMirrors() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            List<AnnotationMirrorWrapper> annotationMirrorWrappers = unit.getAnnotationMirrors();
            MatcherAssert.assertThat(annotationMirrorWrappers, Matchers.hasSize(1));
            MatcherAssert.assertThat(annotationMirrorWrappers.get(0).unwrap().getAnnotationType().toString(), Matchers.is(PassIn.class.getCanonicalName()));
        }).executeTest();

    }

    @Test
    public void test_getAnnotation_byClass() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
            ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
            MatcherAssert.assertThat("Should find annotation", unit.getAnnotation(PassIn.class).isPresent());
            MatcherAssert.assertThat("Shouldn't find annotation", !unit.getAnnotation(Target.class).isPresent());
        }).executeTest();

    }

    @Test
    public void test_compilerMessageTest_NOTE() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage().asNote().write("NOTE");
                        unit.compilerMessage().asNote().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectNoteMessage().thatIsEqualTo("NOTE")
                .expectNoteMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage().asWarning().write("NOTE");
                        unit.compilerMessage().asWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectWarningMessage().thatIsEqualTo("NOTE")
                .expectWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_MANDATORY_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage().asMandatoryWarning().write("NOTE");
                        unit.compilerMessage().asMandatoryWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectMandatoryWarningMessage().thatIsEqualTo("NOTE")
                .expectMandatoryWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_MANDATORY_ERROR() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(TestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage().asError().write("NOTE");
                        unit.compilerMessage().asError().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectErrorMessage().thatIsEqualTo("NOTE")
                .expectErrorMessage().thatIsEqualTo("VM_NOTE")
                .compilationShouldFail()
                .executeTest();

    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface CompilerMessageTestAnnotation {
        String value() default "";
    }

    @CompilerMessageTestAnnotation(value = "abc")
    @PassIn
    static class CompilerMessageTestClass {

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirror_NOTE() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asNote().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asNote().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectNoteMessage().thatIsEqualTo("NOTE")
                .expectNoteMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirror_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asWarning().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectWarningMessage().thatIsEqualTo("NOTE")
                .expectWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirror_MANDATORY_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asMandatoryWarning().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asMandatoryWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectMandatoryWarningMessage().thatIsEqualTo("NOTE")
                .expectMandatoryWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirror_MANDATORY_ERRO() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asError().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap()).asError().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectErrorMessage().thatIsEqualTo("NOTE")
                .expectErrorMessage().thatIsEqualTo("VM_NOTE")
                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void test_compilerMessageTest_withAnnotatioMirrorAndValue_NOTE() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        AnnotationValueWrapper annotationValueWrapper = annotationMirrorWrapper.getAttributeWithDefault();

                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asNote().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asNote().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectNoteMessage().thatIsEqualTo("NOTE")
                .expectNoteMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirrorAndValue_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);

                        AnnotationValueWrapper annotationValueWrapper = annotationMirrorWrapper.getAttributeWithDefault();


                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asWarning().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectWarningMessage().thatIsEqualTo("NOTE")
                .expectWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirrorAndValue_MANDATORY_WARNING() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        AnnotationValueWrapper annotationValueWrapper = annotationMirrorWrapper.getAttributeWithDefault();

                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asMandatoryWarning().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asMandatoryWarning().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectMandatoryWarningMessage().thatIsEqualTo("NOTE")
                .expectMandatoryWarningMessage().thatIsEqualTo("VM_NOTE")
                .executeTest();

    }

    @Test
    public void test_compilerMessageTest_withAnnotatioMirrorAndValue_MANDATORY_ERRO() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);
                    AnnotationMirrorWrapper annotationMirrorWrapper = unit.getAnnotationMirror(CompilerMessageTestAnnotation.class).get();

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        AnnotationValueWrapper annotationValueWrapper = annotationMirrorWrapper.getAttributeWithDefault();

                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asError().write("NOTE");
                        unit.compilerMessage(annotationMirrorWrapper.unwrap(), annotationValueWrapper.unwrap()).asError().write(new ValidationMessage() {
                            @Override
                            public String getCode() {
                                return "";
                            }

                            @Override
                            public String getMessage() {
                                return "VM_NOTE";
                            }
                        });
                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .expectErrorMessage().thatIsEqualTo("NOTE")
                .expectErrorMessage().thatIsEqualTo("VM_NOTE")
                .compilationShouldFail()
                .executeTest();

    }


    @Test
    public void test_toTypeElementWrapper() {

        CompileTestBuilder.unitTest().<TypeElement>defineTestWithPassedInElement(CompilerMessageTestClass.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<TypeElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat(ElementWrapper.toTypeElement(unit), Matchers.notNullValue());


                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();
    }

    public static class MethodTest {

        @PassIn
        public void myMethod() {

        }

    }

    @Test
    public void test_isMethod() {

        CompileTestBuilder.unitTest().<ExecutableElement>defineTestWithPassedInElement(MethodTest.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<ExecutableElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat("Shoulb detected correctly", unit.isMethod());
                        MatcherAssert.assertThat("Shoulb detected correctly", unit.isExecutableElement());

                        // failing validations
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isClass());
                        MatcherAssert.assertThat("Should return false", !unit.isInterface());
                        MatcherAssert.assertThat("Should return false", !unit.isEnum());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotation());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotationAttribute());
                        MatcherAssert.assertThat("Should return false", !unit.isConstructor());
                        MatcherAssert.assertThat("Should return false", !unit.isField());
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isConstructorParameter());
                        MatcherAssert.assertThat("Should return false", !unit.isMethodParameter());

                        MatcherAssert.assertThat("Should return false", !unit.isModuleElement());
                        MatcherAssert.assertThat("Should return false", !unit.isPackageElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeParameterElement());
                        MatcherAssert.assertThat("Should return false", !unit.isVariableElement());

                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void test_toExecutableElementWrapper() {

        CompileTestBuilder.unitTest().<ExecutableElement>defineTestWithPassedInElement(MethodTest.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<ExecutableElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat(ElementWrapper.toExecutableElementWrapper(unit), Matchers.notNullValue());


                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();
    }

    public static class ConstructorTest {

        @PassIn
        public ConstructorTest() {

        }

    }

    @Test
    public void test_isConstructor() {

        CompileTestBuilder.unitTest().<ExecutableElement>defineTestWithPassedInElement(ConstructorTest.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<ExecutableElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat("Shoul detected correctly", unit.isConstructor());
                        MatcherAssert.assertThat("Shoul detected correctly", unit.isExecutableElement());

                        // failing validations
                        MatcherAssert.assertThat("Should return false", !unit.isMethod());
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isClass());
                        MatcherAssert.assertThat("Should return false", !unit.isInterface());
                        MatcherAssert.assertThat("Should return false", !unit.isEnum());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotation());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotationAttribute());
                        MatcherAssert.assertThat("Should return false", !unit.isField());
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isConstructorParameter());
                        MatcherAssert.assertThat("Should return false", !unit.isMethodParameter());

                        MatcherAssert.assertThat("Should return false", !unit.isModuleElement());
                        MatcherAssert.assertThat("Should return false", !unit.isPackageElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeParameterElement());
                        MatcherAssert.assertThat("Should return false", !unit.isVariableElement());

                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();

    }

    public static class FieldTest {

        @PassIn
        private int myField;

    }

    @Test
    public void test_isField() {

        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(FieldTest.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<VariableElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat("Shoulb detected correctly", unit.isField());
                        MatcherAssert.assertThat("Shoulb detected correctly", unit.isVariableElement());

                        // failing validations
                        MatcherAssert.assertThat("Should return false", !unit.isMethod());
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isClass());
                        MatcherAssert.assertThat("Should return false", !unit.isInterface());
                        MatcherAssert.assertThat("Should return false", !unit.isEnum());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotation());
                        MatcherAssert.assertThat("Should return false", !unit.isAnnotationAttribute());
                        MatcherAssert.assertThat("Should return false", !unit.isMethod());
                        MatcherAssert.assertThat("Should return false", !unit.isConstructor());
                        MatcherAssert.assertThat("Should return false", !unit.isPackage());
                        MatcherAssert.assertThat("Should return false", !unit.isConstructorParameter());
                        MatcherAssert.assertThat("Should return false", !unit.isMethodParameter());

                        MatcherAssert.assertThat("Should return false", !unit.isModuleElement());
                        MatcherAssert.assertThat("Should return false", !unit.isPackageElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeElement());
                        MatcherAssert.assertThat("Should return false", !unit.isTypeParameterElement());
                        MatcherAssert.assertThat("Should return false", !unit.isExecutableElement());

                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void test_toVariableElementWrapper() {

        CompileTestBuilder.unitTest().<VariableElement>defineTestWithPassedInElement(FieldTest.class, PassIn.class, (processingEnvironment, element) -> {
                    ElementWrapper<VariableElement> unit = ElementWrapper.wrap(element);

                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        MatcherAssert.assertThat(ElementWrapper.toVariableElementWrapper(unit), Matchers.notNullValue());


                    } finally {
                        ToolingProvider.clearTooling();
                    }

                })
                .compilationShouldSucceed()
                .executeTest();

    }


    static class FlattenedNestedTestClass {

    }
    @Test
    @Ignore
    public void test_filterFlattenedEnclosedElementTree() {
        CompileTestBuilder.unitTest().defineTest((processingEnvironment, element) -> {
                    try {
                        ToolingProvider.setTooling(processingEnvironment);
                        PackageElementWrapper packageElementWrapper = PackageElementWrapper.getByFqn(ElementWrapperTest.class.getPackage().getName()).get();
                        Set<String> types = packageElementWrapper.filterFlattenedEnclosedElementTree().applyFilter(AptkCoreMatchers.IS_TYPE_ELEMENT).getResult().stream().map(e -> e.getQualifiedName().toString()).collect(Collectors.toSet());
                        MatcherAssert.assertThat("Must find this class", types.contains(ElementWrapperTest.class.getCanonicalName()));
                        MatcherAssert.assertThat("Must find nested class", types.contains(FlattenedNestedTestClass.class.getCanonicalName()));
                    }finally {
                        ToolingProvider.clearTooling();
                    }
                })
                .compilationShouldSucceed()
                .executeTest();
    }


}
