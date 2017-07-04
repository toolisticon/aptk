package de.holisticon.annotationprocessortoolkit.tools;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import de.holisticon.annotationprocessortoolkit.tools.generics.GenericType;
import org.hamcrest.MatcherAssert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Unit test for {@link de.holisticon.annotationprocessortoolkit.tools.TypeUtils.Generics}.
 */
@RunWith(Parameterized.class)
public class TypeUtils_GenericsTest extends AbstractAnnotationProcessorTestBaseClass {


    public TypeUtils_GenericsTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(TypeUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "TypeUtils.GENERICS.isAssignable : test case 1 - exactly same type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Map.class,
                                                        getTypeUtils().GENERICS.createGenericType(String.class),
                                                        getTypeUtils().GENERICS.createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignable for exactly the same type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 1 - non matching type parameter inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().GENERICS.createGenericType(String.class),
                                                        getTypeUtils().GENERICS.createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect non matching type parameter inbetween", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 1 - pure wildcard for 2nd Map inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardPure()

                                        );


                                        MatcherAssert.assertThat("Should detect assignability for pure wildcard", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        {
                                "TypeUtils.GENERICS.isAssignable : test case 1 - with super - matching type parameter for super inbetween",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 2 - with extends - matching type parameter for extends inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase2")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().GENERICS.createGenericType(String.class),
                                                        getTypeUtils().GENERICS.createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a Generic Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and super on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a super wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardPure()

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                Map.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - missing generic types - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                Map.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 3 - with extends clause on Type Mirror and invalid extends wildcard  - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                TreeMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a invalid extends wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        // #######################

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().GENERICS.createGenericType(String.class),
                                                        getTypeUtils().GENERICS.createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a Generic Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and extends on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a super wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardPure()

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - missing generic types - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 4 - with super clause on Type Mirror and invalid super wildcard  - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                Collection.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a invalid super wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        // #######################

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 5 - with pure wildcare clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().GENERICS.createGenericType(String.class),
                                                        getTypeUtils().GENERICS.createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side can't be assigned to a Generic Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and extends on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("pure wildcard on assignee side can't be assigned to a extends wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardPure()

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.GENERICS.isAssignable : test case 5 - with pure wildcard clause on Type Mirror and  super wildcard - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.ELEMENT_KIND_FILTER).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.NAME_FILTER).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().TYPE_RETRIEVAL.getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                        getTypeUtils().GENERICS.createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                                getTypeUtils().GENERICS.createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", !getTypeUtils().GENERICS.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },




                }

        );


    }

    @Override
    protected String getFilenameToCompile() {
        return "GenericsTestClass.java";
    }

}