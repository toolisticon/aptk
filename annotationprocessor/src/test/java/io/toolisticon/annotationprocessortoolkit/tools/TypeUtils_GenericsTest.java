package io.toolisticon.annotationprocessortoolkit.tools;

import com.google.testing.compile.JavaFileObjects;
import io.toolisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.testhelper.AbstractAnnotationProcessorUnitTest;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfiguration;
import io.toolisticon.annotationprocessortoolkit.testhelper.unittest.AnnotationProcessorUnitTestConfigurationBuilder;
import io.toolisticon.annotationprocessortoolkit.tools.characteristicsfilter.Filters;
import io.toolisticon.annotationprocessortoolkit.tools.generics.GenericType;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Unit test for {@link TypeUtils.Generics}.
 */
@RunWith(Parameterized.class)
public class TypeUtils_GenericsTest extends AbstractAnnotationProcessorUnitTest {


    public TypeUtils_GenericsTest(String message, AnnotationProcessorUnitTestConfiguration configuration) {
        super(configuration);
    }


    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{
                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - exactly same type",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {
                                        
                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
                                        

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createGenericType(
                                                        Map.class,
                                                        typeUtils.doGenerics().createGenericType(String.class),
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignable for exactly the same type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - non matching type parameter inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createGenericType(
                                                        HashMap.class,
                                                        typeUtils.doGenerics().createGenericType(String.class),
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect non matching type parameter inbetween", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - pure wildcard for 2nd Map inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
                                        
                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Should detect assignability for pure wildcard", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },


                        {
                                "TypeUtils.doGenerics().isAssignable : test case 1 - with super - matching type parameter for super inbetween",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
                                        
                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase1")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 2 - with extends - matching type parameter for extends inbetween ",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase2")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )
                                        );


                                        MatcherAssert.assertThat("Should detect assignability for extends case", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createGenericType(
                                                        HashMap.class,
                                                        typeUtils.doGenerics().createGenericType(String.class),
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a Generic Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and super on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side can't be assigned to a super wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a pure wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                Map.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and valid extends wildcard - missing generic types - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                Map.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 3 - with extends clause on Type Mirror and invalid extends wildcard  - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase3")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? extends HashMap<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                TreeMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a invalid extends wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },


                        // #######################

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createGenericType(
                                                        HashMap.class,
                                                        typeUtils.doGenerics().createGenericType(String.class),
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a Generic Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and extends on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side can't be assigned to a super wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a pure wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and valid super wildcard - missing generic types - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 4 - with super clause on Type Mirror and invalid super wildcard  - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase4")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                Collection.class
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a invalid super wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },


                        // #######################

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcare clause on Type Mirror and GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createGenericType(
                                                        HashMap.class,
                                                        typeUtils.doGenerics().createGenericType(String.class),
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side can't be assigned to a Generic Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and extends on GenericType - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
                                        
                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("pure wildcard on assignee side can't be assigned to a extends wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and pure wildcard - must be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createPureWildcard()

                                        );


                                        MatcherAssert.assertThat("Pure wildcard on assignee side must be detected as assignable with a pure wildcard Type", typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },

                        {
                                "TypeUtils.doGenerics().isAssignable : test case 5 - with pure wildcard clause on Type Mirror and  super wildcard - must not be assignable",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase5")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                                        TypeElement typeElement = typeUtils.doTypeRetrieval().getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                                        // Map<String, ? super Map<String, String>>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Map.class,
                                                typeUtils.doGenerics().createGenericType(String.class),
                                                typeUtils.doGenerics().createWildcardWithSuperBound(
                                                        typeUtils.doGenerics().createGenericType(
                                                                HashMap.class,
                                                                typeUtils.doGenerics().createGenericType(String.class),
                                                                typeUtils.doGenerics().createGenericType(String.class)
                                                        )
                                                )

                                        );


                                        MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", !typeUtils.doGenerics().genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },


                        {
                                "TypeUtils.doArrays().isArrayOfType : test case 6 - comparison with GenericType - matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Comparator.class,
                                                typeUtils.doGenerics().createGenericType(String.class)
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType for exactly matching types should return true", typeUtils.doArrays().isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                        )
                                        .build()


                        },
                        {
                                "TypeUtils.doArrays().isArrayOfType : test case 6 - comparison with GenericType - non matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);
                                        
                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Comparator.class,
                                                typeUtils.doGenerics().createGenericType(Object.class)
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType for assignable types should return false", !typeUtils.doArrays().isArrayOfType(method.getParameters().get(0).asType(), genericType));


                                    }
                                }
                                )
                                .build()


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - matching - with wildcard",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Comparator.class,
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(Map.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - matching - with wildcard should return true", typeUtils.doArrays().isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                    }
                                }
                                )
                                .build()


                        },
                        {
                                "TypeUtils.doArrays().isArrayAssignableTo : test case 6 - comparison with GenericType - with wildcard - non matching",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Comparator.class,
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(String.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - with wildcard - non matching return false", !typeUtils.doArrays().isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));


                                    }
                                }
                                )
                                .build()


                        },
                        {
                                "TypeUtils.isArrayAssignableTo(). : test case 6 - comparison with GenericType - assignable type - with wildcards on both sides",
                                AnnotationProcessorUnitTestConfigurationBuilder.createTestConfig()
                                        .compilationShouldSucceed()
                                        .setProcessor(new AbstractUnitTestAnnotationProcessorClass()  {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        TypeUtils typeUtils = TypeUtils.getTypeUtils(processingEnv);

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("isAssignable_testCase6")
                                                .getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        // Comparator<String>
                                        GenericType genericType = typeUtils.doGenerics().createGenericType(
                                                Comparator.class,
                                                typeUtils.doGenerics().createWildcardWithExtendsBound(
                                                        typeUtils.doGenerics().createGenericType(Map.class)
                                                )
                                        );


                                        MatcherAssert.assertThat("comparison with GenericType - assignable type - with wildcards on both sides should return true", typeUtils.doArrays().isArrayAssignableTo(method.getParameters().get(2).asType(), genericType));


                                    }
                                }
                                )
                                .build()


                        },


                }

        );


    }


    @Override
    protected JavaFileObject getSourceFileForCompilation() {
        return JavaFileObjects.forResource("GenericsTestClass.java");
    }

    @Test
    public void test() {
        super.test();
    }

}