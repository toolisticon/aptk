package io.toolisticon.annotationprocessortoolkit.tools;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * Unit test for {@link ElementUtils.AccessEnclosingElements}.
 */
public class ElementUtils_AccessEnclosingElementsTest {

    @Test
    public void getFlattenedEnclosingElementsTree_withoutRoot_withoutMaxDepth() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(parameterElement, false);

        // validate
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(methodElement, typeElement, packageElement));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(parameterElement)));


    }

    @Test
    public void getFlattenedEnclosingElementsTree_withRoot_withoutMaxDepth() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(parameterElement, true);

        // validate
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(parameterElement, methodElement, typeElement, packageElement));


    }

    @Test
    public void getFlattenedEnclosingElementsTree_withoutRoot_withMaxDepth() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(parameterElement, false, 2);

        // validate
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(methodElement, typeElement));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(parameterElement, packageElement)));


    }

    @Test
    public void getFlattenedEnclosingElementsTree_withRoot_withMaxDepth() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(parameterElement, true, 2);

        // validate
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(parameterElement, methodElement, typeElement));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(packageElement)));


    }

    @Test
    public void getFlattenedEnclosingElementsTree_withMaxDepth_nullSafety() {
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(null, true, 2);

        // validate
        MatcherAssert.assertThat(result, Matchers.<Element>empty());
    }

    @Test
    public void getFlattenedEnclosingElementsTree_withoutMaxDepth_nullSafety() {
        List<Element> result = ElementUtils.AccessEnclosingElements.getFlattenedEnclosingElementsTree(null, true);

        // validate
        MatcherAssert.assertThat(result, Matchers.<Element>empty());
    }

    @Test
    public void getFirstEnclosingElementOfKind_validUsageWithHitForClassKind() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element embeddedTypeElement = Mockito.mock(TypeElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(methodElement.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(embeddedTypeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(packageElement.getKind()).thenReturn(ElementKind.PACKAGE);


        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(embeddedTypeElement);
        Mockito.when(embeddedTypeElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(parameterElement, ElementKind.CLASS);

        // validate
        MatcherAssert.assertThat(result, Matchers.is(embeddedTypeElement));


    }

    @Test
    public void getFirstEnclosingElementOfKind_validUsageWithHitForMethodKind() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element embeddedTypeElement = Mockito.mock(TypeElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(methodElement.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(embeddedTypeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(packageElement.getKind()).thenReturn(ElementKind.PACKAGE);


        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(embeddedTypeElement);
        Mockito.when(embeddedTypeElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(parameterElement, ElementKind.METHOD);

        // validate
        MatcherAssert.assertThat(result, Matchers.is(methodElement));


    }

    @Test
    public void getFirstEnclosingElementOfKind_validUsageWithHitForPackageKind() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element embeddedTypeElement = Mockito.mock(TypeElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(methodElement.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(embeddedTypeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(packageElement.getKind()).thenReturn(ElementKind.PACKAGE);


        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(embeddedTypeElement);
        Mockito.when(embeddedTypeElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(parameterElement, ElementKind.PACKAGE);

        // validate
        MatcherAssert.assertThat(result, Matchers.is(packageElement));


    }

    @Test
    public void getFirstEnclosingElementOfKind_validUsageWithNoHit() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element embeddedTypeElement = Mockito.mock(TypeElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(methodElement.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(embeddedTypeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(packageElement.getKind()).thenReturn(ElementKind.PACKAGE);


        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(embeddedTypeElement);
        Mockito.when(embeddedTypeElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(parameterElement, ElementKind.CONSTRUCTOR);

        // validate
        MatcherAssert.assertThat(result, Matchers.nullValue());


    }

    @Test
    public void getFirstEnclosingElementOfKind_testNullSafety_element() {

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(null, ElementKind.CONSTRUCTOR);

        // validate
        MatcherAssert.assertThat(result, Matchers.nullValue());


    }

    @Test
    public void getFirstEnclosingElementOfKind_testNullSafety_kind() {

        // Prepare
        Element parameterElement = Mockito.mock(VariableElement.class);
        Element methodElement = Mockito.mock(ExecutableElement.class);
        Element embeddedTypeElement = Mockito.mock(TypeElement.class);
        Element typeElement = Mockito.mock(TypeElement.class);
        Element packageElement = Mockito.mock(PackageElement.class);

        Mockito.when(parameterElement.getKind()).thenReturn(ElementKind.PARAMETER);
        Mockito.when(methodElement.getKind()).thenReturn(ElementKind.METHOD);
        Mockito.when(embeddedTypeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(typeElement.getKind()).thenReturn(ElementKind.CLASS);
        Mockito.when(packageElement.getKind()).thenReturn(ElementKind.PACKAGE);


        Mockito.when(parameterElement.getEnclosingElement()).thenReturn(methodElement);
        Mockito.when(methodElement.getEnclosingElement()).thenReturn(embeddedTypeElement);
        Mockito.when(embeddedTypeElement.getEnclosingElement()).thenReturn(typeElement);
        Mockito.when(typeElement.getEnclosingElement()).thenReturn(packageElement);

        // execute
        Element result = ElementUtils.AccessEnclosingElements.getFirstEnclosingElementOfKind(parameterElement, null);

        // validate
        MatcherAssert.assertThat(result, Matchers.nullValue());


    }

}
