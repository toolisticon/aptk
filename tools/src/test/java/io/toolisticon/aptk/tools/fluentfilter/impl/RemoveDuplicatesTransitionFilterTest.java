package io.toolisticon.aptk.tools.fluentfilter.impl;

import io.toolisticon.aptk.cute.APTKUnitTestProcessor;
import io.toolisticon.aptk.tools.MessagerUtils;
import io.toolisticon.aptk.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.aptk.tools.fluentfilter.TransitionFilters;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * Unit test for {@link RemoveDuplicatesTransitionFilter}.
 */
public class RemoveDuplicatesTransitionFilterTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void removeDuplicatesTransitionFilter_testTransitionToRemoveDuplicateValues() {

        CompileTestBuilder.unitTest()
                .defineTest(
                        new APTKUnitTestProcessor<TypeElement>() {
                            @Override
                            public void aptkUnitTest(ProcessingEnvironment processingEnvironment, TypeElement element) {


                                List<Element> list = FluentElementFilter.createFluentElementFilter(element, element).applyTransitionFilter(TransitionFilters.REMOVE_DUPLICATES_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder((Element) element));


                            }
                        })
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"))
                .compilationShouldSucceed()
                .executeTest();

    }

}
