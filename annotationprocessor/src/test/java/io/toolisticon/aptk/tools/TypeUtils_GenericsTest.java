package io.toolisticon.aptk.tools;

import io.toolisticon.aptk.tools.corematcher.CoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.generics.GenericType;
import io.toolisticon.aptk.tools.generics.GenericTypeKind;
import io.toolisticon.aptk.tools.generics.GenericTypeWildcard;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Unit test for {@link TypeUtils.Generics}.
 */
public class TypeUtils_GenericsTest {


    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/GenericsTestClass.java"));

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }


    @Test
    public void typeUtils_Generics_createGenericType_simpleNonGenericType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(String.class);

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 0 type parameters", genericType.getTypeParameters().length == 0);


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_simpleNonGenericType2() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(String.class.getCanonicalName());

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 0 type parameters", genericType.getTypeParameters().length == 0);


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_simpleGenericTypeWithOneParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Collection.class,
                        TypeUtils.Generics.createGenericType(String.class)
                );

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[0]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_simpleGenericTypeWithOnePureWildcardTypeParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Collection.class,
                        TypeUtils.Generics.createPureWildcard()
                );

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                MatcherAssert.assertThat("Type parameter shoulb be pure wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).isPureWildcard());

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_simpleGenericTypeWithOneExtendsWildcardTypeParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Collection.class,
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                MatcherAssert.assertThat("Type parameter should be extends bound wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).hasExtendsBound());
                MatcherAssert.assertThat(((GenericTypeWildcard) genericType.getTypeParameters()[0]).getExtendsBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_simpleGenericTypeWithOneSuperWildcardTypeParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Collection.class,
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Collection.class)));
                MatcherAssert.assertThat(genericType.getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat("Should have 1 type parameters", genericType.getTypeParameters().length == 1);
                MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                MatcherAssert.assertThat("Type parameter should be super bound wildcard", ((GenericTypeWildcard) genericType.getTypeParameters()[0]).hasSuperBound());
                MatcherAssert.assertThat(((GenericTypeWildcard) genericType.getTypeParameters()[0]).getSuperBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_createGenericType_morComplexGenericTypeWithTwoTypeParameter() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class,
                                TypeUtils.Generics.createWildcardWithExtendsBound(TypeUtils.Generics.createGenericType(String.class)),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));
                MatcherAssert.assertThat("Should have 2 type parameters", genericType.getTypeParameters().length == 2);
                MatcherAssert.assertThat(genericType.getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[0]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                MatcherAssert.assertThat(genericType.getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));
                MatcherAssert.assertThat("Should have 2 type parameters", genericType.getTypeParameters().length == 2);
                MatcherAssert.assertThat(genericType.getTypeParameters()[1].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(Map.class)));

                MatcherAssert.assertThat("Should have 2 type parameters", ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters().length == 2);

                MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[0].getType(), Matchers.is(GenericTypeKind.WILDCARD));
                MatcherAssert.assertThat(((GenericTypeWildcard) ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[0]).getExtendsBound().getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

                MatcherAssert.assertThat(((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[1].getType(), Matchers.is(GenericTypeKind.GENERIC_TYPE));
                MatcherAssert.assertThat(((GenericType) ((GenericType) genericType.getTypeParameters()[1]).getTypeParameters()[1]).getRawType(), Matchers.is(TypeUtils.TypeRetrieval.getTypeMirror(String.class)));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    // #######################

    @Test
    public void typeUtils_Generics_genericTypeEquals_testNullSafety() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("Should return false if passed typeMirror is null", !TypeUtils.Generics.genericTypeEquals(null, genericType));
                MatcherAssert.assertThat("Should return false if passed genericType is null", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), null));
                MatcherAssert.assertThat("Should return false if passed typeMirror and genericType are null", !TypeUtils.Generics.genericTypeEquals(null, null));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_tc1_matchingType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("Should detect matching types and return true", TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_Generics_genericTypeEquals_tc2_mismatchingType_differentGenericType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class,
                                TypeUtils.Generics.createGenericType(Integer.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("Should detect mismatching types and return false", !TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_genericTypeEquals_tc3_mismatchingType_typeParameterRawTypeVsGenericType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class
                        )
                );


                MatcherAssert.assertThat("Should detect mismatching types and return false", !TypeUtils.Generics.genericTypeEquals(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // #######################

    @Test
    public void typeUtils_Generics_isAssignable_tc1_exactlySameType() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                Map.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("Should detect assignable for exactly the same type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc2_nonMatchingTypeParameterInbetween() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                HashMap.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("Should detect non matching type parameter inbetween", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc3_pureWildcardFor2ndMapInbetween() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createPureWildcard()

                );


                MatcherAssert.assertThat("Should detect assignability for pure wildcard", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc4_withSuperMatchingTypeParameterForSuperInbetween() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase1")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )
                );


                MatcherAssert.assertThat("Should detect assignability for extends case", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc5_withExtends_MatchingTypeParameterForExtendsInbetween() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase2")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )
                );


                MatcherAssert.assertThat("Should detect assignability for extends case", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc6_withExtends_clauseOnTMAndGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                HashMap.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )

                );


                MatcherAssert.assertThat("Extends on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc7_withExtends_clauseOnTMAndSuperOnGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("Extends on assignee side can't be assigned to a super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc8_withExtends_clauseOnTMAndPureWildcard_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {


                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createPureWildcard()

                );


                MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc9_withExtends_clauseOnTMAndValidExtendsWildcard_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        Map.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc10_withExtends_withExtendsClauseOnTMAndValidExtendsWildcard_missingGenericTypes_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        Map.class
                                )
                        )

                );


                MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a valid extends wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc11_withExtends_withExtendsClauseOnTMAndInvalidExtendsWildcard_missingGenericTypes_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase3")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? extends HashMap<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        TreeMap.class
                                )
                        )

                );


                MatcherAssert.assertThat("Extends on assignee side must be detected as assignable with a invalid extends wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // #######################

    @Test
    public void typeUtils_Generics_isAssignable_tc12_withExtends_withSuperClauseOnTypeMirrorAndGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                HashMap.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )

                );


                MatcherAssert.assertThat("Super on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc13_withSuperClauseOnTypeMirrorAndExtendsOnGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("Super on assignee side can't be assigned to a super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc14_withSuperClauseOnTypeMirrorAndPureWildcard_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createPureWildcard()

                );


                MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc15_withSuperClauseOnTypeMirrorAndValidSuperWildcard_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc16_withSuperClauseOnTypeMirrorAndValidSuperWildcard_missingGenericType_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class
                                )
                        )

                );


                MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc17_withSuperClauseOnTypeMirrorAndInvalidSuperWildcard_missingGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase4")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        Collection.class
                                )
                        )

                );


                MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a invalid super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    // #######################
    @Test
    public void typeUtils_Generics_isAssignable_tc18_withPureWildcardClauseOnTypeMirrorAndGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createGenericType(
                                HashMap.class,
                                TypeUtils.Generics.createGenericType(String.class),
                                TypeUtils.Generics.createGenericType(String.class)
                        )

                );


                MatcherAssert.assertThat("Pure wildcard on assignee side can't be assigned to a Generic Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));


            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_Generics_isAssignable_tc19_withPureWildcardClauseOnTypeMirrorAndGenericType_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("pure wildcard on assignee side can't be assigned to a extends wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc20_withPureWildcardClauseOnTypeMirrorAndGenericType_mustBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {
                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createPureWildcard()

                );


                MatcherAssert.assertThat("Pure wildcard on assignee side must be detected as assignable with a pure wildcard Type", TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc21_withPureWildcardClauseOnTypeMirrorAndSuperWildard_mustNotBeAssignable() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase5")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));

                TypeElement typeElement = TypeUtils.TypeRetrieval.getTypeElement(AbstractUnitTestAnnotationProcessorClass.class);


                // Map<String, ? super Map<String, String>>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Map.class,
                        TypeUtils.Generics.createGenericType(String.class),
                        TypeUtils.Generics.createWildcardWithSuperBound(
                                TypeUtils.Generics.createGenericType(
                                        HashMap.class,
                                        TypeUtils.Generics.createGenericType(String.class),
                                        TypeUtils.Generics.createGenericType(String.class)
                                )
                        )

                );


                MatcherAssert.assertThat("Super on assignee side must be detected as assignable with a valid super wildcard Type", !TypeUtils.Generics.genericIsAssignableTo(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc22_comparisionWithGenericType_matching() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                // Comparator<String>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Comparator.class,
                        TypeUtils.Generics.createGenericType(String.class)
                );


                MatcherAssert.assertThat("comparison with GenericType for exactly matching types should return true", TypeUtils.Arrays.isArrayOfType(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


    @Test
    public void typeUtils_Generics_isAssignable_tc23_comparisionWithGenericType_nonMatching() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                // Comparator<String>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Comparator.class,
                        TypeUtils.Generics.createGenericType(Object.class)
                );


                MatcherAssert.assertThat("comparison with GenericType for assignable types should return false", !TypeUtils.Arrays.isArrayOfType(method.getParameters().get(0).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc23_comparisionWithGenericType_matching_withWildcard() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                // Comparator<String>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Comparator.class,
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(Map.class)
                        )
                );


                MatcherAssert.assertThat("comparison with GenericType - matching - with wildcard should return true", TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc24_comparisionWithGenericType_withWildcard_nonMatching() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                // Comparator<String>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Comparator.class,
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(String.class)
                        )
                );


                MatcherAssert.assertThat("comparison with GenericType - with wildcard - non matching return false", !TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(1).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }

    @Test
    public void typeUtils_Generics_isAssignable_tc25_comparisionWithGenericType_assignableType_withWildcardsOnBothSides() {

        unitTestBuilder.useProcessor(new AbstractUnitTestAnnotationProcessorClass() {
            @Override
            protected void testCase(TypeElement element) {

                List<? extends Element> result = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                        .applyFilter(CoreMatchers.BY_ELEMENT_KIND).filterByOneOf(ElementKind.METHOD)
                        .applyFilter(CoreMatchers.BY_NAME).filterByOneOf("isAssignable_testCase6")
                        .getResult();

                ExecutableElement method = ElementUtils.CastElement.castMethod(result.get(0));


                // Comparator<String>
                GenericType genericType = TypeUtils.Generics.createGenericType(
                        Comparator.class,
                        TypeUtils.Generics.createWildcardWithExtendsBound(
                                TypeUtils.Generics.createGenericType(Map.class)
                        )
                );


                MatcherAssert.assertThat("comparison with GenericType - assignable type - with wildcards on both sides should return true", TypeUtils.Arrays.isArrayAssignableTo(method.getParameters().get(2).asType(), genericType));

            }
        })
                .compilationShouldSucceed()
                .executeTest();
    }


}
