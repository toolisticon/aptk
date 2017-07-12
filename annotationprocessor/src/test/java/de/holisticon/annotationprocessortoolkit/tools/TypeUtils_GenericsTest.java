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
import java.util.Comparator;
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
                                "TypeUtils.doGenerics().isAssignable : test case 1 - exactly same type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createGenericType(
                                                        Map.class,
                                                        getTypeUtils().doGenerics().createGenericType(String.class),
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignable for exactly the same type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - non matching type parameter inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().doGenerics().createGenericType(String.class),
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect non matching type parameter inbetween", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - pure wildcard for 2nd Map inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Should detect assignability for pure wildcard", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - with super - matching type parameter for super inbetween",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 2 - with extends - matching type parameter for extends inbetween ",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase2")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().doGenerics().createGenericType(String.class),
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a Generic Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and super on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a super wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                Map.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - missing generic types - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                Map.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and invalid extends wildcard  - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                TreeMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a invalid extends wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        // #######################

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().doGenerics().createGenericType(String.class),
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a Generic Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and extends on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a super wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - missing generic types - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and invalid super wildcard  - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                Collection.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a invalid super wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        // #######################

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcare clause on Type Mirror and GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createGenericType(
                                                        HashMap.class,
                                                        getTypeUtils().doGenerics().createGenericType(String.class),
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side can't be assigned to a Generic Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and extends on GenericType - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("pure wildcard on assignee side can't be assigned to a extends wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and pure wildcard - must be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side must be detected as assignable with a pure wildcard Type", getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and  super wildcard - must not be assignable",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = getTypeUtils().doTypeRetrieval().getTypeElement(AbstractTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Map.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                getTypeUtils().doGenerics().createWildcardWithSuperBound(
                                                        getTypeUtils().doGenerics().createGenericType(
                                                                HashMap.class,
                                                                getTypeUtils().doGenerics().createGenericType(String.class),
                                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", !getTypeUtils().doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },


                        {
                                "TypeUtils.doArrays().isArrayOfType : test case 6 - comparison with GenericType - matching",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Comparator.class,
                                                getTypeUtils().doGenerics().createGenericType(String.class)
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType for exactly matching types should return true", getTypeUtils().doArrays().isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : test case 6 - comparison with GenericType - non matching",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Comparator.class,
                                                getTypeUtils().doGenerics().createGenericType(Object.class)
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType for assignable types should return false", !getTypeUtils().doArrays().isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - matching - with wildcard",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Comparator.class,
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(Map.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - matching - with wildcard should return true", getTypeUtils().doArrays().isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.doArrays().isArrayAssignableTo : test case 6 - comparison with GenericType - with wildcard - non matching",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Comparator.class,
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - with wildcard - non matching return false", !getTypeUtils().doArrays().isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - assignable type - with wildcards on both sides",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = getTypeUtils().doGenerics().createGenericType(
                                                Comparator.class,
                                                getTypeUtils().doGenerics().createWildcardWithExtendsBound(
                                                        getTypeUtils().doGenerics().createGenericType(Map.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - assignable type - with wildcards on both sides should return true", getTypeUtils().doArrays().isArrayAssignableTo(method.getParameters().get(2).asType(), genericType));


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