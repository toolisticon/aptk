package io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.impl;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.FluentElementFilter;
import io.toolisticon.annotationprocessortoolkit.tools.fluentfilter.TransitionFilters;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

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
                .useProcessor(
                        new AbstractUnitTestAnnotationProcessorClass() {
                            @Override
                            protected void testCase(TypeElement element) {


                                List<Element> list = FluentElementFilter.createFluentElementFilter(element, element).applyTransitionFilter(TransitionFilters.REMOVE_DUPLICATES_ELEMENTS).getResult();
                                MatcherAssert.assertThat(list, Matchers.containsInAnyOrder((Element) element));


                            }
                        })
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationClassAttributeTestClass.java"))
                .compilationShouldSucceed()
                .executeTest();

    }

}
