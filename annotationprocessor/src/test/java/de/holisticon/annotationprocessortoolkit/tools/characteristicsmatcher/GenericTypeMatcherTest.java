package de.holisticon.annotationprocessortoolkit.tools.characteristicsmatcher;

import de.holisticon.annotationprocessortoolkit.AbstractAnnotationProcessorTestBaseClass;
import de.holisticon.annotationprocessortoolkit.filter.FluentElementFilter;
import de.holisticon.annotationprocessortoolkit.tools.ElementUtils;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Unit test for {@link GenericTypeMatcher}.
 */
@RunWith(Parameterized.class)
public class GenericTypeMatcherTest extends AbstractAnnotationProcessorTestBaseClass {

    public GenericTypeMatcherTest(String message, AbstractAnnotationProcessorTestBaseClass.AbstractTestAnnotationProcessorClass testcase, boolean compilationShouldSucceed) {
        super(ElementUtils.class.getSimpleName() + ": " + message, testcase, compilationShouldSucceed);
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static List<Object[]> data() {
        return Arrays.asList(

                new Object[][]{


                        {
                                "getStringRepresentationOfPassedCharacteristic : create String representation correctl",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        GenericType genericTypeToConvert = getTypeUtils().GENERICS.createGenericType(Map.class,
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(StringBuilder.class)
                                                ),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Comparator.class,
                                                        getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        List.class,
                                                                        getTypeUtils().GENERICS.createPureWildcard()
                                                                )
                                                        )

                                                )
                                        );


                                        MatcherAssert.assertThat(Matchers.getGenericTypeMatcher(getFrameworkToolWrapper()).getMatcher().getStringRepresentationOfPassedCharacteristic(genericTypeToConvert), org.hamcrest.Matchers.is("java.util.Map<? extends java.lang.StringBuilder, java.util.Comparator<? super java.util.List<?>>>"));


                                    }
                                },
                                true


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Comparator.class,
                                                        getTypeUtils().GENERICS.createGenericType(Long.class)
                                                )
                                        );

                                        MatcherAssert.assertThat("Should compare successful", Matchers.getGenericTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                    }
                                },
                                true


                        },
                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(Map.class,
                                                getTypeUtils().GENERICS.createGenericType(String.class),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Comparator.class,
                                                        getTypeUtils().GENERICS.createGenericType(Double.class)
                                                )

                                        );

                                        MatcherAssert.assertThat("Should not compare successful", !Matchers.getGenericTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(method.getParameters().get(0), genericType));


                                    }
                                },
                                true


                        },

                        {
                                "checkForMatchingCharacteristic : Should be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>

                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(Map.class,
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(StringBuilder.class)
                                                ),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Comparator.class,
                                                        getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        List.class,
                                                                        getTypeUtils().GENERICS.createPureWildcard()
                                                                )
                                                        )

                                                )
                                        );

                                        MatcherAssert.assertThat("Should compare successful", Matchers.getGenericTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));

                                    }
                                },
                                true


                        },

                        {
                                "checkForMatchingCharacteristic : Should not be able to compare generic type with wildcards",
                                new AbstractTestAnnotationProcessorClass() {
                                    @Override
                                    protected void testCase(TypeElement element) {

                                        List<? extends Element> result = FluentElementFilter.createFluentFilter(element.getEnclosedElements())
                                                .applyFilter(Filters.getElementKindFilter()).filterByOneOf(ElementKind.METHOD)
                                                .applyFilter(Filters.getNameFilter()).filterByOneOf("testGenericsOnParameter").getResult();

                                        ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                                        //  Map<? extends StringBuilder, Comparator<? super List<?>>>


                                        GenericType genericType = getTypeUtils().GENERICS.createGenericType(
                                                Map.class,
                                                getTypeUtils().GENERICS.createWildcardWithExtendsBound(
                                                        getTypeUtils().GENERICS.createGenericType(StringBuilder.class)
                                                ),
                                                getTypeUtils().GENERICS.createGenericType(
                                                        Comparator.class,
                                                        getTypeUtils().GENERICS.createWildcardWithSuperBound(
                                                                getTypeUtils().GENERICS.createGenericType(
                                                                        List.class,
                                                                        getTypeUtils().GENERICS.createWildcardWithExtendsBound(getTypeUtils().GENERICS.createGenericType(String.class))
                                                                )
                                                        )

                                                )
                                        );

                                        MatcherAssert.assertThat("Should not compare successful", !Matchers.getGenericTypeMatcher(getFrameworkToolWrapper()).getMatcher().checkForMatchingCharacteristic(method.getParameters().get(1), genericType));


                                    }
                                },
                                true


                        },


                }

        );


    }

}
