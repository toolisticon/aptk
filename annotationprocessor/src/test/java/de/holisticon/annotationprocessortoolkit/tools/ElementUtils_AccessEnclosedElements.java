package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.internal.Utilities;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.tools.ElementUtils.AccessEnclosedElements}.
 */
public class ElementUtils_AccessEnclosedElements {


    private <T extends Element> T mockElement(Class<T> type, ElementKind elementKind) {
        T element = Mockito.mock(type);
        Mockito.when(element.getKind()).thenReturn(elementKind);

        return element;
    }

    private <T extends Element> T mockElementWithName(Class<T> type, ElementKind elementKind, String nameStr) {
        T element = mockElement(type, elementKind);

        Name name = Mockito.mock(Name.class);
        Mockito.when(name.toString()).thenReturn(nameStr);

        Mockito.when(element.getSimpleName()).thenReturn(name);

        return element;
    }

    private <T extends Element> T mockElementWithAnnotations(Class<T> type, ElementKind elementKind, Class<? extends Annotation>... annotations) {
        T element = mockElement(type, elementKind);

        for (Class<? extends Annotation> annotation : annotations) {
            Mockito.when(element.getAnnotation(annotation)).thenReturn(Mockito.mock(annotation));
        }

        return element;
    }


    @Test
    public void testGetEnclosedFields() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElement(VariableElement.class, ElementKind.FIELD);
        Element toBeFound2 = mockElement(VariableElement.class, ElementKind.FIELD);

        List elements = Utilities.convertVarargsToList(
                mockElement(VariableElement.class, ElementKind.PARAMETER),
                toBeFound1,
                mockElement(ExecutableElement.class, ElementKind.CONSTRUCTOR),
                mockElement(ExecutableElement.class, ElementKind.METHOD),
                mockElement(TypeElement.class, ElementKind.INTERFACE),
                toBeFound2,
                mockElement(TypeElement.class, ElementKind.CLASS)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends VariableElement> elementOfKind = ElementUtils.AccessEnclosedElements.getEnclosedFields(typeElement);

        MatcherAssert.assertThat(elementOfKind, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementOfKind, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    @Test
    public void testGetEnclosedMethods() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElement(ExecutableElement.class, ElementKind.METHOD);
        Element toBeFound2 = mockElement(ExecutableElement.class, ElementKind.METHOD);

        List elements = Utilities.convertVarargsToList(
                mockElement(VariableElement.class, ElementKind.PARAMETER),
                toBeFound1,
                mockElement(ExecutableElement.class, ElementKind.CONSTRUCTOR),
                mockElement(VariableElement.class, ElementKind.FIELD),
                mockElement(TypeElement.class, ElementKind.INTERFACE),
                toBeFound2,
                mockElement(TypeElement.class, ElementKind.CLASS)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends ExecutableElement> elementOfKind = ElementUtils.AccessEnclosedElements.getEnclosedMethods(typeElement);

        MatcherAssert.assertThat(elementOfKind, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementOfKind, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    @Test
    public void testGetEnclosedConstructors() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElement(ExecutableElement.class, ElementKind.CONSTRUCTOR);
        Element toBeFound2 = mockElement(ExecutableElement.class, ElementKind.CONSTRUCTOR);

        List elements = Utilities.convertVarargsToList(
                mockElement(VariableElement.class, ElementKind.PARAMETER),
                toBeFound1,
                mockElement(ExecutableElement.class, ElementKind.METHOD),
                mockElement(VariableElement.class, ElementKind.FIELD),
                mockElement(TypeElement.class, ElementKind.INTERFACE),
                toBeFound2,
                mockElement(TypeElement.class, ElementKind.CLASS)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends ExecutableElement> elementOfKind = ElementUtils.AccessEnclosedElements.getEnclosedConstructors(typeElement);

        MatcherAssert.assertThat(elementOfKind, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementOfKind, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    @Test
    public void testGetEnclosedTypes() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElement(TypeElement.class, ElementKind.CLASS);
        Element toBeFound2 = mockElement(TypeElement.class, ElementKind.CLASS);

        List elements = Utilities.convertVarargsToList(
                mockElement(VariableElement.class, ElementKind.PARAMETER),
                toBeFound1,
                mockElement(ExecutableElement.class, ElementKind.CONSTRUCTOR),
                mockElement(VariableElement.class, ElementKind.FIELD),
                mockElement(TypeElement.class, ElementKind.INTERFACE),
                toBeFound2,
                mockElement(ExecutableElement.class, ElementKind.METHOD)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends TypeElement> elementOfKind = ElementUtils.AccessEnclosedElements.getEnclosedTypes(typeElement);

        MatcherAssert.assertThat(elementOfKind, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementOfKind, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    @Test
    public void testGetEnclosedElementsByName() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElementWithName(TypeElement.class, ElementKind.CLASS, "A");
        Element toBeFound2 = mockElementWithName(TypeElement.class, ElementKind.CLASS, "B");

        List elements = Utilities.convertVarargsToList(
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                toBeFound1,
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                toBeFound2,
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X")

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement, "A", "B");

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementWIthNames, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    @Test
    public void testGetEnclosedElementsByNameRegex() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);


        Element toBeFound1 = mockElementWithName(TypeElement.class, ElementKind.CLASS, "ABC");
        Element toBeFound2 = mockElementWithName(TypeElement.class, ElementKind.CLASS, "BDB");

        List elements = Utilities.convertVarargsToList(
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                toBeFound1,
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X"),
                toBeFound2,
                mockElementWithName(TypeElement.class, ElementKind.CLASS, "X")

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsByNameRegex(typeElement, "AB.*", "B.*B", ".*B.*");

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementWIthNames, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }


    public @interface TestAnnotation1ToBeFound {

    }

    public @interface TestAnnotation2ToBeFound {

    }

    public @interface TestAnnotation1 {

    }

    public @interface TestAnnotation2 {

    }

    public @interface TestAnnotation3 {

    }

    public @interface TestAnnotation4 {

    }

    @Test
    public void testGetEnclosedElementsWithAllAnnotationsOf() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);

        Class<? extends Annotation> annotationClass1ToBeFound = TestAnnotation1ToBeFound.class;
        Class<? extends Annotation> annotationClass2ToBeFound = TestAnnotation2ToBeFound.class;
        Class<? extends Annotation> annotationClass1 = TestAnnotation1.class;
        Class<? extends Annotation> annotationClass2 = TestAnnotation2.class;
        Class<? extends Annotation> annotationClass3 = TestAnnotation3.class;

        Element toBeFound1 = mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1ToBeFound, annotationClass2ToBeFound);
        Element toBeFound2 = mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1ToBeFound, annotationClass2ToBeFound);


        List elements = Utilities.convertVarargsToList(
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1),
                toBeFound1,
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass2),
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass3),
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1ToBeFound),
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass2ToBeFound),
                toBeFound2,
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(typeElement, annotationClass1ToBeFound, annotationClass2ToBeFound);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementWIthNames, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }

    @Test
    public void testGetEnclosedElementsWithAtLeastOneAnnotationsOf() {

        TypeElement typeElement = Mockito.mock(TypeElement.class);

        Class<? extends Annotation> annotationClass1ToBeFound = TestAnnotation1ToBeFound.class;
        Class<? extends Annotation> annotationClass1 = TestAnnotation1.class;
        Class<? extends Annotation> annotationClass2 = TestAnnotation2.class;
        Class<? extends Annotation> annotationClass3 = TestAnnotation3.class;

        Element toBeFound1 = mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1ToBeFound, annotationClass2);
        Element toBeFound2 = mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1ToBeFound);


        List elements = Utilities.convertVarargsToList(
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass1),
                toBeFound1,
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass2),
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass3),
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS, annotationClass2),
                toBeFound2,
                mockElementWithAnnotations(TypeElement.class, ElementKind.CLASS)

        );

        Mockito.when(typeElement.getEnclosedElements()).thenReturn(elements);


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAtLeastOneAnnotationOf(typeElement, annotationClass1ToBeFound);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(2));
        MatcherAssert.assertThat(elementWIthNames, Matchers.containsInAnyOrder(toBeFound1, toBeFound2));

    }

}
