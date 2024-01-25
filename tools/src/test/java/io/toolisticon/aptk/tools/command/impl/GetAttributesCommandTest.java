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

/**
 * Unit Test for {@link GetAttributesCommand}.
 */
public class GetAttributesCommandTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilderApi.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/testcases.commands/GetAttributesCommandTestClass.java"));


    @Test
    public void shouldExecuteSuccessfullyWithGetterAndSetterMethod() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.IS_CLASS)
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethods")
                                        .getResult().get(0);

                                BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.createCommand().execute(typeElement);

                                MatcherAssert.assertThat(attributeResult.length, Matchers.is(1));
                                MatcherAssert.assertThat(attributeResult[0].getFieldName(), Matchers.is("field1"));

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }

    @Test
    public void shouldExecuteSuccessfullyWithGetterAndSetterMethodButWithInvalidParameterType() {

        unitTestBuilder.defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                TypeElement typeElement = FluentElementFilter.createFluentElementFilter(element.getEnclosedElements())
                                        .applyFilter(AptkCoreMatchers.IS_CLASS)
                                        .applyFilter(AptkCoreMatchers.BY_REGEX_NAME).filterByOneOf(".*TestFieldGetterAndSetterMethodsWithInvalidSetterParameterType")
                                        .getResult().get(0);

                                BeanUtils.AttributeResult[] attributeResult = GetAttributesCommand.createCommand().execute(typeElement);

                                MatcherAssert.assertThat(attributeResult.length, Matchers.is(0));

                            }
                        })

                .compilationShouldSucceed()
                .executeTest();

    }


}
