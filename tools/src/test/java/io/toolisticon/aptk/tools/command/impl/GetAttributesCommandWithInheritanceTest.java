package io.toolisticon.aptk.tools.command.impl;


import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.BeanUtils;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.CompileTestBuilderApi;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Unit Test for {@link GetAttributesCommandWithInheritance}.
 */
public class GetAttributesCommandWithInheritanceTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/testcases.commands/GetAttributesCommandWithInheritanceTestClass.java"));


    @Test
    public void shouldExecuteSuccessfullyInheritedDataAnnotatedClass() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {

                                TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.IS_CLASS)
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestInheritedDataAnnotatedClass")
                                        .getResult().get(0);
                                BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                                MatcherAssert.assertThat(attributeResult.length, Matchers.is(2));


                                MatcherAssert.assertThat(Arrays.stream(attributeResult).map(e -> e.getFieldName()).collect(Collectors.toSet()), Matchers.containsInAnyOrder("field1", "field3"));

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }


    @Test
    public void shouldExecuteSuccessfullyGetterAndSetterAnnotatedMethodWithInvalidParameterType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.IS_CLASS)
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType")
                                        .getResult().get(0);

                                BeanUtils.AttributeResult[] attributeResult = GetAttributesCommandWithInheritance.INSTANCE.execute(typeElement);

                                MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }


}
