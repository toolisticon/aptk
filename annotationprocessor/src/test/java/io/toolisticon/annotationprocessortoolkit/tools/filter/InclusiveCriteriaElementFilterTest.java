package io.toolisticon.annotationprocessortoolkit.tools.filter;

import io.toolisticon.annotationprocessortoolkit.AbstractUnitTestAnnotationProcessorClass;
import io.toolisticon.annotationprocessortoolkit.tools.MessagerUtils;
import io.toolisticon.annotationprocessortoolkit.tools.corematcher.CoreMatchers;
import io.toolisticon.cute.CompileTestBuilder;
import io.toolisticon.cute.JavaFileObjectUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;

/**
 * unit test for {@link InclusiveCriteriaElementFilter}.
 */
public class InclusiveCriteriaElementFilterTest {


    @Before
    public void init() {
        MessagerUtils.setPrintMessageCodes(true);
    }

    @Test
    public void findByAll_happyPath() {

        CompileTestBuilder
                .unitTest()
                .useProcessor(new AbstractUnitTestAnnotationProcessorClass() {

                    @Override
                    protected void testCase(TypeElement element) {

                        List<Element> filteredList = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf((List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED);
                        MatcherAssert.assertThat("Must have exactly one element'", filteredList, Matchers.hasSize(1));
                        MatcherAssert.assertThat("Must find one element with name 'synchronizedMethod'", filteredList.get(0).getSimpleName().toString(), Matchers.is("synchronizedMethod"));

                        // shouldn't find anything
                        filteredList = CoreMatchers.BY_MODIFIER.getFilter().filterByAllOf((List<Element>) element.getEnclosedElements(), Modifier.PUBLIC, Modifier.SYNCHRONIZED, Modifier.PROTECTED);
                        MatcherAssert.assertThat("Must have noelement'", filteredList, Matchers.<Element>empty());


                    }
                })
                .useSource(JavaFileObjectUtils.readFromResource("/AnnotationProcessorTestClass.java"))
                .compilationShouldSucceed()
                .executeTest();

    }
}
