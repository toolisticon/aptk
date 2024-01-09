package io.toolisticon.aptk.tools.command.impl;


import io.toolisticon.aptk.tools.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.corematcher.AptkCoreMatchers;
import io.toolisticon.aptk.tools.BeanUtils;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Unit Test for {@link GetAttributesCommandWithInheritance}.
 */
public class GetAttributesCommandWithInheritanceTest {

    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    private CompileTestBuilder.UnitTestBuilder unitTestBuilder = CompileTestBuilder
            .unitTest()
            .useSource(JavaFileObjectUtils.readFromResource("/testcases.commands/GetAttributesCommandWithInheritanceTestClass.java"));



    @Test
    public void shouldExecuteSuccessfullyInheritedDataAnnotatedClass() {

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

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

        unitTestBuilder.useProcessor(
                new AbstractUnitTestAnnotationProcessorClass() {
                    @Override
                    protected void testCase(TypeElement element) {

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
