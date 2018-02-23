package io.toolisticon.annotationprocessortoolkit.tools;

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
 * Unit test for {@link ElementUtils.AccessEnclosedElements}.
 */
public class ElementUtils_AccessEnclosedElementsTest {


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
    public void testGetEnclosedFields_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedFields(null), Matchers.hasSize(0));
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
    public void testGetEnclosedMethods_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedMethods(null), Matchers.hasSize(0));
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
    public void testGetEnclosedConstructors_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedConstructors(null), Matchers.hasSize(0));
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
    public void testGetEnclosedTypes_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedTypes(null), Matchers.hasSize(0));
    }

    // -----------------------------------------
    // -- test getEnclosedElementsByName
    // -----------------------------------------

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
    public void testGetEnclosedElementsByName_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(null, "A", "B"), Matchers.hasSize(0));
    }

    @Test
    public void testGetEnclosedElementsByName_noFilterCriteria() {
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


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsByName(typeElement);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(elements.size()));
    }


    // -----------------------------------------
    // -- test getEnclosedElementsByNameRegex
    // -----------------------------------------

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

    @Test
    public void testGetEnclosedElementsByNameRegex_nullSafety() {
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedElementsByNameRegex(null, "AB.*", "B.*B", ".*B.*"), Matchers.hasSize(0));
    }

    @Test
    public void testGetEnclosedElementsByNameRegex_noFilterCriteria() {
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


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsByNameRegex(typeElement);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(0));
    }


    // -----------------------------------------
    // -- test getEnclosedElementsWithAllAnnotationsOf
    // -----------------------------------------

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
    public void testGetEnclosedElementsWithAllAnnotationsOf_nullSafety() {

        Class<? extends Annotation> annotationClass1ToBeFound = TestAnnotation1ToBeFound.class;
        Class<? extends Annotation> annotationClass2ToBeFound = TestAnnotation2ToBeFound.class;

        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(null, annotationClass1ToBeFound, annotationClass2ToBeFound), Matchers.hasSize(0));

    }

    @Test
    public void testGetEnclosedElementsWithAllAnnotationsOf_noFilterCriteria() {
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


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAllAnnotationsOf(typeElement);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(elements.size()));
    }

    // -----------------------------------------
    // -- test getEnclosedElementsWithAtLeastOneAnnotationOf
    // -----------------------------------------

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

    @Test
    public void testGetEnclosedElementsWithAtLeastOneAnnotationOf_nullSafety() {

        Class<? extends Annotation> annotationClass1ToBeFound = TestAnnotation1ToBeFound.class;
        MatcherAssert.assertThat(ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAtLeastOneAnnotationOf(null, annotationClass1ToBeFound), Matchers.hasSize(0));

    }

    @Test
    public void testGetEnclosedElementsWithAtLeastOneAnnotationOf_noFilterCriteria() {
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


        List<? extends Element> elementWIthNames = ElementUtils.AccessEnclosedElements.getEnclosedElementsWithAtLeastOneAnnotationOf(typeElement);

        MatcherAssert.assertThat(elementWIthNames, Matchers.hasSize(elements.size()));
    }

    @Test
    public void testFlattenEnclosedElementTree_WithNoMaxDepth_WithoutRoot() {


        // prepare test
        Element root = Mockito.mock(TypeElement.class);

        Element field1 = Mockito.mock(VariableElement.class);
        Element field2 = Mockito.mock(VariableElement.class);
        Element field3 = Mockito.mock(VariableElement.class);


        Element method1 = Mockito.mock(ExecutableElement.class);
        Element method2 = Mockito.mock(ExecutableElement.class);
        Element method3 = Mockito.mock(ExecutableElement.class);

        Element method2_parameter1 = Mockito.mock(VariableElement.class);
        Element method2_parameter2 = Mockito.mock(VariableElement.class);
        Element method3_parameter1 = Mockito.mock(VariableElement.class);
        Element method3_parameter2 = Mockito.mock(VariableElement.class);

        List method2EnclosedElements = Utilities.convertVarargsToList(method2_parameter1, method2_parameter2);
        List method3EnclosedElements = Utilities.convertVarargsToList(method3_parameter1, method3_parameter2);

        List rootEnclosedElements = Utilities.convertVarargsToList(field1, field2, field3, method1, method2, method3);


        Mockito.when(method2.getEnclosedElements()).thenReturn(method2EnclosedElements);
        Mockito.when(method3.getEnclosedElements()).thenReturn(method3EnclosedElements);
        Mockito.when(root.getEnclosedElements()).thenReturn(rootEnclosedElements);

        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(root, false);

        // verify
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(field1, field2, field3, method1, method2, method2_parameter1, method2_parameter2, method3, method3_parameter1, method3_parameter2));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(root)));


    }

    @Test
    public void testFlattenEnclosedElementTree_WithNoMaxDepth_WithRoot() {


        // prepare test
        Element root = Mockito.mock(TypeElement.class);

        Element field1 = Mockito.mock(VariableElement.class);
        Element field2 = Mockito.mock(VariableElement.class);
        Element field3 = Mockito.mock(VariableElement.class);


        Element method1 = Mockito.mock(ExecutableElement.class);
        Element method2 = Mockito.mock(ExecutableElement.class);
        Element method3 = Mockito.mock(ExecutableElement.class);

        Element method2_parameter1 = Mockito.mock(VariableElement.class);
        Element method2_parameter2 = Mockito.mock(VariableElement.class);
        Element method3_parameter1 = Mockito.mock(VariableElement.class);
        Element method3_parameter2 = Mockito.mock(VariableElement.class);

        List method2EnclosedElements = Utilities.convertVarargsToList(method2_parameter1, method2_parameter2);
        List method3EnclosedElements = Utilities.convertVarargsToList(method3_parameter1, method3_parameter2);

        List rootEnclosedElements = Utilities.convertVarargsToList(field1, field2, field3, method1, method2, method3);


        Mockito.when(method2.getEnclosedElements()).thenReturn(method2EnclosedElements);
        Mockito.when(method3.getEnclosedElements()).thenReturn(method3EnclosedElements);
        Mockito.when(root.getEnclosedElements()).thenReturn(rootEnclosedElements);

        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(root, true);

        // verify
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(root, field1, field2, field3, method1, method2, method2_parameter1, method2_parameter2, method3, method3_parameter1, method3_parameter2));


    }

    @Test
    public void testFlattenEnclosedElementTree_WithMaxDepth_WithoutRoot() {


        // prepare test
        Element root = Mockito.mock(TypeElement.class);

        Element field1 = Mockito.mock(VariableElement.class);
        Element field2 = Mockito.mock(VariableElement.class);
        Element field3 = Mockito.mock(VariableElement.class);


        Element method1 = Mockito.mock(ExecutableElement.class);
        Element method2 = Mockito.mock(ExecutableElement.class);
        Element method3 = Mockito.mock(ExecutableElement.class);

        Element method2_parameter1 = Mockito.mock(VariableElement.class);
        Element method2_parameter2 = Mockito.mock(VariableElement.class);
        Element method3_parameter1 = Mockito.mock(VariableElement.class);
        Element method3_parameter2 = Mockito.mock(VariableElement.class);

        List method2EnclosedElements = Utilities.convertVarargsToList(method2_parameter1, method2_parameter2);
        List method3EnclosedElements = Utilities.convertVarargsToList(method3_parameter1, method3_parameter2);

        List rootEnclosedElements = Utilities.convertVarargsToList(field1, field2, field3, method1, method2, method3);


        Mockito.when(method2.getEnclosedElements()).thenReturn(method2EnclosedElements);
        Mockito.when(method3.getEnclosedElements()).thenReturn(method3EnclosedElements);
        Mockito.when(root.getEnclosedElements()).thenReturn(rootEnclosedElements);

        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(root, false, 1);

        // verify
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(field1, field2, field3, method1, method2, method3));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(root, method2_parameter1, method2_parameter2, method3_parameter1, method2_parameter2)));


    }

    public void testFlattenEnclosedElementTree_WithMaxDepth_WithRoot() {


        // prepare test
        Element root = Mockito.mock(TypeElement.class);

        Element field1 = Mockito.mock(VariableElement.class);
        Element field2 = Mockito.mock(VariableElement.class);
        Element field3 = Mockito.mock(VariableElement.class);


        Element method1 = Mockito.mock(ExecutableElement.class);
        Element method2 = Mockito.mock(ExecutableElement.class);
        Element method3 = Mockito.mock(ExecutableElement.class);

        Element method2_parameter1 = Mockito.mock(VariableElement.class);
        Element method2_parameter2 = Mockito.mock(VariableElement.class);
        Element method3_parameter1 = Mockito.mock(VariableElement.class);
        Element method3_parameter2 = Mockito.mock(VariableElement.class);

        List method2EnclosedElements = Utilities.convertVarargsToList(method2_parameter1, method2_parameter2);
        List method3EnclosedElements = Utilities.convertVarargsToList(method3_parameter1, method3_parameter2);

        List rootEnclosedElements = Utilities.convertVarargsToList(field1, field2, field3, method1, method2, method3);


        Mockito.when(method2.getEnclosedElements()).thenReturn(method2EnclosedElements);
        Mockito.when(method3.getEnclosedElements()).thenReturn(method3EnclosedElements);
        Mockito.when(root.getEnclosedElements()).thenReturn(rootEnclosedElements);

        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(root, true, 1);

        // verify
        MatcherAssert.assertThat(result, Matchers.containsInAnyOrder(root, field1, field2, field3, method1, method2, method3));
        MatcherAssert.assertThat(result, Matchers.not(Matchers.contains(method2_parameter1, method2_parameter2, method3_parameter1, method2_parameter2)));


    }

    public void testFlattenEnclosedElementTree_WithMaxDepth_nullSafety() {


        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(null, true, 1);

        // verify
        MatcherAssert.assertThat(result, Matchers.<Element>empty());


    }

    public void testFlattenEnclosedElementTree_WithoutMaxDepth_nullSafety() {


        // execute
        List<? extends Element> result = ElementUtils.AccessEnclosedElements.flattenEnclosedElementTree(null, true);

        // verify
        MatcherAssert.assertThat(result, Matchers.<Element>empty());


    }

}
