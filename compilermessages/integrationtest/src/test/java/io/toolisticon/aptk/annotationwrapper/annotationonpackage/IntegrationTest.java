package io.toolisticon.aptk.annotationwrapper.annotationonpackage;

/**
 * Integration Test to test correctness of generated code
 */
public class IntegrationTest {

    /*-
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

                TestAnnotationWrapper testAnnotationWrapper = TestAnnotationWrapper.wrap(typeElement);

                // check if element is returned correctly
                MatcherAssert.assertThat(testAnnotationWrapper._annotatedElement(), Matchers.is((Element) typeElement));
                MatcherAssert.assertThat(testAnnotationWrapper.annotationAttribute()._annotatedElement(), Matchers.is((Element) typeElement));


                // single attribute values

                MatcherAssert.assertThat(testAnnotationWrapper.charAttribute(), Matchers.is('X'));
                MatcherAssert.assertThat(testAnnotationWrapper.stringAttribute(), Matchers.is("WTF"));
                MatcherAssert.assertThat(testAnnotationWrapper.floatAttribute(), Matchers.is(0.0f));
                MatcherAssert.assertThat(testAnnotationWrapper.doubleAttribute(), Matchers.is(1.0));
                MatcherAssert.assertThat(testAnnotationWrapper.shortAttribute(), Matchers.is((short) 0));
                MatcherAssert.assertThat(testAnnotationWrapper.byteAttribute(), Matchers.is((byte) 0));
                MatcherAssert.assertThat(testAnnotationWrapper.intAttribute(), Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.longAttribute(), Matchers.is(1L));
                MatcherAssert.assertThat(testAnnotationWrapper.enumAttribute(), Matchers.is(TestEnum.TWO));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsFqn(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsTypeMirror().toString(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat(testAnnotationWrapper.typeAttributeAsTypeMirrorWrapper().getQualifiedName(), Matchers.is(String.class.getCanonicalName()));
                MatcherAssert.assertThat((Long) AnnotationUtils.getAnnotationValueOfAttributeWithDefaults(testAnnotationWrapper.annotationAttributeAsAnnotationMirror()).getValue(), Matchers.is(1L));
                MatcherAssert.assertThat(testAnnotationWrapper.annotationAttribute().value(), Matchers.is(1L));

                // array based attribute values
                MatcherAssert.assertThat(testAnnotationWrapper.charArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.stringArrayAttribute(), Matchers.arrayContaining("1", "2", "3"));

                MatcherAssert.assertThat(testAnnotationWrapper.intArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.longArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.shortArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.byteArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.floatArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.doubleArrayAttribute().length, Matchers.is(0));
                MatcherAssert.assertThat(testAnnotationWrapper.booleanArrayAttribute().length, Matchers.is(0));

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
            TestDefaultsAnnotationWrapper wrappedAnnotation = TestDefaultsAnnotationWrapper.wrap(typeElement);
            MatcherAssert.assertThat(wrappedAnnotation.withDefaultIsDefaultValue(), Matchers.is(true));
            MatcherAssert.assertThat(wrappedAnnotation.withoutDefaultIsDefaultValue(), Matchers.is(false));
            }
        })
        .compilationShouldSucceed()
        .executeTest();
    }

    @Test
    public void testCustomCodeForwarding() {
        unitTestBuilder.defineTestWithPassedInElement(TestUsage.class, new APTKUnitTestProcessor<TypeElement>() {
                    @Override
                    public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestAnnotationWrapper wrappedAnnotation = TestAnnotationWrapper.wrap(typeElement);
                MatcherAssert.assertThat(wrappedAnnotation.forwardedMethod("yes"), Matchers.is("it worked : " + "yes"));
                wrappedAnnotation.forwardedMethodWithNoReturnValue("yes");
                wrappedAnnotation.autoDetectedMethod("yes");

            }
        })
        .compilationShouldSucceed()
        .executeTest();
    }

    @Test
    public void testCompilerMessageTriggeredByAnnotationWrapper() {
        unitTestBuilder.defineTestWithPassedInElement(TestUsage.class, new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestAnnotationWrapper wrappedAnnotation = TestAnnotationWrapper.wrap(typeElement);
                wrappedAnnotation.compilerMessage().asNote().write("NOTE");
                wrappedAnnotation.compilerMessage().asWarning().write("WARNING");
                wrappedAnnotation.compilerMessage().asMandatoryWarning().write("MWARNING");
                wrappedAnnotation.compilerMessage().asError().write("ERROR");

            }
        })
        .compilationShouldFail()
        .expectNoteMessage().thatIsEqualTo("NOTE")
        .expectWarningMessage().thatIsEqualTo("WARNING")
        .expectMandatoryWarningMessage().thatIsEqualTo("MWARNING")
        .expectErrorMessage().thatIsEqualTo("ERROR")
        .executeTest();
    }

    @Test
    public void testCompilerMessageTriggeredByAnnotationValueWrapper() {
        unitTestBuilder.defineTestWithPassedInElement(TestUsage.class, new APTKUnitTestProcessor<TypeElement>() {
            @Override
            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement typeElement) {

                // single attribute values
                TestAnnotationWrapper wrappedAnnotation = TestAnnotationWrapper.wrap(typeElement);

                wrappedAnnotation.annotationAttribute().compilerMessage().asNote().write("NOTE");
                wrappedAnnotation.annotationAttribute().compilerMessage().asWarning().write("WARNING");
                wrappedAnnotation.annotationAttribute().compilerMessage().asMandatoryWarning().write("MWARNING");
                wrappedAnnotation.annotationAttribute().compilerMessage().asError().write("ERROR");

            }
        })
        .compilationShouldFail()
        .expectNoteMessage().thatIsEqualTo("NOTE")
        .expectWarningMessage().thatIsEqualTo("WARNING")
        .expectMandatoryWarningMessage().thatIsEqualTo("MWARNING")
        .expectErrorMessage().thatIsEqualTo("ERROR")
        .executeTest();
    }

     */

}
