package io.toolisticon.aptk.tools.wrapper;

import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.PassIn;
import io.toolisticon.cute.UnitTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Target;
import java.util.List;
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
            MatcherAssert.assertThat( unit.getEnclosingElement().get().asType().getQualifiedName(), Matchers.is(ElementWrapperTest.class.getCanonicalName()));
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
        MatcherAssert.assertThat(unit.getAllEnclosingElements(true).stream().map(ElementWrapper::unwrap).collect(Collectors.toList()), Matchers.contains(element,packageElement));
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


}
